package net.oraro.control;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.oraro.bean.User;
import net.oraro.factory.ServicesFactory;
import net.oraro.service.CheckInService;

public class CheckinServlet extends HttpServlet {
	
	private Logger log = Logger.getLogger(CheckinServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String msg = null;
		
		String validateCode = request.getParameter("validateCode");
		if(validateCode == null) {
			msg = "打卡失败（验证码不能为空）.";
			log.error(msg);
			request.setAttribute(ServletConstants.REQ_MSG, msg);
			request.getRequestDispatcher("/checkin.jsp").forward(request, response);
			return;
		}
		
		HttpSession session = request.getSession();
		// 获取正确验证码
		String realCode = String.valueOf(session.getAttribute(ServletConstants.SEN_VALIDCODE));
		if(!validateCode.equals(realCode)) {
			msg = "打卡失败（验证码错误）.";
			log.error(msg);
			request.setAttribute(ServletConstants.REQ_MSG, msg);
			request.getRequestDispatcher("/checkin.jsp").forward(request, response);
			return;
		}
		
		// 获取当前登录用户信息
		Object userObj = session.getAttribute(LoginAndOutServlet.SESSIONKEY_CURRENT_USER);
		User curUser = (User) userObj;
		
		// 当前时间
		Date date = new Date();
		
		CheckInService checkInService = ServicesFactory.instance().getCheckInService();
		boolean checkinSuccess = checkInService.checkIn(curUser, date);
		
		if(checkinSuccess) {
			msg = "打卡成功.";
		} else {
			msg = "打卡失败.";
		}
		request.setAttribute(ServletConstants.REQ_MSG, msg);
		request.getRequestDispatcher("/checkin.jsp").forward(request, response);
	}

}
