package cn.huangshaoping.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;

import cn.huangshaoping.bean.Team;
import cn.huangshaoping.bean.User;
import cn.huangshaoping.common.Constants;
import cn.huangshaoping.db.DBUtil;
import cn.huangshaoping.page.Page;
import cn.huangshaoping.util.SXSSFExcel;
import cn.huangshaoping.util.StringUtil;



public class CheckinRecordServlet extends HttpServlet {
	
	private Logger log = Logger.getLogger(CheckinServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String opertype = request.getParameter(ServletConstants.REQ_PARAM_OPERTYPE);
		if(Opertype.VIEW.equals(opertype)) {
			request.getRequestDispatcher("/page/checkin_record_view.jsp").forward(request, response);
		} else if(Opertype.QUERY.equals(opertype)) {
			
			// 查询
			String pageNoStr = request.getParameter("pageNo");
			int pageNo = pageNoStr == null ? 0 : Integer.valueOf(pageNoStr);
			
			List<Map<String, String>> records = getDatas(request);
			
			Page<Map<String, String>> page = new Page<Map<String, String>>(records, Constants.PAGE_SIZE, pageNo);
			request.setAttribute(ServletConstants.REQ_PAGE, page);
			
			// 加载打卡界面
			request.getRequestDispatcher("/page/checkin_record_view.jsp").forward(request, response);
			return;
		} else if(Opertype.EXPORT.equals(opertype)) {
			
			List<Map<String, String>> records = getDatas(request);
			
			// 导出
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=checkin_record.xlsx");

			String[] keys = {"name", "check_ip", "check_time"};
			Map<String, String> titles = new HashMap<String, String>();
			titles.put(keys[0], "姓名");
			titles.put(keys[1], "打卡IP");
			titles.put(keys[2], "日期时间");
			records.add(0, titles);
			
			String filePath = SXSSFExcel.createExcel(records, keys);
			InputStream is = new FileInputStream(filePath);
			ServletOutputStream os = response.getOutputStream();
			
			byte[] b = new byte[1024];
			int len;
			while((len = is.read(b)) != -1) {
				os.write(b, 0, len);
			}
			
			os.flush();
			
			is.close();
			os.close();
			
		} else {
			throw new ServletException("请求的操作不存在<opertype=" + opertype + ">.");
		}
		
	}
	
	/**
	 * Get checkin records by list
	 * @param request
	 * @return
	 */
	private List<Map<String, String>> getDatas(HttpServletRequest request) {
		// 当前登录用户所在组
		User curUser = (User)request.getSession().getAttribute(LoginAndOutServlet.SESSIONKEY_CURRENT_USER);
		Team team = curUser.getTeam();
		if(team == null) {
			return null;
		}
		Integer curTeamId = team.getId();
		if(curTeamId == null) {
			return null;
		}
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		String name  = request.getParameter("name");
		
		StringBuffer sqlStrBuf = new StringBuffer("select date_format(a.check_time,'%Y-%m-%d %H:%i:%s') as check_time, ");
		sqlStrBuf.append("a.check_ip, b.name from kq_checkinrecord a left join kq_user b on a.user_id=b.id ");
		sqlStrBuf.append("where a.user_id in (select id from kq_user where team_id=");
		sqlStrBuf.append(curTeamId);
		sqlStrBuf.append(") ");
		
		if(!StringUtil.isEmpty(name)) {
			sqlStrBuf.append(" and b.name like '%");
			sqlStrBuf.append(name);
			sqlStrBuf.append("%'");
		}
		
		if(!StringUtil.isEmpty(startDate)) {
			sqlStrBuf.append(" and date_format(a.check_time,'%Y-%m-%d')>='");
			sqlStrBuf.append(startDate);
			sqlStrBuf.append("'");
		}
		
		if(!StringUtil.isEmpty(endDate)) {
			sqlStrBuf.append(" and date_format(a.check_time,'%Y-%m-%d')<='");
			sqlStrBuf.append(endDate);
			sqlStrBuf.append("'");
		}
		
		List<Map<String, String>> records = DBUtil.executeQuery(sqlStrBuf.toString());
		return records;
	}

	/**
	 * 操作
	 * @author oraro
	 */
	public class Opertype {
		public static final String VIEW = "view";				// 打卡记录界面
		public static final String QUERY = "query";				// 打卡记录查询
		public static final String EXPORT = "export";			// 打卡记录导出
	}

}
