
drop function  if exists kqf_over_hours;
drop procedure if exists kqp_gen_dailyrecord;
drop procedure if exists kqp_team_manage;
drop procedure if exists kqp_team_mem_manage;
drop procedure if exists kqp_user_manage;
drop procedure if exists kqp_rightgrp_manage;
drop procedure if exists kqp_rightgrp_mem_manage;

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
create procedure kqp_gen_dailyrecord(
	in i_opertype int,					-- 操作类型 1、生成考勤记录；
	in i_user_id int,					-- 用户id
	in i_record_date	date,			-- 日期
	out o_result_code varchar(4)		-- 结果返回码
	)
label_1:begin
	declare v_num int default 0;
	declare v_curdate date;
	declare exit handler for sqlexception begin
		rollback;
		set o_result_code = '1000';		-- 执行过程异常
	end;
	
	-- 入参合法检验
	if i_opertype is null or i_user_id is null or i_record_date is null then
		set o_result_code = '1001';		-- 必选参数为空
		leave label_1;
	end if;
	
	if i_opertype <> 1 then
		set o_result_code = '1002';		-- 参数格式错误
		leave label_1;
	end if;
	
	-- 当前日期
	set v_curdate = curdate();
	
	-- 日期比较
	if date_format(v_curdate, '%Y%m%d') <= date_format(i_record_date, '%Y%m%d') then
		set o_result_code = '6001';		-- 考勤统计日期必须小于当前日期
		leave label_1;
	end if;
	
	start transaction;
	
	-- 判断用户是否存在
	select count(1) into v_num from kq_user where id=i_user_id for update;
	if v_num <= 0 then
		rollback;
		set o_result_code = '3001';			-- 用户不存在
		leave label_1;
	end if;
	
	-- 判断该用户该日期的考勤记录是否存在
	select count(1) into v_num from kq_dailyrecord where user_id=i_user_id and record_date=i_record_date for update;
	if v_num > 0 then
		rollback;
		set o_result_code = '6002';			-- 考勤记录已存在
		leave label_1;
	end if;
	
	select @over_time:=config_value from kq_config where config_name='over_time' limit 1;
	
	-- 无打卡记录
	select count(1) into v_num from kq_checkinrecord where user_id=i_user_id and date(check_time)=i_record_date for update;
	if v_num <= 0 then
		insert into kq_dailyrecord(user_id, record_date, first_time, last_time, over_time, over_time_hour) 
			values(i_user_id, i_record_date, null, null, @over_time, 0);
		commit;
		set o_result_code = '0000';			
		leave label_1;
	end if;
	
	insert into kq_dailyrecord(user_id, record_date, first_time, last_time, over_time, over_time_hour)
		select user_id,
			i_record_date as record_date,
			min(check_time) as first_time,
			max(check_time) as last_time,
			@over_time as overtime,
			kqf_over_hours(@over_time, max(check_time)) as over_time_hour
		from kq_checkinrecord
		where user_id=i_user_id and date(check_time)=i_record_date
		group by user_id;
		
	commit;
	set o_result_code = '0000';			
	leave label_1;
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
		
		select count(1) into v_num from kq_user where id=i_manage_id for update;
		if v_num <= 0 then
			rollback;
			set o_result_code = '3001';			-- 用户不存在
			leave label_1;
		end if;
		
		select team_id into v_num from kq_user where id=i_manage_id;
		if v_num is not null then
			rollback;
			set o_result_code = '2001';			-- 已归属其他组
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
			set o_result_code = '3001';			-- 用户不存在
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

/* 组成员管理 */
create procedure kqp_team_mem_manage(
	in i_opertype int,					-- 操作类型 1、添加一成员；2、删除一成员；
	in i_team_id int,					-- 组id 
	in i_user_id int,					-- 成员用户id
	out o_result_code varchar(4)		-- 结果返回码
	)
