package net.oraro.util;

public class StringUtil {
	
	/**
	 * 判断字符串是否为空
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		if(string == null) 
			return true;
		return string.length() <= 0;
	}
}
