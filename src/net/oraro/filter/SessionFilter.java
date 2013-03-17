package net.oraro.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.oraro.bean.User;
import net.oraro.control.LoginAndOutServlet;
import net.oraro.control.ServletConstants;
import net.oraro.web.WebUtil;

import org.apache.log4j.Logger;
/**
 * Http session invalid 
 * @author Administrator
 *
 */
public class SessionFilter implements Filter {
	
	private Logger log = Logger.getLogger(SessionFilter.class);
	
	public void destroy() {
		log.info("SessionFilter destoryed.");
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		
		String contextPath = request.getContextPath();
		String reqUri = request.getRequestURI();
		if(reqUri.equals(contextPath + "/servlet/LoginAndOutServlet")) {
			arg2.doFilter(arg0, arg1);
			return;
		}
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			WebUtil.sendScript(response, "var t=window.top || window; t.location='" + contextPath + "/login.jsp?" 
					+ ServletConstants.REQ_MSG + "=" + URLEncoder.encode("会话已过期，请重新登录", "utf-8") + "'");
			return;
		}
		
		User user = (User)session.getAttribute(LoginAndOutServlet.SESSIONKEY_CURRENT_USER);
		if(user == null || user.getId() == null) {
			WebUtil.sendScript(response, "var t=window.top || window; t.location='" + contextPath + "/login.jsp?" 
					+ ServletConstants.REQ_MSG + "=" + URLEncoder.encode("会话已过期，请重新登录", "utf-8") + "'");
			WebUtil.clearSession(request);
			return;
		}
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		log.info("SessionFilter init.");
	}

}
