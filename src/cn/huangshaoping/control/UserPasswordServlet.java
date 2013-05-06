package cn.huangshaoping.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;

import cn.huangshaoping.bean.User;
import cn.huangshaoping.service.ServicesFactory;
import cn.huangshaoping.service.bean.evt.UserPasswordModEvt;
import cn.huangshaoping.service.bean.result.Result;
import cn.huangshaoping.util.CryptUtil;


public class UserPasswordServlet extends HttpServlet {
	
	private Logger log = Logger.getLogger(UserPasswordServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opertype = request.getParameter(ServletConstants.REQ_PARAM_OPERTYPE);
		
		if(Opertype.MOD_PASSWORD_VIEW.equals(opertype)) {
			modPasswordView(request, response);
		} else if(Opertype.MOD_PASSWORD_SAVE.equals(opertype)) {
			modPasswordSave(request, response);
		} else {
			throw new ServletException("请求的操作不存在<opertype=" + opertype + ">.");
		}
		return;
	}
	
	private void modPasswordView(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/page/user_password_mod.jsp").forward(request, response);
	}

	private void modPasswordSave(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// 新密码
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		
		User curUser = (User)request.getSession().getAttribute(LoginAndOutServlet.SESSIONKEY_CURRENT_USER);
		Integer id = curUser.getId();
		
		CryptUtil cryptUtil = new CryptUtil();
		password = cryptUtil.encryptToMD5(password);
		newPassword = cryptUtil.encryptToMD5(newPassword);
		
		UserPasswordModEvt evt = new UserPasswordModEvt();
		evt.setId(id);
		evt.setOpertype(1);
		evt.setPassword(password);
		evt.setNewPassword(newPassword);
		
		Result result = ServicesFactory.instance().getUserService().userPasswordMod(evt);
		request.setAttribute(ServletConstants.REQ_MSG, result.getResultDesc());
		request.getRequestDispatcher("/page/user_password_mod.jsp").forward(request, response);
	}

	public class Opertype {
		public static final String MOD_PASSWORD_VIEW = "mod_password_view";		// 修改密码界面
		public static final String MOD_PASSWORD_SAVE = "mod_password_save";		// 修改密码保存
	}

}
