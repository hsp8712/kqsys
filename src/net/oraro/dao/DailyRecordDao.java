package net.oraro.dao;

import java.util.List;

import net.oraro.bean.DailyRecord;
import net.oraro.exception.DataAccessException;

/**
 * 考勤记录访问接口
 * @author shipley
 * @date 2013-1-21
 */
public interface DailyRecordDao {
	/**
	 * Query all dailyRecords, and return a dailyRecords list;
	 * @return
	 */
	List<DailyRecord> queryAll() throws DataAccessException;
	
}
