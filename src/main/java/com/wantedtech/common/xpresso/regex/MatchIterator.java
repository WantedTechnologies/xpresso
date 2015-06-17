package com.wantedtech.common.xpresso.regex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.regex.Matcher;

import com.wantedtech.common.xpresso.x;


public class MatchIterator implements Iterable<Match>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1064821402277030325L;
	
	ArrayList<Match> matches = new ArrayList<Match>();
	
	Matcher matcher;
	
	public MatchIterator(Matcher matcher){
		this.matcher = matcher;
	}
	
	public Iterator<Match> iterator(){
		return new Iterator<Match> () {

			@Override
			public boolean hasNext() {
				return x.isTrue(matcher.find());
			}

			@Override
			public Match next() {
				return new Match(matcher);
			}
		};
	}
}
