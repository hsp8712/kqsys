package net.oraro.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.oraro.bean.User;
import net.oraro.factory.ServicesFactory;
import net.oraro.service.CheckInService;

import org.apache.log4j.Logger;

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
			log.error("Validate Code cannot be null, checkin failed.");
			request.setAttribute(ServletConstants.REQ_MSG, msg);
			request.getRequestDispatcher("/page/checkin.jsp").forward(request, response);
			return;
		}
		
		HttpSession session = request.getSession();
		// 获取正确验证码
		String realCode = String.valueOf(session.getAttribute(ServletConstants.SEN_VALIDCODE));
		if(!validateCode.equals(realCode)) {
			msg = "打卡失败（验证码错误）.";
			log.error("Validate Code is error, checkin failed.");
			request.setAttribute(ServletConstants.REQ_MSG, msg);
			request.getRequestDispatcher("/page/checkin.jsp").forward(request, response);
			return;
		}
		
		// 获取当前登录用户信息
		Object userObj = session.getAttribute(LoginAndOutServlet.SESSIONKEY_CURRENT_USER);
		User curUser = (User) userObj;
		
		CheckInService checkInService = ServicesFactory.instance().getCheckInService();
		boolean checkinSuccess = checkInService.checkIn(curUser);
		
		if(checkinSuccess) {
			msg = "打卡成功.";
			log.info("Check in successfully.");
		} else {
			msg = "打卡失败.";
			log.info("Check in failed.");
		}
		
		request.setAttribute(ServletConstants.REQ_MSG, msg);
		request.getRequestDispatcher("/page/checkin.jsp").forward(request, response);
	}

}
