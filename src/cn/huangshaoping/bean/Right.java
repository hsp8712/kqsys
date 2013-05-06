package cn.huangshaoping.bean;

import java.io.Serializable;

/**
 * 权限
 * @author 6261000301
 *
 */
public class Right implements Serializable {
	private static final long serialVersionUID = -3976896045244032005L;
	
	private Integer id;
	private String rightName;
	private String rightLink;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getRightLink() {
		return rightLink;
	}
	public void setRightLink(String rightLink) {
		this.rightLink = rightLink;
	}
	
	
}
