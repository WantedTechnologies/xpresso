package com.wantedtech.common.xpresso.strings;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.set;
import com.wantedtech.common.xpresso.types.tuples.tuple2;
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
	
	/*
	##############################
	# Advanced Scoring Functions #
	##############################
	*/

	/**
	 * """Return a cleaned string with token sorted."""
	 * @param s
	 * @param force_ascii
	 * @return
	 */
	private static String _process_and_sort(String s, Boolean force_ascii) {
	    // pull tokens
	    list<String> tokens = x.String(FWUtils.full_process(s, force_ascii)).split();

	    // sort tokens and join
	    String sorted_string = x.String(" ").join(x.sort(tokens));
	    return sorted_string.trim();
	}
	    


	/**
	 * 
	# Sorted Token
	#   find all alphanumeric tokens in the string
	#   sort those tokens and take ratio of resulting joined strings
	#   controls for unordered string elements
	 * 
	 * @return
	 */
	private static int _token_sort(String s1, String s2, Boolean partial, Boolean force_ascii) {
		if (partial == null) {
			partial = true;
		}
		if (force_ascii == null) {
			force_ascii = true;
		}
		x.assertNotNull(s1,"s1 is null");
		x.assertNotNull(s2,"s2 is null");

	    String sorted1 = _process_and_sort(s1, force_ascii);
	    String sorted2 = _process_and_sort(s2, force_ascii);

	    if (partial)
	        return partial_ratio(sorted1, sorted2);
	    else
	        return ratio(sorted1, sorted2);		
	}


	/**
	 * 
	    """Return a measure of the sequences' similarity between 0 and 100
	    but sorting the token before comparing.
	    """
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int token_sort_ratio(String s1, String s2, Boolean force_ascii) {
		if (force_ascii == null) {
			force_ascii = true;
		}
	    return _token_sort(s1, s2, false, force_ascii);
	}


	/**
	 * 
	    """Return the ratio of the most similar substring as a number between
	    0 and 100 but sorting the token before comparing.
	    """
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int partial_token_sort_ratio(String s1, String s2, Boolean force_ascii) {
		if (force_ascii == null) {
			force_ascii = true;
		}
	    return _token_sort(s1, s2, true, force_ascii);	
	}

	/**
	 * """Find all alphanumeric tokens in each string...
	        - treat them as a set
	        - construct two strings of the form:
	            <sorted_intersection><sorted_remainder>
	        - take ratios of those two strings
	        - controls for unordered partial matches"""
	 * @param s1
	 * @param s2
	 * @param partial
	 * @param force_ascii
	 */
	private static int _token_set(String s1, String s2, Boolean partial, Boolean force_ascii) {
		if (partial == null) {
			partial = true;
		}
		
		if (force_ascii == null) {
			force_ascii = true;
		}
		
		x.assertNotNull(s1,"s1 is null");
		x.assertNotNull(s2,"s2 is null");

	    String p1 = FWUtils.full_process(s1, force_ascii);
	    String p2 = FWUtils.full_process(s2, force_ascii);

	    if (! FWUtils.validate_string(p1))
	        return 0;
	    if (! FWUtils.validate_string(p2))
	        return 0;

	    // pull tokens
	    set<String> tokens1 = x.set(x.String(FWUtils.full_process(p1,null)).split());
	    set<String> tokens2 = x.set(x.String(FWUtils.full_process(p2,null)).split());

	    set<String> intersection = tokens1.intersection(tokens2);
	    set<String> diff1to2 = tokens1.difference(tokens2);
	    set<String> diff2to1 = tokens2.difference(tokens1);

	    String sorted_sect = x.String(" ").join(x.sort(intersection));
	    String sorted_1to2 = x.String(" ").join(x.sort(diff1to2));
	    String sorted_2to1 = x.String(" ").join(x.sort(diff2to1));

	    String combined_1to2 = sorted_sect + " " + sorted_1to2;
	    String combined_2to1 = sorted_sect + " " + sorted_2to1;

	    // strip
	    sorted_sect = sorted_sect.trim();
	    combined_1to2 = combined_1to2.trim();
	    combined_2to1 = combined_2to1.trim();

	    Function<tuple2<String,String>,Integer> ratio_func;
	    if (partial)
	        ratio_func = new Function<tuple2<String,String>,Integer>() {
	    		public Integer apply(tuple2<String,String> item) {
	    			return partial_ratio(item.value0,item.value1);
	    		}
	    	};
	    else
	        ratio_func = new Function<tuple2<String,String>,Integer>() {
	    		public Integer apply(tuple2<String,String> item) {
	    			return ratio(item.value0,item.value1);
	    		}
	    	};

	    list<Integer> pairwise = x.list(
	        ratio_func.apply(x.tuple2(sorted_sect, combined_1to2)),
	        ratio_func.apply(x.tuple2(sorted_sect, combined_2to1)),
	        ratio_func.apply(x.tuple2(combined_1to2, combined_2to1))
	    );
	    
	    return x.max(pairwise);
		
	}

	public static int token_set_ratio(String s1, String s2, Boolean force_ascii) {
		if (force_ascii == null) {
			force_ascii = true;
		}
	    return _token_set(s1, s2, false, force_ascii);
	}


	public static int partial_token_set_ratio(String s1, String s2, Boolean force_ascii) {
		if (force_ascii == null) {
			force_ascii = true;
		}
	    return _token_set(s1, s2, true, force_ascii);
	}
}