label_1:begin
	declare v_num int default 0;
	declare exit handler for sqlexception begin
		rollback;
		set o_result_code = '1000';		-- 执行过程异常
	end;
	
	-- 入参合法检验
	if i_opertype is null or i_team_id is null or i_user_id is null then
		set o_result_code = '1001';		-- 必选参数为空
		leave label_1;
	end if;
	
	if i_opertype not in (1,2) then
		set o_result_code = '1002';		-- 参数格式错误
		leave label_1;
	end if;
	
	start transaction;
	
	select count(1) into v_num from kq_team where id=i_team_id for update;
	if v_num <= 0 then
		rollback;
		set o_result_code = '2002';		-- 组不存在
		leave label_1;
	end if;
	
	select count(1) into v_num from kq_user where id=i_user_id for update;
	if v_num <= 0 then
		rollback;
		set o_result_code = '3001';		-- 用户不存在
		leave label_1;
	end if;
	
	select team_id into v_num from kq_user where id=i_user_id for update;
	if v_num is not null and v_num <> i_team_id then
		rollback;
		set o_result_code = '2001';		-- 用户已归属其他组
		leave label_1;
	end if;
	
	-- 添加
	if i_opertype = 1 then
		if v_num = i_team_id then
			rollback;
			set o_result_code = '2003';		-- 用户已归属当前组
			leave label_1;
		end if;
		update kq_user set team_id=i_team_id where id=i_user_id;
		commit;
		set o_result_code = '0000';		-- 成功
		leave label_1;
	end if;
	
	-- 移除
	if i_opertype = 2 then
		if v_num is null then
			rollback;
			set o_result_code = '2004';		-- 用户不属于当前组
			leave label_1;
		end if;
		
		-- 是否为管理员，是则先置空组的manager_id
		select manager_id into v_num from kq_team where id=i_team_id for update;
		if i_user_id = v_num then
			update kq_team set manager_id=null where id=i_team_id;
		end if;
		
		update kq_user set team_id=null where id=i_user_id;
		commit;
		set o_result_code = '0000';		-- 成功
		leave label_1;
	end if;
	
end //


