package cn.huangshaoping.service.bean.evt;

/**
 * 组管理服务evt
 * @author oraro
 *
 */
public class TeamMemEvt extends Evt{
	private Integer teamId;
	private Integer userId;
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
