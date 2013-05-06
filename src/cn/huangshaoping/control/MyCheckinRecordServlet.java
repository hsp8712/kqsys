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

import cn.huangshaoping.bean.User;
import cn.huangshaoping.common.Constants;
import cn.huangshaoping.db.DBUtil;
import cn.huangshaoping.page.Page;
import cn.huangshaoping.util.SXSSFExcel;
import cn.huangshaoping.util.StringUtil;



public class MyCheckinRecordServlet extends HttpServlet {
	
	private Logger log = Logger.getLogger(CheckinServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String opertype = request.getParameter(ServletConstants.REQ_PARAM_OPERTYPE);
		if(Opertype.VIEW_MY.equals(opertype)) {
			request.getRequestDispatcher("/page/my_checkin_record_view.jsp").forward(request, response);
		} else if(Opertype.QUERY_MY.equals(opertype)) {
			
			// 查询
			String pageNoStr = request.getParameter("pageNo");
			int pageNo = pageNoStr == null ? 0 : Integer.valueOf(pageNoStr);
			
			List<Map<String, String>> records = getMyDatas(request);
			
			Page<Map<String, String>> page = new Page<Map<String, String>>(records, Constants.PAGE_SIZE, pageNo);
			request.setAttribute(ServletConstants.REQ_PAGE, page);
			
			// 加载打卡界面
			request.getRequestDispatcher("/page/my_checkin_record_view.jsp").forward(request, response);
			return;
		} else if(Opertype.EXPORT_MY.equals(opertype)) {
			
			List<Map<String, String>> records = getMyDatas(request);
			
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
	
	private List<Map<String, String>> getMyDatas(HttpServletRequest request) {
		// 当前登录用户
		User curUser = (User)request.getSession().getAttribute(LoginAndOutServlet.SESSIONKEY_CURRENT_USER);
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		StringBuffer sqlStrBuf = new StringBuffer("select date_format(a.check_time,'%Y-%m-%d %H:%i:%s') as check_time, ");
		sqlStrBuf.append("a.check_ip, b.name from kq_checkinrecord a left join kq_user b on a.user_id=b.id ");
		sqlStrBuf.append("where a.user_id=");
		sqlStrBuf.append(curUser.getId());
		
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
		
		sqlStrBuf.append(" order by a.check_time desc");
		
		List<Map<String, String>> records = DBUtil.executeQuery(sqlStrBuf.toString());
		return records;
	}
	
	/**
	 * 操作
	 * @author oraro
	 */
	public class Opertype {
		public static final String VIEW_MY = "view_my";				// 我的打卡记录
		public static final String QUERY_MY = "query_my";			// 我的打卡记录查询
		public static final String EXPORT_MY = "export_my";			// 我的打卡记录导出
	}

}
