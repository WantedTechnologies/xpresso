package com.wantedtech.common.xpresso.experimental.concurrency;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public class MapReduce<I,L,O> extends dict<O>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7769128656568977392L;
	
	Mapper<I,L> mapper;
	Reducer<L,O> reducer;
	Iterable<I> iterable;
	int numberOfWorkers;
	list<Iterable<I>> chunks;
	Channel<tuple2> mapperChannel = x.Channel(tuple2.class);
	Channel<tuple2> reducerChannel = x.Channel(tuple2.class);

	public MapReduce(Iterable<I> iterable, int numberOfWorkers) {
		this.iterable = iterable;
		this.numberOfWorkers = numberOfWorkers;
		chunks = x.divise(iterable,numberOfWorkers);	
	}
	
	public MapReduce(Iterable<I> iterable) {
		this(iterable, Runtime.getRuntime().availableProcessors());
	}
	
	private Predicate<Channel<tuple2>> mapperWrapper(final I input) {
		return new Predicate<Channel<tuple2>>() {
			@Override
			public Boolean apply(Channel<tuple2> ch) {
				mapper.map(input);
				return true;
			}
		};
	}
	
	public MapReduce<I,L,O> map(Mapper<I,L> mapper) {
		this.mapper = mapper;
		for (I element : iterable) {
			x.go(mapperWrapper(element),mapperChannel);
		}
		return this;
	}
	
	private Predicate<Channel<tuple2>> reducerWrapper(final tuple2<String,list<L>> input) {
		return new Predicate<Channel<tuple2>>() {
			@Override
			public Boolean apply(Channel<tuple2> ch) {
				reducer.reduce(input);
				return true;
			}
		};
	}
	
	public MapReduce<I,L,O> reduce(Reducer<L,O> reducer) {
		this.reducer = reducer;
		for (tuple element : mapperChannel) {
			x.go(reducerWrapper(x.tuple2(element.getString(0),(list<L>)element.get(1))),reducerChannel);
		}
		return this;
	}
}
