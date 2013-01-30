package net.oraro.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.huangshaoping.page.Page;

import net.oraro.bean.DailyRecord;
import net.oraro.factory.ServicesFactory;
import net.oraro.service.DailyRecordService;

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
		
		DailyRecordService service = ServicesFactory.instance().getDailyRecordService();
		
		if(Opertype.QUERY.equals(opertype)) {
			
			// 查询
			
			String pageNoStr = request.getParameter("pageNo");
			int pageNo = pageNoStr == null ? 0 : Integer.valueOf(pageNoStr);
			Page<DailyRecord> page = service.getPageDailyRecords(pageNo);
			request.setAttribute(ServletConstants.REQ_PAGE, page);
			request.getRequestDispatcher("/page/daily_record_view.jsp").forward(request, response);
			return;
		} else if(Opertype.EXPORT.equals(opertype)) {
			
			// 导出
			
		} else {
			throw new ServletException("请求的操作不存在<opertype=" + opertype + ">.");
		}
		
	}
	
	public class Opertype {
		public static final String QUERY = "query";			// 查询
		public static final String EXPORT = "export";		// 导出
	}

}
