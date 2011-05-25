package com.create.util;

import java.util.regex.Pattern;

/**
 * 字符串操作的帮助函数
 *
 * @author perzer
 * @date Feb 23, 2011
 */
public class Strings {

	/**
	 * @param cs 字符串
	 * @return 是不是为空字符串
	 */
	public static boolean isEmpty(CharSequence cs) {
		return null == cs || cs.length() == 0;
	}

	/**
	 * @param cs 字符串
	 * @return 是不是为空白字符串
	 */
	public static boolean isBlank(CharSequence cs) {
		if (null == cs)
			return true;
		int length = cs.length();
		for (int i = 0; i < length; i++) {
			if (!(Character.isWhitespace(cs.charAt(i))))
				return false;
		}
		return true;
	}

	/**
	 * 将字符串首字符大写
	 * 
	 * @param s
	 * @return
	 */
	public static String upperFirst(String s) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == 0)
			return "";
		char c = s.charAt(0);
		if (Character.isUpperCase(c))
			return s.toString();
		return new StringBuilder(len).append(Character.toUpperCase(c)).append(s.subSequence(1, len)).toString();
	}

	/**
	 * 在字符串前后追加字符串
	 * 
	 * @param value 原字符串
	 * @param strings 要添加的字符串
	 * @return
	 */
	public static String addBeginEnd(String value, String... strings) {
		if (strings == null)
			return null;
		if (value == null || strings.length == 0)
			return value;
		if (strings.length == 1)
			return strings[0] + value + strings[0];
		return strings[0] + value + strings[1];
	}

	/**
	 * 是否为脏数据
	 * 
	 * @param str 源字符串
	 * @return 如果有返回true,反之返回false
	 */
	public static boolean isDirty(String str) {
		if (str != null) {
			if (str.indexOf(";") >= 0 || str.indexOf("'") >= 0
					|| Pattern.compile("and .*", Pattern.CASE_INSENSITIVE).matcher(str).find()
					|| Pattern.compile("or .*", Pattern.CASE_INSENSITIVE).matcher(str).find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否为脏数据
	 * @param str
	 * @return
	 */
	public static boolean isDirty(String... str) {
		if (str == null)
			return false;
		for (int i = 0; i < str.length; i++) {
			if (isDirty(str[i]))
				return true;
		}
		return false;
	}

	/**
	 * 检测字符串组是否为空或者是脏数据
	 * 
	 * @param strs 字符串
	 * @return 如果有返回true,反之返回false
	 */
	public static boolean isDirtyOrBlank(String... strs) {
		if (strs == null)
			return true;
		for (int i = 0; i < strs.length; i++) {
			if (isBlank(strs[i])) {
				return true;
			}
			if (isDirty(strs[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 生成特定次数重复的字符串
	 * 
	 * 例:<br>
	 * 1.getRepeatStrs("_",3) "___"<br>
	 * 2.getRepeatStrs("01",4) "01010101"<br>
	 * 3.getRepeatStrs(null,2) ""<br>
	 * 4.getRepeatStrs(null,0) ""<br>
	 * 
	 * @param repaetStr
	 *            被重复的字符串
	 * @param repeatCount
	 *            被重复的次数
	 * @return
	 */
	public static String getRepeatStrs(String repaetStr, int repeatCount) {
		if (isBlank(repaetStr) || repeatCount <= 0)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < repeatCount; i++) {
			sb.append(repaetStr);
		}
		return sb.toString();
	}
}
