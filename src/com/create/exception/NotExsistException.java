package com.create.exception;

/**
 * 不存在异常
 *
 * @author perzer
 * @date Mar 10, 2011
 */
public class NotExsistException extends ApplicationException {

	/**
	 * sid
	 */
	private static final long serialVersionUID = -2287435668571977127L;

	/**
	 * 构造函数
	 */
	public NotExsistException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param message 消息
	 * @param cause 原因
	 */
	public NotExsistException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param message 消息
	 */
	public NotExsistException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 * 
	 * @param cause 原因
	 */
	public NotExsistException(Throwable cause) {
		super(cause);
	}

}
