package com.wantedtech.common.xpresso.sentence.chunker.regexchunker;

import com.wantedtech.common.xpresso.x;

/**
 * 
 * 	    """
	    A rule specifying how to merge chunks in a ``ChunkString``, using
	    two matching tag patterns: a left pattern, and a right pattern.
	    When applied to a ``ChunkString``, it will find any chunk whose end
	    matches left pattern, and immediately followed by a chunk whose
	    beginning matches right pattern.  It will then merge those two
	    chunks into a single chunk.
	    """
 * @author andriy.burkov
 *
 */
public class MergeRule extends RegexpChunkRule {
    String _left_tag_pattern;
    String _right_tag_pattern;
		/**
	        Construct a new ``MergeRule``.

	        @param right_tag_pattern: This rule's right tag
	            pattern.  When applied to a ``ChunkString``, this
	            rule will find any chunk whose end matches
	            ``left_tag_pattern``, and immediately followed by a chunk
	            whose beginning matches this pattern.  It will
	            then merge those two chunks into a single chunk.
	        @param left_tag_pattern: This rule's left tag
	            pattern.  When applied to a ``ChunkString``, this
	            rule will find any chunk whose end matches
	            this pattern, and immediately followed by a chunk
	            whose beginning matches ``right_tag_pattern``.  It will
	            then merge those two chunks into a single chunk.

	        @param descr: A short description of the purpose and/or effect
	            of this rule.
		 * 
		 */
	    public MergeRule(String left_tag_pattern, String right_tag_pattern, String descr) {
	        // Ensure that the individual patterns are coherent.  E.g., if
	        // left='(' and right=')', then this will raise an exception:
	        super(x.Regex("(?<left>" + ChunkerStatic.tag_pattern2re_pattern(left_tag_pattern) + ")}{(?=" + ChunkerStatic.tag_pattern2re_pattern(right_tag_pattern) + ")"), "${left}", descr);
	        x.Regex(ChunkerStatic.tag_pattern2re_pattern(left_tag_pattern));
	        x.Regex(ChunkerStatic.tag_pattern2re_pattern(right_tag_pattern));
	        this._left_tag_pattern = left_tag_pattern;
	        this._right_tag_pattern = right_tag_pattern;

	    }


}
