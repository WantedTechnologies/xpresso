package com.wantedtech.common.xpresso.strings;

import java.util.Locale;

import com.ibm.icu.text.Transliterator;
import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.en.EN;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.ParametrizedFunction;
import com.wantedtech.common.xpresso.functional.Predicate;

public class HappyStringStatic {
	
    Transliterator anyLatin = Transliterator.getInstance("Any-Latin", Transliterator.FORWARD);
	
	public static ParametrizedFunction<Object,Integer> count(String substring){
		return (new ParametrizedFunction<Object,Integer>() {
			public Integer apply(Object string) {
				try{
					return x.String((String)string).count((String)this.params.get(0));	
				}catch(Exception e){
					throw new IllegalArgumentException("Could not interpret the input object as a String.");
				}
			}
		}).params(x.listOf((Object)substring));
	}
	
	public static ParametrizedFunction<Object,Integer> count(char character){
		return (new ParametrizedFunction<Object,Integer>() {
			public Integer apply(Object string) {
				try{
					return x.String(((String)string)).count((char)this.params.get(0));	
				}catch(Exception e){
					throw new IllegalArgumentException("Could not interpret the input object as a char.");
				}
			}
		}).params(x.listOf((Object)character));
	}
	
	public String stripAccents(String string){
		return x.String(string).stripAccents();
	}
	public Function<Object, String> stripAccents = new Function<Object, String>() {
		public String apply(Object string) {
			return stripAccents((String)string);
		}
	};
	
	public String translit(String string){
        return anyLatin.transliterate(string);
	}
	public Function<Object, String> translit = new Function<Object, String>() {
		public String apply(Object string) {
			return translit((String)string);
		}
	};
	
	public String unidecode(String string){
        return Unidecode.decode(string);
	}
	public Function<Object, String> unidecode = new Function<Object, String>() {
		public String apply(Object string) {
			return unidecode((String)string);
		}
	};
	
	public String escape(String string){
		return x.Regex("([\\[\\]/{}()*+?.\\\\^$\\|-])").sub("\\\\$1", string);
	}
	public Function<Object, String> escape = new Function<Object, String>() {
		public String apply(Object string) {
			return escape((String)string);
		}
	};
	
	public Function<Object, String> strip = new Function<Object, String>() {
		public String apply(Object string) {
			return ((String)string).trim();
		}
	};
	
	public Function<Object, String> trim = strip;

	public String toLowerCase(String string){
		return string.toLowerCase();
	}
	public Function<Object, String> toLowerCase = new Function<Object, String>() {
		public String apply(Object string) {
			String realString = string.toString();
			return realString.toLowerCase();
		}
	};

	public Function<Object, String> lower = toLowerCase;
	
	public Function<Object, String> toUpperCase = new Function<Object, String>() {
		public String apply(Object string) {
			String realString = string.toString();
			return realString.toUpperCase();
		}
	};
	
	public Function<Object, String> upper = toUpperCase;
	
	public String capitalize(String string){
		return x.String(string.toString()).capitalize();
	}
	public Function<Object, String> capitalized = new Function<Object, String>() {
		public String apply(Object string) {
			return x.String(string.toString()).capitalize();
		}
	};
	
	public String title(String string){
		return x.String(string.toString()).title();
	}
	public Function<Object, String> asTitle = new Function<Object, String>() {
		public String apply(Object string) {
			return x.String(string.toString()).title();
		}
	};
	
	public Function<Object, Integer> len = x.len;
	
	public EN EN = new EN();
	
