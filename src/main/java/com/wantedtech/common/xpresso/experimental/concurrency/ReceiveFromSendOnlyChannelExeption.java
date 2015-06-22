package com.wantedtech.common.xpresso.experimental.concurrency;

public class ReceiveFromSendOnlyChannelExeption extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4712724282324871022L;

	public ReceiveFromSendOnlyChannelExeption(String message) {
		super(message);
	}

}
