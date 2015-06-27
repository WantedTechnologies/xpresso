package com.wantedtech.common.xpresso.experimental.concurrency;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;
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
	Channel<tuple2<String,L>> mapperChannel = (Channel<tuple2<String,L>>)(Object)x.Channel(tuple2.class,1000000);
	Channel<tuple2<String,O>> reducerChannel = (Channel<tuple2<String,O>>)(Object)x.Channel(tuple2.class,1000000);

	public MapReduce(Iterable<I> iterable, int numberOfWorkers) {
		this.iterable = iterable;
		this.numberOfWorkers = numberOfWorkers;	
	}
	
	public MapReduce(Iterable<I> iterable) {
		this(iterable, Runtime.getRuntime().availableProcessors());
	}

	private Predicate<Channel<tuple2<String,L>>> mapperWrapper(final list<I> elements) {
		return new Predicate<Channel<tuple2<String,L>>>() {
			@Override
			public Boolean apply(Channel<tuple2<String,L>> ch) {
				for (I element : elements) {
					mapper.map(element);	
				}
				return true;
			}
		};
	}
	
	public MapReduce<I,L,O> map(Mapper<I,L> mapper) {
		this.mapper = mapper;
		return this;
	}
	
	private Predicate<Channel<tuple2<String,O>>> reducerWrapper(final tuple2<String,list<L>> input) {
		return new Predicate<Channel<tuple2<String,O>>>() {
			@Override
			public Boolean apply(Channel<tuple2<String,O>> ch) {
				reducer.reduce(input);
				return true;
			}
		};
	}
	
	public MapReduce<I,L,O> reduce(Reducer<L,O> reducer) {
		
		mapper.setChannel(mapperChannel);
		for (list<I> chunk : x.divise(iterable, numberOfWorkers*2)) {
			x.go(mapperWrapper(chunk),mapperChannel);
		}
		
		dict<list<L>> mapperResults = x.dict();
		for (tuple2<String,L> item : mapperChannel) {
			//x.print("I read");
			if (x.String(item.key).in(mapperResults)) {
				mapperResults.get(item.key).append(item.value);
			} else {
				mapperResults.setAt(item.key).value(x.listOf(item.value));
			}
		}
		
		this.reducer = reducer;
		reducer.setChannel(reducerChannel);
		for (tuple2<String,list<L>> element : mapperResults.items()) {
			x.go(reducerWrapper(element),reducerChannel);
		}
		dict<O> reducerResults = x.dict();
		
		for (tuple2<String,O> item : reducerChannel) {
			super.setAt(item.key).value(item.value);
		}
		
		return this;
	}
}
