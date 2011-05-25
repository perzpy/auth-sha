package com.create.security;

import javax.servlet.http.HttpSession;

public class MyHttpSession {
	
	private static ThreadLocal threadLocal = new ThreadLocal();
	
	public static HttpSession getHttpSession(){
		return (HttpSession) threadLocal.get();
	}
	
	public static void setHttpSession(HttpSession session){
		threadLocal.set(session);
	}

}
