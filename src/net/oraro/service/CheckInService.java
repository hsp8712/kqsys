package net.oraro.service;

import java.util.Date;

import net.oraro.bean.User;

/**
 * 打卡服务类
 * @author 6261000301
 *
 */
public interface CheckInService {
	
	
	boolean checkIn(User user, Date date);
	
}
