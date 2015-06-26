package com.wantedtech.common.xpresso.sentence.pos.en.stanford;

/**
 * Title:        StanfordMaxEnt<p>
 * Description:  A Maximum Entropy Toolkit<p>
 * Copyright:    Copyright (c) Trustees of Leland Stanford Junior University<p>
 * Company:      Stanford University<p>
 */

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.wantedtech.common.xpresso.types.tuples.tuple2;


/**
 * This class is used as a base class for TaggerFeature for the
 * tagging problem and for BinaryFeature for the general problem with binary
 * features.
 *
 * @author Kristina Toutanova
 * @version 1.0
 */
public class Feature {

  /**
   * This will contain the (x,y) pairs for which the feature is non-zero in
   * case it is sparse.
   * The pairs (x,y) are coded as x*ySize+y. The values are kept in valuesI.
   * For example, if a feature has only two non-zero values, e.g f(1,2)=3
   * and f(6,3)=0.74, then indexedValues will have values
   * indexedValues={1*ySize+2,6*ySize+2} and valuesI will be {3,.74}
   */
  public int[] indexedValues;

  /**
   * These are the non-zero values we want to keep for the points in
   * indexedValues.
   */
  private double[] valuesI;
  static Experiments domain;  // todo [cdm 2013]: This needs to be removed! Try to put field in Features class, rather than adding as field to every object.
  private Map<Integer,Double> hashValues;
  protected double sum; // the sum of all values

  protected Index<tuple2<Integer,Integer>> instanceIndex;

  public Feature() {
  }


  /**
   * This is if we are given an array of double with a value for each training sample in the order of their occurrence.
   */
  public Feature(Experiments e, double[] vals, Index<tuple2<Integer,Integer>> instanceIndex) {
    this.instanceIndex = instanceIndex;
    Map<Integer, Double> setNonZeros = new HashMap<Integer, Double>();
    for (int i = 0; i < vals.length; i++) {
      if (vals[i] != 0.0) {
        Integer in = Integer.valueOf(indexOf(e.get(i)[0], e.get(i)[1]));// new Integer(e.get(i)[0]*e.ySize+e.get(i)[1]);
        Double oldVal = setNonZeros.put(in, Double.valueOf(vals[i]));
        if (oldVal != null && oldVal.doubleValue() != vals[i]) {
          throw new IllegalStateException("Incorrect function specification: Feature has two values at one point: " + oldVal + " and " + vals[i]);
        }
      }//if
    }// for
    Integer[] keys = setNonZeros.keySet().toArray(new Integer[setNonZeros.keySet().size()]);
    indexedValues = new int[keys.length];
    valuesI = new double[keys.length];
    for (int j = 0; j < keys.length; j++) {
      indexedValues[j] = keys[j].intValue();
      valuesI[j] = setNonZeros.get(keys[j]).doubleValue();
    } // for
    domain = e;
  }


  int indexOf(int x, int y) {
    tuple2<Integer,Integer> iP = tuple2.valueOf(x, y);
    return instanceIndex.indexOf(iP);
  }

  tuple2<Integer,Integer> getPair(int index) {
    return instanceIndex.get(index);
  }

  int getXInstance(int index) {
    tuple2<Integer,Integer> iP = getPair(index);
    return iP.key;
  }

  int getYInstance(int index) {
    tuple2<Integer,Integer> iP = getPair(index);
    return iP.value;
  }

  /**
   * @param vals a value for each (x,y) pair
   */
  public Feature(Experiments e, double[][] vals, Index<tuple2<Integer,Integer>> instanceIndex) {
    this.instanceIndex = instanceIndex;
    domain = e;
    int num = 0;
    for (int x = 0; x < e.xSize; x++) {
      for (int y = 0; y < e.ySize; y++) {
        if (vals[x][y] != 0) {
          num++;
        }
      }
    }
    indexedValues = new int[num];
    valuesI = new double[num];
    int current = 0;
    for (int x = 0; x < e.xSize; x++) {
      for (int y = 0; y < e.ySize; y++) {
        if (vals[x][y] != 0) {
          indexedValues[current] = indexOf(x, y);
          valuesI[current] = vals[x][y];
          current++;
        }//if
      }//for
    }
  }

  public Feature(Experiments e, int numElems, Index<tuple2<Integer,Integer>> instanceIndex) {
    this.instanceIndex = instanceIndex;
    domain = e;
    indexedValues = new int[numElems];
    valuesI = new double[numElems];

  }


  /**
   * @param indexes The pairs (x,y) for which the feature is non-zero. They are coded as x*ySize+y
   * @param vals    The values at these points.
   */
  public Feature(Experiments e, int[] indexes, double[] vals, Index<tuple2<Integer,Integer>> instanceIndex) {
    domain = e;
    indexedValues = indexes;
    valuesI = vals;
    this.instanceIndex = instanceIndex;
  }


  /**
   * Prints out the points where the feature is non-zero and the values
   * at these points.
   */
  public void print() {
    print(System.out);
  }


  /**
   * Used to sequentially set the values of a feature -- index is the pace in the arrays ; key goes into
   * indexedValues, and value goes into valuesI.
   */
  public void setValue(int index, int key, double value) {
    indexedValues[index] = key;
    valuesI[index] = value;

  }

  public void print(PrintStream pf) {
    for (int i = 0; i < indexedValues.length; i++) {
      tuple2<Integer,Integer> iP = getPair(indexedValues[i]);
      int x = iP.key;
      int y = iP.value;
      // int y=indexedValues[i]-x*domain.ySize;
      pf.println(x + ", " + y + ' ' + valuesI[i]);
    }
  }


  /**
   * Get the value at the index-ed non zero value pair (x,y)
   */
  public double getVal(int index) {
    return valuesI[index];
  }


  public void setSum() {
    for (double value : valuesI) {
      sum += value;
    }
  }


  public int len() {
    if (indexedValues != null) {
      return indexedValues.length;
    } else {
      return 0;
    }
  }


  /**
   * @return the history x of the index-th (x,y) pair
   */
  public int getX(int index) {
    return getXInstance(indexedValues[index]);
  }


  /**
   * @return the outcome y of the index-th (x,y) pair
   */
  public int getY(int index) {
    return getYInstance(indexedValues[index]);
    // return indexedValues[index]-(indexedValues[index]/domain.ySize)*domain.ySize;
  }

  /**
   * This is rarely used because it is slower and requires initHashVals() to be called beforehand
   * to initialize the hashValues.
   */
  public double getVal(int x, int y) {
    Double val = hashValues.get(Integer.valueOf(indexOf(x, y)));
    if (val == null) {
      return 0.0;
    } else {
      return val.doubleValue();
    }
  }


  /**
   * Creates a HashMap with keys indices from pairs (x,y) and values the value of the function at the pair;
   * required for use of getVal(x,y)
   */
  public void initHashVals() {
    hashValues = new HashMap<Integer,Double>();
    for (int i = 0; i < len(); i++) {
      int x = getX(i);
      int y = getY(i);
      Double value = new Double(getVal(i));
      this.hashValues.put(Integer.valueOf(indexOf(x, y)), value);
    }
  }


  /**
   * @return The empirical expectation of the feature.
   */
  public double ftilde() {
    double s = 0.0;
    for (int i = 0; i < indexedValues.length; i++) {
      int x = getXInstance(indexedValues[i]);
      int y = getYInstance(indexedValues[i]);
      // int y=indexedValues[i]-x*domain.ySize;
      s = s + domain.ptildeXY(x, y) * getVal(i);
    }
    return s;
  }

}