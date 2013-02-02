package net.oraro.service.bean.result;

import java.util.List;
import java.util.Map;

import net.oraro.db.DBUtil;

/**
 * 结果result工具类
 * @author oraro
 *
 */
public class ResultUtil {
	
	/**
	 * 根据result的resultCode更新resultDesc
	 * @param result
	 */
	public static void updateResultDesc(Result result) {
		if(result == null) {
			return;
		}
		
		String resultCode = result.getResultCode();
		String sql = "select result_desc from kq_result_code where result_code='" + resultCode + "'";
		List<Map<String, String>> list = DBUtil.executeQuery(sql);
		
		if(list != null && list.size() > 0) {
			Map<String, String> map = list.get(0);
			if(map != null) {
				result.setResultDesc(map.get("result_desc"));
			}
		}
	}
	
}