	 /**
     * <p>Checks if the String contains only whitespace.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isWhitespace(null)   = false
     * StringUtils.isWhitespace("")     = true
     * StringUtils.isWhitespace("  ")   = true
     * StringUtils.isWhitespace("abc")  = false
     * StringUtils.isWhitespace("ab2c") = false
     * StringUtils.isWhitespace("ab-c") = false
     * </pre>
     *
     * @param string  the String to check, may be null
     * @return {@code true} if only contains whitespace, and is non-null
     * @since 2.0
     * @since 3.0 Changed signature from isWhitespace(String) to isWhitespace(CharSequence)
     */
    public boolean isWhitespace(final String string) {
        if (string == null) {
            return false;
        }
        final int sz = string.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isWhitespace(string.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
	public Predicate<Object> isWhitespace = new Predicate<Object>() {
		public Boolean apply(Object string) {
			return isWhitespace(string.toString());
		}
	};
	
    /**
     * Returns a formatted string using the specified format string and
     * arguments.
     *
     * <p> The locale always used is the one returned by {@link
     * java.util.Locale#getDefault() Locale.getDefault()}.
     *
     * @param  format
     *         A <a href="../util/Formatter.html#syntax">format string</a>
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         <tt>null</tt> argument depends on the <a
     *         href="../util/Formatter.html#syntax">conversion</a>.
     *
     *
     * @throws  NullPointerException
     *          If the <tt>format</tt> is <tt>null</tt>
     *
     * @return  A formatted string
     *
     * @see  java.util.Formatter
     */
    public String format(String format, Object... args) {
    	return String.format(format, args);
    }

    /**
     * Returns a formatted string using the specified locale, format string,
     * and arguments.
     *
     * @param  l
     *         The {@linkplain java.util.Locale locale} to apply during
     *         formatting.  If <tt>l</tt> is <tt>null</tt> then no localization
     *         is applied.
     *
     * @param  format
     *         A <a href="../util/Formatter.html#syntax">format string</a>
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         <tt>null</tt> argument depends on the <a
     *         href="../util/Formatter.html#syntax">conversion</a>.
     *
     * @return  A formatted string
     *
     * @see  java.util.Formatter
     */
    public String format(Locale l, String format, Object... args) {
    	return String.format(l, format, args);
    }

    /**
     * Returns the string representation of the <code>Object</code> argument.
     *
     * @param   obj   an <code>Object</code>.
     * @return  if the argument is <code>null</code>, then a string equal to
     *          <code>"null"</code>; otherwise, the value of
     *          <code>obj.toString()</code> is returned.
     * @see     java.lang.Object#toString()
     */
    public String valueOf(Object obj) {
    	return String.valueOf(obj);
    }

    /**
     * Returns the string representation of the <code>char</code> array
     * argument. The contents of the character array are copied; subsequent
     * modification of the character array does not affect the newly
     * created string.
     *
     * @param   data   a <code>char</code> array.
     * @return  a newly allocated string representing the same sequence of
     *          characters contained in the character array argument.
     */
    public String valueOf(char data[]) {
    	return String.valueOf(data);
    }

    /**
     * Returns the string representation of a specific subarray of the
     * <code>char</code> array argument.
     * <p>
     * The <code>offset</code> argument is the index of the first
     * character of the subarray. The <code>count</code> argument
     * specifies the length of the subarray. The contents of the subarray
     * are copied; subsequent modification of the character array does not
     * affect the newly created string.
     *
     * @param   data     the character array.
     * @param   offset   the initial offset into the value of the
     *                  <code>String</code>.
     * @param   count    the length of the value of the <code>String</code>.
     * @return  a string representing the sequence of characters contained
     *          in the subarray of the character array argument.
     * @exception IndexOutOfBoundsException if <code>offset</code> is
     *          negative, or <code>count</code> is negative, or
     *          <code>offset+count</code> is larger than
     *          <code>data.length</code>.
     */
    public String valueOf(char data[], int offset, int count) {
    	return String.valueOf(data, offset, count);
    }

    /**
     * Returns a String that represents the character sequence in the
     * array specified.
     *
     * @param   data     the character array.
     * @param   offset   initial offset of the subarray.
     * @param   count    length of the subarray.
     * @return  a <code>String</code> that contains the characters of the
     *          specified subarray of the character array.
     */
    public String copyValueOf(char data[], int offset, int count) {
        // All public String constructors now copy the data.
    	return String.copyValueOf(data, offset, count);
    }

    /**
     * Returns a String that represents the character sequence in the
     * array specified.
     *
     * @param   data   the character array.
     * @return  a <code>String</code> that contains the characters of the
     *          character array.
     */
    public String copyValueOf(char data[]) {
    	return String.copyValueOf(data);
    }

    /**
     * Returns the string representation of the <code>boolean</code> argument.
     *
     * @param   b   a <code>boolean</code>.
     * @return  if the argument is <code>true</code>, a string equal to
     *          <code>"true"</code> is returned; otherwise, a string equal to
     *          <code>"false"</code> is returned.
     */
    public String valueOf(boolean b) {
    	return String.valueOf(b);
    }

    /**
     * Returns the string representation of the <code>char</code>
     * argument.
     *
     * @param   c   a <code>char</code>.
     * @return  a string of length <code>1</code> containing
     *          as its single character the argument <code>c</code>.
     */
    public String valueOf(char c) {
    	return String.valueOf(c);
    }

    /**
     * Returns the string representation of the <code>int</code> argument.
     * <p>
     * The representation is exactly the one returned by the
     * <code>Integer.toString</code> method of one argument.
     *
     * @param   i   an <code>int</code>.
     * @return  a string representation of the <code>int</code> argument.
     * @see     java.lang.Integer#toString(int, int)
     */
    public String valueOf(int i) {
        return Integer.toString(i);
    }

    /**
     * Returns the string representation of the <code>long</code> argument.
     * <p>
     * The representation is exactly the one returned by the
     * <code>Long.toString</code> method of one argument.
     *
     * @param   l   a <code>long</code>.
     * @return  a string representation of the <code>long</code> argument.
     * @see     java.lang.Long#toString(long)
     */
    public String valueOf(long l) {
        return Long.toString(l);
    }

    /**
     * Returns the string representation of the <code>float</code> argument.
     * <p>
     * The representation is exactly the one returned by the
     * <code>Float.toString</code> method of one argument.
     *
     * @param   f   a <code>float</code>.
     * @return  a string representation of the <code>float</code> argument.
     * @see     java.lang.Float#toString(float)
     */
    public String valueOf(float f) {
        return Float.toString(f);
    }

    /**
     * Returns the string representation of the <code>double</code> argument.
     * <p>
     * The representation is exactly the one returned by the
     * <code>Double.toString</code> method of one argument.
     *
     * @param   d   a <code>double</code>.
     * @return  a  string representation of the <code>double</code> argument.
     * @see     java.lang.Double#toString(double)
     */
    public String valueOf(double d) {
        return Double.toString(d);
    }
}
