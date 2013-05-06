package cn.huangshaoping.bean;

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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
}
