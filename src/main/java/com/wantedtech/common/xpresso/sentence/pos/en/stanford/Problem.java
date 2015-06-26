package com.wantedtech.common.xpresso.sentence.pos.en.stanford;

/**
 * Title:        StanfordMaxEnt<p>
 * Description:  A Maximum Entropy Toolkit<p>
 * Copyright:    Copyright (c) Trustees of Leland Stanford University
 * Company:      Stanford University<p>
 */


/**
 * This is a general class for a Problem to be solved by the MaxEnt toolkit.
 * There have to be experiments and features.
 *
 * @author Kristina Toutanova
 * @version 1.0
 */
public class Problem {

  public int exSize;
  public int fSize;

  /**
   * This is the training data.
   */
  public Experiments data;

  /**
   * These are the features.
   */
  public Features functions;

  public Problem(Experiments d, Features f) {
    data = d;
    functions = f;
    exSize = d.size();
    fSize = f.size();
  }

  public Problem() {
  }


  public void add(Feature f) {
    functions.add(f);
    fSize++;
  }


  public void removeLast() {
    functions.removeLast();
    fSize--;
  }

  public void print() {
    System.out.println(" Problem printing ");
    data.print();
    System.out.println(" Function printing ");
    for (int i = 0; i < fSize; i++) {
      functions.get(i).print();
    }
  }
}