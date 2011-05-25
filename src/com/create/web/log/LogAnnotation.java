package com.create.web.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个日志专用的注释 
 * 元注释Retention用于指定此注释类型的注释要保留多久, 元注释Target用于指定此注释类型的注释适用的程序元素的种类
 *
 * @author perzer
 * @date Feb 24, 2011
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAnnotation {
	String explain() default "";
}