package net.oraro.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.oraro.db.CallableParam;
import net.oraro.db.DBUtil;
import net.oraro.service.TeamService;
import net.oraro.service.bean.evt.TeamEvt;
import net.oraro.service.bean.evt.TeamMemEvt;
import net.oraro.service.bean.result.BeanResult;
import net.oraro.service.bean.result.Result;
import net.oraro.service.bean.result.ResultUtil;
import net.oraro.util.StringUtil;

import org.apache.log4j.Logger;

public class TeamServiceImpl implements TeamService{
	
	private Logger log = Logger.getLogger(TeamServiceImpl.class);

	public BeanResult teamManage(TeamEvt evt) {
		
		if(evt == null) {
			throw new NullPointerException("Param evt can not be null.");
		}
		
		Map<Integer, CallableParam> paramMap = new HashMap<Integer, CallableParam>();
		paramMap.put(1, new CallableParam(CallableParam.TYPE_IN, evt.getOpertype()));
		paramMap.put(2, new CallableParam(CallableParam.TYPE_IN, evt.getTeamId()));
		paramMap.put(3, new CallableParam(CallableParam.TYPE_IN, evt.getTeamName()));
		paramMap.put(4, new CallableParam(CallableParam.TYPE_IN, evt.getDescription()));
		paramMap.put(5, new CallableParam(CallableParam.TYPE_IN, evt.getManagerId()));
		paramMap.put(6, new CallableParam(CallableParam.TYPE_OUT, null));
		paramMap.put(7, new CallableParam(CallableParam.TYPE_OUT, ""));
		
		BeanResult bResult = new BeanResult();
		Map<Integer, Object> outParamMap = null;
		try {
			outParamMap = DBUtil.execProcedure("kqp_team_manage", paramMap);
		} catch (SQLException e) {
			log.error(e.getMessage());
			bResult.setResultCode("1000");
		}
		bResult.setResultCode(String.valueOf(outParamMap.get(6)));
		
		Object obj = outParamMap.get(7);
		Integer idInt = null;
		if(obj == null) {
			idInt = 0;
		} else {
			idInt = Integer.valueOf(obj.toString());
		}
		
		bResult.setId(idInt);
		ResultUtil.updateResultDesc(bResult);
		return bResult;
	}

	public Result teamMemManage(TeamMemEvt evt) {
		if(evt == null) {
			throw new NullPointerException("Param evt can not be null.");
		}
		
		Map<Integer, CallableParam> paramMap = new HashMap<Integer, CallableParam>();
		paramMap.put(1, new CallableParam(CallableParam.TYPE_IN, evt.getOpertype()));
		paramMap.put(2, new CallableParam(CallableParam.TYPE_IN, evt.getTeamId()));
		paramMap.put(3, new CallableParam(CallableParam.TYPE_IN, evt.getUserId()));
		paramMap.put(4, new CallableParam(CallableParam.TYPE_OUT, null));
		
		Result result = new Result();
		Map<Integer, Object> outParamMap = null;
		try {
			outParamMap = DBUtil.execProcedure("kqp_team_mem_manage", paramMap);
		} catch (SQLException e) {
			log.error(e.getMessage());
			result.setResultCode("1000");
		}
		result.setResultCode(String.valueOf(outParamMap.get(4)));
		ResultUtil.updateResultDesc(result);
		return result;
	}

}
