package com.wantedtech.common.xpresso.en.sentence.chunker;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.regex.Regex;

/**
	    """
	    A rule specifying how to remove chinks to a ``ChunkString``,
	    using a matching tag pattern.  When applied to a
	    ``ChunkString``, it will find any substring that matches this
	    tag pattern and that is contained in a chunk, and remove it
	    from that chunk, thus creating two new chunks.
	    """
 * @author andriy.burkov
 *
 */
public class ChunkRule extends RegexpChunkRule{

	String _pattern;
		/**
	        """
	        Construct a new ``ChinkRule``.

	        :type tag_pattern: str
	        :param tag_pattern: This rule's tag pattern.  When
	            applied to a ``ChunkString``, this rule will
	            find any substring that matches this tag pattern and that
	            is contained in a chunk, and remove it from that chunk,
	            thus creating two new chunks.
	        :type descr: str
	        :param descr: A short description of the purpose and/or effect
	            of this rule.
	        """
		 */
	    public ChunkRule(String tag_pattern, String descr) {	    	
	        super(x.Regex("(?<chunk>" + ChunkerStatic.tag_pattern2re_pattern(tag_pattern) + ")" + ChunkString.IN_CHINK_PATTERN), "{${chunk}}", descr);
	        this._pattern = tag_pattern;
	    }


}
