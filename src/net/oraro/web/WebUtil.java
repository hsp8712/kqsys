package net.oraro.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Web工具
 * @author 6261000301
 *
 */
public class WebUtil {
	
	/**
	 * 清除当前session，并创建新的session
	 * @param request
	 * @return
	 */
	public static HttpSession getNewSession(HttpServletRequest request) {
		if(request == null) return null;
		clearSession(request);
		return request.getSession();
	}
	
	/**
	 * 使当前session失效
	 * @param request
	 */
	public static void clearSession(HttpServletRequest request) {
		
		// 获取与request关联的session，如果session不存在，不创建新的session，返回null
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			return;
		}
		
		session.invalidate();
		Cookie[] cookies = request.getCookies();
		
		if( cookies == null || cookies.length <= 0 ) {
			return;
		}
		
		for (Cookie cookie : cookies) {
			if( cookie == null ) continue;
			if("JSESSIONID".equals(cookie.getName())) {
				cookie.setMaxAge(0);	// 设置cookie立即过期
			}
		}
		
	}
	
	
	/** 
     * MD5 加密 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
     */  
    private String getMD5Str(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	
    	if(str == null) {
    		return null;
    	}
    	
        MessageDigest messageDigest = null;  
        messageDigest = MessageDigest.getInstance("MD5");  
        messageDigest.reset();  
        messageDigest.update(str.getBytes("UTF-8"));  
        
        byte[] byteArray = messageDigest.digest();  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1){
            	md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            } else {
            	md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
            }
        }
  
        return md5StrBuff.toString();
    } 
    
    /**
     * 发送javascript脚本
     * @param response
     * @param script
     * @throws IOException
     */
    public static void sendScript(HttpServletResponse response, String script) throws IOException {
    	PrintWriter out = response.getWriter();
    	out.println("<script language=\"javascript\">");
    	out.println(script);
    	out.print("</script>");
    	out.flush();
    	out.close();
    }
}
