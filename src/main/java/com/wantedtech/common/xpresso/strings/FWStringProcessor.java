package com.wantedtech.common.xpresso.strings;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.regex.Regex;

/**
 * 
	    """
	    This class defines method to process strings in the most
	    efficient way. Ideally all the methods below use unicode strings
	    for both input and output.
	    """
 * 
 * @author andriy.burkov
 *
 */
public class FWStringProcessor {
	    static Regex regex = x.Regex("\\W");

	    /**
	     * 
	        """
	        This function replaces any sequence of non letters and non
	        numbers with a single white space.
	        """
	     * 
	     * @return
	     */
	    public static String replace_non_letters_non_numbers_with_whitespace(String a_string) {

	        return regex.sub(" ", a_string);	    	
	    }
}
