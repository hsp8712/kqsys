package net.oraro.dao;

import java.util.List;

import net.oraro.bean.CheckinRecord;
import net.oraro.exception.DataAccessException;

/**
 * 打卡记录数据访问接口
 * @author shipley
 * @date 2013-1-21
 */
public interface CheckinRecordDao {
	
	/**
	 * Add a new checkin record.
	 * @param checkinRecord
	 * @return
	 * @throws DataAccessException
	 */
	boolean insert(CheckinRecord checkinRecord) throws DataAccessException;
	
	/**
	 * Update a checkin record.
	 * @param checkinRecord
	 * @return
	 * @throws DataAccessException
	 */
	boolean update(CheckinRecord checkinRecord) throws DataAccessException;
	
	/**
	 * Delete a checkinRecord.
	 * @param id
	 * @return
	 */
	boolean delete(Integer id) throws DataAccessException;
	
	/**
	 * Query all checkinRecords, and return a checkinRecords list;
	 * @return
	 */
	List<CheckinRecord> queryAll() throws DataAccessException;
	
	/**
	 * 执行sql操作，一般用于执行增删改操作
	 * @param sql
	 * @return
	 */
	boolean execute(String sql) throws DataAccessException;
	
	/**
	 * 执行sql查询checkinRecord操作，返回checkinRecord结果集列表
	 * @param sql
	 * @return
	 */
	List<CheckinRecord> executeQuery(String sql) throws DataAccessException;
	
	
}
