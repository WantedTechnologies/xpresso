package com.wantedtech.common.xpresso.sentence.chunker.regexchunker;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.sentence.chunker.Node;
import com.wantedtech.common.xpresso.text.Tokenizer;
import com.wantedtech.common.xpresso.text.tokenizer.en.stanford.RuleBasedTokenizer;
import com.wantedtech.common.xpresso.regex.Match;
import com.wantedtech.common.xpresso.sentence.Chunker;
import com.wantedtech.common.xpresso.sentence.Sentence;
import com.wantedtech.common.xpresso.token.Token;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public class RegexBasedChunker implements Chunker{

	int _trace;
	list<RegexpChunkParser> _stages;
	String _grammar;
	int _loop;

	public RegexBasedChunker(String grammar) {
		this(grammar, "S", 1, 0);
	}

	public RegexBasedChunker(String grammar, String root_label, int loop, int trace) {
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
				lhs = m.group("nonterminal").trim();
				rules = x.list();
				line = m.group("rule").trim();
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

        @param chunk_struct: the chunk structure to be (further) chunked
            (this tree is modified, and is also returned)
        @param trace: The level of tracing that should be used when
            parsing a text.  ``0`` will generate no tracing output;
            ``1`` will generate normal tracing output; and ``2`` or
            highter will generate verbose tracing output.  This value
            overrides the trace level value that was given to the
            constructor.
        @return: the chunked output.
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

	/**
		Apply the chunk parser to this input.

		@param sent a Sentence to chunk
		@param trace: The level of tracing that should be used when
    		parsing a text.  ``0`` will generate no tracing output;
    		``1`` will generate normal tracing output; and ``2`` or
    		highter will generate verbose tracing output.  This value
    		overrides the trace level value that was given to the
    		constructor.
		@return: the chunked output.
	 */
	private Node parse(Sentence sent, Integer trace) {
		if (trace == null) trace = this._trace;

		Node tree = new Node("S");
		for (Token elem : sent) {
			tree.append(new Node(elem.getAnnotation("pos"),elem.toString()));
		}

		for (int i : x.countTo(this._loop)) {
			for (RegexpChunkParser parser : this._stages) {
				tree = parser.parse(tree, trace);	
			}        	
		}
		return tree;
	}

	@Override
	public list<Node> chunk(String text) {
		Tokenizer tokenizer = new RuleBasedTokenizer();
		list<Node> result = x.list();
		for (Sentence s : tokenizer.tokenize(text)) {
			result.append(parse(s,0));
		}
		return result;
	}

	@Override
	public Node chunk(Sentence sent) {
		return parse(sent, 0);
	}

}
