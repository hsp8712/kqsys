package cn.huangshaoping.service.bean.evt;

/**
 * 用于操作服务入参
 * @author oraro
 *
 * @param <T>
 */
public abstract class Evt {
	/** 操作类型 */
	private Integer opertype;
	
	public Integer getOpertype() {
		return opertype;
	}
	public void setOpertype(Integer opertype) {
		this.opertype = opertype;
	}
}
