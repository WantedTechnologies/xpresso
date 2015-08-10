package com.wantedtech.common.xpresso.regex;
import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.str;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -5558401165270154328L;

		
	public Pattern pattern;
	dict<String> replacements;
	
	public Regex(String regularExpression,int flags){
		pattern = Pattern.compile(regularExpression,flags);
	}
	
	public Regex(String regularExpression){
		pattern = Pattern.compile(regularExpression,0);
	}
	
	public Regex(dict<String> translator,int flags){
		list<String> keys = x.list(translator.keys());
		this.replacements = x.dict();
		StringBuilder patternBuilder = new StringBuilder();
		for (tuple2<Integer,String> idx__key : x.enumerate(keys)) {
			patternBuilder.append("(?<g" + idx__key.value0 + ">" +idx__key.value1 + ")|");
			this.replacements.setAt("g"+idx__key.value0).value(translator.get(idx__key.value1));
		}
		pattern = Pattern.compile(x.String(patternBuilder.toString()).sliceTo(-1),flags);
	}
		
	public String sub(String replacement,String string){
		return pattern.matcher(string).replaceAll(replacement);
	}
	public str sub(String replacement,str str){
		return x.str(sub(replacement,str.toString()));
	}
	public Function<Object,String> sub(final String replacement) {
		return new Function<Object,String> () {
			public String apply(Object string) {
				return sub(replacement, string.toString());
			} 
		};
	}
	
	public String sub(Function<Match,String> replacer,String string){
		int currentIndex = 0;
		StringBuilder resultString = new StringBuilder();
		for(Match m:findIter(string)){
			resultString.append(x.String(string).slice(currentIndex,m.start(0)));
			resultString.append(replacer.apply(m));
			currentIndex = m.end(0);
		}
		resultString.append(x.String(string).sliceFrom(currentIndex));
		return resultString.toString();
	}
	public str sub(Function<Match,String> replacer,str str){
		return x.str(sub(replacer,str.toString()));
	}
	
	public String sub(dict<String> translator,String string){
		int currentIndex = 0;
		StringBuilder resultString = new StringBuilder();
		for(Match m:findIter(string)){
			resultString.append(x.String(string).slice(currentIndex,m.start(0)));
			try{
				resultString.append(translator.get(m.group(0)));	
			}catch(Exception e){
				resultString.append(m.group(0));
			}
			currentIndex = m.end(0);
		}
		resultString.append(x.String(string).sliceFrom(currentIndex));
		return resultString.toString();
	}
	public str sub(dict<String> translator,str str){
		return x.str(sub(translator,str.toString()));
	}
	
	public String translate(String string){
		int currentIndex = 0;
		StringBuilder resultString = new StringBuilder();
		for(Match m:findIter(string)){
			resultString.append(x.String(string).slice(currentIndex,m.start(0)));
			for(String groupName : m.groupDict){
				//if(groupName.equals("g0")){
				//	continue;
				//}
				String groupMatch = m.group(groupName);
				if(groupMatch != null){
					resultString.append(this.replacements.get(groupName));	
					currentIndex = m.end(0);		
				}
			}
		}
		resultString.append(x.String(string).sliceFrom(currentIndex));
		return resultString.toString();
	}
	
	public str translate(str str){
		return x.str(translate(str.toString()));
	}
	
	/**
	 * a shortcut for {@link Regex#sub(String, String)}(emptyString, string)
	 * @param string	a {@link String} in which the pattern will be searched and, 
	 * 					if found, then deleted 
	 * @return			the copy of the input string in which all the matches of the 
	 * 					pattern have been deleted 
	 */
	public String clean(String string){
		return sub("", string);
	}
	
	/**
	 * a shortcut for {@link Regex#sub(String, str)}(emptyString, str)
	 * @param str	a {@link str} in which the pattern will be searched and,
	 * 				if found, then deleted
	 * @return		the copy of the input str in which all the matches of the 
	 * 				pattern have been deleted
	 */
	public str clean(str str){
		return sub("", str);
	}
	
	public MatchIterator findIter(String string){
		Matcher m = pattern.matcher(string);
		return new MatchIterator(m);
	}
	public MatchIterator searchIter(str str){
		return findIter(str.toString());
	}
	
	public Match find(String string){
		Matcher m = pattern.matcher(string);
		if(m.find()){
			return new Match(m);	
		}else{
			return null;
		}
	}
	public Match find(str str){
		return find(str.toString());
	}
	
	public Match find(String string, int startIndex){
		return find(x.String(string).sliceFrom(startIndex));
	}
	public Match find(str str, int startIndex){
		return find(str.sliceFrom(startIndex));
	}
	
	public Match find(String string, int startIndex, int endIndex){
		return find(x.String(string).slice(startIndex, endIndex));
	}
	public Match find(str str, int startIndex, int endIndex){
		return find(str.slice(startIndex, endIndex));
	}
	
	public list<String> split(String string,int limit){
		return x.list(pattern.split(string,limit));
	}
	public list<str> split(str str,int limit){
		list<String> listOfStrings = x.list(pattern.split(str.toString(),limit));
		list<str> listOfStrs = x.<str>list();
		for (String string : listOfStrings){
			listOfStrs = listOfStrs.append(x.str(string));
		}
		return listOfStrs;
	}
	public list<String> split(String string){
		return split(string,Integer.MAX_VALUE);
	}
	public list<str> split(str str){
		return split(str,Integer.MAX_VALUE);
	}
	
	public list<String> findAll(String string){
		Matcher m = pattern.matcher(string);
		list<String> listOfStringMatches = x.list();
		while (m.find()) {
			listOfStringMatches = listOfStringMatches.append(m.group(0));
		}
		return listOfStringMatches;
	}
	public list<str> findAll(str str){
		String stringInput = str.toString();
		list<String> lstStrings = findAll(stringInput);
		list<str> listStrs = x.<str>list();
		for (String string : lstStrings){
			listStrs = listStrs.append(x.str(string));
		}
		return listStrs;
	}
	
	@Override
	public String toString(){
		return pattern.pattern();
	}
}
