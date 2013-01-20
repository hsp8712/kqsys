package net.oraro.exception;

/**
 * 数据访问异常
 * @author shipley
 * @date 2013-1-20
 */
public class DataAccessException extends Exception {

	public DataAccessException() {
		super();
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}
	
}
