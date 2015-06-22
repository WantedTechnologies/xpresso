package com.wantedtech.common.xpresso.en.sentence.chunker;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.regex.Match;
import com.wantedtech.common.xpresso.types.list;

public class RegexpParser {

    int _trace;
    list<RegexpChunkParser> _stages;
    String _grammar;
    int _loop;
	
	public RegexpParser(String grammar) {
		this(grammar, "S", 1, 0);
	}
    
	public RegexpParser(String grammar, String root_label, int loop, int trace) {
        this._trace = trace;
        this._stages = x.list();
        this._grammar = grammar;
        this._loop = 1;
        
        this._read_grammar(grammar, "S", trace);
	}

	/**
	 * 
     *   Helper function for the constructor: read the grammar {@link String}.
	 * 
	 * @param grammar	The grammar string
	 * @param root_label
	 * @param trace
	 */
    private void _read_grammar(String grammar, String root_label, int trace){
        list<RegexpChunkRule> rules = x.list();
        String lhs = null;
        for (String line : x.String(grammar).split("\n")){
            line = line.trim();

            // New stage begins if there's an unescaped ':'
            Match m = x.Regex("^(?<nonterminal>(\\.|[^:])*)(:(?<rule>.*))").find(line);
            if (x.isTrue(m)){
                // Record the stage that we just completed.

                this._add_stage(rules, lhs, root_label, trace);
                // Start a new stage.
                try {
					lhs = m.group("nonterminal").trim();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                rules = x.list();
                try {
					line = m.group("rule").trim();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
            }

            // Skip blank & comment-only lines
            if (line.equals("") || line.startsWith("#")) {
            	continue;
            } 

            // Add the rule
            rules.append(RegexpChunkRule.fromstring(line));	
        }

        // Record the final stage
        this._add_stage(rules, lhs, root_label, trace);
    }
    
    /**
     * Helper function for the constructor: add a new stage to the parser.
     * @param rules
     * @param lhs
     * @param root_label
     * @param trace
     */
    private void _add_stage(list<RegexpChunkRule> rules, String lhs, String root_label, int trace) throws IllegalArgumentException{
        if (x.isNotEmpty(rules)){
            if (x.isFalse(lhs)) {
                throw new IllegalArgumentException("Expected stage marker (eg NP:)");
            }
            RegexpChunkParser parser = new RegexpChunkParser(rules, lhs, root_label, trace);
            this._stages.append(parser);	        	
        }

    }
    
    /**
        Apply the chunk parser to this input.

        :type chunk_struct: Tree
        :param chunk_struct: the chunk structure to be (further) chunked
            (this tree is modified, and is also returned)
        :type trace: int
        :param trace: The level of tracing that should be used when
            parsing a text.  ``0`` will generate no tracing output;
            ``1`` will generate normal tracing output; and ``2`` or
            highter will generate verbose tracing output.  This value
            overrides the trace level value that was given to the
            constructor.
        :return: the chunked output.
        :rtype: Tree
     * @return
     */
    public Node parse(Node chunk_struct, Integer trace) {
        if (trace == null) trace = this._trace;
        for (int i : x.countTo(this._loop)) {
            for (RegexpChunkParser parser : this._stages) {
                chunk_struct = parser.parse(chunk_struct, trace);	
            }        	
        }

        return chunk_struct;
    }
}
