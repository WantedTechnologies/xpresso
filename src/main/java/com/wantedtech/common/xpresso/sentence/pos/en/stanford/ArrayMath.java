package com.wantedtech.common.xpresso.sentence.pos.en.stanford;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Class ArrayMath
 *
 * @author Teg Grenager
 */

/**
 * If a difference is bigger than this in log terms, then the sum or
 * difference of them will just be the larger (to 12 or so decimal
 * places for double, and 7 or 8 for float).
 */

public class ArrayMath {

	static final double LOGTOLERANCE = 30.0;
	static final float LOGTOLERANCE_F = 20.0f;

  /**
   * Returns the log of the sum of an array of numbers, which are
   * themselves input in log form.  This is all natural logarithms.
   * Reasonable care is taken to do this as efficiently as possible
   * (under the assumption that the numbers might differ greatly in
   * magnitude), with high accuracy, and without numerical overflow.
   *
   * @param logInputs An array of numbers [log(x1), ..., log(xn)]
   * @return {@literal log(x1 + ... + xn)}
   */
  public static double logSum(double... logInputs) {
    return logSum(logInputs,0,logInputs.length);
  }
  
  /**
   * Returns the log of the portion between <code>fromIndex</code>, inclusive, and
   * <code>toIndex</code>, exclusive, of an array of numbers, which are
   * themselves input in log form.  This is all natural logarithms.
   * Reasonable care is taken to do this as efficiently as possible
   * (under the assumption that the numbers might differ greatly in
   * magnitude), with high accuracy, and without numerical overflow.  Throws an
   * {@link IllegalArgumentException} if <code>logInputs</code> is of length zero.
   * Otherwise, returns Double.NEGATIVE_INFINITY if <code>fromIndex</code> &gt;=
   * <code>toIndex</code>.
   *
   * @param logInputs An array of numbers [log(x1), ..., log(xn)]
   * @param fromIndex The array index to start the sum from
   * @param toIndex The array index after the last element to be summed
   * @return {@literal log(x1 + ... + xn)}
   */
  public static double logSum(double[] logInputs, int fromIndex, int toIndex) {
    if (logInputs.length == 0)
      throw new IllegalArgumentException();
    if(fromIndex >= 0 && toIndex < logInputs.length && fromIndex >= toIndex)
      return Double.NEGATIVE_INFINITY;
    int maxIdx = fromIndex;
    double max = logInputs[fromIndex];
    for (int i = fromIndex+1; i < toIndex; i++) {
      if (logInputs[i] > max) {
        maxIdx = i;
        max = logInputs[i];
      }
    }
    boolean haveTerms = false;
    double intermediate = 0.0;
    double cutoff = max - LOGTOLERANCE;
    // we avoid rearranging the array and so test indices each time!
    for (int i = fromIndex; i < toIndex; i++) {
      if (i != maxIdx && logInputs[i] > cutoff) {
        haveTerms = true;
        intermediate += Math.exp(logInputs[i] - max);
      }
    }
    if (haveTerms) {
      return max + Math.log(1.0 + intermediate);
    } else {
      return max;
    }
  }
  
  public static void pairwiseAddInPlace(double[] to, double[] from) {
	    if (to.length != from.length) {
	      throw new RuntimeException("to length:" + to.length + " from length:" + from.length);
	    }
	    for (int i = 0; i < to.length; i++) {
	      to[i] = to[i] + from[i];
	    }
  }
  
  /**
   * Makes the values in this array sum to 1.0. Does it in place.
   * If the total is 0.0, throws a RuntimeException.
   * If the total is Double.NEGATIVE_INFINITY, then it replaces the
   * array with a normalized uniform distribution. CDM: This last bit is
   * weird!  Do we really want that?
   */
  public static void logNormalize(double[] a) {
    double logTotal = logSum(a);
    if (logTotal == Double.NEGATIVE_INFINITY) {
      // to avoid NaN values
      double v = -Math.log(a.length);
      for (int i = 0; i < a.length; i++) {
        a[i] = v;
      }
      return;
    }
    addInPlace(a, -logTotal); // subtract log total from each value
  }
  
  /**
   * Increases the values in this array by b. Does it in place.
   *
   * @param a The array
   * @param b The amount by which to increase each item
   */
  public static void addInPlace(double[] a, double b) {
    for (int i = 0; i < a.length; i++) {
      a[i] = a[i] + b;
    }
  }
  
}