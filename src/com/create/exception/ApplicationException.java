package com.create.exception;

/**
 * 应用异常
 *
 * @author perzer
 * @date Feb 23, 2011
 */
public class ApplicationException extends Exception {

	/**
	 * sid
	 */
	private static final long serialVersionUID = -5839131794739410737L;

	public ApplicationException() {
		super();
	}

	/**
	 * 
	 * @param message 消息
	 * @param cause 原因
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause 原因
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}

}
