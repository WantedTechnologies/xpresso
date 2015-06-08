package com.wantedtech.common.xpresso.regex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;


public class MatchIterator implements Iterable<Match>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1064821402277030325L;
	
	ArrayList<Match> matches = new ArrayList<Match>();
	
	public MatchIterator(Matcher matcher){
		while(matcher.find()){
			matches.add(new Match(matcher));
		}
	}
	
	public Iterator<Match> iterator(){
		return matches.iterator();
	}
}
