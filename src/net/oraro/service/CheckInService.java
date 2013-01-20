package net.oraro.service;

import java.util.Date;

import net.oraro.bean.User;

/**
 * 打卡服务类
 * @author 6261000301
 *
 */
public interface CheckInService {
	
	/**
	 * 用户指定时间打卡
	 * @param user
	 * @param date
	 * @return
	 */
	boolean checkIn(User user, Date date);
	
	/**
	 * 用户当前系统时间打卡
	 * @param user
	 * @return
	 */
	boolean checkIn(User user);
	
}
