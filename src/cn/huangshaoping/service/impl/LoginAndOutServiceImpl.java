package cn.huangshaoping.service.impl;

import java.util.List;


import org.apache.log4j.Logger;

import cn.huangshaoping.bean.Right;
import cn.huangshaoping.bean.User;
import cn.huangshaoping.dao.DaoFactory;
import cn.huangshaoping.dao.UserDao;
import cn.huangshaoping.exception.DataAccessException;
import cn.huangshaoping.exception.LoginAndOutException;
import cn.huangshaoping.service.LoginAndOutService;
import cn.huangshaoping.util.CryptUtil;
import cn.huangshaoping.util.StringUtil;


public class LoginAndOutServiceImpl implements LoginAndOutService{
	
	private Logger log = Logger.getLogger(LoginAndOutServiceImpl.class);
	
	public User login(String account, String passwd) throws LoginAndOutException {
		
		User user = null;
		if(StringUtil.isEmpty(account) || StringUtil.isEmpty(passwd)) {
			throw new LoginAndOutException("用户名和密码均不能为空.");
		}
		
		UserDao userDao = DaoFactory.getInstance().getUserDao();
		try {
			passwd = new CryptUtil().encryptToMD5(passwd);
			user = userDao.queryByAccountAndPassword(account, passwd);
		} catch (DataAccessException e) {
			log.error(e.getMessage());
			throw new LoginAndOutException(e.getMessage());
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
