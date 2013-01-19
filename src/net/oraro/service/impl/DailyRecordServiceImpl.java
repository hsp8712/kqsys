package net.oraro.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.huangshaoping.page.Page;

import net.oraro.bean.DailyRecord;
import net.oraro.common.Constants;
import net.oraro.service.DailyRecordService;

public class DailyRecordServiceImpl implements DailyRecordService{
	
	public Page<DailyRecord> getPageDailyRecords(int pageNo) {
		
		List<DailyRecord> dailyRecords = new ArrayList<DailyRecord>();
		
		DailyRecord dr1 = new DailyRecord();
		Date now = new Date();
		dr1.setDate(now);
		dr1.setLastTime(now);
		dr1.setFirstTime(now);
		dr1.setId(1);
		dr1.setOverTime(now);
		dr1.setOverTimeHour(2);
		
		int i = 0;
		while(i < 15) {
			DailyRecord dr2 = null;
			try {
				dr2 = dr1.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			dailyRecords.add(dr2);
			i++;
		}
		
		int totalRecordCount = 15;
		
//		int from = (pageNo - 1) * Constants.PAGE_SIZE;
//		int to = (pageNo * Constants.PAGE_SIZE) > totalRecordCount ? totalRecordCount : (pageNo * Constants.PAGE_SIZE);
		
		return new Page<DailyRecord>(dailyRecords, Constants.PAGE_SIZE, pageNo, totalRecordCount);
	}

}
