package cn.huangshaoping.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;

import cn.huangshaoping.bean.Right;
import cn.huangshaoping.bean.User;
import cn.huangshaoping.exception.LoginAndOutException;
import cn.huangshaoping.service.LoginAndOutService;
import cn.huangshaoping.service.ServicesFactory;
import cn.huangshaoping.web.WebUtil;


/**
 * 登录注销servlet
 * @author 6261000301
 *
 */
public class LoginAndOutServlet extends HttpServlet {
	
	private Logger log = Logger.getLogger(LoginAndOutServlet.class);
	
	public static final String SESSIONKEY_CURRENT_USER = "CURRENT_USER";
	public static final String SESSIONKEY_CURRENT_USER_RIGHTS = "CURRENT_USER_RIGHTS";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String account = request.getParameter("account");
		String passwd = request.getParameter("passwd");
		String opertype = request.getParameter(ServletConstants.REQ_PARAM_OPERTYPE);
		
		LoginAndOutService service = ServicesFactory.instance().getLoginAndOutService();
		if("0".equals(opertype)) {
			// 登录
			User user = null;
			try {
				user = service.login(account, passwd);
			} catch (LoginAndOutException e) {
				log.error(e.getMessage());
				request.setAttribute(ServletConstants.REQ_MSG, e.getMessage());
			}
			
			if(user != null) {
				log.info("Login success.");
				
				// 放入session
				HttpSession session = WebUtil.getNewSession(request);
				session.setAttribute(SESSIONKEY_CURRENT_USER, user);
				
				List<Right> rights = service.getRights(user.getId());
				session.setAttribute(SESSIONKEY_CURRENT_USER_RIGHTS, rights);
				
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				return;
			} else {
				request.setAttribute(ServletConstants.REQ_MSG, "用户不存在或密码错误.");
			}
				
			
			
		} else if("1".equals(opertype)) {
			User curUser = (User)request.getSession().getAttribute(SESSIONKEY_CURRENT_USER);
			
			// 注销
			try {
				service.logout(curUser);
				WebUtil.clearSession(request);
				log.info("Logout success.");
			} catch (LoginAndOutException e) {
				log.error(e.getMessage());
			}
		}
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

}
