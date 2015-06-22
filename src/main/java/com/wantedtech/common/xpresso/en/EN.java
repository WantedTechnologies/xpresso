package com.wantedtech.common.xpresso.en;

import com.wantedtech.common.xpresso.en.text.Tokenizer;

public class EN {
	public Tokenizer tokenize(String text) {
		return new Tokenizer(text);
	}
}
