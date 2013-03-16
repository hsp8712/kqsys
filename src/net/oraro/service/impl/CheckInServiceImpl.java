package net.oraro.service.impl;

import java.util.Calendar;
import java.util.Date;

import net.oraro.bean.CheckinRecord;
import net.oraro.bean.User;
import net.oraro.dao.DaoFactory;
import net.oraro.exception.DataAccessException;
import net.oraro.service.CheckInService;

import org.apache.log4j.Logger;

public class CheckInServiceImpl implements CheckInService{
	
	private Logger log = Logger.getLogger(CheckInServiceImpl.class);
	
	public boolean checkIn(User user, Date date) {
		
		if(user == null) {
			throw new NullPointerException("User is null.");
		}
		if(date == null) {
			throw new NullPointerException("Date is null.");
		}
		
		CheckinRecord cr = new CheckinRecord();
		cr.setCheckTime(date);
		cr.setUser(user);
		
		boolean isSuccess = false;
		try {
			isSuccess = DaoFactory.getInstance().getCheckinRecordDao().insert(cr);
		} catch (DataAccessException e) {
			log.error(e.getMessage());
		}
		
		return true;
	}

	public boolean checkIn(User user) {
		
		// 当前时间
		Date date = Calendar.getInstance().getTime();
		return checkIn(user, date);
	}

}
