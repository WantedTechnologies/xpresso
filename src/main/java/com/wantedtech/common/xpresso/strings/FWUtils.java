package com.wantedtech.common.xpresso.strings;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public class FWUtils {

	public static boolean validate_string(String s) {
	    try{
	        return x.len(s) > 0;
	    } catch (Exception e) {
	        return false;	
	    }	
	}

	static Function<Object,String> getChar = new Function<Object,String>() {
		public String apply(Object i) {
			char a = (char)(int)i;
			return String.valueOf(a);
		}
	};
	
	static list<tuple> bad_chars = x.list(x.yield("_","_").replace("").apply(getChar).where("_").in(x.range(128, 256)));  // ascii dammit!

	public static  String asciionly(String s) {
        return x.String(s).translate(bad_chars);		
	}

	public static String asciidammit(String s) {
		return x.String(s).unidecode();	
	}

	/**
	 * 
	    """Process string by
	        -- removing all but letters and numbers
	        -- trim whitespace
	        -- force to lower case
	        if force_ascii == True, force convert to ascii"""
	 * 
	 * @param s
	 * @param force_ascii
	 * @return
	 */
	public static  String full_process(String s, Boolean force_ascii) {
		if (force_ascii == null) {
			force_ascii = false;
		}
	    if (s == null) {
	        return "";	    	
	    }

	    if (force_ascii)
	        s = asciidammit(s);
	    
	    String string_out;
	    // Keep only Letters and Numbers (see Unicode docs).
	    string_out = FWStringProcessor.replace_non_letters_non_numbers_with_whitespace(s);
	    // Force into lowercase.
	    string_out = string_out.toLowerCase();
	    // Remove leading and trailing whitespaces.
	    string_out = string_out.trim();
	    return string_out;	
	}

	/**
	 * 
		'''Returns a correctly rounded integer'''
	 * 
	 * @param n
	 * @return
	 */
	int intr(Number n) {
	    Double num = n.doubleValue();
	    return (int)(x.round(num));	
	}


}
