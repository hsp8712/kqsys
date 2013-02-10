
use kqsys;

/* kq_right */
insert into kq_right values(1,'在线打卡','servlet/CheckinServlet?opertype=view');
insert into kq_right values(2,'组管理','servlet/TeamServlet?opertype=view');
insert into kq_right values(3,'组考勤记录','servlet/DailyRecordServlet?opertype=query');
insert into kq_right values(4,'用户管理','servlet/UserServlet?opertype=view');

insert into kq_rightgrp values(1,'超级管理员');
insert into kq_rightgrp values(2,'组管理员');
insert into kq_rightgrp values(3,'员工');

insert into kq_rightgrp_right values(null,1,1);
insert into kq_rightgrp_right values(null,1,2);
insert into kq_rightgrp_right values(null,1,3);
insert into kq_rightgrp_right values(null,1,4);

insert into kq_rightgrp_right values(null,2,1);
insert into kq_rightgrp_right values(null,2,3);

insert into kq_rightgrp_right values(null,3,1);


/* kq_team */
insert into kq_team values(1,'dev','dev team',null);
insert into kq_team values(2,'test','test team',null);

/* kq_user */
insert into kq_user values(1, 'CH0001', 'TOM', 'tom', '000000', 1, 1);
insert into kq_user values(2, 'CH0002', 'JIM', 'jim', '000000', 1, 3);
insert into kq_user values(3, 'CH0003', 'LILY', 'lily', '000000', 2, 2);
insert into kq_user values(4, 'CH0004', 'LUCY', 'lucy', '000000', 2, 3);

insert into kq_user values(null,'CH0005','AMY','amy',  '000000', null, 3);
insert into kq_user values(null,'CH0006','CHRIS','chris',  '000000', null, 3);

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

commit;


