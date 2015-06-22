package com.wantedtech.common.xpresso.en.sentence.chunker;

import com.wantedtech.common.xpresso.x;
	
	/**
	 * 
	    """
	    A rule specifying how to split chunks in a ``ChunkString``, using
	    two matching tag patterns: a left pattern, and a right pattern.
	    When applied to a ``ChunkString``, it will find any chunk that
	    matches the left pattern followed by the right pattern.  It will
	    then split the chunk into two new chunks, at the point between the
	    two pattern matches.
	    """
	 * 
	 */
	class SplitRule extends RegexpChunkRule {

        String _left_tag_pattern;
        String _right_tag_pattern;
		/**
		 * 
	        """
	        Construct a new ``SplitRule``.

	        :type right_tag_pattern: str
	        :param right_tag_pattern: This rule's right tag
	            pattern.  When applied to a ``ChunkString``, this rule will
	            find any chunk containing a substring that matches
	            ``left_tag_pattern`` followed by this pattern.  It will
	            then split the chunk into two new chunks at the point
	            between these two matching patterns.
	        :type left_tag_pattern: str
	        :param left_tag_pattern: This rule's left tag
	            pattern.  When applied to a ``ChunkString``, this rule will
	            find any chunk containing a substring that matches this
	            pattern followed by ``right_tag_pattern``.  It will then
	            split the chunk into two new chunks at the point between
	            these two matching patterns.
	        :type descr: str
	        :param descr: A short description of the purpose and/or effect
	            of this rule.
	        """
		 * 
		 */
	    public SplitRule(String left_tag_pattern, String right_tag_pattern, String descr) {
	        super(x.Regex("(?<left>" + ChunkerStatic.tag_pattern2re_pattern(left_tag_pattern) + ")(?=" +ChunkerStatic.tag_pattern2re_pattern(right_tag_pattern) + ")"), "${left}}{", descr);
	        // Ensure that the individual patterns are coherent.  E.g., if
	        // left='(' and right=')', then this will raise an exception:
	        x.Regex(ChunkerStatic.tag_pattern2re_pattern(left_tag_pattern));
	        x.Regex(ChunkerStatic.tag_pattern2re_pattern(right_tag_pattern));
	        this._left_tag_pattern = left_tag_pattern;
	        this._right_tag_pattern = right_tag_pattern;

		}
}
