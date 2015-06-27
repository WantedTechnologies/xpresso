package com.wantedtech.common.xpresso.experimental.concurrency;

import com.wantedtech.common.xpresso.types.tuples.tuple2;

public abstract class Mapper<I,O> {

	Channel<tuple2<String,O>> channel;
	
	public abstract void map(I input);
	
	public void yield(tuple2<String,O> value) {
		channel.send(value);
	}
	
	public Mapper<I,O> setChannel(Channel<tuple2<String,O>> channel) {
		this.channel = channel; 
		return this;
	}
}
