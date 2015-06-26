package com.wantedtech.common.xpresso.sentence.pos.en.stanford;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.sentence.PosTagger;
import com.wantedtech.common.xpresso.sentence.Sentence;
import com.wantedtech.common.xpresso.text.Tokenizer;
import com.wantedtech.common.xpresso.text.tokenizer.en.stanford.RuleBasedTokenizer;
import com.wantedtech.common.xpresso.types.list;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class MaxentPosTagger implements PosTagger {

	public enum Model {
		ENGLISH_LEFT_3_WORDS("pos/english-left3words-distsim.tagger"),
		ENGLISH_BIDIRECTIONAL_DISTSIM("pos/english-bidirectional-distsim.tagger");
		
		public String fileName;
		Model(String fileName) {
			this.fileName = fileName;
		}
	}
	MaxentTagger tagger;
	
	public MaxentPosTagger(Model model) {
		tagger = new MaxentTagger(model);
	}
	
	public MaxentPosTagger() {
		tagger = new MaxentTagger(MaxentPosTagger.Model.ENGLISH_LEFT_3_WORDS);
	}
	
	@Override
	public Sentence tag(Sentence sent) {
		sent = tagger.tagSentence(sent);
		return sent;
	}

	@Override
	public list<Sentence> tag(String text) {
		Tokenizer tokenizer = new RuleBasedTokenizer();
		list<Sentence> result = x.list();
		for (Sentence s : tokenizer.tokenize(text)) {
			tagger.tagSentence(s);
			result.append(s);
		}
		return result;
	}
}
