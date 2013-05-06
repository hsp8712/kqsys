package cn.huangshaoping.dao;

import java.util.List;

import cn.huangshaoping.bean.Team;
import cn.huangshaoping.exception.DataAccessException;



/**
 * Team Data Access Object
 * @author shipley
 * @date 2013-1-20
 */
public interface TeamDao {
	
	/**
	 * Query all teams, and return a teams list;
	 * @return
	 */
	List<Team> queryAll() throws DataAccessException;
	
	/**
	 * 根据id查询，返回一个team对象
	 * @param sql
	 * @return
	 */
	Team queryById(Integer id) throws DataAccessException;
	
}
