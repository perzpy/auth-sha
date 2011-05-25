package com.create.web.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import com.create.exception.ApplicationException;
import com.create.util.Dates;
import com.create.util.Lang;

@Aspect
public class LogAdvice {
	Log log = LogFactory.getLog(LogAdvice.class);

	@After("com.create.web.log.LogAspect.queryServiceLayer()" + "|| com.create.web.log.LogAspect.inServiceLayer() "
			+ "|| com.create.web.log.LogAspect.updateServiceLayer() "
			+ "|| com.create.web.log.LogAspect.deleServiceLayer() "
			+ "|| com.create.web.log.LogAspect.removeServiceLayer() "
			+ "|| com.create.web.log.LogAspect.commitServiceLayer() ")
	public void queryAfterInfo(JoinPoint joinPoint) {
		log.info(Dates.getNowDate());
		log.info("Aop: do after in service layer");
		log.info("IP:    " + Lang.getRemoteClientIp());
		log.info("对象名:  " + joinPoint.getTarget().getClass());
		log.info("方法名:  " + joinPoint.getSignature().getName());
		log.info("参数表");
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			log.info("入参：" + arg);
		}
	}

	@AfterThrowing(pointcut = "com.create.web.log.LogAspect.inServiceLayer()", throwing = "ex")
	public void logExceptionInfo(JoinPoint joinPoint, ApplicationException ex) {
		log.info("Aop: do AfterThrowing in service layer");
		log.info(" IP:   " + Lang.getRemoteClientIp());
		log.info("对象名:  " + joinPoint.getTarget().getClass());
		log.info("方法名:  " + joinPoint.getSignature().getName());
		log.info("参数表");
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			log.info("入参：" + arg);
		}
		log.info("异常信息:  " + ex.getMessage());
	}
}
