package cn.huangshaoping.dao;

import java.util.List;

import cn.huangshaoping.bean.User;
import cn.huangshaoping.exception.DataAccessException;



/**
 * User Data Access Object
 * @author shipley
 * @date 2013-1-20
 */
public interface UserDao {
	
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
	
	
}
