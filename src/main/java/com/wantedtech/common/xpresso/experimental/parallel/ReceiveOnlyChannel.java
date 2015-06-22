package com.wantedtech.common.xpresso.experimental.parallel;

import java.util.Iterator;

public class ReceiveOnlyChannel<T> extends Channel<T>{

	Channel<T> channel;
	
	public ReceiveOnlyChannel(Channel<T> channel) {
		super(channel.getType(),channel.getBufferSize());
		this.channel = channel;
	}
	
	@Override
	public void registerGoer(Goer<T> g) {
		channel.registerGoer(g);
	}
	
	@Override
	public void send(T value) {
		try {
			channel.close();
		} catch (Exception e) {
			
		}
		throw new SendToReceiveOnlyChannelExeption("Cannot send values to a read only channel.");
	}
	
	@Override
	public T receive() {
		return channel.receive();
	}
	
	@Override
	public Iterator<T> iterator() {
		return channel.iterator();
	}
}
