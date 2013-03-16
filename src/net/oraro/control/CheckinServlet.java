package net.oraro.control;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.oraro.bean.User;
import net.oraro.service.ServicesFactory;
import net.oraro.service.bean.evt.CheckinRecordEvt;
import net.oraro.service.bean.result.Result;

import org.apache.log4j.Logger;

public class CheckinServlet extends HttpServlet {
	
	private Logger log = Logger.getLogger(CheckinServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String opertype = request.getParameter(ServletConstants.REQ_PARAM_OPERTYPE);
		
		if(Opertype.CHECKIN.equals(opertype)) {
			
			// 打卡
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
			
			// Get request ip
			String ip = request.getRemoteAddr();
			
			CheckinRecordEvt evt = new CheckinRecordEvt();
			evt.setOpertype(1);
			evt.setUserId(curUser.getId());
			evt.setCheckTime(Calendar.getInstance().getTime());
			evt.setCheckIp(ip);
			
			
			Result result = ServicesFactory.instance().getCheckInService().checkinManage(evt);
			if("0000".equals(result.getResultCode())) {
				log.info("Checkin successfully.");
			} else {
				log.info("Checkin failed.");
			}
			
			request.setAttribute(ServletConstants.REQ_MSG, result.getResultDesc());
			request.getRequestDispatcher("/page/checkin.jsp").forward(request, response);
			
		} else if(Opertype.VIEW.equals(opertype)) {
			
			// 加载打卡界面
			request.getRequestDispatcher("/page/checkin.jsp").forward(request, response);
		} else if(Opertype.SERVER_TIME.equals(opertype)) {
			
			log.info("Get Server Time.");
			String serverTime = Calendar.getInstance().getTimeInMillis() + "";
			log.info("Server Time in millis:" + serverTime);
			response.getWriter().print(serverTime);
		} else {
			throw new ServletException("请求的操作不存在<opertype=" + opertype + ">.");
		}
		
	}
	
	/**
	 * 操作
	 * @author oraro
	 */
	public class Opertype {
		public static final String VIEW = "view";				// 打卡界面
		public static final String CHECKIN = "checkin";			// 打卡
		public static final String SERVER_TIME = "server_time";			// 打卡
	}

}
