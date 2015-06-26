package com.wantedtech.common.xpresso.sentence;

import com.wantedtech.common.xpresso.sentence.chunker.Node;
import com.wantedtech.common.xpresso.types.list;

public interface Chunker {
	public list<Node> chunk(String string);
	public Node chunk(Sentence string);
}
