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
	 * Add a new checkin record.
	 * @param dailyRecord
	 * @return
	 * @throws DataAccessException
	 */
	boolean insert(DailyRecord dailyRecord) throws DataAccessException;
	
	/**
	 * Update a checkin record.
	 * @param dailyRecord
	 * @return
	 * @throws DataAccessException
	 */
	boolean update(DailyRecord dailyRecord) throws DataAccessException;
	
	/**
	 * Delete a dailyRecord.
	 * @param id
	 * @return
	 */
	boolean delete(Integer id) throws DataAccessException;
	
	/**
	 * Query all dailyRecords, and return a dailyRecords list;
	 * @return
	 */
	List<DailyRecord> queryAll() throws DataAccessException;
	
	/**
	 * 执行sql操作，一般用于执行增删改操作
	 * @param sql
	 * @return
	 */
	boolean execute(String sql) throws DataAccessException;
	
	/**
	 * 执行sql查询dailyRecord操作，返回dailyRecord结果集列表
	 * @param sql
	 * @return
	 */
	List<DailyRecord> executeQuery(String sql) throws DataAccessException;
	
	
}
