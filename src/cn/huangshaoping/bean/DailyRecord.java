package cn.huangshaoping.bean;

import java.util.Date;

/**
 * 日考勤记录
 * @author 6261000301
 *
 */
public class DailyRecord {
	private Integer id;			// id
	private User user;			// 用户
	private Date date;			// 日期
	private Date firstTime;		// 首次打卡时间
	private Date lastTime;		// 最后一次打卡时间
	private Date overTime;		// 加班开始时间	
	private float overTimeHour;		// 加班时长小时数（不足30分钟计0，超过30分钟不足1小时，计0.5）
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public Date getOverTime() {
		return overTime;
	}
	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}
	public float getOverTimeHour() {
		return overTimeHour;
	}
	public void setOverTimeHour(float overTimeHour) {
		this.overTimeHour = overTimeHour;
	}
	
	
}
