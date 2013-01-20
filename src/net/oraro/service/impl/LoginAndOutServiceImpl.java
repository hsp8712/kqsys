package net.oraro.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.oraro.bean.Right;
import net.oraro.bean.User;
import net.oraro.dao.DaoFactory;
import net.oraro.dao.UserDao;
import net.oraro.exception.DataAccessException;
import net.oraro.exception.LoginAndOutException;
import net.oraro.service.LoginAndOutService;
import net.oraro.util.StringUtil;

import org.apache.log4j.Logger;

public class LoginAndOutServiceImpl implements LoginAndOutService{
	
	private Logger log = Logger.getLogger(LoginAndOutServiceImpl.class);
	
	public User login(String account, String passwd) throws LoginAndOutException {
		
		User user = null;
		if(StringUtil.isEmpty(account) || StringUtil.isEmpty(passwd)) {
			throw new LoginAndOutException("用户名和密码均不能为空.");
		}
		
		UserDao userDao = DaoFactory.getInstance().getUserDao();
		try {
			user = userDao.queryByAccountAndPassword(account, passwd);
		} catch (DataAccessException e) {
			log.error(e.getMessage());
		}
		
		return user;
	}

	public User getUserByAccount(String account) {
		
		
		User user = new User();
		user.setAccount("SHIPLEY");
		user.setId(1);
		user.setName("SHIPLEY");
		return user;
	}

	public List<Right> getRights(int userId) {
		List<Right> rights = null;
		try {
			rights = DaoFactory.getInstance().getRightDao().queryByUserId(userId);
		} catch (DataAccessException e) {
			log.error(e.getMessage());
		}
		return rights;
	}

	public void logout(User user) throws LoginAndOutException {
	}

}
