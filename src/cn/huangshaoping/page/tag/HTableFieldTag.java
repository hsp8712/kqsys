package cn.huangshaoping.page.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

/**
 * 分页数据项标签
 * @author 6261000301
 *
 */
public class HTableFieldTag extends BodyTagSupport {
	
	private Logger log = Logger.getLogger(HTableFieldTag.class);
	
	/** 标题 */
	private String title;
	
	
	public int doStartTag() throws JspException {
		
		Tag tag = this.getParent();
		HTableTag tableTag = null;
		if(tag == null || !( tag instanceof HTableTag )) {
			return SKIP_BODY;
		}
		
		tableTag = (HTableTag)tag;
		
		if(tableTag.getIterateCount() == 0) {
			tableTag.addTitle(title);
		}
		
		return EVAL_BODY_BUFFERED;
	}

	
	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}
	
	
	public int doEndTag() throws JspException {
		
		JspWriter out = null;
		
		String currentBodyContent = null;
		
		if(this.bodyContent == null) {
			out = this.pageContext.getOut();
			currentBodyContent = "";
		} else {
			out = this.bodyContent.getEnclosingWriter();
			currentBodyContent = this.bodyContent.getString();
		}
			
		try {
			out.append("<td>");
			out.append(currentBodyContent);
			out.append("</td>");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
		return EVAL_PAGE;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
