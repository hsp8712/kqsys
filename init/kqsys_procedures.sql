
drop function if exists kqf_over_hours;
drop procedure if exists kqp_gen_dailyrecord;
drop procedure if exists kqp_team_manage;

delimiter //

/* 
	计算加班小时数，精确到0.5，即不满30分钟计0小时，满30分钟不满60分钟计0.5， 
	over_time: 开始加班时间
	last_time: 最后打卡时间
*/
create function kqf_over_hours(over_time time, last_time time) returns decimal(3,1)
begin
	set @hour_o:=hour(over_time),
		@hour_l:=hour(last_time),
		@minute_o:=minute(over_time),
		@minute_l:=minute(last_time);
	
	if over_time>last_time then
		return 0.00;
	end if;
	
	set @hours:=(@hour_l-@hour_o),
		@minutes:=(@minute_l-@minute_o)/30;
		
	if @minutes<-1 then
		set @hours:=@hours-1;
	elseif @minutes>=-1 && @minutes<0 then
		set @hours:=@hours-0.5;
	elseif @minutes>=0 && @minutes<1 then
		set @hours:=@hours+0;
	else
		set @hours:=@hours+0.5;
	end if;
	return @hours;
end //

/* 生成日考勤记录 */
create procedure kqp_gen_dailyrecord()
begin
	select @over_time:=config_value from kq_config where config_name='over_time' limit 1;
	insert into kq_dailyrecord( user_id, record_date, first_time, last_time, over_time, over_time_hour)
		select user_id,
			date_add(curdate(), interval -1 day) as record_date,
			min(check_time) as first_time,
			max(check_time) as last_time,
			@over_time as overtime,
			kqf_over_hours(@over_time, max(check_time)) as over_time_hour
		from kq_checkinrecord
		where date(check_time)=date_add(curdate(), interval -1 day)
		group by user_id;

end //

/* 组管理 */
create procedure kqp_team_manage(
	in i_opertype int,					-- 操作类型 1、新增；2、修改；3、删除；
	in i_team_id int,					-- 组id 
	in i_team_name varchar(50),			-- 组名称 (i_opertype 为1、2时必选)
	in i_description varchar(100),		-- 描述
	in i_manage_id int,					-- 管理员用户id
	out o_result_code varchar(4),		-- 结果返回码
	out o_team_id int					-- 返回组id(i_opertype 为1、2时有效)
	)
label_1:begin
	
	declare v_num int default 0;
	declare exit handler for sqlexception begin
		rollback;
		set o_result_code = '1000';		-- 执行过程异常
	end;
	
	-- 入参合法检验
	if i_opertype is null then
		set o_result_code = '1001';		-- 必选参数为空
		leave label_1;
	end if;
	
	if i_opertype not in (1,2,3) then
		set o_result_code = '1002';		-- 参数格式错误
		leave label_1;
	end if;
	
	if i_opertype <> 1 then
		if i_team_id is null then
			set o_result_code = '1001';		-- 必选参数为空
			leave label_1;
		end if;
		
		select count(1) into v_num from kq_team where id=i_team_id;
		if v_num <= 0 then
			set o_result_code = '2002';		-- 组不存在
			leave label_1;
		end if;
	end if;
	
	if i_opertype <> 3  and i_team_name is null then
		set o_result_code = '1001';		-- 必选参数为空
		leave label_1;
	end if;
	
	-- 新增
	if i_opertype = 1 then
		
		start transaction;
		insert into kq_team(team_name, description) values(i_team_name, i_description);		-- 插入组记录

		-- 将i_manage_id用户加入组，并将组管理员设为该用户
		if i_manage_id is null then
			commit;
			set o_result_code = '0000';		-- 成功
			set o_team_id = last_insert_id();
			leave label_1;			
		end if;
		
		select count(1) into v_num from kq_user where id=i_manage_id and team_id is null for update;
		
		if v_num <= 0 then
			rollback;
			set o_result_code = '2001';			-- 管理员不存在或已归属其他组
			leave label_1;
		end if;
		
		update kq_user set team_id=last_insert_id() where id=i_manage_id;
		update kq_team set manager_id=i_manage_id where id=last_insert_id();
		commit;
		set o_result_code = '0000';				-- 成功
		set o_team_id = last_insert_id();
		leave label_1;
	end if;
	
	-- 修改
	if i_opertype = 2 then
		start transaction;
		
		-- i_manage_id为null
		if i_manage_id is null then
			update kq_team set team_name=i_team_name, description=i_description where id=i_team_id;
			commit;
			set o_result_code = '0000';			-- 成功（不更新管理员）
			set o_team_id = i_team_id;
			leave label_1;
		end if;
		
		-- 判断i_manage_id对应的用户是否为空，或是否归属当前组
		select count(1) into v_num from kq_user where id=i_manage_id for update;
		if v_num <= 0 then
			rollback;
			set o_result_code = '2001';			-- 用户不存在
			leave label_1;
		end if;
		
		select team_id into v_num from kq_user where id=i_manage_id for update;
		if v_num = i_team_id then		-- 归属当前组
			update kq_team set team_name=i_team_name, description=i_description, manager_id=i_manage_id where id=i_team_id;
			commit;
			set o_result_code = '0000';			-- 成功
			set o_team_id = i_team_id;
			leave label_1;
		elseif 	v_num is null then 		-- 用户组为空
			update kq_user set team_id=i_team_id where id=i_manage_id;
			update kq_team set team_name=i_team_name, description=i_description, manager_id=i_manage_id where id=i_team_id;
			commit;
			set o_result_code = '0000';			-- 成功
			set o_team_id = i_team_id;
			leave label_1;
		else
			rollback;
			set o_result_code = '2001';			-- 用户已归属其他组
			leave label_1;
		end if;
	end if;
	
	-- 删除
	if i_opertype = 3 then
		start transaction;
		delete from kq_team where id=i_team_id;
		commit;
		set o_result_code = '0000';
		leave label_1;
	end if;
	
end //

delimiter ;

