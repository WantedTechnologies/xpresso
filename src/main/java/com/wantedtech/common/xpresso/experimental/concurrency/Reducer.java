package com.wantedtech.common.xpresso.experimental.concurrency;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public abstract class Reducer<I,O> {
	
	public abstract void reduce(tuple2<String,list<I>> input);
	
	Channel<tuple2<String,O>> channel;
	
	public void yield(String key, O value) {
		channel.send(x.tuple2(key, value));
	}
	
	public Reducer<I,O> setChannel(Channel<tuple2<String,O>> channel) {
		this.channel = channel;
		return this;
	}
}
