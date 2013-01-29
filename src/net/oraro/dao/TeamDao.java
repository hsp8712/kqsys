package net.oraro.dao;

import java.util.List;

import net.oraro.bean.Team;
import net.oraro.exception.DataAccessException;

/**
 * Team Data Access Object
 * @author shipley
 * @date 2013-1-20
 */
public interface TeamDao {
	
	/**
	 * Add a new team.
	 * @param team
	 * @return
	 * @throws DataAccessException 
	 */
	boolean insert(Team team) throws DataAccessException;
	
	/**
	 * Update a new team.
	 * @param team
	 * @return
	 */
	boolean update(Team team) throws DataAccessException;
	
	/**
	 * Delete a team.
	 * @param id
	 * @return
	 */
	boolean delete(Integer id) throws DataAccessException;
	
	/**
	 * Query all teams, and return a teams list;
	 * @return
	 */
	List<Team> queryAll() throws DataAccessException;
	
	/**
	 * Query team by team's account,password.
	 * @param account
	 * @param password
	 * @return
	 */
	Team queryByAccountAndPassword(String account, String password) throws DataAccessException;
	
	/**
	 * 执行sql操作，一般用于执行增删改操作
	 * @param sql
	 * @return
	 */
	boolean execute(String sql) throws DataAccessException;
	
	/**
	 * 执行sql查询team操作，返回team结果集列表
	 * @param sql
	 * @return
	 */
	List<Team> executeQuery(String sql) throws DataAccessException;
	
}
