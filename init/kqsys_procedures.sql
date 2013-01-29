
drop function if exists kqf_over_hours;
drop procedure if exists kqp_gen_dailyrecord;

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

/*
	生成日考勤记录
*/
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

delimiter ;

commit;
				