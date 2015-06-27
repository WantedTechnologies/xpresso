package com.wantedtech.common.xpresso.experimental.concurrency;

import com.wantedtech.common.xpresso.experimental.generator.Generator;
import com.wantedtech.common.xpresso.types.tuples.tuple2;


public interface Mapper<I,O> {
	
	public Generator<tuple2<String,O>> getMapper(final I input);

}
