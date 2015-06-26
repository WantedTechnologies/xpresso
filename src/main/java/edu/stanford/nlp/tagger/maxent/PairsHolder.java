package edu.stanford.nlp.tagger.maxent;

/**
 * Title:       StanfordMaxEnt<p>
 * Description: A Maximum Entropy Toolkit<p>
 * Copyright:   The Board of Trustees of The Leland Stanford Junior University
 * Company:     Stanford University<p>
 */

import java.util.*;

import com.wantedtech.common.xpresso.types.tuples.tuple2;


/** A simple class that maintains a list of WordTag pairs which are interned
 *  as they are added.  This stores a tagged corpus.
 *
 *  @author Kristina Toutanova
 *  @version 1.0
 */
public class PairsHolder {

  // todo: In Java 5+, just make this class an ArrayList<WordTag> and be done with it?? Or actually, probably a PaddedList. Or need a WindowedList?

  private final ArrayList<tuple2<String,String>> arr = new ArrayList<tuple2<String,String>>();

  public PairsHolder() {}

  // todo: This method seems crazy.  Can't we either just do nothing or using ensureCapacity()?
  public void setSize(int s) {
    while (arr.size() < s) {
      arr.add(tuple2.<String,String>valueOf(null,"NN"));  // todo: remove NN.  NA okay?
    }
  }

  public int getSize() {
    return arr.size();
  }

  void clear() {
    arr.clear();
  }

  void add(tuple2<String,String> wordtag) {
    arr.add(wordtag);
  }

  void setWord(int pos, String word) {
	tuple2<String,String> oldTuple = arr.get(pos);
	tuple2<String,String> newTuple = tuple2.valueOf(word, oldTuple.value);
	
    arr.set(pos,newTuple);
  }

  void setTag(int pos, String tag) {
		tuple2<String,String> oldTuple = arr.get(pos);
		tuple2<String,String> newTuple = tuple2.valueOf(oldTuple.key, tag);
	    arr.set(pos,newTuple);
  }

  String getTag(int position) {
    return arr.get(position).value;
  }
  String getWord(int position) {
    return arr.get(position).key;
  }

  String getWord(History h, int position) {
    if (((h.current + position) >= h.start) && (h.current + position <= h.end)) {
      return arr.get(h.current + position).key;
    } else {
      return "NA";
    }
  }

  String getTag(History h, int position) {
    if (((h.current + position) >= h.start) && (h.current + position <= h.end)) {
      return arr.get(h.current + position).value;
    } else {
      return "NA";
    }
  }
}