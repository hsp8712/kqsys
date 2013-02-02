package net.oraro.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oraro.bean.Team;
import net.oraro.bean.User;
import net.oraro.common.Constants;
import net.oraro.dao.DaoFactory;
import net.oraro.exception.DataAccessException;
import net.oraro.service.ServicesFactory;
import net.oraro.service.bean.evt.TeamEvt;
import net.oraro.service.bean.result.BeanResult;
import net.oraro.util.StringUtil;

import org.apache.log4j.Logger;

import cn.huangshaoping.page.Page;

public class TeamServlet extends HttpServlet {
	
	private Logger log = Logger.getLogger(TeamServlet.class);

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
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
			throw new ServletException("Modify team: team id cannot be null or empty.");
		}
		
		TeamEvt evt = new TeamEvt();
		evt.setTeamId(Integer.valueOf(id));
		evt.setOpertype(3);
		
		BeanResult bResult = ServicesFactory.instance().getTeamService().teamManage(evt);
		request.setAttribute(ServletConstants.REQ_MSG, bResult.getResultDesc());
		
		view(request, response);
	}

	private void view(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String pageNoStr = request.getParameter("pageNo");
		int pageNo = pageNoStr == null ? 0 : Integer.valueOf(pageNoStr);
		
		List<Team> teams = null;
		try {
			teams = DaoFactory.getInstance().getTeamDao().queryAll();
		} catch (DataAccessException e) {
			log.error(e.getMessage());
		}
		
		Page<Team> page = new Page<Team>(teams, Constants.PAGE_SIZE, pageNo);
		request.setAttribute(ServletConstants.REQ_PAGE, page);
		request.getRequestDispatcher("/page/team_view.jsp").forward(request, response);
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
			throw new ServletException("Modify team: team id cannot be null or empty.");
		}
		
		Team team = null;
		try {
			team = DaoFactory.getInstance().getTeamDao().queryById(Integer.valueOf(id));
		} catch (DataAccessException e1) {
			throw new ServletException(e1.getMessage());
		}
		
		request.setAttribute("team", team);
		
		// 获取未被分配的用户或该组成员用户
		String sql = "select * from kq_user where team_id is null or team_id=" + id;
		List<User> users = null;
		try {
			users = DaoFactory.getInstance().getUserDao().executeQuery(sql);
		} catch (DataAccessException e) {
			log.error(e.getMessage());
		}
		
		request.setAttribute("noTeamUsers", users);
		request.getRequestDispatcher("/page/team_edit.jsp").forward(request, response);
	}

	/**
	 * 保存
	 * @param request
	 * @param response
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// 获取参数
		String teamName = request.getParameter("teamName");
		
		if(StringUtil.isEmpty(teamName)) {
			request.setAttribute(ServletConstants.REQ_MSG, "组名不能为空");
			addView(request, response);
			return;
		}
		
		String description = request.getParameter("description");
		String managerId = request.getParameter("manager");
		Integer managerIdInt = ( StringUtil.isEmpty(managerId) ? null : Integer.valueOf(managerId) );
		
		TeamEvt evt = new TeamEvt();
		evt.setDescription(description);
		evt.setManagerId(managerIdInt);
		evt.setTeamName(teamName);
		
		
		// 获取待修改记录的id
		String id = request.getParameter("id");
		
		if(id == null) {
			// id为null表示新增保存
			evt.setOpertype(1);	
		} else {
			// 修改保存
			evt.setTeamId(Integer.valueOf(id));
			evt.setOpertype(2);
		}
		
		BeanResult bResult = ServicesFactory.instance().getTeamService().teamManage(evt);
		request.setAttribute(ServletConstants.REQ_MSG, bResult.getResultDesc());
		
		if(id == null) {
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
		String sql = "select * from kq_user where team_id is null";
		List<User> users = null;
		try {
			users = DaoFactory.getInstance().getUserDao().executeQuery(sql);
		} catch (DataAccessException e) {
			log.error(e.getMessage());
		}
		
		request.setAttribute("noTeamUsers", users);
		request.getRequestDispatcher("/page/team_edit.jsp").forward(request, response);
	}

	public class Opertype {
		public static final String VIEW = "view";			// 主界面
		public static final String ADD_VIEW = "add_view";	// 新增界面
		public static final String MOD_VIEW = "mod_view";	// 修改界面
		public static final String SAVE = "save";			// 保存
		public static final String DELETE = "delete";		// 保存
	}

}
