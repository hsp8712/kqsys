package net.oraro.service;

import net.oraro.service.bean.evt.DailyRecordEvt;
import net.oraro.service.bean.result.Result;


/**
 * 日考勤记录服务
 * @author 6261000301
 *
 */
public interface DailyRecordService {
	
	/**
	 * 生成考勤日记录
	 * @param evt
	 * @return
	 */
	public Result genDailyRecord(DailyRecordEvt evt);
}
