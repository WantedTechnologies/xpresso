package com.wantedtech.common.xpresso.regex;
import java.io.Serializable;
import java.util.regex.Matcher;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;

public class Match implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7977241631907730960L;
	
	int groupCount = 0;
	list<String> groupStringsList = x.list();
	list<Integer> groupStartsList = x.list();
	list<Integer> groupEndsList = x.list();
	dict<String> namedGroups = x.dict();
	public Match(Matcher matcher){
		for(Match groupNameMatch:x.Regex("\\(\\?<([^>]+)>").findIter(matcher.pattern().toString())){
			namedGroups.setAt(groupNameMatch.group(1)).value(matcher.group(groupNameMatch.group(1)));
		}
		this.groupCount = matcher.groupCount();
		for(Integer counter : x.countTo(groupCount+1)){
			groupStringsList.append(matcher.group(counter));
			groupStartsList.append(matcher.start(counter));
			groupEndsList.append(matcher.end(counter));
		}
	}
	public int start(int groupIndex){
		return groupStartsList.get(groupIndex);
	}
	public int end(int groupIndex){
		return groupEndsList.get(groupIndex);
	}
	public String group(int groupIndex){
		return groupStringsList.get(groupIndex);
	}
	public String group(String groupName) throws NoSuchFieldException{
		return namedGroups.get(groupName);
	}
	public boolean hasGroup(int groupIndex){
		if(x.len(groupStringsList) >= groupIndex){
			return true;
		}
		return false;
	}
	public boolean hasGroup(String groupName){
		if(x.String(groupName).in(namedGroups)){
			return true;
		}
		return false;
	}
	
	public dict<String> namedGroups() {
		return namedGroups;
	}
	
	public list<String> groups() {
		list<String> lst = x.list();
		for (String gStr : groupStringsList.sliceFrom(1)) {
			lst.append(gStr);	
		}
		return lst;
	}
	public list<String> groups(String def) {
		list<String> lst = x.list();
		for (String gStr : groupStringsList.sliceFrom(1)) {
			if (gStr == null) {
				lst.append(def);	
			}	
		}
		return lst;
	}
	public list<String> matchedGroups() {
		list<String> lst = x.list();
		for (String gStr : groupStringsList.sliceFrom(1)) {
			if (gStr != null) {
				lst.append(gStr);	
			}	
		}
		return lst;
	}
	public dict<String> matchedNamedGroups() {
		dict<String> dct = x.dict();
		for (String gStr : namedGroups) {
			try {
				if (namedGroups.get(gStr) != null) {
					dct.setAt(gStr).value(namedGroups.get(gStr));	
				}
			} catch (NoSuchFieldException e) {
				// cant happen
			}	
		}
		return dct;
	}
}
