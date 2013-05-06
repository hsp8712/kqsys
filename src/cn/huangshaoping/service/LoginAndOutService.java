package cn.huangshaoping.service;

import java.util.List;

import cn.huangshaoping.bean.Right;
import cn.huangshaoping.bean.User;
import cn.huangshaoping.exception.LoginAndOutException;



/**
 * 登录、注销服务
 * @author 6261000301
 *
 */
public interface LoginAndOutService {
	
	/**
	 * 登录
	 * @param account	账户
	 * @param passwd	密码
	 * @return
	 * @throws LoginAndOutException
	 */
	User login(String account, String passwd) throws LoginAndOutException;
	
	/**
	 * 注销登录
	 * @param user		待注销登录用户
	 * @throws LoginAndOutException
	 */
	void logout(User user) throws LoginAndOutException;
	
	/**
	 * 根据账户获取用户信息
	 * @param account	账户
	 * @return
	 */
	User getUserByAccount(String account);
	
	/**
	 * 获取权限
	 * @param userId	用户id
	 * @return
	 */
	List<Right> getRights(int userId);
}
