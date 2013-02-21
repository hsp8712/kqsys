package net.oraro.service.bean.evt;

/**
 * 权限组管理服务evt
 * @author oraro
 *
 */
public class RightgrpEvt extends Evt{
	private Integer rightgrpId;
	private String rightgrpName;
	public Integer getRightgrpId() {
		return rightgrpId;
	}
	public void setRightgrpId(Integer rightgrpId) {
		this.rightgrpId = rightgrpId;
	}
	public String getRightgrpName() {
		return rightgrpName;
	}
	public void setRightgrpName(String rightgrpName) {
		this.rightgrpName = rightgrpName;
	}
	
}
