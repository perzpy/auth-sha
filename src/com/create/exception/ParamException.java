package com.create.exception;

/**
 * 参数异常
 *
 * @author perzer
 * @date Mar 8, 2011
 */
public class ParamException extends ApplicationException {

	/**
	 * sid
	 */
	private static final long serialVersionUID = -2151905636450075039L;

	/**
	 * 构造函数
	 */
	public ParamException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param message
	 *            消息
	 * @param cause
	 *            原因
	 */
	public ParamException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param message
	 *            消息
	 */
	public ParamException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 * 
	 * @param cause
	 *            原因
	 */
	public ParamException(Throwable cause) {
		super(cause);
	}

}
