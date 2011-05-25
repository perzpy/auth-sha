package com.create.exception;

/**
 * 数据库操作异常
 *
 * @author perzer
 * @date Feb 23, 2011
 */
public class DatabaseException extends ApplicationException {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 515203892180220619L;

	public DatabaseException() {
		super();
	}

	/**
	 * 
	 * @param message 消息
	 * @param cause 原因
	 */
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param message 消息
	 */
	public DatabaseException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause 原因
	 */
	public DatabaseException(Throwable cause) {
		super(cause);
	}

}
