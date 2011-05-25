package com.create.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.create.model.User;
import com.create.security.MyHttpSession;

public class SessionFilter implements Filter {
	
	//private Logger logger = Logger.getLogger(SessionFilter.class);
	
	private String allowPage;
	private String loginPage;
	private String errorPage;

	public void init(FilterConfig filterConfig) throws ServletException {
		allowPage = filterConfig.getInitParameter("allowPage");
		loginPage = filterConfig.getInitParameter("loginPage");
		errorPage = filterConfig.getInitParameter("errorPage");
	}
	
	public void destroy() {
		allowPage = null;
		loginPage = null;
		errorPage = null;
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		try{
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			HttpSession session = request.getSession();
			MyHttpSession.setHttpSession(request.getSession());
			
			User user = (User) session.getAttribute("user");
			//String basePath = request.getContextPath();
			String path = request.getRequestURI();
			
			//logger.debug("基本路径 = " + basePath);
			//logger.debug("请求页面 = " + path);
			
			if(user != null){
				//logger.debug("用户已经登陆！");
				chain.doFilter(request, response);
			}else if(path.indexOf(loginPage) != -1){
				//logger.debug("用户没有登陆，进入登陆页面！");
				chain.doFilter(request, response);
			}else if(path.indexOf(errorPage) != -1){
				//logger.debug("用户没有登陆，进入错误页面！");
				chain.doFilter(request, response);
			}else{
				boolean flag = false;
				String[] allowPages = allowPage.split(",");
				for(int i = 0; i < allowPages.length; i ++){
					if(allowPages[i] != null && !"".equals(allowPages[i])){
						if(path.indexOf(allowPages[i]) != -1){
							flag = true;
						}
					}
				}
				if(flag){
					//logger.debug("用户没有登陆，允许进入页面！");
					chain.doFilter(request, response);
				}else{
					response.sendRedirect(errorPage);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MyHttpSession.setHttpSession(null);
		}
	}

}
