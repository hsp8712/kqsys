package cn.huangshaoping.service;

import cn.huangshaoping.service.bean.evt.TeamEvt;
import cn.huangshaoping.service.bean.evt.TeamMemEvt;
import cn.huangshaoping.service.bean.result.BeanResult;
import cn.huangshaoping.service.bean.result.Result;

/**
 * 日考勤记录服务
 * @author 6261000301
 *
 */
public interface TeamService {
	
	/**
	 * 组管理
	 * OperateEvt.opertype: 1、新增；2、修改；3、删除
	 * @param evt
	 * @return
	 */
	BeanResult teamManage(TeamEvt evt);
	
	/**
	 * 组成员管理
	 * OperateEvt.opertype: 1、添加成员；2、移除成员
	 * @param evt
	 * @return
	 */
	Result teamMemManage(TeamMemEvt evt);
}
