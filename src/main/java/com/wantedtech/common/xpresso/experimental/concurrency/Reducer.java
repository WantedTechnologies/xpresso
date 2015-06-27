package com.wantedtech.common.xpresso.experimental.concurrency;

import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public abstract class Reducer<I,O> {
	public abstract void reduce(tuple2<String,list<I>> input);
	
	Channel<tuple2<String,O>> channel;
	
	public void yield(tuple2<String,O> value) {
		channel.send(value);
	}
	
	public void setChannel(Channel<tuple2<String,O>> channel) {
		this.channel = channel; 
	}
}
