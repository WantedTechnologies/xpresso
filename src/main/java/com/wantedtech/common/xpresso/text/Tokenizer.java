package com.wantedtech.common.xpresso.text;

import com.wantedtech.common.xpresso.sentence.Sentence;
import com.wantedtech.common.xpresso.types.list;

public interface Tokenizer extends Iterable<Sentence>{
	public Tokenizer tokenize(String text);
}
