
create database if not exists kqsys character set utf8;

use kqsys;

alter table kq_checkinrecord drop foreign key fk_kq_checkinrecord_1;
alter table kq_dailyrecord   drop foreign key fk_kq_dailyrecord_1;
alter table kq_team          drop foreign key fk_kq_team_1;
alter table kq_user          drop foreign key fk_kq_user_1;
alter table kq_user_right    drop foreign key fk_kq_user_right_2;
alter table kq_user_right    drop foreign key fk_kq_user_right_1;

/* 用户表 */
drop table if exists kq_user;

create table kq_user
(
   id     int       primary key auto_increment,
   empno  varchar(20)	null unique, 				-- 工号
   name     varchar(50)    not null, 				-- 姓名
   account   varchar(50)    not null unique,		-- 登录账号
   password   varchar(100)	not null,				-- 登录密码
   team_id		int	 null							-- 所属组
) engine=innodb;


/* 权限表 */
drop table if exists kq_right;

create table kq_right
(
   id     int       primary key auto_increment, 
   right_name     varchar(50)    not null, 		-- 权限菜单名称
   right_link   varchar(100)    not null		-- 权限菜单链接
	
) engine=innodb;

/* 用户表-权限表:中间表 */
drop table if exists kq_user_right;

create table kq_user_right
(
   id     int       primary key auto_increment, 
   user_id     int    not null,     
   right_id   int    not null      
) engine=innodb;

alter table kq_user_right add constraint fk_kq_user_right_1 foreign key(user_id) references kq_user(id) on delete cascade;
alter table kq_user_right add constraint fk_kq_user_right_2 foreign key(right_id) references kq_right(id) on delete cascade;

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
alter table kq_team add constraint fk_kq_team_1 foreign key(manager_id) references kq_user(id);

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

