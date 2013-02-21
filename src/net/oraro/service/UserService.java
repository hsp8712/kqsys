package net.oraro.service;

import net.oraro.service.bean.evt.UserEvt;
import net.oraro.service.bean.result.BeanResult;

/**
 * 用户管理服务
 * @author 6261000301
 *
 */
public interface UserService {
	
	/**
	 * 用户管理
	 * OperateEvt.opertype: 1、新增；2、修改；3、删除；4、修改密码
	 * @param evt
	 * @return
	 */
	BeanResult userManage(UserEvt evt);
	
}
