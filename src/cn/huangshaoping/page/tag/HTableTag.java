package cn.huangshaoping.page.tag;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import cn.huangshaoping.page.Page;

/**
 * 分页标签
 * @author 6261000301
 *
 */
public class HTableTag extends BodyTagSupport {
	
	private Logger log = Logger.getLogger(HTableTag.class);
	
	/** 数据集合变量名 */
	private String name;
	
	/** 是否显示分页 */
	private boolean showPageBottom;
	
	/** 分页信息对象 */
	private Page page;
	
	private Iterator it;
	
	/** 值对象引用变量名 */
	private String var;
	
	/** 保存原始pageContext对象中，var为key的对象 */
	private Object oldVarObj;
	
	/** 表格样式类 */
	private String tableClass;
	
	/** 表单名称 */
	private String formName;
	
	/** 最终的jsp输出 */
	private StringBuffer finalJspString;
	
	/** 迭代计数 */
	private int iterateCount;
	
	/** 标题集合 */
	private List<String> titles;
	
	/** 是否有分页数据 */
	private boolean haveNoDatas;
	
	
	public int doStartTag() throws JspException {
		
		// 赋初始值
		haveNoDatas = false;
		iterateCount = 0;
		titles = new ArrayList<String>();
		finalJspString = new StringBuffer();
		it = null;
		
		Object obj = this.pageContext.getRequest().getAttribute(name);
		
		// 原始pageContext对象中，var为key的对象
		oldVarObj = this.pageContext.getAttribute(var);
		if(oldVarObj != null) {
			this.pageContext.removeAttribute(var);
		}
		
		if(obj == null || !(obj instanceof Page)) {
			haveNoDatas = true;
			return EVAL_BODY_BUFFERED;
			
		}
		
		page = (Page) obj;
		it = page.getRecordList().iterator();
		
		if(it != null && it.hasNext()) {
			iterateCount = 0;
			this.pageContext.setAttribute(var, it.next());
		} else {
			haveNoDatas = true;
		}
		
		return EVAL_BODY_BUFFERED;
	}
	
	
	public int doAfterBody() throws JspException {
		
		finalJspString.append("<tr>");
		
		// 无数据，添加标识行
		if(haveNoDatas) {
			finalJspString.append("<td colspan='" + titles.size() + "'>");
			finalJspString.append("暂无数据！");
			finalJspString.append("</td>");
			finalJspString.append("</tr>");
			return SKIP_BODY;
		} 
		
		finalJspString.append(this.bodyContent.getString());
		finalJspString.append("</tr>");
		
		if(it.hasNext()) {
			
			// 下一次迭代，计数加1
			iterateCount++;
			
			this.pageContext.setAttribute(var, it.next());
			
			// 清空上一次的标签体计算内容，用于存储下一次的标签体内容
			try {
				this.bodyContent.clear();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			
			return EVAL_BODY_AGAIN;
		}
		
		return SKIP_BODY;
	}
	
	
	public int doEndTag() throws JspException {

		JspWriter out = this.bodyContent.getEnclosingWriter();

		// 输入表格
		try {
			out.append("<table class='" + tableClass + "'>");
			
			// 标题行
			out.append("<tr>");
			for (int i = 0; i < titles.size(); i++) {
				out.append("<th>" + titles.get(i) + "</th>");
			}
			out.append("</tr>");
			
			out.append(finalJspString.toString());
			
			if(!haveNoDatas && showPageBottom) {
				out.append("<tr><td colspan=\"");
				out.append(String.valueOf(titles.size()));
				out.append("\">");
				out.append(getPageBottomHtml());
				out.append("</td></tr>");
			}
			
			out.append("</table>");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
		// 清空标题集合
		titles.clear();
		
		// 恢复原始pageContext对象中，var为key的对象
		this.pageContext.setAttribute(var, oldVarObj);
		
		return EVAL_PAGE;
	}
	
	/**
	 * 获取分页栏html代码
	 * @return
	 */
	private String getPageBottomHtml() {
		
		if(page == null) return null;
		
		String tableBottomHtml = null;
		try {
			tableBottomHtml = ResourceTemplate.getInstance().loadTemplate("cn/huangshaoping/page/tag/HTableBottom.template");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		
		if(tableBottomHtml != null) {
			tableBottomHtml = tableBottomHtml.replaceAll("#formName", formName);										// 表单名称
			tableBottomHtml = tableBottomHtml.replaceAll("#totalSize", String.valueOf(page.getTotalSize()));			// 总记录数
			tableBottomHtml = tableBottomHtml.replaceAll("#currentPageNo", String.valueOf(page.getPageNo() + 1));		// 当前页号
			tableBottomHtml = tableBottomHtml.replaceAll("#totalPageNum", String.valueOf(page.getTotalPage()));		// 总页数
			
			int pageNo = page.getPageNo();
			int totalPage = page.getTotalPage();
			tableBottomHtml = tableBottomHtml.replaceAll("#firstPageNo", pageNo == 0 ? "-1" : "0");		// 首页
			tableBottomHtml = tableBottomHtml.replaceAll("#firstStyle", pageNo == 0 ? "" : "cursor:pointer;");
			
			tableBottomHtml = tableBottomHtml.replaceAll("#prePageNo", 
					pageNo == 0 ? "-1" : (pageNo - 1) + "");											// 上页
			tableBottomHtml = tableBottomHtml.replaceAll("#preStyle", 
					pageNo == 0 ? "" : "cursor:pointer;");
			
			tableBottomHtml = tableBottomHtml.replaceAll("#nextPageNo", 
					pageNo == ( totalPage - 1 ) ? "-1" : (pageNo + 1) + "");							// 下页
			tableBottomHtml = tableBottomHtml.replaceAll("#nextStyle", 
					pageNo == ( totalPage - 1 ) ? "" : "cursor:pointer;");
			
			tableBottomHtml = tableBottomHtml.replaceAll("#lastPageNo", 
					pageNo == ( totalPage - 1 ) ? "-1" : (totalPage - 1) + "");							// 最后页
			tableBottomHtml = tableBottomHtml.replaceAll("#lastStyle", 
					pageNo == ( totalPage - 1 ) ? "" : "cursor:pointer;");
		}
		
		return tableBottomHtml;
	}
	
	public void setVar(String var) {
		this.var = var;
	}

	public void setTableClass(String tableClass) {
		this.tableClass = tableClass;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIterateCount() {
		return iterateCount;
	}
	
	// 添加标题
	public void addTitle(String title) {
		this.titles.add(title);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getFormName() {
		return formName;
	}

	public boolean isShowPageBottom() {
		return showPageBottom;
	}

	public void setShowPageBottom(boolean showPageBottom) {
		this.showPageBottom = showPageBottom;
	}

	
	
}


