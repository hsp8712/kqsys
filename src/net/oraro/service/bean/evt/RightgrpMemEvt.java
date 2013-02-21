package net.oraro.service.bean.evt;

/**
 * 权限组权限成员管理evt
 * @author oraro
 *
 */
public class RightgrpMemEvt extends Evt{
	private Integer id;
	private Integer rightgrpId;
	private Integer rightId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRightgrpId() {
		return rightgrpId;
	}
	public void setRightgrpId(Integer rightgrpId) {
		this.rightgrpId = rightgrpId;
	}
	public Integer getRightId() {
		return rightId;
	}
	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}
	
}