/*
	用户管理
*/
create procedure kqp_user_manage(
	in i_opertype 	int,					-- 操作类型 1、新增；2、修改；3、删除；4、密码修改
	in i_user_id 	int,					-- 用户id
	in i_empno		varchar(20),			-- 工号
	in i_name		varchar(50),			-- 姓名
	in i_account	varchar(50),			-- 登录账号
	in i_password	varchar(40),			-- 登录密码
	in i_team_id	int,					-- 所属用户组
	in i_rightgrp_id int,					-- 权限组
	out o_user_id 	varchar(4),				-- 用户id
	out o_result_code varchar(4)			-- 结果返回码
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
	
	if i_opertype not in (1,2,3,4) then
		set o_result_code = '1002';		-- 参数格式错误
		leave label_1;
	end if;
	
	start transaction;
	
	if i_opertype <> 1 then
		if i_user_id is null then
			rollback;
			set o_result_code = '1001';		-- 必选参数为空
			leave label_1;
		end if;
		
		select count(1) into v_num from kq_user where id=i_user_id for update;
		if v_num <= 0 then
			rollback;
			set o_result_code = '3001';		-- 用户不存在
			leave label_1;
		end if;
	end if;
	
	if i_opertype not in (3,4) and
		( i_empno is null or i_name is null or i_account is null or i_rightgrp_id is null )  then
		rollback;
		set o_result_code = '1001';		-- 必选参数为空
		leave label_1;
	end if;
	
	-- 新增
	if i_opertype = 1 then
		if i_password is null then
			rollback;
			set o_result_code = '1001';		-- 必选参数为空
			leave label_1;
		end if;
		insert into kq_user(empno, name, account, password, team_id, rightgrp_id) values(i_empno, i_name, i_account, i_password, i_team_id, i_rightgrp_id);
		commit;
		set o_user_id = last_insert_id();
		set o_result_code = '0000';		-- 成功
		leave label_1;
	end if;
	
	-- 修改，所属组不能修改
	if i_opertype = 2 then
		update kq_user set empno=i_empno, name=i_name, account=i_account, team_id=i_team_id, rightgrp_id=i_rightgrp_id where id=i_user_id;
		commit;
		set o_user_id = i_user_id;
		set o_result_code = '0000';		-- 成功
		leave label_1;
	end if;
	
	-- 删除
	if i_opertype = 3 then
		delete from kq_user where id=i_user_id;
		commit;
		set o_user_id = null;
		set o_result_code = '0000';		-- 成功
		leave label_1;
	end if;
	
	-- 修改密码
	if i_opertype = 4 then
		if i_password is null then
			rollback;
			set o_result_code = '1001';		-- 必选参数为空
			leave label_1;
		end if;
		update kq_user set password=i_password where id=i_user_id;
		commit;
		set o_result_code = '0000';		-- 成功
		leave label_1;
	end if;
	
	rollback;
end //

/* 权限组管理 */
create procedure kqp_rightgrp_manage(
	in i_opertype 		int,					-- 操作类型 1、新增；2、修改；3、删除
	in i_rightgrp_id 	int,					-- 权限组id
	in i_rightgrp_name	varchar(50),			-- 姓名
	out o_rightgrp_id	int,					-- 权限组id
	out o_result_code 	varchar(4)				-- 结果返回码
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
	
	start transaction;
	
	if i_opertype <> 1 then
		if i_rightgrp_id is null then
			rollback;
			set o_result_code = '1001';		-- 必选参数为空
			leave label_1;
		end if;
		
		select count(1) into v_num from kq_rightgrp where id=i_rightgrp_id for update;
		if v_num <= 0 then
			rollback;
			set o_result_code = '4001';		-- 权限组不存在
			leave label_1;
		end if;
	end if;
	
	if i_opertype <> 3 and i_rightgrp_name is null then
		rollback;
		set o_result_code = '1001';			-- 必选参数为空
		leave label_1;
	end if;
	
	-- 新增
	if i_opertype = 1 then
		insert into kq_rightgrp(rightgrp_name) values(i_rightgrp_name);
		commit;
		set o_rightgrp_id = last_insert_id();
		set o_result_code = '0000';		-- 成功
		leave label_1;
	end if;
	
	-- 修改，所属组不能修改
	if i_opertype = 2 then
		update kq_rightgrp set rightgrp_name=i_rightgrp_name where id=i_rightgrp_id;
		commit;
		set o_rightgrp_id = i_rightgrp_id;
		set o_result_code = '0000';		-- 成功
		leave label_1;
	end if;
	
	-- 删除
	if i_opertype = 3 then
		delete from kq_rightgrp where id=i_rightgrp_id;
		commit;
		set o_rightgrp_id = null;
		set o_result_code = '0000';		-- 成功
		leave label_1;
	end if;
	
	rollback;
end //

/* 权限组管理 */
create procedure kqp_rightgrp_mem_manage(
	in i_opertype 		int,					-- 操作类型 1、增加一权限；2、移除一权限
	in i_rightgrp_id 	int,					-- 权限组id
	in i_right_id		int,					-- 权限id
	out o_result_code 	varchar(4)				-- 结果返回码
	)
label_1:begin
	declare v_num int default 0;
	declare exit handler for sqlexception begin
		rollback;
		set o_result_code = '1000';		-- 执行过程异常
	end;
	
	-- 入参合法检验
	if i_opertype is null or i_rightgrp_id is null or i_right_id is null then
		set o_result_code = '1001';		-- 必选参数为空
		leave label_1;
	end if;
	
	if i_opertype not in (1,2) then
		set o_result_code = '1002';		-- 参数格式错误
		leave label_1;
	end if;
	
	start transaction;
	select count(1) into v_num from kq_rightgrp where id=i_rightgrp_id for update;
	if v_num <= 0 then
		rollback;
		set o_result_code = '4001';		-- 权限组不存在
		leave label_1;
	end if;
	
	select count(1) into v_num from kq_right where id=i_right_id for update;
	if v_num <= 0 then
		rollback;
		set o_result_code = '5001';		-- 权限不存在
		leave label_1;
	end if;
	
	-- 新增
	if i_opertype = 1 then
		insert into kq_rightgrp_right(rightgrp_id, right_id) values(i_rightgrp_id, i_right_id);
		commit;
		set o_result_code = '0000';		-- 成功
		leave label_1;
	end if;
	
	-- 移除
	if i_opertype = 2 then
		delete from kq_rightgrp_right where rightgrp_id=i_rightgrp_id and right_id=i_right_id;
		commit;
		set o_result_code = '0000';		-- 成功
		leave label_1;
	end if;
	
	rollback;
end //

delimiter ;

