package com.wantedtech.common.xpresso.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.helpers.Helpers;

public class str extends list<String> implements Iterable<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2972950675343815087L;

	public str(){
		this.list = new ArrayList<String>();
	}	
	
	public str(String string){
		list = new ArrayList<String>();
		char[] arr = string.toCharArray();
		for(int i = 0;i<arr.length;i++){
			((ArrayList<String>)list).add(String.valueOf(arr[i]));
		}
	}
	
	public str(str str){
		this.list = str.toArrayList();
	}
	
	public str(Iterable<String> list){
		ArrayList<String> listCopy = Helpers.newArrayList(this.list);
		for(String element : list){
			listCopy.addAll(x.str(element).toArrayList());
		}
		this.list = listCopy;
	}
	
	public str plus(str str){
		ArrayList<String> newList = new ArrayList<String>();
		newList.addAll((ArrayList<String>)this.list);
		newList.addAll(str.toArrayList());
		return new str(newList);
	}
	
	public str plus(String... strings){
		try {
			return new str(x.String("").join(strings));
		} catch (IOException e) {
			e.printStackTrace();
			return new str();
		}
	}
	public str times(int multiplier){
		list<String> lst = x.list(this.copy());
		return x.str(lst.times(multiplier));
	}
	public str slice(){
		list<String> lst = x.list(this.copy());
		return x.str(lst);
	}	
	public str slice(int startIndex,int endIndex){
		list<String> lst = x.list(this.copy());
		return x.str(lst.slice(startIndex,endIndex));
	}
	public str sliceTo(int endIndex){
		list<String> lst = x.list(this.copy());
		return x.str(lst.sliceTo(endIndex));
	}
	public str sliceFrom(int startIndex){
		list<String> lst = x.list(this.copy());
		return x.str(lst.sliceFrom(startIndex));
	}
	
	public String get(int index){
		return super.get(index);
	}
		
	public boolean in(String string){
		return string.contains(this.toString());
	}
	public boolean in(str str){
		return str.toString().contains(this.toString());
	}
	
	public boolean notIn(String string){
		return !in(string);
	}
	public boolean notIn(str str){
		return !in(str);
	}
	
	public boolean contains(String string){
		return (this.toString()).contains(string);
	}
	
	public boolean contains(str str){
		return this.toString().contains(str.toString());
	}
	
	public str map(Function<Object,String> function){
		str newStr = x.str();
		for(String element : this.list){
			newStr.append(function.apply(element));
		}
		return newStr;
	}
	
	public str filter(Predicate<Object> predicate){
		str newStr = x.str();
		for(String element : this.list){
			if(predicate.apply(element)){
				newStr.append(element);	
			}
		}
		return newStr;
	}
	
	public str copy(){
		return new str(x.String("").join(list));
	}
	
	public str join(Iterable<?> iterable){
		return x.str(x.String(this.toString()).join(iterable));
	}
	
	public String joinToString(Iterable<?> iterable){
		return join(iterable).toString();
	}
	
	public list<str> split(String regex){
		list<str> constructor = new list<str>();
		Iterable<String> split = x.String(this.toString()).split(regex);
		for(String element:split){
			constructor.append(x.str(element));
		}
		return constructor;
	}
	
	public list<str> split(){
		list<str> result = x.list();
		for(String element : x.String(this.toString()).split()){
			result.append(x.str(element));
		}
		return result;
	}
	
	public list<str> split(str str){
		list<str> constructor = new list<str>();
		for(str element:split(str.toString())){
			constructor.append(element);
		}
		return constructor;
	}

	public str stripAccents(){
		return x.str(x.String(this).stripAccents());
	}
	
	public str translit(){
		return x.str(x.String(this).translit());
	}
	
	public str unidecode(){
		return x.str(x.String(this).unidecode());
	}
	
	public int count(String character){
		return x.String(this).count(character);
	}
	
	public int count(char character){
		return x.String(this).count(character);
	}
	
	public str toUpperCase(){
		return x.str(this.list.toString().toUpperCase());
	}
	
	public str upper(){
		return toUpperCase();
	}
	
	public str toLowerCase(){
		return x.str(this.toString().toLowerCase());
	}
	
	public str lower(){
		return x.str(this.toString().toLowerCase());
	}
	
	public str strip(){
		return x.str(this.toString().trim());
	}
	
	public str strip(String chars){
		return x.str(toString().replaceAll("^["+chars+"]+", "").replaceAll("["+chars+"]+$", ""));
	}
	
	public str trim(){
		return strip();
	}
	
	public str trim(str chars){
		return trim(chars);
	}
	
	public str trim(String chars){
		return trim(chars);
	}
	
	public str translate(list<tuple> fromTo){
		list<tuple> fromToAsStrings = x.list(x.yield("a", "b").apply(x.joinOn(""), x.joinOn("")).where("a","b").in(fromTo));
		return x.str(x.String(this).translate(fromToAsStrings));
	}
	
	public str translate(dict<String> fromTo){
		list<tuple> fromToAsStrings = x.list(x.yield("a", "b").apply(x.joinOn(""), x.joinOn("")).where("a","b").in(fromTo.items()));
		return x.str(x.String(this).translate(fromToAsStrings));
	}
	
	public str title(){
		return x.str("").join(x.yield().apply(x.String.capitalized).forEach(this.split()));
	}
	
	public str capitalize(){
		return x.str(Character.toUpperCase(this.toString().charAt(0)) + this.toString().substring(1));
	}
		
	public boolean notEquals(String string){
		return !toString().equals(string);
	}
	
	public boolean notEquals(str str){
		return !toString().equals(str.toString());
	}
	
	public int compareTo(String string){
		return toString().compareTo(string);
	}
	
	public int compareTo(str str){
		return toString().compareTo(str.toString());
	}
	
	@Override
	public String toString(){
		return x.String("").join(list);
	}
	
	@Override
	public int hashCode(){
		return toString().hashCode();
	}
	
	@Override
	public Iterator<String> iterator(){
		return list.iterator();
	}

}
