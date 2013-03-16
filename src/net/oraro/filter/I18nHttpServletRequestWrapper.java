package net.oraro.filter;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class I18nHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private Map hashMap = new HashMap();
	  private String charset = "iso-8859-1";
	  public I18nHttpServletRequestWrapper(HttpServletRequest request, String charset) {
	    super(request);
	    this.charset = charset;
	    initParameterMap(request);
	  }
	  private void initParameterMap(HttpServletRequest request) {
	    if (request == null) {
	       return;
	    }
	    Map map = request.getParameterMap();
	    Set names = map.keySet();
	    for (Iterator i = names.iterator(); i.hasNext(); ) {
	        String name = (String)i.next();
	        this.hashMap.put(name, map.get(name));
	    }
	  }
	  public String getParameter(String name) {
	    String[] values = this.getParameterValues(name);
	    if (values != null && values.length > 0) {
	       return values[0];
	    } else {
	       return null;
	    }
	  }

	  public String[] getParameterValues(String name) {
	    String[] values = (String[])this.hashMap.get(name);
	    if (values == null) {
	       return null;
	    }
	    String[] result = new String[values.length];
	    for (int i = 0; values != null && i < values.length; i++) {
	        result[i] = convertCharset(values[i]);
	    }
	    return result;
	  }
	  private String convertCharset(String str) {
	    if (str == null) {
	       return null;
	    }
	    try {
	      byte[] bs = str.getBytes("iso-8859-1");
	      String result = new String(bs, this.charset);
	      return result;
	    } catch (UnsupportedEncodingException ex) {
	    }
	    return null;
	  }
}
