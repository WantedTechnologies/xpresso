package com.wantedtech.common.xpresso.sentence.pos.en.stanford;

/**
 * Title:        StanfordMaxEnt<p>
 * Description:  A Maximum Entropy Toolkit<p>
 * Copyright:    Copyright (c) Kristina Toutanova<p>
 * Company:      Stanford University<p>
 */

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.wantedtech.common.xpresso.types.Bag;


/** Maintains a map from words to tags and their counts.
 *
 *  @author Kristina Toutanova
 *  @version 1.0
 */
public class Dictionary {

  private final Map<String,TagCount> dict = new HashMap<String,TagCount>();
  private final Map<Integer,CountWrapper> partTakingVerbs = new HashMap<Integer,CountWrapper>();
  private static final String naWord = "NA";
  private static final boolean VERBOSE = false;

  public Dictionary() {
  }

  void fillWordTagCounts(Map<String, Bag<String>> wordTagCounts) {
    for (String word : wordTagCounts.keySet()) {
      TagCount count = new TagCount(wordTagCounts.get(word));
      dict.put(word, count);
    }
  }

  protected void addVThatTaking(String verb) {
    int i = verb.hashCode();
    if (this.partTakingVerbs.containsKey(i)) {
      this.partTakingVerbs.get(i).incThat();
    } else {
      this.partTakingVerbs.put(i, new CountWrapper(verb, 0, 1, 0, 0));
    }
  }

  protected int getCountPart(String verb) {
    int i = verb.hashCode();
    if (this.partTakingVerbs.containsKey(i)) {
      return this.partTakingVerbs.get(i).getCountPart();
    }
    return 0;
  }


  protected int getCountThat(String verb) {
    int i = verb.hashCode();
    if (this.partTakingVerbs.containsKey(i)) {
      return this.partTakingVerbs.get(i).getCountThat();
    }
    return 0;
  }


  protected int getCountIn(String verb) {
    int i = verb.hashCode();
    if (this.partTakingVerbs.containsKey(i)) {
      return this.partTakingVerbs.get(i).getCountIn();
    }
    return 0;
  }


  protected int getCountRB(String verb) {
    int i = verb.hashCode();
    if (this.partTakingVerbs.containsKey(i)) {
      return this.partTakingVerbs.get(i).getCountRB();
    }
    return 0;
  }


  public int getCount(String word, String tag) {
    TagCount count = dict.get(word);
    if (count == null) {
      return 0;
    } else {
      return count.get(tag);
    }
  }


  public String[] getTags(String word) {
    TagCount count = get(word);
    if (count == null) {
      return null;
    }
    return count.getTags();
  }


  protected TagCount get(String word) {
    return dict.get(word);
  }


  public String getFirstTag(String word) {
    TagCount count = dict.get(word);
    if (count != null) {
      return count.getFirstTag();
    }
    return null;
  }


  public int sum(String word) {
    TagCount count = dict.get(word);
    if (count != null) {
      return count.sum();
    }
    return 0;
  }

  public boolean isUnknown(String word) {
    return ! dict.containsKey(word);
  }


  /*
  public void save(String filename) {
    try {
      DataOutputStream rf = IOUtils.getDataOutputStream(filename);
      save(rf);
      rf.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  */

  void save(DataOutputStream file) {
    String[] arr = dict.keySet().toArray(new String[dict.keySet().size()]);
    try {
      file.writeInt(arr.length);
      System.err.println("Saving dictionary of " + arr.length + " words ...");
      for (String word : arr) {
        TagCount count = get(word);
        file.writeUTF(word);
        count.save(file);
      }
      Integer[] arrverbs = this.partTakingVerbs.keySet().toArray(new Integer[partTakingVerbs.keySet().size()]);
      file.writeInt(arrverbs.length);
      for (Integer iO : arrverbs) {
        CountWrapper tC = this.partTakingVerbs.get(iO);
        file.writeInt(iO.intValue());
        tC.save(file);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void read(DataInputStream rf, String filename) throws IOException {
    // Object[] arr=dict.keySet().toArray();

    int maxNumTags = 0;
    int len = rf.readInt();
    if (VERBOSE) {
      System.err.println("Reading Dictionary of " + len + " words from " + filename + '.');
    }

    for (int i = 0; i < len; i++) {
      String word = rf.readUTF();
      TagCount count = TagCount.readTagCount(rf);
      int numTags = count.numTags();
      if (numTags > maxNumTags) {
        maxNumTags = numTags;
      }
      this.dict.put(word, count);
      if (VERBOSE) {
        System.err.println("  " + word + " [idx=" + i + "]: " + count);
      }
    }
    if (VERBOSE) {
      System.err.println("Read dictionary of " + len + " words; max tags for word was " + maxNumTags + '.');
    }
  }

  public void read(DataInputStream file) {
	    try {
	      readTags(file);

	      int len1 = file.readInt();
	      for (int i = 0; i < len1; i++) {
	        int iO = file.readInt();
	        CountWrapper tC = new CountWrapper();
	        tC.read(file);

	        this.partTakingVerbs.put(iO, tC);
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
  
  private void readTags(DataInputStream rf) throws IOException {
    // Object[] arr=dict.keySet().toArray();

    int maxNumTags = 0;
    int len = rf.readInt();
    if (VERBOSE) {
      System.err.println("Reading Dictionary of " + len + " words.");
    }

    for (int i = 0; i < len; i++) {
      String word = rf.readUTF();
      TagCount count = TagCount.readTagCount(rf);
      int numTags = count.numTags();
      if (numTags > maxNumTags) {
        maxNumTags = numTags;
      }
      this.dict.put(word, count);
      if (VERBOSE) {
        System.err.println("  " + word + " [idx=" + i + "]: " + count);
      }
    }
    if (VERBOSE) {
      System.err.println("Read dictionary of " + len + " words; max tags for word was " + maxNumTags + '.');
    }
  }

  /**
   * This makes ambiguity classes from all words in the dictionary and remembers
   * their classes in the TagCounts
   */
  public void setAmbClasses(AmbiguityClasses ambClasses, int veryCommonWordThresh, TTags ttags) {
    for (Map.Entry<String,TagCount> entry : dict.entrySet()) {
      String w = entry.getKey();
      TagCount count = entry.getValue();
      int ambClassId = ambClasses.getClass(w, this, veryCommonWordThresh, ttags);
      count.setAmbClassId(ambClassId);
    }
  }

  protected int getAmbClass(String word) {
    if (word.equals(naWord)) {
      return -2;
    }
    if (get(word) == null) {
      return -1;
    }
    return get(word).getAmbClassId();
  }

  public static void main(String[] args) {
    String s = "word";
    String tag = "tag";
    Dictionary d = new Dictionary();

    System.out.println(d.getCount(s, tag));
    System.out.println(d.getFirstTag(s));
  }

}