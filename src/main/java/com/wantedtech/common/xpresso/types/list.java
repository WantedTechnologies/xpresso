package com.wantedtech.common.xpresso.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.wantedtech.common.xpresso.Slicable;
import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.helpers.Slicer;

public class list<T> implements Iterable<T>,Slicable<T>,Comparable<list<T>>,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3923221503510025027L;

	//used by the construction list.setAt(index).value(val);
	private int setAtIndex = Integer.MAX_VALUE;
	
	//used by the construction list.setAt(startIndex,endIndex).values(iterable);
	private int setAtStartIndex = Integer.MAX_VALUE;
	private int setAtEndIndex = Integer.MAX_VALUE;
	
	protected ArrayList<T> list;
	
	public list(){
		this.list = new ArrayList<T>();
	}
	
	public list(Iterable<T> elements){
		if (elements instanceof list<?>){
			this.list = ((list<T>)elements).toArrayList();
		}else{
			this.list = Helpers.newArrayList(elements);	
		}
	}
	
	public list<T> plus(Iterable<T> iterable){
		ArrayList<T> newList = new ArrayList<T>();
		newList.addAll(this.list);
		if(iterable instanceof ArrayList<?>){
			newList.addAll((ArrayList<T>)iterable);
		}else if(iterable instanceof list<?>){
			newList.addAll(((list<T>)iterable).toArrayList());
		}else{
			newList.addAll(Helpers.newArrayList(iterable));	
		}
		return new list<T>(newList);
	}
	
	@SuppressWarnings("unchecked")
	public list<T> plus(T element0,T element1,T... elements){
		return plus(Helpers.newArrayList(element0,element1,elements));
	}

	@SuppressWarnings("unused")
	public list<T> times(int multiplier){
		list<T> newList = x.list();
		for(int i : x.countTo(multiplier)){
			for(T element : this.list){
				newList.append(element);
			}
		}
		return newList;
	}
	
	public list<T> setAt(Slicer slicer){
		if(slicer.step != 1){
			throw new IllegalArgumentException("Step has to be equal to 1.");
		}
		return setAt(slicer.startIndex,slicer.endIndex);
	}
	
	public list<T> setAt(int startIndex,int endIndex){
		if (startIndex < 0){
			startIndex = this.list.size()+startIndex;
		}
		if (startIndex < 0){
			startIndex = 0;
		}
		if (endIndex < 0){
			endIndex = this.list.size()+endIndex;
		}
		if (endIndex < 0){
			endIndex = 0;
		}
		if (startIndex > this.list.size()){
			startIndex = this.list.size();
		}
		if (endIndex > this.list.size()){
			endIndex = this.list.size();
		}
		
		this.setAtStartIndex = startIndex;
		this.setAtEndIndex = endIndex;
		return this;
	}
	
	public void values(Iterable<T> values){
		this.list = this.sliceTo(setAtStartIndex).plus(values).plus(this.sliceFrom(this.setAtEndIndex)).toArrayList();
	}
	
	public list<T> slice(Slicer slicer){
		return slice(slicer.startIndex,slicer.endIndex,slicer.step,slicer.includeEnd);
	}
	
	private list<T> slice(int startIndex,int endIndex, int step, boolean includeEnd){
		if(step == 0){
			step = 1;
		}
		if (startIndex < 0){
			startIndex = this.list.size()+startIndex;
		}
		if (startIndex < 0){
			startIndex = 0;
		}
		if (endIndex < 0){
			endIndex = this.list.size()+endIndex;
		}
		if (endIndex < 0){
			endIndex = 0;
			if(step < 0 && startIndex >= endIndex){
				includeEnd = true;	
			}
		}
		if (startIndex > this.list.size()-1){
			if (step < 0){
				startIndex = this.list.size()-1;
			}else{
				return x.list();	
			}
		}
		if (endIndex > this.list.size()-1){
			endIndex = this.list.size()-1;
			includeEnd = true;
		}
		
		list<T> newList = x.list();
		
		if(step > 0){
			while (startIndex < endIndex + (includeEnd?1:0)){
				newList.append(this.get(startIndex));
				startIndex+=step;
			}	
		}else{
			while (startIndex > endIndex - (includeEnd?1:0)){
				newList.append(this.get(startIndex));
				startIndex+=step;
			}
		}
		
		return newList;
	}
	
	public list<T> slice(int startIndex,int endIndex, int step){
		return slice(startIndex, endIndex, step, false);
	}

	public list<T> sliceTo(int endIndex,int step){
		int startIndex = 0;
		if (step < 0){
			startIndex = x.len(this)-1;
			return slice(startIndex,endIndex,step,true);
		}
		return slice(startIndex,endIndex,step);
	}
	public list<T> sliceFrom(int startIndex, int step){
		int endIndex = x.len(this)-1;
		if (step < 0){
			endIndex = 0;
		}
		return slice(startIndex,endIndex,step,true);
	}
	
	public list<T> sliceTo(int endIndex) {
		return sliceTo(endIndex, 1);
	}

	public list<T> sliceFrom(int startIndex) {
		return sliceFrom(startIndex,1);
	}
	
	public list<T> slice(int startIndex, int endIndex) {
		return slice(startIndex, endIndex, 1);
	}

	public list<T> slice(int step){
		if (step < 0){
			return slice(x.len(this)-1,0,step,true);	
		}else{
			return slice(0,x.len(this),step,false);
		}
	}
	
	public list<T> slice(){
		return slice(1);
	}
	
	public T get(int index){
		if (index < 0){
			index = this.list.size()+index;
			if (index < 0){
				index = 0;
			}
		}
		return this.list.get(index);
	}
	
	public T get(Object index){
		return get(((int)index));
	}
	
	public list<T> extend(Iterable<T> iterable){
		for(T element : iterable){
			this.list.add(element);
		}
		return this;
	}
	
	public list<T> extend(T[] array){
		for(T element : array){
			this.list.add(element);
		}
		return this;
	}
	
	private list<T> set(int index,T value){
		this.list.set(index, value);
		return this;
	}
	
	public list<T> setAt(int index){
		if (index < 0){
			index = this.list.size()+index;
			if (index < 0){
				index = 0;
			}
		}
		if(this.list.size() < index - 1){
			throw new IndexOutOfBoundsException();
		}
		setAtIndex = index;
		return this;
	}
	
	public void value(T value){
		set(setAtIndex,value);
	}
	
	public list<T> mapped(Function<Object,T> function){
		list<T> newList = x.list();
		for(T element : this.list){
			newList.append(function.apply(element));
		}
		return newList;
	}
	
	public list<T> filtered(Predicate<Object> predicate){
		list<T> newList = x.list();
		for(T element : this.list){
			if(predicate.apply(element)){
				newList.append(element);	
			}
		}
		return newList;
	}
	
	public list<T> compressed(){
		list<T> newList = x.list();
		set<T> seen = x.set();
		for(T element : this.list){
			if (!seen.contains(element)){
				newList.append(element);
				seen.put(element);
			}
		}
		return newList;
	}
	
	public boolean contains(T value){
		return this.list.contains(value);
	}
	
	public boolean in(Iterable<list<T>> anotherList){
		return x.list(anotherList).contains(this);
	}
	
	public boolean in(@SuppressWarnings("unchecked") list<T>... lists){
		return x.list(lists).contains(this);
	}
	
	public boolean notIn(Iterable<list<T>> anotherList){
		return !in(anotherList);
	}
	
	public boolean notIn(@SuppressWarnings("unchecked") list<T>... lists){
		return !in(lists);
	}
	
	public int count(T value){
		int counter = 0;
		if(this.list.contains(value)){
			for(T element : this.list){
				if((element).equals(value)){
					counter++;
				}	
			}	
		}
		return counter;
	}
	
	public list<T> append(T element){
		this.list.add(element);
		return this;
	}
	
    public list<list<T>> ngrams(int n) {
        list<list<T>> ngrams = x.list();
        for (int i = 0; i < x.len(this) - n + 1; i++){
            ngrams.append(this.slice(i, i+n));	
        }
        return ngrams;
    }

	
	public <E> list<E> flattened(Class<E> classOfelements){
		list<E> result = x.list();
		for(T element : this){
			if(element instanceof list<?>){
				result.extend(((list<?>)element).<E>flattened(classOfelements));
			}else if(element instanceof Iterable<?>){
					result.extend((x.list((Iterable<?>)element)).<E>flattened(classOfelements));
			}else{	
				result.append(classOfelements.cast(element));
			}
		}
		return result;
	}
	
	public list<T> copy(){
		ArrayList<T> newArrayList = Helpers.newArrayList(this.list);
		return x.list(newArrayList);
	}
	
	public ArrayList<T> toArrayList(){
		return Helpers.newArrayList(this);
	}
	
	public T toScalar(){
		x.assertNotEmpty(this);
		x.assertTrue(x.len(this) == 1);
		return this.get(0);
	}
	
	@Override
	public String toString(){
		return "["+x.String(", ").join(list)+"]";
	}
	
    public Iterator<T> iterator(){
    	return this.list.iterator();
    }

	@Override
	public int compareTo(list<T> o) {
		return this.toString().compareTo(o.toString());
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof list){
			return this.toString().equals(o.toString());
		}
		return false;
	}
}
