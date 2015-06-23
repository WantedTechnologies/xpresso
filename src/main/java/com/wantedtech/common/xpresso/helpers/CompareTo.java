/*
 * The code is adapted from the CompateToBuilder of org.apache.commons.lang3.builder
 * 
 */
package com.wantedtech.common.xpresso.helpers;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Comparator;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;

/** 
 * Assists in implementing {@link java.lang.Comparable#compareTo(Object)} methods.
 *
 * <p>Two Objects that compare equal using <code>equals(Object)</code> should normally
 * also compare equal using <code>compareTo(Object)</code>.</p>
 *
 * <p>To use this class write code as follows:</p>
 *
 * <pre>
 * public class MyClass {
 *   String field1;
 *   int field2;
 *   boolean field3;//do not use in the comparison
 *
 *   ...
 *
 *   public int compareTo(Object o) {
 *     MyClass myClass = (MyClass) o;
 *     return new CompareTo(this, o, "field1", "field2").getComparison();
 *   }
 * }
 * </pre>
 * 
 * <p>Values are compared in the order they are given to the constructor. In the example above
 * the values in the "field1" will be compared first, the the values in the "field2".
 *
 * @see java.lang.Comparable
 * @see java.lang.Object#equals(Object)
 * @see java.lang.Object#hashCode()
 * @see Equals
 * @see HashCode
 * @since 0.1
 */
public class CompareTo {
    
    /**
     * Current state of the comparison as appended fields are checked.
     */

    final Object lhs;
    final Object rhs;   
    final dict<Integer> fieldComparisons = x.dict();
    final list<String> fieldList = x.list();
    
    int finalComparison = 0;
    
    /**
     * <p>Constructor for CompareTo.</p>
     * 
     * @param lhs  left-hand object
     * @param rhs  right-hand object
     * @param field0	including <pre>moreFields</pre> are fields to compare in the given order
     * @param moreFields  including <pre>field0</pre> are fields to compare in the given order
     *
     */
    public CompareTo(Object lhs, Object rhs, String field0, String... moreFields) {

        this.lhs = lhs;
        this.rhs = rhs;
        fieldList.append(field0);
        for(String fieldName : moreFields)
        	fieldList.append(fieldName);
        reflectionCompare();
    }

    /**
     * <p>Compares two <code>Object</code>s via reflection.</p>
     *
     * <p>Fields can be private, thus <code>AccessibleObject.setAccessible</code>
     * is used to bypass normal access control checks. This will fail under a 
     * security manager unless the appropriate permissions are set.</p>
     *
     * <ul>
     * <li>Static fields will not be compared</li>
     * <li>Compares also superclass fields for all superclasses
     * (if those fields are given to the constructor).
     * </ul>
     *
     * <p>If both <code>lhs</code> and <code>rhs</code> are <code>null</code>,
     * they are considered equal.</p>
     *
     * @throws NullPointerException  if either <code>lhs</code> or <code>rhs</code>
     *  (but not both) is <code>null</code>
     * @throws ClassCastException  if <code>rhs</code> is not assignment-compatible
     *  with <code>lhs</code>
     */
    public void reflectionCompare() {
        if (lhs == rhs) {
        	finalComparison = 0;
            return;
        }
        
        if (lhs == null || rhs == null) {
            throw new NullPointerException();
        }
        
        Class<?> lhsClazz = lhs.getClass();
        if (!lhsClazz.isInstance(rhs)) {
            throw new ClassCastException();
        }
        
        reflectionAppend(lhs, rhs, lhsClazz);
        while (lhsClazz.getSuperclass() != null) {
            lhsClazz = lhsClazz.getSuperclass();
            reflectionAppend(lhs, rhs, lhsClazz);
        }
        
        for(String fieldName : this.fieldList){
        	if(fieldComparisons.contains(fieldName)){
            	if(fieldComparisons.get(fieldName) != 0){
            		this.finalComparison = this.fieldComparisons.get(fieldName);
            		return;
            	}        		
        	}else{
        		//throw new InvalidParameterException("Couldn't find the member "+fieldName+" among the members of the lhs class!");
        	}
        }
		this.finalComparison = 0;
		return;
    }

