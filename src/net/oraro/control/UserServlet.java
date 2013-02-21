package net.oraro.control;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oraro.common.Constants;
import net.oraro.db.DBUtil;
import net.oraro.service.ServicesFactory;
import net.oraro.service.bean.evt.UserEvt;
import net.oraro.service.bean.result.BeanResult;
import net.oraro.util.StringUtil;

import org.apache.log4j.Logger;

import cn.huangshaoping.page.Page;

public class UserServlet extends HttpServlet {
	
	private Logger log = Logger.getLogger(UserServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opertype = request.getParameter(ServletConstants.REQ_PARAM_OPERTYPE);
		
		if(Opertype.VIEW.equals(opertype)) {
			view(request, response);
		} else if(Opertype.ADD_VIEW.equals(opertype)) {
			addView(request, response);
		} else if(Opertype.MOD_VIEW.equals(opertype)) {
			modView(request, response);
		} else if(Opertype.SAVE.equals(opertype)) {
			save(request, response);
		} else if(Opertype.DELETE.equals(opertype)) {
			delete(request, response);
		} else {
			throw new ServletException("请求的操作不存在<opertype=" + opertype + ">.");
		}
		return;
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String id = request.getParameter("id");
		if(StringUtil.isEmpty(id)) {
			throw new ServletException("Delete user: user id cannot be null or empty.");
		}
		
		UserEvt evt = new UserEvt();
		evt.setId(Integer.valueOf(id));
		evt.setOpertype(3);
		
		BeanResult bResult = ServicesFactory.instance().getUserService().userManage(evt);
		request.setAttribute(ServletConstants.REQ_MSG, bResult.getResultDesc());
		
		view(request, response);
	}

	private void view(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String pageNoStr = request.getParameter("pageNo");
		int pageNo = pageNoStr == null ? 0 : Integer.valueOf(pageNoStr);
		
		String sql = "select a.id, a.empno, a.name, a.account, b.team_name, c.rightgrp_name from kq_user a " +
				"left join kq_team b on a.team_id=b.id " +
				"left join kq_rightgrp c on a.rightgrp_id=c.id";
		List<Map<String, String>> teams = DBUtil.executeQuery(sql);
		
		Page<Map<String, String>> page = new Page<Map<String, String>>(teams, Constants.PAGE_SIZE, pageNo);
		request.setAttribute(ServletConstants.REQ_PAGE, page);
		request.getRequestDispatcher("/page/user_view.jsp").forward(request, response);
	}

	/**
	 * 修改页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void modView(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
		// 获取待修改记录的id
		String id = request.getParameter("id");
		if(StringUtil.isEmpty(id)) {
			throw new ServletException("Modify user: user id cannot be null or empty.");
		}
		
		String sql = "select a.id, a.name, a.empno, a.account, a.team_id, b.team_name, a.rightgrp_id " +
				"from kq_user a left join kq_team b on a.team_id=b.id where a.id=" + id;
		Map<String, String> user = DBUtil.executeQueryOne(sql);
		request.setAttribute("user", user);
		
		// 获取权限组集合
		String rightgrpSql = "select * from kq_rightgrp";
		List<Map<String, String>> rightgrps = DBUtil.executeQuery(rightgrpSql);
		request.setAttribute("rightgrps", rightgrps);
		
		request.getRequestDispatcher("/page/user_edit.jsp").forward(request, response);
	}

	/**
	 * 保存
	 * @param request
	 * @param response
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// 获取参数
		String name = request.getParameter("name");				// 用户
		String empno = request.getParameter("empno");			// 工号
		String account = request.getParameter("account");		// 账号
		String teamId = request.getParameter("team");			// 组id
		String rightgrpId = request.getParameter("rightgrp");	// 权限组
		
		if(StringUtil.isEmpty(name)) {
			request.setAttribute(ServletConstants.REQ_MSG, "姓名不能为空");
			addView(request, response);
			return;
		}
		
		if(StringUtil.isEmpty(empno)) {
			request.setAttribute(ServletConstants.REQ_MSG, "工号不能为空");
			addView(request, response);
			return;
		}
		
		if(StringUtil.isEmpty(account)) {
			request.setAttribute(ServletConstants.REQ_MSG, "账号不能为空");
			addView(request, response);
			return;
		}
		
		Integer teamIdInt = ( StringUtil.isEmpty(teamId) ? null : Integer.valueOf(teamId) );
		Integer rightgrpIdInt = ( StringUtil.isEmpty(rightgrpId) ? null : Integer.valueOf(rightgrpId) );
		
		UserEvt evt = new UserEvt();
		evt.setAccount(account);
		evt.setEmpno(empno);
		evt.setName(name);
		evt.setTeamId(teamIdInt);
		evt.setRightgrpId(rightgrpIdInt);
		
		// 获取待修改记录的id
		String id = request.getParameter("id");
		
		if(StringUtil.isEmpty(id)) {
			// id为null表示新增保存
			evt.setOpertype(1);
			evt.setPassword(Constants.INIT_PASSWORD);
		} else {
			// 修改保存
			evt.setId(Integer.valueOf(id));
			evt.setOpertype(2);
		}
		
		BeanResult bResult = ServicesFactory.instance().getUserService().userManage(evt);
		request.setAttribute(ServletConstants.REQ_MSG, bResult.getResultDesc());
		
		if(StringUtil.isEmpty(id)) {
			addView(request, response);
		} else {
			modView(request, response);
		}
	}

	/**
	 * 新增页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addView(HttpServletRequest request,
			HttpServletResponse response) 
			throws ServletException, IOException {
		
		// 获取未被分配的用户集合
		String sql = "select team_name, id from kq_team";
		List<Map<String, String>> teams = DBUtil.executeQuery(sql);
		request.setAttribute("teams", teams);
		
		// 获取权限组集合
		String rightgrpSql = "select * from kq_rightgrp";
		List<Map<String, String>> rightgrps = DBUtil.executeQuery(rightgrpSql);
		request.setAttribute("rightgrps", rightgrps);
		
		request.getRequestDispatcher("/page/user_edit.jsp").forward(request, response);
	}

	public class Opertype {
		public static final String VIEW = "view";			// 主界面
		public static final String ADD_VIEW = "add_view";	// 新增界面
		public static final String MOD_VIEW = "mod_view";	// 修改界面
		public static final String SAVE = "save";			// 保存
		public static final String DELETE = "delete";		// 保存
	}

}
