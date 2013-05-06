package cn.huangshaoping.service.bean.evt;

/**
 * 组管理服务evt
 * @author oraro
 *
 */
public class TeamEvt extends Evt{
	private Integer teamId;
	private String teamName;
	private String description;
	private Integer managerId;
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	
}
