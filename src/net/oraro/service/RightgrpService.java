package net.oraro.service;

import net.oraro.service.bean.evt.RightgrpEvt;
import net.oraro.service.bean.evt.RightgrpMemEvt;
import net.oraro.service.bean.result.BeanResult;
import net.oraro.service.bean.result.Result;

/**
 * 权限组管理服务
 * @author 6261000301
 *
 */
public interface RightgrpService {
	
	/**
	 * 权限组管理
	 * OperateEvt.opertype: 1、新增；2、修改；3、删除
	 * @param evt
	 * @return
	 */
	BeanResult rightgrpManage(RightgrpEvt evt);
	
	/**
	 * 权限组权限成员管理
	 * @param evt
	 * @return
	 */
	Result rightgrpMemManage(RightgrpMemEvt evt);
	
}
