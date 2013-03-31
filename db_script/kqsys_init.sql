
use kqsys;

insert into kq_config(config_name, config_value) values('over_time', '18:00:00');

/* kq_right */
insert into kq_right values(1,'在线打卡','servlet/CheckinServlet?opertype=view');
insert into kq_right values(2,'用户管理','servlet/UserServlet?opertype=view');
insert into kq_right values(3,'密码修改','servlet/UserPasswordServlet?opertype=mod_password_view');
insert into kq_right values(4,'权限组管理','servlet/RightgrpServlet?opertype=view');
insert into kq_right values(5,'组管理','servlet/TeamServlet?opertype=view');
insert into kq_right values(6,'我的打卡记录','servlet/MyCheckinRecordServlet?opertype=view_my');
insert into kq_right values(7,'组打卡记录','servlet/CheckinRecordServlet?opertype=view');
insert into kq_right values(8,'组考勤记录','servlet/DailyRecordServlet?opertype=query');

insert into kq_rightgrp values(1,'超级管理员');
insert into kq_rightgrp values(2,'组管理员');
insert into kq_rightgrp values(3,'员工');

insert into kq_rightgrp_right values(null,1,1);
insert into kq_rightgrp_right values(null,1,2);
insert into kq_rightgrp_right values(null,1,3);
insert into kq_rightgrp_right values(null,1,4);
insert into kq_rightgrp_right values(null,1,5);

insert into kq_rightgrp_right values(null,2,1);
insert into kq_rightgrp_right values(null,2,3);
insert into kq_rightgrp_right values(null,2,6);
insert into kq_rightgrp_right values(null,2,7);
insert into kq_rightgrp_right values(null,2,8);

insert into kq_rightgrp_right values(null,3,1);
insert into kq_rightgrp_right values(null,3,3);
insert into kq_rightgrp_right values(null,3,6);


/* kq_team */
insert into kq_team values(1,'dev','dev team',null);
insert into kq_team values(2,'test','test team',null);

/* kq_user */
insert into kq_user values(1, 'CH0000', 'admin', 'admin', '670B14728AD9902AECBA32E22FA4F6BD', null, 1);
insert into kq_user values(2, 'CH0002', 'JIM', 'jim', '670B14728AD9902AECBA32E22FA4F6BD', 1, 3);
insert into kq_user values(3, 'CH0003', 'LILY', 'lily', '670B14728AD9902AECBA32E22FA4F6BD', 2, 2);
insert into kq_user values(4, 'CH0004', 'LUCY', 'lucy', '670B14728AD9902AECBA32E22FA4F6BD', 2, 3);

insert into kq_user values(null,'CH0005','AMY','amy',  '670B14728AD9902AECBA32E22FA4F6BD', null, 3);
insert into kq_user values(null,'CH0006','CHRIS','chris',  '670B14728AD9902AECBA32E22FA4F6BD', null, 3);

update kq_team set manager_id=1 where id=1;
update kq_team set manager_id=3 where id=2;



delete from kq_result_code;

/* 操作结果返回码字典表 */
insert into kq_result_code values('0000', '成功');

insert into kq_result_code values('1000', '执行过程异常');
insert into kq_result_code values('1001', '必选参数为空');
insert into kq_result_code values('1002', '参数格式错误');

insert into kq_result_code values('2001', '用户已归属其他组');
insert into kq_result_code values('2002', '组不存在');
insert into kq_result_code values('2003', '用户已归属当前组');
insert into kq_result_code values('2004', '用户不属于当前组');

insert into kq_result_code values('3001', '用户不存在');
insert into kq_result_code values('3002', '密码错误');

insert into kq_result_code values('4001', '权限组不存在');

insert into kq_result_code values('5001', '权限不存在');

insert into kq_result_code values('6001', '考勤统计日期必须小于当前日期');
insert into kq_result_code values('6002', '考勤记录已存在');

insert into kq_result_code values('7001', '距上次打卡不到两分钟');

commit;


