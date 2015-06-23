package com.wantedtech.common.xpresso.en.sentence.chunker;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.helpers.Lengthful;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public class Node implements Lengthful, Iterable<Node>{

	list<Node> leaves;
	String label;
	String value;
	
	public Node(String label, list<Node> leaves) {
		this.label = label;
		this.leaves = leaves;
	}
	
	public Node(String label, Node element) {
		this.label = label;
		this.value = element.value;
	}
	
	public Node(tuple element) {
		this.label = element.getString(0);
		this.value = element.getString(1);
	}
	
	public Node(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void append(Node node) {
		leaves.append(node);
	}
	
	public list<Node> getLeaves() {
		return this.leaves;
	}
	
	@Override
	public String toString() {
		if (value != null) {
			return "(" + label.toString() + " : " + value.toString() + ")";	
		} else {
			return "\n" + label.toString() + " :\n" + leaves.toString() + "\n";
		}
	}

	@Override
	public int len() {
		return x.len(leaves);
	}

	private void getAllLeaves(Node startNode, list<Node> listToFill) {
		listToFill.append(startNode);
		if (startNode.leaves != null) {
			for (Node element : leaves) {
				getAllLeaves(element, listToFill);
			}
		}
	}
	
	@Override
	public Iterator<Node> iterator() {
		
		list<Node> lst = x.list();
		getAllLeaves(this,lst);
		final Iterator<Node> iter = lst.iterator();
		
		return new Iterator<Node>() {
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public Node next() {
				return iter.next();
			}			
		};
	}
}
