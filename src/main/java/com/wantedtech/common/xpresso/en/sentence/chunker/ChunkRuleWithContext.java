package com.wantedtech.common.xpresso.en.sentence.chunker;

import com.wantedtech.common.xpresso.x;

/**
 * 
 * 	    """
	    A rule specifying how to add chunks to a ``ChunkString``, using
	    three matching tag patterns: one for the left context, one for the
	    chunk, and one for the right context.  When applied to a
	    ``ChunkString``, it will find any substring that matches the chunk
	    tag pattern, is surrounded by substrings that match the two
	    context patterns, and is not already part of a chunk; and create a
	    new chunk containing the substring that matched the chunk tag
	    pattern.

	    Caveat: Both the left and right context are consumed when this
	    rule matches; therefore, if you need to find overlapping matches,
	    you will need to apply your rule more than once.
	    """

 * 
 * @author andriy.burkov
 *
 */
public class ChunkRuleWithContext extends RegexpChunkRule {

	    String _left_context_tag_pattern;
	    String _chunk_tag_pattern;
	    String _right_context_tag_pattern;
	
		/**
		 * 
		 * 
	        Construct a new ``ChunkRuleWithContext``.
		 * 
		 * @param left_context_tag_pattern : A tag pattern that must match
	            the left context of ``chunk_tag_pattern`` for this rule to
	            apply.
		 * @param chunk_tag_pattern : A tag pattern that must match for this
	            rule to apply.  If the rule does apply, then this pattern
	            also identifies the substring that will be made into a chunk.
		 * @param right_context_tag_pattern : A tag pattern that must match
	            the right context of ``chunk_tag_pattern`` for this rule to
	            apply.
		 * @param descr : A short description of the purpose and/or effect
	            of this rule.
		 */
	    public ChunkRuleWithContext(String left_context_tag_pattern, String chunk_tag_pattern, String right_context_tag_pattern, String descr) {
	        super(x.Regex("(?<left>" + ChunkerStatic.tag_pattern2re_pattern(left_context_tag_pattern) + ")(?<chunk>" + ChunkerStatic.tag_pattern2re_pattern(chunk_tag_pattern) + ")(?<right>" + ChunkerStatic.tag_pattern2re_pattern(right_context_tag_pattern) + ")" + ChunkString.IN_CHINK_PATTERN), "${left}{${chunk}}${right}", descr);
	        // Ensure that the individual patterns are coherent.  E.g., if
	        // left='(' and right=')', then this will raise an exception:
	        x.Regex(ChunkerStatic.tag_pattern2re_pattern(left_context_tag_pattern));
	        x.Regex(ChunkerStatic.tag_pattern2re_pattern(chunk_tag_pattern));
	        x.Regex(ChunkerStatic.tag_pattern2re_pattern(right_context_tag_pattern));
	        
	        this._left_context_tag_pattern = left_context_tag_pattern;
	        this._chunk_tag_pattern = chunk_tag_pattern;
	        this._right_context_tag_pattern = right_context_tag_pattern;
	    }
}
