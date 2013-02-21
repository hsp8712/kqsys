package net.oraro.control;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import net.oraro.db.DBUtil;
import net.oraro.service.ServicesFactory;
import net.oraro.service.bean.evt.DailyRecordEvt;
import net.oraro.service.bean.result.Result;
import net.oraro.web.WebTimerTaskManage;

public class ContextListener implements ServletContextListener {
	
	private Logger log = Logger.getLogger(ContextListener.class);
	
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		// 每日统计日考勤记录任务
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.HOUR_OF_DAY, 2);
		cld.set(Calendar.MINUTE, 0);
		cld.set(Calendar.SECOND, 0);
		Date date = cld.getTime();
		WebTimerTaskManage.getInstance().schedule(new TimerTask() {

			@Override
			public void run() {
				
				log.info("Generate daily record.");
				
				// 查询所有用户
				List<Map<String, String>> users = DBUtil.executeQuery("select id from kq_user");
				if(users == null || users.size() <= 0) {
					log.info("Have no users.");
					return;
				}
				
				// 昨天的日期
				Calendar cld = Calendar.getInstance();
				long timeMillis = cld.getTimeInMillis();
				long yestordayTimeMills = timeMillis - (24 * 60 * 60 * 1000);
				java.sql.Date yestorday = new java.sql.Date(yestordayTimeMills);
				
				log.info("Generate daily record date:" + yestorday);
				
				for (Map<String, String> map : users) {
					if(map == null) continue;
					
					String idStr = map.get("id");
					int id = Integer.valueOf(idStr);
					
					DailyRecordEvt evt = new DailyRecordEvt();
					evt.setOpertype(1);
					evt.setUserId(id);
					evt.setRecordDate(yestorday);
					
					Result rst = ServicesFactory.instance().getDailyRecordService().genDailyRecord(evt);
					log.info("result_code=" + rst.getResultCode() + ", result_desc=" + rst.getResultDesc());
				}
			}
		}, date, 24 * 60 * 60 * 1000);
	}

}
