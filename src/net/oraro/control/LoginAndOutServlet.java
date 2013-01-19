package net.oraro.control;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.oraro.bean.Right;
import net.oraro.bean.User;
import net.oraro.exception.LoginAndOutException;
import net.oraro.factory.ServicesFactory;
import net.oraro.service.LoginAndOutService;
import net.oraro.web.WebUtil;

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
		String opertype = request.getParameter("opertype");
		
		LoginAndOutService service = ServicesFactory.instance().getLoginAndOutService();
		if("0".equals(opertype)) {
			// 登录
			
			try {
				boolean loginSuccess = service.login(account, passwd);
				if(loginSuccess) {
					log.info("Login success.");
					
					// 放入session
					User currUser = service.getUserByAccount(account);
					HttpSession session = WebUtil.getNewSession(request);
					session.setAttribute(SESSIONKEY_CURRENT_USER, currUser);
					
					Set<Right> rights = service.getRights(currUser.getId());
					session.setAttribute(SESSIONKEY_CURRENT_USER_RIGHTS, rights);
					
					request.getRequestDispatcher("/index.jsp").forward(request, response);
					return;
				} else {
					request.setAttribute(ServletConstants.REQ_MSG, "登录失败.");
				}
				
			} catch (LoginAndOutException e) {
				log.error(e.getMessage());
				request.setAttribute(ServletConstants.REQ_MSG, e.getMessage());
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
