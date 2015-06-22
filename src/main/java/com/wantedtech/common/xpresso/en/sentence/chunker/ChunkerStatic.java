package com.wantedtech.common.xpresso.en.sentence.chunker;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.regex.Regex;
import com.wantedtech.common.xpresso.types.list;

public class ChunkerStatic {

	static Regex CHUNK_TAG_PATTERN = x.Regex("^((" + "[^\\{\\}<>]+" + "|<" + "[^\\{\\}<>]+" + ">)*)$");
	
	/**
	    """
	    Convert a tag pattern to a regular expression pattern.  A "tag
	    pattern" is a modified version of a regular expression, designed
	    for matching sequences of tags.  The differences between regular
	    expression patterns and tag patterns are:

	        - In tag patterns, ``'<'`` and ``'>'`` act as parentheses; so
	          ``'<NN>+'`` matches one or more repetitions of ``'<NN>'``, not
	          ``'<NN'`` followed by one or more repetitions of ``'>'``.
	        - Whitespace in tag patterns is ignored.  So
	          ``'<DT> | <NN>'`` is equivalant to ``'<DT>|<NN>'``
	        - In tag patterns, ``'.'`` is equivalant to ``'[^{}<>]'``; so
	          ``'<NN.*>'`` matches any single tag starting with ``'NN'``.

	    In particular, ``tag_pattern2re_pattern`` performs the following
	    transformations on the given pattern:

	        - Replace '.' with '[^<>{}]'
	        - Remove any whitespace
	        - Add extra parens around '<' and '>', to make '<' and '>' act
	          like parentheses.  E.g., so that in '<NN>+', the '+' has scope
	          over the entire '<NN>'; and so that in '<NN|IN>', the '|' has
	          scope over 'NN' and 'IN', but not '<' or '>'.
	        - Check to make sure the resulting pattern is valid.

	    :type tag_pattern: str
	    :param tag_pattern: The tag pattern to convert to a regular
	        expression pattern.
	    :raise ValueError: If ``tag_pattern`` is not a valid tag pattern.
	        In particular, ``tag_pattern`` should not include braces; and it
	        should not contain nested or mismatched angle-brackets.
	    :rtype: str
	    :return: A regular expression pattern corresponding to
	        ``tag_pattern``.
	    """
	 * @return
	 */
	public static String tag_pattern2re_pattern(String tag_pattern) {
	    // Clean up the regular expression
	    tag_pattern = x.Regex("\\s").sub("", tag_pattern);
	    tag_pattern = x.Regex("<").sub("(<(", tag_pattern);
	    tag_pattern = x.Regex(">").sub(")>)", tag_pattern);

	    // Check the regular expression
	    if (x.isFalse(CHUNK_TAG_PATTERN.find(tag_pattern))) {
	        throw new IllegalArgumentException("Bad tag pattern: " + tag_pattern);	    	
	    }


	    /* Replace "." with CHUNK_TAG_CHAR.
	    # We have to do this after, since it adds {}[]<>s, which would
	    # confuse CHUNK_TAG_PATTERN.
	    # PRE doesn't have lookback assertions, so reverse twice, and do
	    # the pattern backwards (with lookahead assertions).  This can be
	    # made much cleaner once we can switch back to SRE.
	    */


	    String tc_rev = reverse_str(ChunkString.CHUNK_TAG_CHAR);
	    String reversed = reverse_str(tag_pattern);
	    reversed = x.Regex("\\.(?!\\\\(\\\\\\\\)*($|[^\\\\]))").sub(tc_rev, reversed);
	    tag_pattern = reverse_str(reversed);

	    return tag_pattern;	
	}

    static String reverse_str(String st) {
    	list<String> lst = x.reverse(x.str(st));
        return x.String("").join(lst);	    	
    }

}
