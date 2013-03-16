package net.oraro.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class I18nServletFilter extends HttpServlet implements Filter {
	private FilterConfig filterConfig;
	private String charset = "UTF-8";
	public void destroy() {
		super.destroy();
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		try {
	        if (this.charset != null) {
	           I18nHttpServletRequestWrapper requestWrapper = new I18nHttpServletRequestWrapper((HttpServletRequest)arg0, charset);
	           arg2.doFilter(requestWrapper, arg1);
	        } else {
	        	arg2.doFilter(arg0, arg1);
	        }
	    } catch(ServletException sx) {
	      filterConfig.getServletContext().log(sx.getMessage());
	    } catch(IOException iox) {
	      filterConfig.getServletContext().log(iox.getMessage());
	    }

	}

	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	    this.charset = filterConfig.getInitParameter("charset");
	    if (this.charset == null || this.charset.trim().length() == 0) {
	       this.charset = null;
	    }

	}

}
