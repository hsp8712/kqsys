package cn.huangshaoping.service.bean.evt;

import java.util.Date;


/**
 * 打卡记录evt
 * @author oraro
 *
 */
public class CheckinRecordEvt extends Evt{
	private Integer userId;
	private Date checkTime;
	private String checkIp;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckIp() {
		return checkIp;
	}
	public void setCheckIp(String checkIp) {
		this.checkIp = checkIp;
	}
	
}
