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
	 * Query all rights, and return a rights list;
	 * @return
	 */
	List<Right> queryByUserId(Integer userId) throws DataAccessException;
	
}
