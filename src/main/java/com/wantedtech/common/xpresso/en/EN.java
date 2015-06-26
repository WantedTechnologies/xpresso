package com.wantedtech.common.xpresso.en;

import com.wantedtech.common.xpresso.sentence.Sentence;
import com.wantedtech.common.xpresso.sentence.chunker.Node;
import com.wantedtech.common.xpresso.sentence.chunker.regexchunker.RegexBasedChunker;
import com.wantedtech.common.xpresso.sentence.pos.en.stanford.MaxentPosTagger;
import com.wantedtech.common.xpresso.text.tokenizer.en.stanford.RuleBasedTokenizer;

public class EN {
	public Iterable<Sentence> tokenize(String text) {
		return new RuleBasedTokenizer().tokenize(text);
	}
	
	public Iterable<Sentence> posTag(String text) {
		return new MaxentPosTagger().tag(text);
	}
	public Sentence posTag(Sentence sent) {
		return new MaxentPosTagger().tag(sent);
	}
	
	public Iterable<Node> chunk(String text, String grammar) {
		return new RegexBasedChunker(grammar).chunk(text);
	}
	public Node chunk(Sentence sent, String grammar) {
		return new RegexBasedChunker(grammar).chunk(sent);
	}
	
}
