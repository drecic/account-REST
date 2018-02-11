package org.drecic.email.sparkmail;

public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	private Throwable cause;
	
	public ServiceException(String message, Throwable cause) {
		this.message = message;
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	
	@Override
	public String toString() {
		return this.message + ": " + this.cause.getMessage();
	}
}
