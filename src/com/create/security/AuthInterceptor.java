package com.create.security;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;

import com.create.exception.ApplicationException;
import com.create.model.LoginUser;

@Aspect
public class AuthInterceptor {
	private static final Log logger = LogFactory.getLog(AuthInterceptor.class);

	public synchronized Object attest(ProceedingJoinPoint pjp) throws Throwable, ApplicationException {
		LoginUser user = null;
		
		HttpSession session = MyHttpSession.getHttpSession();
		if(session != null){
			user = (LoginUser)session.getAttribute("security_user");
		}
	
	    if (user == null) {
	    	logger.warn("没有用户信息");
	    	return pjp.proceed();
	    }
	
	    String mName = pjp.toShortString();
	    mName = mName.substring(mName.indexOf("(") + 1, mName.length() - 1);
	    mName = mName.substring(mName.indexOf(".") + 1, mName.indexOf("("));
	
	    if (logger.isDebugEnabled()) {
	      logger.debug("拦截方法：" + mName);
	    }
	    Object ob = pjp.getTarget();
	    
	    Method method = null;
	    Class[] paraTypes = ((CodeSignature)pjp.getStaticPart().getSignature()).getParameterTypes();
	    method = ob.getClass().getDeclaredMethod(mName, paraTypes);
	
	    if (!method.isAnnotationPresent(Authenticate.class)) {
	    	if (logger.isDebugEnabled()) {
	    		logger.debug("该方法不需要拦截：没有带注释信息 - " + 
	    				ob.getClass().getCanonicalName() + "." + mName + "()");
	    	}
	    	return pjp.proceed();
	    }
	
	    Authenticate auth = (Authenticate)method.getAnnotation(Authenticate.class);
	    if (auth == null) {
	      logger.error("没有得到注解类 method.getAnnotation(Authenticate.class)");
	      throw new RuntimeException("严重错误！");
	    }
	
	    String v = auth.value();
	    String e = auth.expect();
	    String var = auth.variable();
	    String value = "";
		if (!v.equals("")) {
			value = v;
	    } else if (!e.equals("")) {
	    	value = e;
	    } else if (!var.equals("")) {
	    	Field f = ob.getClass().getDeclaredField(var);
	    	f.setAccessible(true);
	    	value = (String)f.get(ob);
	    }
	
		if (value.trim().equals("")) {
			if (logger.isWarnEnabled()) {
				logger.warn("缺少预设值");
			}
			return pjp.proceed();
		}
		if (logger.isInfoEnabled()) {
			logger.info("系统预设值 = " + value);
			StringBuilder sb = new StringBuilder();
			sb.append("数据库查询值 = ");
			String[] authors = user.getAuthors();
			for(int i = 0; i < authors.length; i ++ ){
				sb.append(authors[i]);
				if(i != authors.length - 1){
					sb.append(",");
				}
			}
			logger.info(sb.toString());
		}
	
	    String[] valuesAt = new String[0];
	    String[] valuesLine = new String[0];
	    if (value.indexOf("&&") != -1) {
	    	valuesAt = value.split("&&");
	    } else if (value.indexOf("||") != -1) {
	    	valuesLine = value.split("\\|\\|");
	    }

		boolean flag = false;
		if (valuesAt.length > 0) {
			boolean isValid = true;
      		for (int i = 0; i < valuesAt.length; i++) {
    	  		if (!isVaild(user.getAuthors(), valuesAt[i])) {
        			isValid = false;
        		}
      		}
      		flag = isValid;
		} else if (valuesLine.length > 0) {
			for (int i = 0; i < valuesLine.length; i++) {
				if (isVaild(user.getAuthors(), valuesLine[i])) {
					flag = true;
				}
      		}
		} else {
    		flag = isVaild(user.getAuthors(), value);
		}

		if (!flag) {
    		throw new ApplicationException("越权操作");
		}
		return pjp.proceed();
	}

	private boolean isVaild(String[] auth, String value) {
		boolean flag = false;
		value = value.trim();
		for (int i = 0; i < auth.length; i ++) {
			if (auth[i].trim().equals(value)) {
				flag = true;
			}
		}
		return flag;
	}
}
