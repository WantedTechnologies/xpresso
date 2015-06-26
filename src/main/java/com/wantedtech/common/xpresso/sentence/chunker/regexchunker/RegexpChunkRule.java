package com.wantedtech.common.xpresso.sentence.chunker.regexchunker;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.regex.Match;
import com.wantedtech.common.xpresso.regex.Regex;
import com.wantedtech.common.xpresso.types.tuple;

/**
	    """
	    A rule specifying how to modify the chunking in a ``ChunkString``,
	    using a transformational regular expression.  The
	    ``RegexpChunkRule`` class itself can be used to implement any
	    transformational rule based on regular expressions.  There are
	    also a number of subclasses, which can be used to implement
	    simpler types of rules, based on matching regular expressions.

	    Each ``RegexpChunkRule`` has a regular expression and a
	    replacement expression.  When a ``RegexpChunkRule`` is "applied"
	    to a ``ChunkString``, it searches the ``ChunkString`` for any
	    substring that matches the regular expression, and replaces it
	    using the replacement expression.  This search/replace operation
	    has the same semantics as ``re.sub``.

	    Each ``RegexpChunkRule`` also has a description string, which
	    gives a short (typically less than 75 characters) description of
	    the purpose of the rule.

	    This transformation defined by this ``RegexpChunkRule`` should
	    only add and remove braces; it should *not* modify the sequence
	    of angle-bracket delimited tags.  Furthermore, this transformation
	    may not result in nested or mismatched bracketing.
	    """
 * @author andriy.burkov
 *
 */
public class RegexpChunkRule {

    String _repl;
    String _descr;
    Regex _regexp;	  
	
		/**
	        Construct a new RegexpChunkRule.

	        @param regexp: The regular expression for this ``RegexpChunkRule``.
	            When this rule is applied to a ``ChunkString``, any
	            substring that matches ``regexp`` will be replaced using
	            the replacement string ``repl``.  Note that this must be a
	            normal regular expression, not a tag pattern.
	        @param repl: The replacement expression for this ``RegexpChunkRule``.
	            When this rule is applied to a ``ChunkString``, any substring
	            that matches ``regexp`` will be replaced using ``repl``.
	        @param descr: A short description of the purpose and/or effect
	            of this rule.
		 * 
		 */
	    public RegexpChunkRule(Regex regexp, String repl, String descr) {
	        this._repl = repl;
	        this._descr = descr;
	        this._regexp = regexp;	    	
	    }


	    /**
	        Apply this rule to the given {@link ChunkString}.  See the
	        class reference documentation for a description of what it
	        means to apply a rule.

	        @param chunkstr	The {@link ChunkString} to which this rule is applied.
	     */
	    public void apply(ChunkString chunkstr) {
	        chunkstr.xform(this._regexp, this._repl);	    	
	    }


	    /**
	        Return a short description of the purpose and/or effect of
	        this rule.
	        @return a {@link String} with a short description of the purpose and/or effect of this rule.
	     */
	    String descr() {
	        return this._descr;
	    }

	    /**
	        Create a {@link RegexpChunkRule} from a string description.
	        Currently, the following formats are supported:

	          {regexp}         # chunk rule
	          }regexp{         # chink rule
	          regexp}{regexp   # split rule
	          regexp{}regexp   # merge rule

	        Where ``regexp`` is a regular expression for the rule.  Any
	        text following the comment marker (``#``) will be used as
	        the rule's description:

			Example:
			<pre>
			{@code
	        RegexpChunkRule.fromstring('{<DT>?<NN.*>+}')
	        }
	        </pre>
	        Console:
	        <pre>
	        {@code
	        <ChunkRule: "<DT>?<NN.*>+">
	        }
	        </pre>
	       @param s 	a String with a rule 
	     * @return	a {@link RegexpChunkRule} object
	     */
	    public static RegexpChunkRule fromstring(String s) {
	        // Split off the comment (but don't split on '\#')
	        Match m = x.Regex("^(?<rule>(\\\\.|[^#])*)(?<comment>#.*)?").find(s);
	        String rule = "";
			rule = m.group("rule").trim();
	        String comment = "";
			if (m.group("comment") != null) {
				comment = x.str(m.group("comment")).sliceFrom(1).strip().toString();	        	
			}

	        // Pattern bodies: chunk, chink, split, merge
	        try {
	        	
	            if (!x.isTrue(rule)) {
	                throw new IllegalArgumentException("Empty chunk pattern");	
	            }
	            
	            else if (x.String(rule).get(0).equals("{") && x.String(rule).get(-1).equals("}")) {
	            	return new ChunkRule(x.String(rule).slice(1,-1), comment);
	            }
	            
	            else if (x.String(rule).get(0).equals("}") && x.String(rule).get(-1).equals("{")) {
	                return new ChinkRule(x.String(rule).slice(1,-1), comment);	            	
	            }

	            else if (x.String("}{").in(rule)) {
	                return new SplitRule(x.String(rule).split("}{").get(0), x.String(rule).split("}{").get(1), comment);
	            }

	            else if (x.String("{}").in(rule)) {
	                return new MergeRule(x.String(rule).split("{}").get(0), x.String(rule).split("{}").get(1), comment);	            	
	            }
	            
	            else if (x.isTrue(x.Regex("[^{}]*{[^{}]*}[^{}]*").find(rule))) {
	                tuple split = x.tupleOf(x.Regex("[{}]").split(rule));
	                split.name("left", "chunk", "right");
	                return new ChunkRuleWithContext(split.getString("left"), split.getString("chunk"), split.getString("right"), comment);	            	
	            }

	            else {
		            throw new IllegalArgumentException("Illegal chunk pattern: " + rule);
	            }
	        } catch (IllegalArgumentException e) {
	            throw new IllegalArgumentException("Illegal chunk pattern: " + rule);	
	        }
	    }

	    @Override
	    public String toString() {
	    	return this._regexp.pattern + " -> " + this._repl;
	    }
}
