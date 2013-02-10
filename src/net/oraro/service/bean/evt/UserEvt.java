package net.oraro.service.bean.evt;

/**
 * 组管理服务evt
 * @author oraro
 *
 */
public class UserEvt extends Evt{
	private Integer id;
	private String name;
	private String empno;
	private String account;
	private String password;
	private Integer teamId;
	private Integer rightgrpId;
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
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public Integer getRightgrpId() {
		return rightgrpId;
	}
	public void setRightgrpId(Integer rightgrpId) {
		this.rightgrpId = rightgrpId;
	}
	
}
