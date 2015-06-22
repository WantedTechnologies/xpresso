package com.wantedtech.common.xpresso.en.sentence.chunker;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.helpers.Lengthful;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;

public class Node implements Lengthful{

	list<Node> elements = x.list();
	String label;
	String value;
	
	public Node(String label, list<Node> elements) {
		this.label = label;
		this.elements = elements;
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
	
	public String label() {
		return this.label;
	}
	
	public void append(Node node) {
		elements.append(node);
	}
	
	public list<Node> elements() {
		return this.elements;
	}
	
	@Override
	public String toString() {
		if (value != null) {
			return "(" + label.toString() + " : " + value.toString() + ")";	
		} else {
			return "\n" + label.toString() + " :\n" + elements.toString() + "\n";
		}
	}

	@Override
	public int len() {
		return x.len(elements);
	}
}
