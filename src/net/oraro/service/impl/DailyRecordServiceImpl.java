package net.oraro.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import net.oraro.bean.DailyRecord;
import net.oraro.common.Constants;
import net.oraro.dao.DaoFactory;
import net.oraro.exception.DataAccessException;
import net.oraro.service.DailyRecordService;
import cn.huangshaoping.page.Page;

public class DailyRecordServiceImpl implements DailyRecordService{
	
	private Logger log = Logger.getLogger(DailyRecordServiceImpl.class);
	
	public Page<DailyRecord> getPageDailyRecords(int pageNo) {
		
		List<DailyRecord> dailyRecords = null;
		try {
			dailyRecords = DaoFactory.getInstance().getDailyRecordDao().queryAll();
		} catch (DataAccessException e) {
			log.error(e.getMessage());
		}
		
		return new Page<DailyRecord>(dailyRecords, Constants.PAGE_SIZE, pageNo);
	}

}
