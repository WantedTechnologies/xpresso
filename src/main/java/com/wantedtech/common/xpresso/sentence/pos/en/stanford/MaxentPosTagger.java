package com.wantedtech.common.xpresso.sentence.pos.en.stanford;

import java.util.ArrayList;
import java.util.List;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.sentence.PosTagger;
import com.wantedtech.common.xpresso.sentence.Sentence;
import com.wantedtech.common.xpresso.text.Tokenizer;
import com.wantedtech.common.xpresso.text.tokenizer.en.stanford.RuleBasedTokenizer;
import com.wantedtech.common.xpresso.token.Token;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.tagger.maxent.TaggerConfig;
import edu.stanford.nlp.util.StringUtils;

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
		tagger = new MaxentTagger(model.fileName, StringUtils.argsToProperties("-model", model.fileName));
	}
	
	public MaxentPosTagger() {
		tagger = new MaxentTagger(MaxentPosTagger.Model.ENGLISH_LEFT_3_WORDS.fileName, StringUtils.argsToProperties("-model", MaxentPosTagger.Model.ENGLISH_LEFT_3_WORDS.fileName));
	}
	
	public Sentence tag(Sentence sent) {
		List<HasWord> ss = new ArrayList<HasWord>();
		for (Token t : sent) {
			HasWord hw = new Word();
			hw.setWord(t.toString());
			ss.add(hw);
		} 
		List<TaggedWord> sst = tagger.tagSentence(ss);
		for (tuple2<Integer,TaggedWord> item : x.enumerate(sst)) {
			Token tk = sent.get(item.key);
			tk.annotate("pos", item.value.tag());
			sent.setAt(item.key).value(tk);
		}
		  
		return sent;
	}

	@Override
	public list<Sentence> tag(String text) {
		Tokenizer tokenizer = new RuleBasedTokenizer();
		list<Sentence> result = x.list();
		for (Sentence s : tokenizer.tokenize(text)) {
			tag(s);
			result.append(s);
		}
		return result;
	}
}
