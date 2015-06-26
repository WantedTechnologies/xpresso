package com.wantedtech.common.xpresso.sentence;

import com.wantedtech.common.xpresso.types.list;

public interface PosTagger {
	public Sentence tag(Sentence sent);
	public list<Sentence> tag(String text);
}
