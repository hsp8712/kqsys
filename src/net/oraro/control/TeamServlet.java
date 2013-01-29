package net.oraro.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oraro.bean.Team;
import net.oraro.bean.User;
import net.oraro.factory.ServicesFactory;
import cn.huangshaoping.page.Page;

public class TeamServlet extends HttpServlet {

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
		String opertype = request.getParameter("opertype");
		
		if(Opertype.VIEW.equals(opertype)) {
			view(request, response);
		} else if(Opertype.ADD_VIEW.equals(opertype)) {
			addView(request, response);
		} else if(Opertype.MOD_VIEW.equals(opertype)) {
			modView(request, response);
		} else if(Opertype.SAVE.equals(opertype)) {
			save(request, response);
		} else {
			throw new ServletException("请求的操作不存在<opertype=" + opertype + ">.");
		}
		return;
	}
	
	private void view(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String pageNoStr = request.getParameter("pageNo");
		int pageNo = pageNoStr == null ? 0 : Integer.valueOf(pageNoStr);
		Page<Team> page = ServicesFactory.instance().getTeamService().getAllTeams(pageNo);
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
		String managerId = request.getParameter("manager");
		String description = request.getParameter("description");
		
		
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
		List<User> users = ServicesFactory.instance().getTeamService().getNoTeamUsers();
		
		request.setAttribute("noTeamUsers", users);
		request.getRequestDispatcher("/page/team_edit.jsp").forward(request, response);
	}

	public class Opertype {
		public static final String VIEW = "view";			// 主界面
		public static final String ADD_VIEW = "add_view";	// 新增界面
		public static final String MOD_VIEW = "mod_view";	// 修改界面
		public static final String SAVE = "save";			// 保存
	}

}
