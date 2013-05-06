package cn.huangshaoping.db;

/**
 * 用于存储过程请求参数
 * @author oraro
 */
public class CallableParam {
	
	public static final int TYPE_IN 	= 1;
	public static final int TYPE_OUT 	= 2;
	public static final int TYPE_INOUT 	= 3;
	
	public CallableParam(int type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	private Object value;
	private int type;
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public String toString() {
		return "[type=" + 
			(this.type == 1 ? "in" : this.type == 2 ? "out" : this.type == 3 ? "inout" : "unknow") +
			", value=" + this.value + "]";
	}
	
}
