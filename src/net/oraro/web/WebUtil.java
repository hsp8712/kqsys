package net.oraro.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Web工具
 * @author 6261000301
 *
 */
public class WebUtil {
	
	/**
	 * 清除当前session，并创建新的session
	 * @param request
	 * @return
	 */
	public static HttpSession getNewSession(HttpServletRequest request) {
		if(request == null) return null;
		clearSession(request);
		return request.getSession();
	}
	
	/**
	 * 使当前session失效
	 * @param request
	 */
	public static void clearSession(HttpServletRequest request) {
		
		// 获取与request关联的session，如果session不存在，不创建新的session，返回null
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			return;
		}
		
		session.invalidate();
		Cookie[] cookies = request.getCookies();
		
		if( cookies == null || cookies.length <= 0 ) {
			return;
		}
		
		for (Cookie cookie : cookies) {
			if( cookie == null ) continue;
			if("JSESSIONID".equals(cookie.getName())) {
				cookie.setMaxAge(0);	// 设置cookie立即过期
			}
		}
		
	}
}
