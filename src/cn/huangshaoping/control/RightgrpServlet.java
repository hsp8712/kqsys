package cn.huangshaoping.control;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;

import cn.huangshaoping.common.Constants;
import cn.huangshaoping.db.DBUtil;
import cn.huangshaoping.page.Page;
import cn.huangshaoping.service.ServicesFactory;
import cn.huangshaoping.service.bean.evt.RightgrpEvt;
import cn.huangshaoping.service.bean.evt.RightgrpMemEvt;
import cn.huangshaoping.service.bean.evt.TeamMemEvt;
import cn.huangshaoping.service.bean.result.BeanResult;
import cn.huangshaoping.service.bean.result.Result;
import cn.huangshaoping.util.StringUtil;



public class RightgrpServlet extends HttpServlet {
	
	private Logger log = Logger.getLogger(RightgrpServlet.class);

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
		} else if(Opertype.MEM_VIEW.equals(opertype)) {
			memView(request, response);
		} else if(Opertype.MEM_ADD.equals(opertype)) {
			memAdd(request, response);
		} else if(Opertype.MEM_REMOVE.equals(opertype)) {
			memRemove(request, response);
		} else {
			throw new ServletException("请求的操作不存在<opertype=" + opertype + ">.");
		}
		return;
	}
	
	private void memView(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String rightgrpId = request.getParameter("id");
		if(StringUtil.isEmpty(rightgrpId)) {
			throw new ServletException("Modify rightgrp: rightgrp id cannot be null or empty.");
		}
		
		Map<String, String> rightgrp  = DBUtil.executeQueryOne("select * from kq_rightgrp where id=" + rightgrpId);
		request.setAttribute("rightgrp", rightgrp);
		
		// 查询权限组成员权限
		String sqlmem = "select b.id, b.right_name from kq_rightgrp_right a, kq_right b where a.right_id=b.id and a.rightgrp_id=" + rightgrpId;
		List<Map<String, String>> memRights = DBUtil.executeQuery(sqlmem);
		
		// 所有权限
		String sqlnomem = "select id, right_name from kq_right c where c.id not in(" +
		"select b.id from kq_rightgrp_right a, kq_right b where a.right_id=b.id and a.rightgrp_id=" + rightgrpId + ")";
		List<Map<String, String>> rights = DBUtil.executeQuery(sqlnomem);
		
		request.setAttribute("memRights", memRights);
		request.setAttribute("rights", rights);
		
		request.getRequestDispatcher("/page/rightgrp_mem_view.jsp").forward(request, response);
	}

	private void memRemove(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String rightgrpId = request.getParameter("id");
		String rightId = request.getParameter("rightId");
		if(StringUtil.isEmpty(rightgrpId) || StringUtil.isEmpty(rightId)) {
			throw new ServletException("Modify rightgrp member: rightgrp id and right id cannot be null or empty.");
		}
		
		RightgrpMemEvt evt = new RightgrpMemEvt();
		evt.setOpertype(2);		// 添加操作
		evt.setRightgrpId(Integer.valueOf(rightgrpId));
		evt.setRightId(Integer.valueOf(rightId));
		
		Result result = ServicesFactory.instance().getRightgrpService().rightgrpMemManage(evt);
		request.setAttribute(ServletConstants.REQ_MSG, result.getResultDesc());
		
		memView(request, response);
	}

	private void memAdd(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String rightgrpId = request.getParameter("id");
		String rightId = request.getParameter("rightId");
		if(StringUtil.isEmpty(rightgrpId) || StringUtil.isEmpty(rightId)) {
			throw new ServletException("Modify rightgrp member: rightgrp id and right id cannot be null or empty.");
		}
		
		RightgrpMemEvt evt = new RightgrpMemEvt();
		evt.setOpertype(1);		// 添加操作
		evt.setRightgrpId(Integer.valueOf(rightgrpId));
		evt.setRightId(Integer.valueOf(rightId));
		
		Result result = ServicesFactory.instance().getRightgrpService().rightgrpMemManage(evt);
		request.setAttribute(ServletConstants.REQ_MSG, result.getResultDesc());
		
		memView(request, response);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String id = request.getParameter("id");
		if(StringUtil.isEmpty(id)) {
			throw new ServletException("Delete rightgrp: rightgrp id cannot be null or empty.");
		}
		
		RightgrpEvt evt = new RightgrpEvt();
		evt.setRightgrpId(Integer.valueOf(id));
		evt.setOpertype(3);
		
		BeanResult bResult = ServicesFactory.instance().getRightgrpService().rightgrpManage(evt);
		request.setAttribute(ServletConstants.REQ_MSG, bResult.getResultDesc());
		
		view(request, response);
	}

	private void view(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String pageNoStr = request.getParameter("pageNo");
		int pageNo = pageNoStr == null ? 0 : Integer.valueOf(pageNoStr);
		
		List<Map<String, String>> rightgrps = DBUtil.executeQuery("select * from kq_rightgrp where id<>1");
		
		Page<Map<String, String>> page = new Page<Map<String, String>>(rightgrps, Constants.PAGE_SIZE, pageNo);
		request.setAttribute(ServletConstants.REQ_PAGE, page);
		request.getRequestDispatcher("/page/rightgrp_view.jsp").forward(request, response);
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
			throw new ServletException("Modify rightgrp: rightgrp id cannot be null or empty.");
		}
		
		Map<String, String> map = DBUtil.executeQueryOne("select * from kq_rightgrp where id=" + id);
		request.setAttribute("rightgrp", map);
		
		request.getRequestDispatcher("/page/rightgrp_edit.jsp").forward(request, response);
	}

	/**
	 * 保存
	 * @param request
	 * @param response
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// 获取参数
		String rightgrpName = request.getParameter("rightgrpName");
		
		if(StringUtil.isEmpty(rightgrpName)) {
			request.setAttribute(ServletConstants.REQ_MSG, "权限组名不能为空");
			addView(request, response);
			return;
		}
		
		RightgrpEvt evt = new RightgrpEvt();
		evt.setRightgrpName(rightgrpName);
		
		// 获取待修改记录的id
		String id = request.getParameter("id");
		
		if(StringUtil.isEmpty(id)) {
			// id为null表示新增保存
			evt.setOpertype(1);	
		} else {
			// 修改保存
			evt.setRightgrpId(Integer.valueOf(id));
			evt.setOpertype(2);
		}
		
		BeanResult bResult = ServicesFactory.instance().getRightgrpService().rightgrpManage(evt);
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
		
		request.getRequestDispatcher("/page/rightgrp_edit.jsp").forward(request, response);
	}

	public class Opertype {
		public static final String VIEW = "view";			// 主界面
		public static final String ADD_VIEW = "add_view";	// 新增界面
		public static final String MOD_VIEW = "mod_view";	// 修改界面
		public static final String SAVE = "save";			// 保存
		public static final String DELETE = "delete";		// 保存
		
		public static final String MEM_VIEW = "mem_view";		// 成员管理界面
		public static final String MEM_ADD = "mem_add";			// 添加成员
		public static final String MEM_REMOVE = "mem_remove";	// 移除成员
	}

}
