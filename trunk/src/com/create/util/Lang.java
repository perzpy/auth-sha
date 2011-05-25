package com.create.util;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

/**
 * 一些JAVA帮助函数
 *
 * @author perzer,cxb
 * @date Feb 24, 2011
 */
public class Lang {
	/**
	 * 透过VPN取客户端IP
	 * 
	 * @return
	 */
	public static String getRemoteClientIp() {
		WebContext wc = WebContextFactory.get();
		HttpServletRequest request = wc.getHttpServletRequest();
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
