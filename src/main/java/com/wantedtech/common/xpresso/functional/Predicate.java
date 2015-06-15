package com.wantedtech.common.xpresso.functional;

/**
 * Determines a true or false value for a given input.
 */

public interface Predicate<T> extends Function<T,Boolean> {
  /**
   * Returns the result of applying this predicate to {@code input}.
  */
  Boolean apply(T input);
}
