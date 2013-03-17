package net.oraro.dao;

import net.oraro.dao.impl.RightDaoImpl;
import net.oraro.dao.impl.TeamDaoImpl;
import net.oraro.dao.impl.UserDaoImpl;

/**
 * dao工厂
 * @author shipley
 * @date 2013-1-20
 */
public class DaoFactory {
	
	private TeamDao teamDao;
	private UserDao userDao;
	private RightDao rightDao;
	
	public TeamDao getTeamDao() {
		if(teamDao == null) {
			teamDao = new TeamDaoImpl();
		}
		return teamDao;
	}
	
	/**
	 * 获取userDao
	 * @return
	 */
	public UserDao getUserDao() {
		if(userDao == null) {
			userDao = new UserDaoImpl();
		}
		return userDao;
	}
	
	public RightDao getRightDao() {
		if(rightDao == null) {
			rightDao = new RightDaoImpl();
		}
		return rightDao;
	}
	
	
	private static DaoFactory daoFactory;
	
	private DaoFactory() {
		
	}
	
	public static DaoFactory getInstance() {
		if(daoFactory == null) {
			daoFactory = new DaoFactory();
		}
		
		return daoFactory;
	}
	
}
