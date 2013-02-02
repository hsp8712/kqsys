package net.oraro.service.bean.result;

/**
 * 服务操作返回结果
 * @author oraro
 *
 */
public class Result {
	/** 返回码 */
	private String resultCode;
	
	/** 描述 */
	private String resultDesc;
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
}
