package net.oraro.service;

import cn.huangshaoping.page.Page;
import net.oraro.bean.DailyRecord;

/**
 * 日考勤记录服务
 * @author 6261000301
 *
 */
public interface DailyRecordService {
	
	/**
	 * 获取日考勤分页记录
	 * @param pageNo
	 * @return
	 */
	Page<DailyRecord> getPageDailyRecords(int pageNo);
	
}
