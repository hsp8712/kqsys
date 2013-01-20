package net.oraro.dao;

import java.util.List;

import net.oraro.bean.Right;
import net.oraro.exception.DataAccessException;

/**
 * Right Data Access Object
 * @author shipley
 * @date 2013-1-20
 */
public interface RightDao {
	
	/**
	 * Add a new right.
	 * @param right
	 * @return
	 * @throws DataAccessException 
	 */
	boolean insert(Right right) throws DataAccessException;
	
	/**
	 * Update a new right.
	 * @param right
	 * @return
	 */
	boolean update(Right right) throws DataAccessException;
	
	/**
	 * Delete a right.
	 * @param id
	 * @return
	 */
	boolean delete(Integer id) throws DataAccessException;
	
	/**
	 * Query all rights, and return a rights list;
	 * @return
	 */
	List<Right> queryAll() throws DataAccessException;
	
	/**
	 * Query all rights, and return a rights list;
	 * @return
	 */
	List<Right> queryByUserId(Integer userId) throws DataAccessException;
	
	/**
	 * 执行sql操作，一般用于执行增删改操作
	 * @param sql
	 * @return
	 */
	boolean execute(String sql) throws DataAccessException;
	
	/**
	 * 执行sql查询right操作，返回right结果集列表
	 * @param sql
	 * @return
	 */
	List<Right> executeQuery(String sql) throws DataAccessException;
	
}
