package net.oraro.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.oraro.db.CallableParam;
import net.oraro.db.DBUtil;
import net.oraro.service.CheckInService;
import net.oraro.service.bean.evt.CheckinRecordEvt;
import net.oraro.service.bean.result.Result;
import net.oraro.service.bean.result.ResultUtil;

import org.apache.log4j.Logger;

public class CheckInServiceImpl implements CheckInService{
	
	private Logger log = Logger.getLogger(CheckInServiceImpl.class);
	
	public Result checkinManage(CheckinRecordEvt evt) {
		if(evt == null) {
			throw new NullPointerException("Param evt can not be null.");
		}
		
		Map<Integer, CallableParam> paramMap = new HashMap<Integer, CallableParam>();
		paramMap.put(1, new CallableParam(CallableParam.TYPE_IN, evt.getOpertype()));
		paramMap.put(2, new CallableParam(CallableParam.TYPE_IN, evt.getUserId()));
		paramMap.put(3, new CallableParam(CallableParam.TYPE_IN, evt.getCheckTime()));
		paramMap.put(4, new CallableParam(CallableParam.TYPE_IN, evt.getCheckIp()));
		paramMap.put(5, new CallableParam(CallableParam.TYPE_OUT, null));
		
		Result result = new Result();
		Map<Integer, Object> outParamMap = null;
		try {
			outParamMap = DBUtil.execProcedure("kqp_checkin_manage", paramMap);
		} catch (SQLException e) {
			log.error(e.getMessage());
			result.setResultCode("1000");
		}
		result.setResultCode(String.valueOf(outParamMap.get(5)));
		ResultUtil.updateResultDesc(result);
		return result;
	}

}
