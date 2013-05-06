package cn.huangshaoping.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import org.apache.log4j.Logger;

import cn.huangshaoping.db.CallableParam;
import cn.huangshaoping.db.DBUtil;
import cn.huangshaoping.service.RightgrpService;
import cn.huangshaoping.service.bean.evt.RightgrpEvt;
import cn.huangshaoping.service.bean.evt.RightgrpMemEvt;
import cn.huangshaoping.service.bean.result.BeanResult;
import cn.huangshaoping.service.bean.result.Result;
import cn.huangshaoping.service.bean.result.ResultUtil;


public class RightgrpServiceImpl implements RightgrpService {
	
	private Logger log = Logger.getLogger(RightgrpServiceImpl.class);
	
	public BeanResult rightgrpManage(RightgrpEvt evt) {
		
		if(evt == null) {
			throw new NullPointerException("Param evt can not be null.");
		}
		
		Map<Integer, CallableParam> paramMap = new HashMap<Integer, CallableParam>();
		paramMap.put(1, new CallableParam(CallableParam.TYPE_IN, evt.getOpertype()));
		paramMap.put(2, new CallableParam(CallableParam.TYPE_IN, evt.getRightgrpId()));
		paramMap.put(3, new CallableParam(CallableParam.TYPE_IN, evt.getRightgrpName()));
		paramMap.put(4, new CallableParam(CallableParam.TYPE_OUT, null));
		paramMap.put(5, new CallableParam(CallableParam.TYPE_OUT, null));
		
		BeanResult bResult = new BeanResult();
		Map<Integer, Object> outParamMap = null;
		try {
			outParamMap = DBUtil.execProcedure("kqp_rightgrp_manage", paramMap);
		} catch (SQLException e) {
			log.error(e.getMessage());
			bResult.setResultCode("1000");
		}
		bResult.setResultCode(String.valueOf(outParamMap.get(5)));
		
		Object obj = outParamMap.get(4);
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

	public Result rightgrpMemManage(RightgrpMemEvt evt) {
		
		if(evt == null) {
			throw new NullPointerException("Param evt can not be null.");
		}
		
		Map<Integer, CallableParam> paramMap = new HashMap<Integer, CallableParam>();
		paramMap.put(1, new CallableParam(CallableParam.TYPE_IN, evt.getOpertype()));
		paramMap.put(2, new CallableParam(CallableParam.TYPE_IN, evt.getRightgrpId()));
		paramMap.put(3, new CallableParam(CallableParam.TYPE_IN, evt.getRightId()));
		paramMap.put(4, new CallableParam(CallableParam.TYPE_OUT, null));
		
		Result rst = new Result();
		Map<Integer, Object> outParamMap = null;
		try {
			outParamMap = DBUtil.execProcedure("kqp_rightgrp_mem_manage", paramMap);
		} catch (SQLException e) {
			log.error(e.getMessage());
			rst.setResultCode("1000");
		}
		rst.setResultCode(String.valueOf(outParamMap.get(4)));
		ResultUtil.updateResultDesc(rst);
		return rst;
	}

}
