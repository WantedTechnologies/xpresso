package com.wantedtech.common.xpresso.experimental.concurrency;

public class SendToClosedChannelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2799468327085111164L;

	public SendToClosedChannelException(String message) {
		super(message);
	}
	
}
