package net.oraro.factory;

import net.oraro.service.CheckInService;
import net.oraro.service.DailyRecordService;
import net.oraro.service.LoginAndOutService;
import net.oraro.service.impl.CheckInServiceImpl;
import net.oraro.service.impl.DailyRecordServiceImpl;
import net.oraro.service.impl.LoginAndOutServiceImpl;

/**
 * 单例服务工厂
 * @author 6261000301
 *
 */
public class ServicesFactory {
	
	private static ServicesFactory factory;
	private ServicesFactory() {}
	
	public static ServicesFactory instance() {
		if(factory == null) {
			factory = new ServicesFactory();
		}
		return factory;
	}
	
	/** 登录注销服务 */
	private LoginAndOutService loginAndOutService;
	public LoginAndOutService getLoginAndOutService() {
		if(loginAndOutService == null) {
			loginAndOutService = new LoginAndOutServiceImpl();
		}
		return loginAndOutService;
	}
	
	/** 打卡服务 */
	private CheckInService checkInService;
	public CheckInService getCheckInService() {
		if(checkInService == null) {
			checkInService = new CheckInServiceImpl();
		}
		return checkInService;
	}
	
	/** 日考勤记录服务 */
	private DailyRecordService dailyRecordService;
	public DailyRecordService getDailyRecordService() {
		if(dailyRecordService == null) {
			dailyRecordService = new DailyRecordServiceImpl();
		}
		return dailyRecordService;
	}
	
	
}
