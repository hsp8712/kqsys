package net.oraro.bean;

import java.util.List;

/**
 * 用户
 * @author 6261000301
 *
 */
public class User {
	private Integer 	id;				// id
	private String 		name;			// 姓名
	private String 		account;		// 账号
	private Team 		team;			// 组
	private List<Right> rights;			// 权限
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public List<Right> getRights() {
		return rights;
	}
	public void setRights(List<Right> rights) {
		this.rights = rights;
	}
	
}
