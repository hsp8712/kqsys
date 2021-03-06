package cn.huangshaoping.bean;

import java.util.List;

/**
 * 组
 * @author 6261000301
 *
 */
public class Team {
	private Integer id;				// id
	private String 	teamName;		// 组名
	private String description; 	// 描述
	private User manager;		// 组管理员
	private List<User> members;		// 组员
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public User getManager() {
		return manager;
	}
	public void setManager(User manager) {
		this.manager = manager;
	}
	public List<User> getMembers() {
		return members;
	}
	public void setMembers(List<User> members) {
		this.members = members;
	}
	
}
