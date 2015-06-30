package com.wantedtech.common.xpresso.strings;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.regex.Regex;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.set;
import com.wantedtech.common.xpresso.types.tuples.tuple2;
import com.wantedtech.common.xpresso.types.tuples.tuple3;

public class FuzzyWuzzy {
	
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
    private static String replace_non_letters_non_numbers_with_whitespace(String a_string) {

        return regex.sub(" ", a_string);	    	
    }
    
	private static boolean validate_string(String s) {
	    try{
	        return x.len(s) > 0;
	    } catch (Exception e) {
	        return false;	
	    }	
	}

	private static String asciidammit(String s) {
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
	private static String full_process(String s, Boolean force_ascii) {
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
	    string_out = replace_non_letters_non_numbers_with_whitespace(s);
	    // Force into lowercase.
	    string_out = string_out.toLowerCase();
	    // Remove leading and trailing whitespaces.
	    string_out = string_out.trim();
	    return string_out;	
	}

	private static Function<tuple2<String,Boolean>,String> full_process = new Function<tuple2<String,Boolean>,String>() {
		public String apply(tuple2<String,Boolean> input) {
			return full_process(input.key, input.value);
		}
	};
	
	/**
	 * Returns the standard python's SequenceMatcher ratio multiplied by 100
	 * @param s1
	 * @param s2
	 * @return
	 */
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
	    list<String> tokens = x.String(full_process(s, force_ascii)).split();

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

	    String p1 = full_process(s1, force_ascii);
	    String p2 = full_process(s2, force_ascii);

	    if (! validate_string(p1))
	        return 0;
	    if (! validate_string(p2))
	        return 0;

	    // pull tokens
	    set<String> tokens1 = x.set(x.String(full_process(p1,null)).split());
	    set<String> tokens2 = x.set(x.String(full_process(p2,null)).split());

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
	
	/**
    """Return a measure of the sequences' similarity between 0 and 100,
    using different algorithms.
    """
	 * 
	 */
	private static int WRatio(String s1, String s2, Boolean force_ascii) {
		if (force_ascii == null) {
			force_ascii = true;
		}
		
	    String p1 = full_process(s1, force_ascii);
	    String p2 = full_process(s2, force_ascii);

	    if (! validate_string(p1)) {
	        return 0;	    	
	    }

	    if (! validate_string(p2)) {
	        return 0;	    	
	    }

	    // should we look at partials?
	    boolean try_partial = true;
	    double unbase_scale = .95;
	    double partial_scale = .90;

	    int base = ratio(p1, p2);
	    double len_ratio = (double)(x.max(x.len(p1), x.len(p2))) / (double)x.min(x.len(p1), x.len(p2));

	    // if strings are similar length, don't use partials
	    if (len_ratio < 1.5)
	        try_partial = false;

	    // if one string is much much shorter than the other
	    if (len_ratio > 8)
	        partial_scale = .6;

	    if (try_partial) {
	        double partial = partial_ratio(p1, p2) * partial_scale;
	        double ptsor = partial_token_sort_ratio(p1, p2, force_ascii) * unbase_scale * partial_scale;
	        double ptser = partial_token_set_ratio(p1, p2, force_ascii) * unbase_scale * partial_scale;

	        return (int)(x.max((double)base, partial, ptsor, ptser)).doubleValue();	    	
	    } else {
	        double tsor = token_sort_ratio(p1, p2, force_ascii) * unbase_scale;
	        double tser = token_set_ratio(p1, p2, force_ascii) * unbase_scale;

	        return (int)(x.max((double)base, tsor, tser)).doubleValue();    	
	    }
	}
	
	private static Function<tuple3<String,String,Boolean>,Integer> WRatio = new Function<tuple3<String,String,Boolean>,Integer>() {
		public Integer apply(tuple3<String,String,Boolean> input) {
			return WRatio(input.left, input.middle, input.right);
		}
	};
	
	/**
	 * 
	    """Select the best match in a list or dictionary of choices.

	    Find best matches in a list or dictionary of choices, return a
	    list of tuples containing the match and it's score. If a dictionery
	    is used, also returns the key for each match.

	    Arguments:
	        query: An object representing the thing we want to find.
	        choices: An iterable or dictionary-like object containing choices
	            to be matched against the query. Dictionary arguments of
	            {key: value} pairs will attempt to match the query against
	            each value.
	        processor: Optional function of the form f(a) -> b, where a is an
	            individual choice and b is the choice to be used in matching.

	            This can be used to match against, say, the first element of
	            a list:

	            lambda x: x[0]

	            Defaults to fuzzywuzzy.utils.full_process().
	        scorer: Optional function for scoring matches between the query and
	            an individual processed choice. This should be a function
	            of the form f(query, choice) -> int.

	            By default, fuzz.WRatio() is used and expects both query and
	            choice to be strings.
	        limit: Optional maximum for the number of elements returned. Defaults
	            to 5.

	    Returns:
	        List of tuples containing the match and its score.

	        If a list is used for choices, then the result will be 2-tuples.
	        If a dictionery is used, then the result will be 3-tuples containing
	        he key for each match.

	        For example, searching for 'bird' in the dictionary

	        {'bard': 'train', 'dog': 'man'}

	        may return

	        [('train', 22, 'bard'), ('man', 0, 'dog')]
	    """
	 * 
	 */
	public static list<tuple2<String,Integer>> extract(String query, Iterable<String> choices, Function<tuple2<String,Boolean>,String> processor, Function<tuple3<String,String,Boolean>,Integer> scorer, Integer limit) {
		
		if (limit == null) {
			limit = 5;
		}

	    if (choices == null) {
	        return x.list();	    	
	    }


	    // Catch generators without lengths
	    try {
	        if (x.len(choices) == 0) {
	            return x.list();
	        }
	    } catch (Exception e) {
	    	
	    }

	    // default, turn whatever the choice is into a workable string
	    if (x.isFalse(processor)) {
	        processor = full_process;	    	
	    }


	    // default: wratio
	    if (x.isFalse(scorer)) {
	        scorer = WRatio; 	
	    }


	    list<tuple2<String,Integer>> sl = x.list();

        for (String choice : choices) {
            String processed = processor.apply(x.<String,Boolean>tuple2(choice,null));
            int score = scorer.apply(x.<String,String,Boolean>tuple3(query, processed, null));
            sl.append(x.tuple2(choice, score));    	
        }
	    sl = x.list(x.sort(sl,x.<Integer>lambdaF("i : i[1]"),true));
	    return sl.sliceTo(limit);
	}

	/**
	 * 
	    """Get a list of the best matches to a collection of choices.

	    Convenience function for getting the choices with best scores.

	    Args:
	        query: A string to match against
	        choices: A list or dictionary of choices, suitable for use with
	            extract().
	        processor: Optional function for transforming choices before matching.
	            See extract().
	        scorer: Scoring function for extract().
	        score_cutoff: Optional argument for score threshold. No matches with
	            a score less than this number will be returned. Defaults to 0.
	        limit: Optional maximum for the number of elements returned. Defaults
	            to 5.

	    Returns: A a list of (match, score) tuples.
	    """
	 * 
	 * @return
	 */
	public list<tuple2<String,Integer>> extractBestOnes(String query, list<String> choices, Function<tuple2<String,Boolean>,String> processor, Function<tuple3<String,String,Boolean>,Integer> scorer, Integer scoreCutoff, Integer limit) {
		if (scoreCutoff == null) {
			scoreCutoff = 0;
		}
	
		if (limit == null) {
			limit = 5;
		}
		
		list<tuple2<String,Integer>> best_list = extract(query, choices, processor, scorer, limit);
	    return x.list(x.takeWhile(x.lambdaP("x : x[1] >= " + String.valueOf(scoreCutoff)), best_list));
	}

	/**
	 * 
	    """Find the single best match above a score in a list of choices.

	    This is a convenience method which returns the single best choice.
	    See extract() for the full arguments list.

	    Args:
	        query: A string to match against
	        choices: A list or dictionary of choices, suitable for use with
	            extract().
	        processor: Optional function for transforming choices before matching.
	            See extract().
	        scorer: Scoring function for extract().
	        score_cutoff: Optional argument for score threshold. If the best
	            match is found, but it is not greater than this number, then
	            return None anyway ("not a good enough match").  Defaults to 0.

	    Returns:
	        A tuple containing a single match and its score, if a match
	        was found that was above score_cutoff. Otherwise, returns None.
	    """
	 * 
	 */
	public static tuple2<String,Integer> extractOne(String query, list<String> choices, Function<tuple2<String,Boolean>,String> processor, Function<tuple3<String,String,Boolean>,Integer> scorer, Integer score_cutoff) {
		
		if (score_cutoff == null) {
			score_cutoff = 0;
		}
		
	    list<tuple2<String,Integer>> best_list = extract(query, choices, processor, scorer, 1);
	    if (x.len(best_list) > 0 && best_list.get(0).value >= score_cutoff)
	        return best_list.get(0);
	    return null;
	}

	/**
	 * 
	    """This convenience function takes a list of strings containing duplicates and uses fuzzy matching to identify 
	    and remove duplicates. Specifically, it uses the process.extract to identify duplicates that 
	    score greater than a user defined threshold. Then, it looks for the longest item in the duplicate list
	    since we assume this item contains the most entity information and returns that. It breaks string 
	    length ties on an alphabetical sort.
	    
	    Note: as the threshold DECREASES the number of duplicates that are found INCREASES. This means that the 
	        returned deduplicated list will likely be shorter. Raise the threshold for fuzzy_dedupe to be less 
	        sensitive.
	    
	    Args:
	        contains_dupes: A list of strings that we would like to dedupe.
	        threshold: the numerical value (0,100) point at which we expect to find duplicates. 
	            Defaults to 70 out of 100
	        scorer: Optional function for scoring matches between the query and
	            an individual processed choice. This should be a function
	            of the form f(query, choice) -> int.
	            By default, fuzz.token_set_ratio() is used and expects both query and
	            choice to be strings.

	    Returns:
	        A deduplicated list. For example:

	            In: contains_dupes = ['Frodo Baggin', 'Frodo Baggins', 'F. Baggins', 'Samwise G.', 'Gandalf', 'Bilbo Baggins']
	            In: fuzzy_dedupe(contains_dupes)
	            Out: ['Frodo Baggins', 'Samwise G.', 'Bilbo Baggins', 'Gandalf']
	        """
	 * 
	 * @return
	 */
	public Iterable<String> dedupe(Iterable<String> iterableWithDupes, Integer threshold, Function<tuple3<String,String,Boolean>,Integer> scorer) {
		if (threshold == null) {
			threshold = 70;

		}
		
	    list<String> extractor = x.list();
	    	    
	    // iterate over items in *contains_dupes*
	    for (String element : iterableWithDupes) {
	        // return all duplicate matches found
	        list<tuple2<String,Integer>> matches = extract(element, iterableWithDupes, null, scorer, null);
	        // filter matches based on the threshold 
	        list<tuple2<String,Integer>> filtered = x.list(x.<tuple2<String,Integer>>yield().forEach(matches).when(x.lambdaP("x[1] >"+threshold)));
	        // if there is only 1 item in *filtered*, no duplicates were found so append to *extracted*
	        if (x.len(filtered) == 1) {
	            extractor.append(filtered.get(0).key);	        	
	        } else {
	            // alpha sort
	            filtered = x.list(x.sort(filtered, x.<Integer>lambdaF("x : x[0]")));
	            // length sort
	            list<tuple2<String,Integer>>filter_sort = x.list(x.sort(filtered, x.<Integer>lambdaF("x : f0(x[0])",x.len), true));
	            // take first item as our 'canonical example'
	            extractor.append(filter_sort.get(0).key);	        	
	        }
	    	
	    }


	    // uniquify *extractor* list
	    dict<Integer> keys = x.dict();
	    for (String e : extractor) {
	        keys.setAt(e).value(1);	    	
	    }

	    extractor = keys.keys();
	    
	    // check that extractor differs from contain_dupes (e.g. duplicates were found)
	    // if not, then return the original list
	    if (x.len(extractor) == x.len(iterableWithDupes))
	        return iterableWithDupes;
	    else
	        return extractor;		
	}
	
}
