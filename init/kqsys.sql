
connect kqsys;

alter table kq_checkinrecord drop foreign key fk_kq_checkinrecord_1;
alter table kq_dailyrecord   drop foreign key fk_kq_dailyrecord_1;
alter table kq_team          drop foreign key fk_kq_team_1;
alter table kq_user          drop foreign key fk_kq_user_1;
alter table kq_user_right    drop foreign key fk_kq_user_right_2;
alter table kq_user_right    drop foreign key fk_kq_user_right_1;

commit;

/* 用户表 */
drop table if exists kq_user;

create table kq_user
(
   id     int       primary key auto_increment,
   empno  varchar(20)	null unique, 
   name     varchar(50)    not null, 
   account   varchar(50)    not null unique,
   password   varchar(100)	not null,
   team_id		int	not null
);


/* 权限表 */
drop table if exists kq_right;

create table kq_right
(
   id     int       primary key auto_increment, 
   right_name     varchar(50)    not null, 
   right_link   varchar(100)    not null
	
);

/* 用户表-权限表:中间表 */
drop table if exists kq_user_right;

create table kq_user_right
(
   id     int       primary key auto_increment, 
   user_id     int    not null,     
   right_id   int    not null      
);

alter table kq_user_right add constraint fk_kq_user_right_1 foreign key(user_id) references kq_user(id) on delete cascade;
alter table kq_user_right add constraint fk_kq_user_right_2 foreign key(right_id) references kq_right(id) on delete cascade;

/* 用户组表 */
drop table if exists kq_team;

create table kq_team
(
   id     int       primary key auto_increment,
   team_name     varchar(50)    not null, 
   description   varchar(100)    null,  
   manager_id   int	 null
);


alter table kq_user add constraint fk_kq_user_1 foreign key(team_id) references kq_team(id);
alter table kq_team add constraint fk_kq_team_1 foreign key(manager_id) references kq_user(id);

/* 日考勤记录 */
drop table if exists kq_dailyrecord;

create table kq_dailyrecord
(
   id     int       primary key auto_increment, 
   user_id     int    not null, 
   record_date   date    not null,
   first_time	datetime null,
   last_time		datetime null,
   over_time	datetime null,	
   over_time_hour  datetime  null
);

alter table kq_dailyrecord add constraint fk_kq_dailyrecord_1 foreign key(user_id) references kq_user(id) on delete cascade;


/* 日考勤记录 */
drop table if exists kq_checkinrecord;

create table kq_checkinrecord
(
   id     int       primary key auto_increment, 
   user_id     int    not null,
   check_time	datetime null
);

alter table kq_checkinrecord add constraint fk_kq_checkinrecord_1 foreign key(user_id) references kq_user(id) on delete cascade;

commit;


