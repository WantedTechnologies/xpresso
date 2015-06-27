package com.wantedtech.common.xpresso.experimental.concurrency;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.experimental.generator.Generator;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public class MapReduce<I,O> {

	Mapper<I,?> mapper;
	Function<?,O> reducer;
	Iterable<I> iterable;
	int numberOfWorkers;

	/*
	public MapReduce(Iterable<I> iterable, int numberOfWorkers) {
		this.iterable = iterable;
		this.numberOfWorkers = numberOfWorkers;
		list<Iterable<I>> chunks = x.divise(iterable,numberOfWorkers);
		
		Channel<I> channel = (Channel<I>)x.Channel(iterable.iterator().next().getClass());
		
		Predicate<I> mapperWrapper = new Predicate<I> () {

			@Override
			public Boolean apply(I input) {
				for (tuple2<String,?> key__value : mapper(input)) {
					
				}
				mapper.apply(input);
			}
			
		}
		
		for (Iterable<I> chunk : chunks) {
			x.go()
		}
	}
	
	public MapReduce(Iterable<I> iterable) {
		this(iterable, Runtime.getRuntime().availableProcessors());
	}
	
	public MapReduce<I,O> mapper(Mapper<I,?> mapper) {
		this.mapper = mapper;
		return this;
	}
	
	public MapReduce<I,O> reducer(Function<tuple2<String,Iterable<?>>,O> function) {
		reducer = function;
		return this;
	}
	*/
}
