package com.create.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author perzer
 * @date Feb 24, 2011
 */
public class Dates {
	public static SimpleDateFormat sdfYMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取当前时间
	 * @param sdf 时间格式
	 * @return
	 */
	public static String getNowDate(SimpleDateFormat sdf) {
		return sdf.format(new Date());
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getNowDate() {
		return getNowDate(sdfYMDHMS);
	}

}
