package com.wantedtech.common.xpresso.functional;

/**
 * Determines an output value based on an input value.
 *
 */
public interface Function<F, T> {
  /**
   * Returns the result of applying this function to {@code input}.
   *
   * @param input	the input value of type F for the function
   * @return		the output of type T
   *
   */
  T apply(F input);
}