    /**
     * <p>Adds to <code>fieldComparisons</code> dict the result of comparison between
     * the two input objects 
     * for each respective field found in the definition of the <code>clazz</code> class.
     * 
     * @param lhs  left-hand object
     * @param rhs  right-hand object
     * @param clazz  <code>Class</code> that defines fields to be compared
     * @param excludeFields  fields to exclude
     */
    private void reflectionAppend(final Object lhs, final Object rhs, final Class<?> clazz) {
        
        final Field[] fields = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (int i = 0; i < fields.length; i++) {
            final Field f = fields[i];
            if (x.String(f.getName()).in(fieldList)){
                try {
                    append(f.get(lhs), f.get(rhs), f.getName());
                } catch (final IllegalAccessException e) {
                    // This can't happen. Would get a Security exception instead.
                    // Throw a runtime exception in case the impossible happens.
                    throw new InternalError("Unexpected IllegalAccessException");
                }
            }
        }
    }
    
    //-----------------------------------------------------------------------
    /**
     * <p>Appends to the <code>fieldComparisons</code> the result of comparison of
     * two <code>Object</code>s for a given field name,</p>
     *
     * @param lhs  left-hand object
     * @param rhs  right-hand object
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return int the result of comparison
     * @throws ClassCastException  if <code>rhs</code> is not assignment-compatible
     *  with <code>lhs</code>
     */
    public int append(final Object lhs, final Object rhs, final String fieldName) {
        return append(lhs, rhs, null, fieldName);
    }

