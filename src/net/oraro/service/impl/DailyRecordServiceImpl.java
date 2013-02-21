package net.oraro.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.oraro.db.CallableParam;
import net.oraro.db.DBUtil;
import net.oraro.service.DailyRecordService;
import net.oraro.service.bean.evt.DailyRecordEvt;
import net.oraro.service.bean.result.Result;
import net.oraro.service.bean.result.ResultUtil;

import org.apache.log4j.Logger;

public class DailyRecordServiceImpl implements DailyRecordService{
	
	private Logger log = Logger.getLogger(DailyRecordServiceImpl.class);

	public Result genDailyRecord(DailyRecordEvt evt) {
		if(evt == null) {
			throw new NullPointerException("Param evt can not be null.");
		}
		
		Map<Integer, CallableParam> paramMap = new HashMap<Integer, CallableParam>();
		paramMap.put(1, new CallableParam(CallableParam.TYPE_IN, evt.getOpertype()));
		paramMap.put(2, new CallableParam(CallableParam.TYPE_IN, evt.getUserId()));
		paramMap.put(3, new CallableParam(CallableParam.TYPE_IN, evt.getRecordDate()));
		paramMap.put(4, new CallableParam(CallableParam.TYPE_OUT, null));
		
		Result result = new Result();
		Map<Integer, Object> outParamMap = null;
		try {
			outParamMap = DBUtil.execProcedure("kqp_gen_dailyrecord", paramMap);
		} catch (SQLException e) {
			log.error(e.getMessage());
			result.setResultCode("1000");
		}
		result.setResultCode(String.valueOf(outParamMap.get(4)));
		ResultUtil.updateResultDesc(result);
		return result;
	}
	
}
