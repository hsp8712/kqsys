package cn.huangshaoping.util;

public class StringUtil {
	
	/**
	 * 判断字符串是否为空
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		if(string == null) 
			return true;
		if(string.length() <= 0) {
			return true;
		}
		
		return string.matches("^[ \\t]*$");
	}
}
