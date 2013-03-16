package net.oraro.service.bean.evt;

/**
 * 组管理服务evt
 * @author oraro
 *
 */
public class UserPasswordModEvt extends Evt{
	private Integer id;
	private String password;
	private String newPassword;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
