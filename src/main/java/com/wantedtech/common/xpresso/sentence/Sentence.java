package com.wantedtech.common.xpresso.sentence;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.token.Token;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public class Sentence extends list<Token>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1712482775997262917L;
	
	public Sentence(Sentence ph) {
		this.list = Helpers.newArrayList(ph.list);
	}
	
	public Sentence() {
		super();
	}
	
	@Override
	public Sentence copy() {
		return new Sentence(this);
	}
	
	public list<String> getWords() {
		list<String> lst = x.list();
		for (Token token : this.list) {
			lst.append(token.toString());
		}
		return lst;
	}
	
	public list<tuple2<String,String>> getAnnotations(String key) {
		list<tuple2<String,String>> lst = x.list();
		for (Token token : this.list) {
			lst.append(tuple2.valueOf(token.toString(), token.getAnnotation(key)));
		}
		return lst;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (Token t : list) {
			if (t.isPunct()) {
				b.append(t.toString());
			} else {
				b.append(" " + t.toString());
			}
		}
		return b.toString();
	} 
}
