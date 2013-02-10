
drop database if exists kqsys; 
create database kqsys character set utf8;

use kqsys;

/* 用户表 */
drop table if exists kq_user;
create table kq_user
(
   id     int       primary key auto_increment,
   empno  varchar(20)	null unique, 				-- 工号
   name     varchar(20)    not null, 				-- 姓名
   account   varchar(20)    not null unique,		-- 登录账号
   password   varchar(40)	not null,				-- 登录密码
   team_id		int	 null,							-- 所属组
   rightgrp_id  int not null						-- 权限组
) engine=innodb;


/* 权限表 */
drop table if exists kq_right;

create table kq_right
(
   id     int       primary key auto_increment, 
   right_name     varchar(50)    not null, 		-- 权限菜单名称
   right_link   varchar(100)    not null		-- 权限菜单链接
) engine=innodb;

/* 权限组表 */
drop table if exists kq_rightgrp;
create table kq_rightgrp
(
   id     int       primary key auto_increment, 
   rightgrp_name     varchar(50)    not null 		-- 权限组名称
) engine=innodb;

/* 权限组表-权限表:中间表 */
drop table if exists kq_rightgrp_right;
create table kq_rightgrp_right
(
   id     int       primary key auto_increment, 
   rightgrp_id     int    not null,     		-- 权限组id
   right_id   int    not null      				-- 权限id
) engine=innodb;

alter table kq_rightgrp_right add constraint fk_kq_rightgrp_right_1 foreign key(rightgrp_id) references kq_rightgrp(id) on delete cascade;
alter table kq_rightgrp_right add constraint fk_kq_rightgrp_right_2 foreign key(right_id) references kq_right(id) on delete cascade;

/* 用户组表 */
drop table if exists kq_team;

create table kq_team
(
   id     int       primary key auto_increment,
   team_name     varchar(50)    not null, 	-- 组名称
   description   varchar(100)    null,  	-- 描述
   manager_id   int	 null					-- 管理员用户id
) engine=innodb;


alter table kq_user add constraint fk_kq_user_1 foreign key(team_id) references kq_team(id) on delete set null;
alter table kq_user add constraint fk_kq_user_2 foreign key(rightgrp_id) references kq_rightgrp(id);
alter table kq_team add constraint fk_kq_team_1 foreign key(manager_id) references kq_user(id) on delete set null;

/* 日考勤记录 */
drop table if exists kq_dailyrecord;

create table kq_dailyrecord
(
   id     int       primary key auto_increment, 
   user_id     int    not null, 
   record_date   date    not null,		-- 记录日期
   first_time	time null,				-- 首次打卡时间
   last_time	time null,				-- 最后打卡时间
   over_time	time null,				-- 加班开始时间
   over_time_hour  decimal(3,1) null	-- 加班时长，精确到0.5h
) engine=innodb;

alter table kq_dailyrecord add constraint fk_kq_dailyrecord_1 foreign key(user_id) references kq_user(id) on delete cascade;


/* 日考勤记录 */
drop table if exists kq_checkinrecord;

create table kq_checkinrecord
(
   id     int       primary key auto_increment, 
   user_id     int    not null,			-- 用户id
   check_time	datetime null			-- 打卡日期时间
) engine=innodb;

alter table kq_checkinrecord add constraint fk_kq_checkinrecord_1 foreign key(user_id) references kq_user(id) on delete cascade;

/* 配置项表 */
drop table if exists kq_config;

create table kq_config
(
	id int primary key auto_increment, 
	config_name varchar(50) not null,	-- 配置项名称
	config_value varchar(100) null		-- 配置项值
) engine=innodb;


/* 操作结果返回码字典表 */
drop table if exists kq_result_code;

create table kq_result_code
(
	result_code varchar(4) primary key, -- 结果返回码
	result_desc varchar(200) not null	-- 结果描述
) engine=innodb;

