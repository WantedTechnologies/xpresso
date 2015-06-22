package com.wantedtech.common.xpresso.en.sentence;

import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.token.Token;
import com.wantedtech.common.xpresso.types.list;

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
