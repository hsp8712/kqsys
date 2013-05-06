package cn.huangshaoping.service;

import cn.huangshaoping.service.impl.CheckInServiceImpl;
import cn.huangshaoping.service.impl.DailyRecordServiceImpl;
import cn.huangshaoping.service.impl.LoginAndOutServiceImpl;
import cn.huangshaoping.service.impl.RightgrpServiceImpl;
import cn.huangshaoping.service.impl.TeamServiceImpl;
import cn.huangshaoping.service.impl.UserServiceImpl;

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
	
	/** 组管理 */
	private TeamService teamService;
	public TeamService getTeamService() {
		if(teamService == null) {
			teamService = new TeamServiceImpl();
		}
		return teamService;
	}
	
	/** 用户管理 */
	private UserService userService;
	public UserService getUserService() {
		if(userService == null) {
			userService = new UserServiceImpl();
		}
		return userService;
	}
	
	/** 权限组管理 */
	private RightgrpService rightgrpService;
	public RightgrpService getRightgrpService() {
		if(rightgrpService == null) {
			rightgrpService = new RightgrpServiceImpl();
		}
		return rightgrpService;
	}
	
}
