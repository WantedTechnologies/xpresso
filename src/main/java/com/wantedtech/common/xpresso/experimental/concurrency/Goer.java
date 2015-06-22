package com.wantedtech.common.xpresso.experimental.concurrency;

import com.wantedtech.common.xpresso.functional.Predicate;

public class Goer<T> {

	class Success {
		public Boolean success;
	}
	
	public final Success success = new Success();
	
	Channel<T> channel;
	
	Thread producer;
	
	public Goer (final Predicate<Channel<T>> predicate, final Channel<T> channel) {
		producer = new Thread(new ThreadGroup("goers"), new Runnable() {
			@Override
			public void run() {
				success.success = predicate.apply(channel);
			}
		});
		this.channel = channel;
	}
	
	public Goer<T> go() {
		channel.registerGoer(this);
		producer.start();
		return this;
	}
	
	public boolean finished() {
		return success.success != null;
	}
	
	public boolean success() {
		return success.success;
	}
	
}
