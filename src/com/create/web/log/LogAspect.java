package com.create.web.log;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 定义Pointcut
 *
 * @author perzer
 * @date Feb 24, 2011
 */
@Aspect
public class LogAspect {

	@Pointcut("execution(* com.create.web.rpc..*.add*(..))")
	public void inServiceLayer() {
	}
	
	@Pointcut("execution(* com.create.web.rpc..*.update*(..))")
	public void updateServiceLayer() {
	}
	
	@Pointcut("execution(* com.create.web.rpc..*.del*(..))")
	public void deleServiceLayer() {
	}
	
	@Pointcut("execution(* com.create.web.rpc..*.remove*(..))")
	public void removeServiceLayer() {
	}
	
	@Pointcut("execution(* com.create.web.rpc..*.commit*(..))")
	public void commitServiceLayer() {
	}
	
	@Pointcut("execution(* com.create.web.rpc..*.*(..))")
	public void queryServiceLayer() {
	}
}
