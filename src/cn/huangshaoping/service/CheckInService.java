package cn.huangshaoping.service;

import cn.huangshaoping.service.bean.evt.CheckinRecordEvt;
import cn.huangshaoping.service.bean.result.Result;

/**
 * 打卡服务类
 * @author 6261000301
 *
 */
public interface CheckInService {
	
	/**
	 * 打卡服务
	 * @param evt	打卡记录evt
	 * @return
	 */
	Result checkinManage(CheckinRecordEvt evt);
	
}