    /**
     * <p>Appends to the <code>fieldComparisons</code> the resultat of comparison of
     * two <code>Object</code>s for the given field <code>fieldName</code>.</p>
     *
     * <ol>
     * <li>Check if <code>lhs == rhs</code></li>
     * <li>Check if either <code>lhs</code> or <code>rhs</code> is <code>null</code>,
     *     a <code>null</code> object is less than a non-<code>null</code> object</li>
     * <li>Check the object contents</li>
     * </ol>
     *
     * <p>If <code>lhs</code> is an array, array comparison methods will be used.
     * Otherwise <code>comparator</code> will be used to compare the objects.
     * If <code>comparator</code> is <code>null</code>, <code>lhs</code> must
     * implement {@link Comparable} instead.</p>
     *
     * @param lhs  left-hand object
     * @param rhs  right-hand object
     * @param comparator  <code>Comparator</code> used to compare the objects,
     *  <code>null</code> means treat lhs as <code>Comparable</code>
     * @param fieldName  field to compare
     * @return int 	the result of comprarison
     * @throws ClassCastException  if <code>rhs</code> is not assignment-compatible
     *  with <code>lhs</code>
     */
    public int append(final Object lhs, final Object rhs, final Comparator<?> comparator, final String fieldName) {
    	int comparison = 0;
        if (lhs == rhs) {
        	comparison = 0;
        	fieldComparisons.setAt(fieldName).value(comparison);
            return comparison;
        }
        if (lhs == null) {
            comparison = -1;
        	fieldComparisons.setAt(fieldName).value(comparison);
            return comparison;
        }
        if (rhs == null) {
            comparison = +1;
        	fieldComparisons.setAt(fieldName).value(comparison);
            return comparison;
        }
        if (lhs.getClass().isArray()) {
            // switch on type of array, to dispatch to the correct handler
            // handles multi dimensional arrays
            // throws a ClassCastException if rhs is not the correct array type
            if (lhs instanceof long[]) {
            	comparison = append((long[]) lhs, (long[]) rhs, fieldName);
            	fieldComparisons.setAt(fieldName).value(comparison);
                return comparison;
            } else if (lhs instanceof int[]) {
            	comparison = append((int[]) lhs, (int[]) rhs, fieldName);
                fieldComparisons.setAt(fieldName).value(comparison);
                return comparison;
            } else if (lhs instanceof short[]) {
            	comparison = append((short[]) lhs, (short[]) rhs, fieldName);
            	fieldComparisons.setAt(fieldName).value(comparison);
                return comparison;
            } else if (lhs instanceof char[]) {
            	comparison = append((char[]) lhs, (char[]) rhs, fieldName);
            	fieldComparisons.setAt(fieldName).value(comparison);
                return comparison;
            } else if (lhs instanceof byte[]) {
            	comparison = append((byte[]) lhs, (byte[]) rhs, fieldName);
            	fieldComparisons.setAt(fieldName).value(comparison);
                return comparison;
            } else if (lhs instanceof double[]) {
            	comparison = append((double[]) lhs, (double[]) rhs, fieldName);
            	fieldComparisons.setAt(fieldName).value(comparison);
                return comparison;
            } else if (lhs instanceof float[]) {
            	comparison = append((float[]) lhs, (float[]) rhs, fieldName);
            	fieldComparisons.setAt(fieldName).value(comparison);
                return comparison;
            } else if (lhs instanceof boolean[]) {
            	comparison = append((boolean[]) lhs, (boolean[]) rhs, fieldName);
            	fieldComparisons.setAt(fieldName).value(comparison);
                return comparison;
            } else {
                // not an array of primitives
                // throws a ClassCastException if rhs is not an array
                append((Object[]) lhs, (Object[]) rhs, comparator, fieldName);
            	fieldComparisons.setAt(fieldName).value(comparison);
                return comparison;
            }
        } else {
            // the simple case, not an array, just test the element
            if (comparator == null) {
                @SuppressWarnings("unchecked") // assume this can be done; if not throw CCE as per Javadoc
                final Comparable<Object> comparable = (Comparable<Object>) lhs;
                comparison = comparable.compareTo(rhs);
            	fieldComparisons.setAt(fieldName).value(comparison);
                return comparison;
            } else {
                @SuppressWarnings("unchecked") // assume this can be done; if not throw CCE as per Javadoc
                final Comparator<Object> comparator2 = (Comparator<Object>) comparator;
                comparison = comparator2.compare(lhs, rhs);
            	fieldComparisons.setAt(fieldName).value(comparison);
                return comparison;
            }
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Appends to the <code>fieldComparisons</code> the comparison of
     * two <code>long</code>s.
     *
     * @param lhs  left-hand value
     * @param rhs  right-hand value
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return  the result of comparison
     */
    public int append(final long lhs, final long rhs, final String fieldName) {
        int comparison = ((lhs < rhs) ? -1 : ((lhs > rhs) ? 1 : 0));
        fieldComparisons.setAt(fieldName).value(comparison);
        return comparison;
    }

    /**
     * Appends to the <code>fieldComparisons</code> the comparison of
     * two <code>int</code>s.
     *
     * @param lhs  left-hand value
     * @param rhs  right-hand value
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return  the result of comparison
     */
    public int append(final int lhs, final int rhs, final String fieldName) {
        int comparison = ((lhs < rhs) ? -1 : ((lhs > rhs) ? 1 : 0));
        fieldComparisons.setAt(fieldName).value(comparison);
        return comparison;
    }

    /**
     * Appends to the <code>fieldComparisons</code> the comparison of
     * two <code>short</code>s.
     * 
     * @param lhs  left-hand value
     * @param rhs  right-hand value
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return  the result of comparison
     */
    public int append(final short lhs, final short rhs, final String fieldName) {
        int comparison = ((lhs < rhs) ? -1 : ((lhs > rhs) ? 1 : 0));
        fieldComparisons.setAt(fieldName).value(comparison);
        return comparison;
    }

    /**
     * Appends to the <code>fieldComparisons</code> the comparison of
     * two <code>char</code>s.
     *
     * @param lhs  left-hand value
     * @param rhs  right-hand value
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return  the result of comparison
     */
    public int append(final char lhs, final char rhs, final String fieldName) {
        int comparison = ((lhs < rhs) ? -1 : ((lhs > rhs) ? 1 : 0));
        fieldComparisons.setAt(fieldName).value(comparison);
        return comparison;
    }

    /**
     * Appends to the <code>fieldComparisons</code> the comparison of
     * two <code>byte</code>s.
     * 
     * @param lhs  left-hand value
     * @param rhs  right-hand value
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return  the result of comparison
     */
    public int append(final byte lhs, final byte rhs, final String fieldName) {
        int comparison = ((lhs < rhs) ? -1 : ((lhs > rhs) ? 1 : 0));
        fieldComparisons.setAt(fieldName).value(comparison);
        return comparison;
    }

    /**
     * <p>Appends to the <code>fieldComparisons</code> the comparison of
     * two <code>double</code>s.</p>
     *
     * <p>This handles NaNs, Infinities, and <code>-0.0</code>.</p>
     *
     * <p>It is compatible with the hash code generated by
     * <code>HashCodeBuilder</code>.</p>
     *
     * @param lhs  left-hand value
     * @param rhs  right-hand value
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return  the result of comparison
     */
    public int append(final double lhs, final double rhs, final String fieldName) {
        int comparison = Double.compare(lhs, rhs);
        fieldComparisons.setAt(fieldName).value(comparison);
        return comparison;
    }

    /**
     * <p>Appends to the <code>fieldComparisons</code> the comparison of
     * two <code>float</code>s.</p>
     *
     * <p>This handles NaNs, Infinities, and <code>-0.0</code>.</p>
     *
     * @param lhs  left-hand value
     * @param rhs  right-hand value
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return  the result of comparison
     */
    public int append(final float lhs, final float rhs, final String fieldName) {
        int comparison = Float.compare(lhs, rhs);
        fieldComparisons.setAt(fieldName).value(comparison);
        return comparison;
    }

    /**
     * Appends to the <code>builder</code> the comparison of
     * two <code>booleans</code>s.
     *
     * @param lhs  left-hand value
     * @param rhs  right-hand value
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return  the result of comparison
     */
    public int append(final boolean lhs, final boolean rhs, final String fieldName) {
    	int comparison = 0;
        if (lhs == rhs) {
            comparison = 1;
        }
        if (lhs == false) {
            comparison = -1;
        } else {
            comparison = +1;
        }
        fieldComparisons.setAt(fieldName).value(comparison);
        return comparison;
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Appends to the <code>fieldComparisons</code> the deep comparison of
     * two <code>Object</code> arrays.</p>
     *
     * <ol>
     *  <li>Check if arrays are the same using <code>==</code></li>
     *  <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li>
     *  <li>Check array length, a short length array is less than a long length array</li>
     *  <li>Check array contents element by element using {@code append(Object, Object, Comparator)}</li>
     * </ol>
     *
     * <p>This method will also will be called for the top level of multi-dimensional,
     * ragged, and multi-typed arrays.</p>
     *
     * @param lhs  left-hand array
     * @param rhs  right-hand array
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return  the result of comparison
     * @throws ClassCastException  if <code>rhs</code> is not assignment-compatible
     *  with <code>lhs</code>
     */
    public int append(final Object[] lhs, final Object[] rhs, final String fieldName) {
        int comparison = append(lhs, rhs, null, fieldName);
        fieldComparisons.setAt(fieldName).value(comparison);
        return comparison;
    }
    
    /**
     * <p>Appends to the <code>fieldComparisons</code> the deep comparison of
     * two <code>Object</code> arrays.</p>
     *
     * <ol>
     *  <li>Check if arrays are the same using <code>==</code></li>
     *  <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li>
     *  <li>Check array length, a short length array is less than a long length array</li>
     *  <li>Check array contents element by element using {@code append(Object, Object, Comparator)}</li>
     * </ol>
     *
     * <p>This method will also will be called for the top level of multi-dimensional,
     * ragged, and multi-typed arrays.</p>
     *
     * @param lhs  left-hand array
     * @param rhs  right-hand array
     * @param comparator  <code>Comparator</code> to use to compare the array elements,
     *  <code>null</code> means to treat <code>lhs</code> elements as <code>Comparable</code>.
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return  the result of comparison
     * @throws ClassCastException  if <code>rhs</code> is not assignment-compatible
     *  with <code>lhs</code>
     */
    public int append(final Object[] lhs, final Object[] rhs, final Comparator<?> comparator, final String fieldName) {
    	int comparison = 0;
        if (lhs == rhs) {
        	comparison = 0;
        }
        if (lhs == null) {
            comparison = -1;
        }
        if (rhs == null) {
            comparison = +1;
        }
        if (lhs.length != rhs.length) {
            comparison = (lhs.length < rhs.length) ? -1 : +1;
        }
        for (int i = 0; i < lhs.length && comparison == 0; i++) {
        	comparison = append(lhs[i], rhs[i], comparator, fieldName);
            if(append(lhs[i], rhs[i], comparator, fieldName) != 0){
                fieldComparisons.setAt(fieldName).value(comparison);
            	return comparison;
            };
        }
        fieldComparisons.setAt(fieldName).value(comparison);
        return comparison;
    }

    /**
     * <p>Appends to the <code>fieldComparisons</code> the deep comparison of
     * two <code>long</code> arrays.</p>
     *
     * <ol>
     *  <li>Check if arrays are the same using <code>==</code></li>
     *  <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li>
     *  <li>Check array length, a shorter length array is less than a longer length array</li>
     *  <li>Check array contents element by element using {@code append(long, long)}</li>
     * </ol>
     *
     * @param lhs  left-hand array
     * @param rhs  right-hand array
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return the result of comparison
     */
    public int append(final long[] lhs, final long[] rhs, String fieldName) {
    	int comparison = 0;
        if (comparison != 0) {
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs == rhs) {
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs == null) {
            comparison = -1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (rhs == null) {
            comparison = +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs.length != rhs.length) {
            comparison = (lhs.length < rhs.length) ? -1 : +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        for (int i = 0; i < lhs.length; i++) {
        	comparison = append(lhs[i], rhs[i], fieldName);
        	if (comparison != 0){
        		fieldComparisons.setAt(fieldName).value(comparison);
        		return comparison; 
        	}
        }
		fieldComparisons.setAt(fieldName).value(comparison);
		return comparison;
    }

    /**
     * <p>Appends to the <code>fieldComparisons</code> the deep comparison of
     * two <code>int</code> arrays.</p>
     *
     * <ol>
     *  <li>Check if arrays are the same using <code>==</code></li>
     *  <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li>
     *  <li>Check array length, a shorter length array is less than a longer length array</li>
     *  <li>Check array contents element by element using {@code append(int, int)}</li>
     * </ol>
     *
     * @param lhs  left-hand array
     * @param rhs  right-hand array
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return result of comparison
     */
    public int append(final int[] lhs, final int[] rhs, String fieldName) {
    	int comparison = 0;
        if (comparison != 0) {
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs == rhs) {
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs == null) {
            comparison = -1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (rhs == null) {
            comparison = +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs.length != rhs.length) {
            comparison = (lhs.length < rhs.length) ? -1 : +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        for (int i = 0; i < lhs.length && comparison == 0; i++) {
        	comparison = append(lhs[i], rhs[i], fieldName);
            if (comparison != 0) {
        		fieldComparisons.setAt(fieldName).value(comparison);
        		return comparison;
            }
        }
		fieldComparisons.setAt(fieldName).value(comparison);
		return comparison;
    }

    /**
     * <p>Appends to the <code>fieldComparisons</code> the deep comparison of
     * two <code>short</code> arrays.</p>
     *
     * <ol>
     *  <li>Check if arrays are the same using <code>==</code></li>
     *  <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li>
     *  <li>Check array length, a shorter length array is less than a longer length array</li>
     *  <li>Check array contents element by element using {@code #append(short, short)}</li>
     * </ol>
     *
     * @param lhs  left-hand array
     * @param rhs  right-hand array
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return result of comparison
     */
    public int append(final short[] lhs, final short[] rhs, String fieldName) {
    	int comparison = 0;
        if (lhs == rhs) {
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs == null) {
            comparison = -1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (rhs == null) {
            comparison = +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs.length != rhs.length) {
            comparison = (lhs.length < rhs.length) ? -1 : +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        for (int i = 0; i < lhs.length; i++) {
        	comparison = append(lhs[i], rhs[i], fieldName);
        	if (comparison != 0){
        		fieldComparisons.setAt(fieldName).value(comparison);
        		return comparison;	
        	}
        }
		fieldComparisons.setAt(fieldName).value(comparison);
		return comparison;
    }

    /**
     * <p>Appends to the <code>fieldComparisons</code> the deep comparison of
     * two <code>char</code> arrays.</p>
     *
     * <ol>
     *  <li>Check if arrays are the same using <code>==</code></li>
     *  <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li>
     *  <li>Check array length, a shorter length array is less than a longer length array</li>
     *  <li>Check array contents element by element using {@code #append(char, char)}</li>
     * </ol>
     *
     * @param lhs  left-hand array
     * @param rhs  right-hand array
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return result of comparison
     */
    public int append(final char[] lhs, final char[] rhs, String fieldName) {
    	int comparison = 0;
        if (lhs == rhs) {
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs == null) {
            comparison = -1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (rhs == null) {
            comparison = +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs.length != rhs.length) {
            comparison = (lhs.length < rhs.length) ? -1 : +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        for (int i = 0; i < lhs.length; i++) {
        	comparison = append(lhs[i], rhs[i], fieldName);
        	if (comparison != 0){
        		fieldComparisons.setAt(fieldName).value(comparison);
        		return comparison;
        	}
        }
		fieldComparisons.setAt(fieldName).value(comparison);
		return comparison;
    }

    /**
     * <p>Appends to the <code>fieldComparisons</code> the deep comparison of
     * two <code>byte</code> arrays.</p>
     *
     * <ol>
     *  <li>Check if arrays are the same using <code>==</code></li>
     *  <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li>
     *  <li>Check array length, a shorter length array is less than a longer length array</li>
     *  <li>Check array contents element by element using {@code #append(byte, byte)}</li>
     * </ol>
     *
     * @param lhs  left-hand array
     * @param rhs  right-hand array
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return result of comparison
     */
    public int append(final byte[] lhs, final byte[] rhs, String fieldName) {
    	int comparison = 0;
        if (lhs == rhs) {
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs == null) {
            comparison = -1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (rhs == null) {
            comparison = +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs.length != rhs.length) {
            comparison = (lhs.length < rhs.length) ? -1 : +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        for (int i = 0; i < lhs.length; i++) {
            comparison = append(lhs[i], rhs[i], fieldName);
            if (comparison != 0){
        		fieldComparisons.setAt(fieldName).value(comparison);
        		return comparison;
            }
        }
		fieldComparisons.setAt(fieldName).value(comparison);
		return comparison;
    }

    /**
     * <p>Appends to the <code>fieldComparisons</code> the deep comparison of
     * two <code>double</code> arrays.</p>
     *
     * <ol>
     *  <li>Check if arrays are the same using <code>==</code></li>
     *  <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li>
     *  <li>Check array length, a shorter length array is less than a longer length array</li>
     *  <li>Check array contents element by element using {@code #append(double, double)}</li>
     * </ol>
     *
     * @param lhs  left-hand array
     * @param rhs  right-hand array
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return result of comparison
     */
    public int append(final double[] lhs, final double[] rhs, String fieldName) {
        int comparison = 0;
        
        if (lhs == rhs) {
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs == null) {
            comparison = -1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (rhs == null) {
            comparison = +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs.length != rhs.length) {
            comparison = (lhs.length < rhs.length) ? -1 : +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        for (int i = 0; i < lhs.length; i++) {
        	comparison = append(lhs[i], rhs[i], fieldName);
            if (comparison != 0){
        		fieldComparisons.setAt(fieldName).value(comparison);
        		return comparison;
            }
        }
		fieldComparisons.setAt(fieldName).value(comparison);
		return comparison;
    }

    /**
     * <p>Appends to the <code>fieldComparisons</code> the deep comparison of
     * two <code>float</code> arrays.</p>
     *
     * <ol>
     *  <li>Check if arrays are the same using <code>==</code></li>
     *  <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li>
     *  <li>Check array length, a shorter length array is less than a longer length array</li>
     *  <li>Check array contents element by element using {@code #append(float, float)}</li>
     * </ol>
     *
     * @param lhs  left-hand array
     * @param rhs  right-hand array
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return the result of comparison
     */
    public int append(final float[] lhs, final float[] rhs, String fieldName) {
    	
        int comparison = 0;
        
        if (lhs == rhs) {
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs == null) {
            comparison = -1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (rhs == null) {
            comparison = +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs.length != rhs.length) {
            comparison = (lhs.length < rhs.length) ? -1 : +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        for (int i = 0; i < lhs.length; i++) {
        	comparison = append(lhs[i], rhs[i], fieldName);
        	if(comparison != 0){
        		fieldComparisons.setAt(fieldName).value(comparison);
        		return comparison;	
        	}
        }
		fieldComparisons.setAt(fieldName).value(comparison);
		return comparison;
    }

    /**
     * <p>Appends to the <code>fieldComparisons</code> the deep comparison of
     * two <code>boolean</code> arrays.</p>
     *
     * <ol>
     *  <li>Check if arrays are the same using <code>==</code></li>
     *  <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li>
     *  <li>Check array length, a shorter length array is less than a longer length array</li>
     *  <li>Check array contents element by element using {@code append(boolean, boolean)}</li>
     * </ol>
     *
     * @param lhs  left-hand array
     * @param rhs  right-hand array
     * @param fieldName a field name for the value of which we want to compare the two objects
     * @return result of comparison
     */
    public int append(final boolean[] lhs, final boolean[] rhs, String fieldName) {
        int comparison = 0;
        if (lhs == rhs) {
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs == null) {
            comparison = -1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (rhs == null) {
            comparison = +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        if (lhs.length != rhs.length) {
            comparison = (lhs.length < rhs.length) ? -1 : +1;
    		fieldComparisons.setAt(fieldName).value(comparison);
    		return comparison;
        }
        for (int i = 0; i < lhs.length; i++) {
        	comparison = append(lhs[i], rhs[i], fieldName);
            if (comparison != 0){
        		fieldComparisons.setAt(fieldName).value(comparison);
        		return comparison;
            }
        }
		fieldComparisons.setAt(fieldName).value(comparison);
		return comparison;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a negative integer, a positive integer, or zero as
     * the <code>builder</code> has judged the "left-hand" side
     * as less than, greater than, or equal to the "right-hand"
     * side.
     * 
     * @return final comparison result
     */
    public int getComparison() {
        return finalComparison;
    }
}

