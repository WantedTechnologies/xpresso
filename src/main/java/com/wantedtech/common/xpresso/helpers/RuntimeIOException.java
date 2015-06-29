package com.wantedtech.common.xpresso.helpers;

public class RuntimeIOException extends RuntimeException {

	private Exception ex;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3109906993660350633L;

	public RuntimeIOException(Exception e) {
		this.ex = e;
	}	
	@Override
	public void printStackTrace() {
		ex.printStackTrace();
	} 

}
