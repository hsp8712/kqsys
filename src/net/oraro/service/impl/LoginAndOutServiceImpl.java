package net.oraro.service.impl;

import java.util.HashSet;
import java.util.Set;

import net.oraro.bean.Right;
import net.oraro.bean.User;
import net.oraro.exception.LoginAndOutException;
import net.oraro.service.LoginAndOutService;
import net.oraro.util.StringUtil;

public class LoginAndOutServiceImpl implements LoginAndOutService{

	public boolean login(String account, String passwd) throws LoginAndOutException {
		
		if(StringUtil.isEmpty(account) || StringUtil.isEmpty(passwd)) {
			throw new LoginAndOutException("用户名和密码均不能为空.");
		}
		
		if(!"SHIPLEY".equals(account.toUpperCase())) {
			throw new LoginAndOutException("用户名不存在.");
		}
		
		if(!"SHIPLEY".equals(passwd.toUpperCase())) {
			throw new LoginAndOutException("密码错误.");
		}
		
		return true;
	}

	public User getUserByAccount(String account) {
		User user = new User();
		user.setAccount("SHIPLEY");
		user.setId(1);
		user.setName("SHIPLEY");
		return user;
	}

	public Set<Right> getRights(int userId) {
		Set<Right> rights = new HashSet<Right>();
		
		Right r1 = new Right();
		Right r2 = new Right();
		Right r3 = new Right();
		
		r1.setId(1);
		r1.setRightLink("servlet/checkin");
		r1.setRightName("打卡");
		
		r2.setId(2);
		r2.setRightLink("servlet/myCheckinRecordManage");
		r2.setRightName("我的考勤记录");
		
		r3.setId(3);
		r3.setRightLink("servlet/DailyRecordServlet?opertype=0");
		r3.setRightName("组考勤记录");
		
		rights.add(r1);
		rights.add(r2);
		rights.add(r3);
		
		return rights;
	}

	public void logout(User user) throws LoginAndOutException {
	}

}
