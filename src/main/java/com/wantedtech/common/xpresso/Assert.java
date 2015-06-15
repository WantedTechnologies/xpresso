/* 
 * Adapted from com.google.common.base.Assert and org.apache.commons.lang3
 * 
 * (c) Kevin Bourrillion and other contributors
 * */

package com.wantedtech.common.xpresso;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Simple methods to be called at the start of your own methods to verify
 * correct arguments and state. This allows constructs such as
 * <pre>
 * {@code
 *     if (count <= 0) {
 *       throw new IllegalArgumentException("must be positive: " + count);
 *     }
 * }
 * </pre>
 *
 * to be replaced with the more compact
 * <pre>
 * {@code
 *     isTrue(count > 0, "must be positive: %s", count);
 * }
 * </pre>
 *
 * Note that the sense of the expression is inverted; with {@code Assert}
 * you declare what you expect to be {@code true}, just as you do with an
 * <a href="http://java.sun.com/j2se/1.5.0/docs/guide/language/assert.html">
 * {@code assert}</a> or a JUnit {@code assertTrue} call.
 *
 * <p><b>Warning:</b> only the {@code "%s"} specifier is recognized as a
 * placeholder in these messages, not the full range of {@link
 * String#format(String, Object[])} specifiers.
 *
 * Take care not to confuse precondition checking with other similar types
 * of checks! Precondition exceptions -- including those provided here, but also
 * {@link IndexOutOfBoundsException}, {@link NoSuchElementException}, {@link
 * UnsupportedOperationException} and others -- are used to signal that the
 * <i>calling method</i> has made an error. This tells the caller that it should
 * not have invoked the method when it did, with the arguments it did, or
 * perhaps ever. Postcondition or other invariant failures should not throw
 * these types of exceptions.
 */

public final class Assert {
  public Assert() {}

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   *
   * @param expression a boolean expression
   * @throws IllegalArgumentException if {@code expression} is false
   */
  public void True(boolean expression) {
    if (!expression) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   *
   * @param expression a boolean expression
   * @param errorMessage the exception message to use if the check fails; will
   *     be converted to a string using {@link String#valueOf(Object)}
   * @throws IllegalArgumentException if {@code expression} is false
   */
  public void True(
      boolean expression, Object errorMessage) {
    if (!expression) {
      throw new IllegalArgumentException(String.valueOf(errorMessage));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   *
   * @param expression a boolean expression
   * @param errorMessageTemplate a template for the exception message should the
   *     check fail. The message is formed by replacing each {@code %s}
   *     placeholder in the template with an argument. These are matched by
   *     position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
   *     Unmatched arguments will be appended to the formatted message in square
   *     braces. Unmatched placeholders will be left as-is.
   * @param errorMessageArgs the arguments to be substituted into the message
   *     template. Arguments are converted to strings using
   *     {@link String#valueOf(Object)}.
   * @throws IllegalArgumentException if {@code expression} is false
   * @throws NullPointerException if the check fails and either {@code
   *     errorMessageTemplate} or {@code errorMessageArgs} is null (don't let
   *     this happen)
   */
  public void True(boolean expression,
      String errorMessageTemplate,
      Object... errorMessageArgs) {
    if (!expression) {
      throw new IllegalArgumentException(
          format(errorMessageTemplate, errorMessageArgs));
    }
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling
   * method is not null.
   *
   * @param reference	an object reference
   * @param <T>			any type
   * @return the non-null reference that was validated
   * @throws NullPointerException if {@code reference} is null
   */
  public <T> T notNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    }
    return reference;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling
   * method is not null.
   *
   * @param <T>				any type
   * @param reference		an object reference
   * @param errorMessage	the exception message to use if the check fails; will
   *     					be converted to a string using {@link String#valueOf(Object)}
   * @return the non-null reference that was validated
   * @throws NullPointerException if {@code reference} is null
   */
  public <T> T notNull(T reference, Object errorMessage) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    }
    return reference;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling
   * method is not null.
   *
   * @param <T> 					any type
   * @param reference 				an object reference
   * @param errorMessageTemplate	Template a template for the exception message should the
   *     							check fail. The message is formed by replacing each {@code %s}
   *     							placeholder in the template with an argument. These are matched by
   *     							position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
   *     							Unmatched arguments will be appended to the formatted message in square
   *		     					braces. Unmatched placeholders will be left as-is.
   * @param errorMessageArgs		the arguments to be substituted into the message
   *     							template. Arguments are converted to strings using
   *     							{@link String#valueOf(Object)}.
   * @return the non-null reference that was validated
   * @throws NullPointerException if {@code reference} is null
   */
  public <T> T notNull(T reference,
      String errorMessageTemplate,
      Object... errorMessageArgs) {
    if (reference == null) {
      // If either of these parameters is null, the right thing happens anyway
      throw new NullPointerException(
          format(errorMessageTemplate, errorMessageArgs));
    }
    return reference;
  }

  /**
   * <p>Assert that the specified argument array is neither {@code null}
   * nor a length of zero (no elements); otherwise throwing an exception
   * with the specified message.
   *
   * <pre>x.Assert.notEmpty(myArray, "The array must not be empty");</pre>
   *
   * @param <T> the array type
   * @param array  the array to check, validated not null by this method
   * @param message  the {@link String#format(String, Object...)} exception message if invalid, not null
   * @param values  the optional values for the formatted exception message, null array not recommended
   * @return the validated array (never {@code null} method for chaining)
   * @throws NullPointerException if the array is {@code null}
   * @throws IllegalArgumentException if the array is empty
   * @see #notEmpty(Object[])
   */
  public <T> T[] notEmpty(final T[] array, final String message, final Object... values) {
      if (array == null) {
          throw new NullPointerException(String.format(message, values));
      }
      if (array.length == 0) {
          throw new IllegalArgumentException(String.format(message, values));
      }
      return array;
  }

  /**
   * <p>Assert that the specified argument array is neither {@code null}
   * nor a length of zero (no elements); otherwise throwing an exception.
   *
   * <pre>x.Assert.notEmpty(myArray);</pre>
   *
   * <p>The message in the exception is &quot;The validated array is
   * empty&quot;.
   *
   * @param <T> the array type
   * @param array  the array to check, validated not null by this method
   * @return the validated array (never {@code null} method for chaining)
   * @throws NullPointerException if the array is {@code null}
   * @throws IllegalArgumentException if the array is empty
   * @see #notEmpty(Object[], String, Object...)
   */
  public <T> T[] notEmpty(final T[] array) {
      return notEmpty(array, "The validated array is empty");
  }

  // notEmpty iterable
  //---------------------------------------------------------------------------------

  /**
   * <p>Assert that the specified argument iterable is neither {@code null}
   * nor a size of zero (no elements); otherwise throwing an exception
   * with the specified message.
   *
   * <pre>x.Assert.notEmpty(myIterable, "The iterable must not be empty");</pre>
   *
   * @param <T> the iterable type
   * @param iterable  the iterable to check, validated not null by this method
   * @param message  the {@link String#format(String, Object...)} exception message if invalid, not null
   * @param values  the optional values for the formatted exception message, null array not recommended
   * @throws NullPointerException if the iterable is {@code null}
   * @throws IllegalArgumentException if the iterable is empty
   * @see #notEmpty(Object[])
   */
  public <T> void notEmpty(final T iterable, final String message, final Object... values) {
      if (iterable == null) {
          throw new NullPointerException(String.format(message, values));
      }
      if (x.len(iterable) == 0) {
          throw new IllegalArgumentException(String.format(message, values));
      }
  }

  /**
   * <p>Assert that the specified argument iterable is neither {@code null}
   * nor a size of zero (no elements); otherwise throwing an exception.
   *
   * <pre>x.Assert.notEmpty(myIterable);</pre>
   *
   * <p>The message in the exception is &quot;The validated iterable is
   * empty&quot;.</p>
   *
   * @param <T> the iterable type
   * @param iterable  the iterable to check, validated not null by this method
   * @throws NullPointerException if the iterable is {@code null}
   * @throws IllegalArgumentException if the iterable is empty
   */
  public <T> void notEmpty(final T iterable) {
      notEmpty(iterable, "The validated iterable is empty");
  }

  // notEmpty map
  //---------------------------------------------------------------------------------

  /**
   * <p>Assert that the specified argument map is neither {@code null}
   * nor a size of zero (no elements); otherwise throwing an exception
   * with the specified message.
   *
   * <pre>x.Assert.notEmpty(myMap, "The map must not be empty");</pre>
   *
   * @param <K>		the type of key
   * @param <V>		the type of value
   * @param map  the map to check, validated not null by this method
   * @param message  the {@link String#format(String, Object...)} exception message if invalid, not null
   * @throws NullPointerException if the map is {@code null}
   * @throws IllegalArgumentException if the map is empty
   * @see #notEmpty(Object[])
   */
  public <K,V> void notEmpty(final Map<K,V> map, final String message) {
      if (map == null) {
          throw new NullPointerException(message);
      }
      if (map.isEmpty()) {
          throw new IllegalArgumentException(message);
      }
  }

  /**
   * <p>Assert that the specified argument map is neither {@code null}
   * nor a size of zero (no elements); otherwise throwing an exception.
   *
   * <pre>x.Assert.notEmpty(myMap);</pre>
   *
   * <p>The message in the exception is &quot;The validated map is
   * empty&quot;.</p>
   *
   * @param <K>	the type of key
   * @param <V>	the type of value
   * @param map	the map to check, validated not null by this method
   * @throws NullPointerException if the map is {@code null}
   * @throws IllegalArgumentException if the map is empty
   * @see #notEmpty(Map, String)
   */
  public <K,V> void notEmpty(final Map<K,V> map) {
      notEmpty(map, "The validated map is empty");
  }

  // notEmpty string
  //---------------------------------------------------------------------------------

  /**
   * <p>Assert that the specified argument character sequence is
   * neither {@code null} nor a length of zero (no characters);
   * otherwise throwing an exception with the specified message.
   *
   * <pre>x.Assert.notEmpty(myString, "The string must not be empty");</pre>
   *
   * @param <T>		the character sequence type
   * @param chars	the character sequence to check, validated not null by this method
   * @param message	the {@link String#format(String, Object...)} exception message if invalid, not null
   * @throws NullPointerException if the character sequence is {@code null}
   * @throws IllegalArgumentException if the character sequence is empty
   */
  public <T extends CharSequence> void notEmpty(final T chars, final String message) {
      if (chars == null) {
          throw new NullPointerException(message);
      }
      if (chars.length() == 0) {
          throw new IllegalArgumentException(message);
      }
  }

  
  /**
   * Substitutes each {@code %s} in {@code template} with an argument. These
   * are matched by position - the first {@code %s} gets {@code args[0]}, etc.
   * If there are more arguments than placeholders, the unmatched arguments will
   * be appended to the end of the formatted message in square braces.
   *
   * @param template a non-null string containing 0 or more {@code %s}
   *     placeholders.
   * @param args the arguments to be substituted into the message
   *     template. Arguments are converted to strings using
   *     {@link String#valueOf(Object)}. Arguments can be null.
   */
  static String format(String template,
      Object... args) {
    template = String.valueOf(template); // null -> "null"

    // start substituting the arguments into the '%s' placeholders
    StringBuilder builder = new StringBuilder(
        template.length() + 16 * args.length);
    int templateStart = 0;
    int i = 0;
    while (i < args.length) {
      int placeholderStart = template.indexOf("%s", templateStart);
      if (placeholderStart == -1) {
        break;
      }
      builder.append(template.substring(templateStart, placeholderStart));
      builder.append(args[i++]);
      templateStart = placeholderStart + 2;
    }
    builder.append(template.substring(templateStart));

    // if we run out of placeholders, append the extra args in square braces
    if (i < args.length) {
      builder.append(" [");
      builder.append(args[i++]);
      while (i < args.length) {
        builder.append(", ");
        builder.append(args[i++]);
      }
      builder.append(']');
    }

    return builder.toString();
  }
}