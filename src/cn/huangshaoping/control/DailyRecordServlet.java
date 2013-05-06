package cn.huangshaoping.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.huangshaoping.bean.Team;
import cn.huangshaoping.bean.User;
import cn.huangshaoping.common.Constants;
import cn.huangshaoping.db.DBUtil;
import cn.huangshaoping.page.Page;
import cn.huangshaoping.util.SXSSFExcel;
import cn.huangshaoping.util.StringUtil;



/**
 * 日考勤记录管理
 * @author 6261000301
 *
 */
public class DailyRecordServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opertype = request.getParameter(ServletConstants.REQ_PARAM_OPERTYPE);
		
		if(Opertype.QUERY.equals(opertype)) {
			
			// 查询
			String pageNoStr = request.getParameter("pageNo");
			int pageNo = pageNoStr == null ? 0 : Integer.valueOf(pageNoStr);
			
			List<Map<String, String>> dailyRecords = getDatas(request);
			
			Page<Map<String, String>> page = new Page<Map<String, String>>(dailyRecords, Constants.PAGE_SIZE, pageNo);
			request.setAttribute(ServletConstants.REQ_PAGE, page);
			request.getRequestDispatcher("/page/daily_record_view.jsp").forward(request, response);
			return;
		} else if(Opertype.EXPORT.equals(opertype)) {
			
			// 导出
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=daily_record.xlsx");

			List<Map<String, String>> dailyRecords = getDatas(request);
			String[] keys = {"record_date", "empno", "name", "first_time", "last_time", "over_time", "over_time_hour"};
			Map<String, String> titles = new HashMap<String, String>();
			titles.put(keys[0], "日期");
			titles.put(keys[1], "工号");
			titles.put(keys[2], "姓名");
			titles.put(keys[3], "首次打卡时间");
			titles.put(keys[4], "最后打卡时间");
			titles.put(keys[5], "加班开始时间");
			titles.put(keys[6], "加班时长");
			dailyRecords.add(0, titles);
			
			String filePath = SXSSFExcel.createExcel(dailyRecords, keys);
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
		
		String sql = "select b.empno, b.name, a.record_date, a.first_time, a.last_time, a.over_time, a.over_time_hour" + 
				" from kq_dailyrecord a, kq_user b where a.user_id=b.id and user_id in " + 
				"(select id from kq_user where team_id=" + curTeamId + ") ";
		
		if(!StringUtil.isEmpty(startDate)) {
			sql += " and date_format(a.record_date, '%Y-%m-%d')>='" + startDate + "'";
		} else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			startDate = df.format(new Date());
			sql += " and date_format(a.record_date, '%Y-%m-%d')>='" + startDate + "'";
		}
		
		if(!StringUtil.isEmpty(endDate)) {
			sql += " and date_format(a.record_date, '%Y-%m-%d')<='" + endDate + "'";
		} else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			endDate = df.format(new Date());
			sql += " and date_format(a.record_date, '%Y-%m-%d')>='" + endDate + "'";
		}
		
		List<Map<String, String>> dailyRecords = DBUtil.executeQuery(sql);
		
		return dailyRecords;
	}
	
	public class Opertype {
		public static final String QUERY = "query";			// 查询
		public static final String EXPORT = "export";		// 导出
	}

}
