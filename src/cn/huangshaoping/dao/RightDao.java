package cn.huangshaoping.dao;

import java.util.List;

import cn.huangshaoping.bean.Right;
import cn.huangshaoping.exception.DataAccessException;



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
