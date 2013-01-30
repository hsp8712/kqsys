package cn.huangshaoping.page.tag;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import cn.huangshaoping.page.Page;

/**
 * 分页数据项标签
 * @author 6261000301
 *
 */
public class HTableBottomTag extends BodyTagSupport {
	
	
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	
	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}
	
	
	public int doEndTag() throws JspException {
		
		// 获取父标签对象
		Tag tag = this.getParent();
		HTableTag tableTag = null;
		if(tag == null || !( tag instanceof HTableTag )) {
			return EVAL_PAGE;
		}
		
		tableTag = (HTableTag)tag;
		
		// 表单名称
		String formName = tableTag.getFormName();
		
		// 获取page对象分页信息
		Page page = tableTag.getPage();
		
		String sb = null;
		try {
			sb = ResourceTemplate.getInstance().loadTemplate("com/zte/cms/common/taglib/TableBottom.template");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if(sb != null) {
			sb.replaceAll("#formName", formName);										// 表单名称
			sb.replaceAll("#totalSize", String.valueOf(page.getTotalSize()));			// 总记录数
			sb.replaceAll("#currentPageNo", String.valueOf(page.getPageNo() + 1));		// 当前页号
			sb.replaceAll("#totalPageNum", String.valueOf(page.getTotalPage()));		// 总页数
			sb.replaceAll("#firstPageNo", page.getPageNo() == 0 ? "-1" : "0");		// 总页数
			sb.replaceAll("#prePageNo", String.valueOf(page.getPageNo() == 0 ? -1 : ( page.getPageNo() - 1) ));		// 总页数
			sb.replaceAll("#nextPageNo", String.valueOf(page.getPageNo() == ( page.getTotalPage() - 1 ) ? -1 : ( page.getPageNo() + 1 ) ));		// 总页数
			sb.replaceAll("#lastPageNo", String.valueOf(page.getPageNo() == ( page.getTotalPage() - 1 ) ? -1 : ( page.getTotalPage() - 1 ) ));		// 总页数
		}
		
		JspWriter out = this.bodyContent.getEnclosingWriter();
		try {
			out.append(sb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return EVAL_PAGE;
	}
	
}
