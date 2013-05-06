package cn.huangshaoping.service.bean.evt;

import java.sql.Date;

/**
 * 组管理服务evt
 * @author oraro
 *
 */
public class DailyRecordEvt extends Evt{
	private Integer userId;
	private Date recordDate;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
}
