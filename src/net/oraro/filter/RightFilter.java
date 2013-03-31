package net.oraro.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oraro.bean.Right;
import net.oraro.bean.User;
import net.oraro.control.LoginAndOutServlet;
import net.oraro.service.ServicesFactory;

import org.apache.log4j.Logger;
/**
 * User Right Filter
 * @author Administrator
 *
 */
public class RightFilter implements Filter {
	
	private Logger log = Logger.getLogger(RightFilter.class);
	
	public void destroy() {
		log.info("SessionFilter destoryed.");
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		
		String contextPath = request.getContextPath();
		String reqUri = request.getRequestURI();
		if(reqUri.equals(contextPath + "/servlet/LoginAndOutServlet")) {
			arg2.doFilter(arg0, arg1);
			return;
		}
		
		String servletLink = null;
		
		int indexOfServlet = -1;
		if((indexOfServlet = reqUri.indexOf("servlet")) != -1) {
			int lastQueMarkIndex = reqUri.lastIndexOf('?');
			if(lastQueMarkIndex == -1) {
				servletLink = reqUri.substring(indexOfServlet);
			} else if(lastQueMarkIndex > indexOfServlet) {
				servletLink = reqUri.substring(indexOfServlet, lastQueMarkIndex);
			} else {
				throw new ServletException("Request uri error.");
			}
		} else {
			arg2.doFilter(arg0, arg1);
		}
		
		boolean isCurUserRight = false;
		// 当前登录用户
		User curUser = (User)request.getSession().getAttribute(LoginAndOutServlet.SESSIONKEY_CURRENT_USER);
		List<Right> curRights = ServicesFactory.instance().getLoginAndOutService().getRights(curUser.getId());
		for (Right right : curRights) {
			String link = right.getRightLink();
			if(link != null && link.startsWith(servletLink)) {
				isCurUserRight = true;
				break;
			}
		}
		
		if(isCurUserRight) {
			arg2.doFilter(arg0, arg1);
		} else {
			throw new ServletException("Right error.");
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		log.info("SessionFilter init.");
	}

}
