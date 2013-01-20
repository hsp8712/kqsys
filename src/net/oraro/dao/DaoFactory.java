package net.oraro.dao;

import net.oraro.dao.impl.CheckinRecordDaoImpl;
import net.oraro.dao.impl.RightDaoImpl;
import net.oraro.dao.impl.UserDaoImpl;

/**
 * dao工厂
 * @author shipley
 * @date 2013-1-20
 */
public class DaoFactory {
	
	private UserDao userDao;
	private RightDao rightDao;
	private CheckinRecordDao checkinRecordDao;
	
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
	
	/**
	 * 获取userDao
	 * @return
	 */
	public RightDao getRightDao() {
		if(rightDao == null) {
			rightDao = new RightDaoImpl();
		}
		return rightDao;
	}
	
	/**
	 * 获取userDao
	 * @return
	 */
	public CheckinRecordDao getCheckinRecordDao() {
		if(checkinRecordDao == null) {
			checkinRecordDao = new CheckinRecordDaoImpl();
		}
		return checkinRecordDao;
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
