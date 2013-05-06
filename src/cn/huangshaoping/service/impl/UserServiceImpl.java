package cn.huangshaoping.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import org.apache.log4j.Logger;

import cn.huangshaoping.db.CallableParam;
import cn.huangshaoping.db.DBUtil;
import cn.huangshaoping.service.UserService;
import cn.huangshaoping.service.bean.evt.UserEvt;
import cn.huangshaoping.service.bean.evt.UserPasswordModEvt;
import cn.huangshaoping.service.bean.result.BeanResult;
import cn.huangshaoping.service.bean.result.Result;
import cn.huangshaoping.service.bean.result.ResultUtil;


public class UserServiceImpl implements UserService{
	
	private Logger log = Logger.getLogger(UserServiceImpl.class);

	public BeanResult userManage(UserEvt evt) {
		
		if(evt == null) {
			throw new NullPointerException("Param evt can not be null.");
		}
		
		Map<Integer, CallableParam> paramMap = new HashMap<Integer, CallableParam>();
		paramMap.put(1, new CallableParam(CallableParam.TYPE_IN, evt.getOpertype()));
		paramMap.put(2, new CallableParam(CallableParam.TYPE_IN, evt.getId()));
		paramMap.put(3, new CallableParam(CallableParam.TYPE_IN, evt.getEmpno()));
		paramMap.put(4, new CallableParam(CallableParam.TYPE_IN, evt.getName()));
		paramMap.put(5, new CallableParam(CallableParam.TYPE_IN, evt.getAccount()));
		paramMap.put(6, new CallableParam(CallableParam.TYPE_IN, evt.getPassword()));
		paramMap.put(7, new CallableParam(CallableParam.TYPE_IN, evt.getTeamId()));
		paramMap.put(8, new CallableParam(CallableParam.TYPE_IN, evt.getRightgrpId()));
		paramMap.put(9, new CallableParam(CallableParam.TYPE_OUT, null));
		paramMap.put(10, new CallableParam(CallableParam.TYPE_OUT, null));
		
		BeanResult bResult = new BeanResult();
		Map<Integer, Object> outParamMap = null;
		try {
			outParamMap = DBUtil.execProcedure("kqp_user_manage", paramMap);
		} catch (SQLException e) {
			log.error(e.getMessage());
			bResult.setResultCode("1000");
		}
		bResult.setResultCode(String.valueOf(outParamMap.get(10)));
		
		Object obj = outParamMap.get(9);
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

	public Result userPasswordMod(UserPasswordModEvt evt) {
		
		if(evt == null) {
			throw new NullPointerException("Param evt can not be null.");
		}
		
		Map<Integer, CallableParam> paramMap = new HashMap<Integer, CallableParam>();
		paramMap.put(1, new CallableParam(CallableParam.TYPE_IN, evt.getOpertype()));
		paramMap.put(2, new CallableParam(CallableParam.TYPE_IN, evt.getId()));
		paramMap.put(3, new CallableParam(CallableParam.TYPE_IN, evt.getPassword()));
		paramMap.put(4, new CallableParam(CallableParam.TYPE_IN, evt.getNewPassword()));
		paramMap.put(5, new CallableParam(CallableParam.TYPE_OUT, null));
		
		Result result = new Result();
		Map<Integer, Object> outParamMap = null;
		try {
			outParamMap = DBUtil.execProcedure("kqp_user_password_mod", paramMap);
		} catch (SQLException e) {
			log.error(e.getMessage());
			result.setResultCode("1000");
		}
		result.setResultCode(String.valueOf(outParamMap.get(5)));
		ResultUtil.updateResultDesc(result);
		return result;
	}


}
