package com.wantedtech.common.xpresso.strings;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuples.tuple3;

public class FuzzyWuzzy {
	
	public static int ratio(String s1, String s2) {
		x.assertNotNull(s1, "s1 is null");
		x.assertNotNull(s2, "ss is null");

	    SequenceMatcher m = new SequenceMatcher(s1, s2);
	    return (int)(x.round(100 * m.ratio()));		
	}

	/*
	 * 
	    """"Return the ratio of the most similar substring
	    as a number between 0 and 100."""
	 * 
	 */
	public static int partial_ratio(String s1, String s2) {
		x.assertNotNull(s1, "s1 is null");
		x.assertNotNull(s2, "ss is null");

    	String shorter = s2;
    	String longer = s1;
	    if (x.len(s1) <= x.len(s2)) {
	        shorter = s1;
	        longer = s2;
	    }

	    SequenceMatcher m = new SequenceMatcher(shorter, longer);
	    
	    list<tuple3<Integer,Integer,Integer>> blocks = m.get_matching_blocks();

	    /*
	    # each block represents a sequence of matching characters in a string
	    # of the form (idx_1, idx_2, len)
	    # the best partial match will block align with at least one of those blocks
	    #   e.g. shorter = "abcd", longer = XXXbcdeEEE
	    #   block = (1,3,3)
	    #   best score === ratio("abcd", "Xbcd")
	    */
	    list<Double> scores = x.list();
	    for (tuple3<Integer,Integer,Integer> block : blocks) {
	        int long_start = (block.getInt(1) - block.getInt(0)) > 0 ? block.getInt(1) - block.getInt(0) : 0;
	        int long_end = long_start + x.len(shorter);
	        String long_substr = x.String(longer).slice(long_start,long_end);

	        SequenceMatcher m2 = new SequenceMatcher(shorter, long_substr);
	        double r = m2.ratio();
	        if (r > .995) {
	            return 100;	        	
	        } else {
	            scores.append(r);
	        }
	    }
	    
	    return (int)(100 * x.max(scores));	
	}	
}
