package net.oraro.bean;

import java.util.Date;

/**
 * 打卡记录
 * @author 6261000301
 *
 */
public class CheckinRecord {
	private Integer id;			// id
	private User user;			// 用户
	private Date checkTime;		// 打卡时间
	
	private Integer getId() {
		return id;
	}
	private void setId(Integer id) {
		this.id = id;
	}
	private User getUser() {
		return user;
	}
	private void setUser(User user) {
		this.user = user;
	}
	private Date getCheckTime() {
		return checkTime;
	}
	private void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
}
