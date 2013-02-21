package net.oraro.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.oraro.db.CallableParam;
import net.oraro.db.DBUtil;
import net.oraro.service.UserService;
import net.oraro.service.bean.evt.UserEvt;
import net.oraro.service.bean.result.BeanResult;
import net.oraro.service.bean.result.ResultUtil;

import org.apache.log4j.Logger;

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


}
