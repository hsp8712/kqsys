
use kqsys;

/* kq_team */
insert into kq_team values(1,'dev','dev team',null);
insert into kq_team values(2,'test','test team',null);

/* kq_user */
insert into kq_user values(1,'CH0001','TOM','tom', '000000',1);
insert into kq_user values(2,'CH0002','JIM',  'jim',   '000000',1);
insert into kq_user values(3,'CH0003','LILY',  'lily',   '000000',2);
insert into kq_user values(4,'CH0004','LUCY','lucy',  '000000',2);

insert into kq_user values(null,'CH0005','AMY','amy',  '000000',null);
insert into kq_user values(null,'CH0006','CHRIS','chris',  '000000',null);

update kq_team set manager_id=1 where id=1;
update kq_team set manager_id=3 where id=2;

/* kq_right */
insert into kq_right values(1,'在线打卡','servlet/CheckinServlet?opertype=view');
insert into kq_right values(2,'组管理','servlet/TeamServlet?opertype=view');
insert into kq_right values(3,'组考勤记录','servlet/DailyRecordServlet?opertype=query');

insert into kq_user_right values(null,1,1);
insert into kq_user_right values(null,2,1);
insert into kq_user_right values(null,3,1);
insert into kq_user_right values(null,4,1);

insert into kq_user_right values(null,1,2);
insert into kq_user_right values(null,3,2);

insert into kq_user_right values(null,1,3);
insert into kq_user_right values(null,3,3);

commit;


