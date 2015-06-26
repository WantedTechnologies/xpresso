package com.wantedtech.common.xpresso.exceptions;

public class RuntimeIOException extends RuntimeException{

	Exception e;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7039021499295779203L;

	public RuntimeIOException(Exception e) {
		this.e = e;
	}
	
	public RuntimeIOException(String message) {
		this.e = new Exception(message);
	}
	
	@Override
	public void printStackTrace() {
		e.printStackTrace();
	}
	
}
