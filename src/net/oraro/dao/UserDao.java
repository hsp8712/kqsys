package net.oraro.dao;

import java.util.List;

import net.oraro.bean.User;
import net.oraro.exception.DataAccessException;

/**
 * User Data Access Object
 * @author shipley
 * @date 2013-1-20
 */
public interface UserDao {
	
	/**
	 * Add a new user.
	 * @param user
	 * @return
	 * @throws DataAccessException 
	 */
	boolean insert(User user) throws DataAccessException;
	
	/**
	 * Update a new user.
	 * @param user
	 * @return
	 */
	boolean update(User user) throws DataAccessException;
	
	/**
	 * Delete a user.
	 * @param id
	 * @return
	 */
	boolean delete(Integer id) throws DataAccessException;
	
	/**
	 * Query all users, and return a users list;
	 * @return
	 */
	List<User> queryAll() throws DataAccessException;
	
	/**
	 * Query user by user's account,password.
	 * @param account
	 * @param password
	 * @return
	 */
	User queryByAccountAndPassword(String account, String password) throws DataAccessException;
	
	/**
	 * 执行sql操作，一般用于执行增删改操作
	 * @param sql
	 * @return
	 */
	boolean execute(String sql) throws DataAccessException;
	
	/**
	 * 执行sql查询user操作，返回user结果集列表
	 * @param sql
	 * @return
	 */
	List<User> executeQuery(String sql) throws DataAccessException;
	
}
