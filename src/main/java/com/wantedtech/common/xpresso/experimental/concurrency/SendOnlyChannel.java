package com.wantedtech.common.xpresso.experimental.concurrency;

import java.util.Iterator;

public class SendOnlyChannel<T> extends Channel<T>{

	Channel<T> channel;
	
	public SendOnlyChannel(Channel<T> channel) {
		super(channel.getType(),channel.getBufferSize());
		this.channel = channel;
	}
	
	@Override
	public void registerGoer(Goer<T> g) {
		channel.registerGoer(g);
	}
	
	@Override
	public T receive() {
		try {
			channel.close();
		} catch (Exception e) {
			
		}
		throw new ReceiveFromSendOnlyChannelExeption("Cannot receive values from a send only channel.");
	}
	
	@Override
	public void send(T value) {
		channel.send(value);
	}
	
	@Override
	public Iterator<T> iterator() {
		try {
			channel.close();
		} catch (Exception e) {
			
		}
		throw new ReceiveFromSendOnlyChannelExeption("Cannot receive values from a send only channel.");
	}
	
}
