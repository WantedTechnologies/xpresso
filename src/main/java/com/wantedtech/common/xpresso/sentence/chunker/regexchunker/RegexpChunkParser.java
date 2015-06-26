package com.wantedtech.common.xpresso.sentence.chunker.regexchunker;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.sentence.chunker.Node;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;

/**
    A regular expression based chunk parser.  ``RegexpChunkParser`` uses a
    sequence of "rules" to find chunks of a single type within a
    text.  The chunking of the text is encoded using a ``ChunkString``,
    and each rule acts by modifying the chunking in the
    ``ChunkString``.  The rules are all implemented using regular
    expression matching and substitution.

    The ``RegexpChunkRule`` class and its subclasses (``ChunkRule``,
    ``ChinkRule``, ``UnChunkRule``, ``MergeRule``, and ``SplitRule``)
    define the rules that are used by ``RegexpChunkParser``.  Each rule
    defines an ``apply()`` method, which modifies the chunking encoded
    by a given ``ChunkString``.

    :type _rules: list(RegexpChunkRule)
    :ivar _rules: The list of rules that should be applied to a text.
    :type _trace: int
    :ivar _trace: The default level of tracing.
 * @author andriy.burkov
 *
 */

public class RegexpChunkParser {
	
	int _trace;
	String _chunk_label;
	String _root_label;
	list<RegexpChunkRule> _rules;
	
	/**
        Construct a new ``RegexpChunkParser``.

        @param rules: The sequence of rules that should be used to
            generate the chunking for a tagged text.

        @param chunk_label: The node value that should be used for
            chunk subtrees.  This is typically a short string
            describing the type of information contained by the chunk,
            such as ``"NP"`` for base noun phrases.
            
        @param root_label: The node value that should be used for the
            top node of the chunk structure.

        @param trace: The level of tracing that should be used when
            parsing a text.  ``0`` will generate no tracing output;
            ``1`` will generate normal tracing output; and ``2`` or
            higher will generate verbose tracing output.
	 */
	
    public RegexpChunkParser(list<RegexpChunkRule> rules, String chunk_label, String root_label, int trace) {
        this._rules = rules;
        this._trace = trace;
        this._chunk_label = chunk_label;
        this._root_label = root_label;
    }

    /**
        """
        Apply each rule of this ``RegexpChunkParser`` to ``chunkstr``, in
        turn.

        :param chunkstr: The chunk string to which each rule should be
            applied.
        :type chunkstr: ChunkString
        :rtype: None
        """
     * @param chunkstr
     * @return
     */
    private void _notrace_apply(ChunkString chunkstr) {
        for (RegexpChunkRule rule : this._rules) {
            rule.apply(chunkstr);
        }    	
    }


    /**
        @param chunk_struct: the chunk structure to be (further) chunked
        @param trace: The level of tracing that should be used when
            parsing a text.  ``0`` will generate no tracing output;
            ``1`` will generate normal tracing output; and ``2`` or
            highter will generate verbose tracing output.  This value
            overrides the trace level value that was given to the
            constructor.
        @return: a chunk structure that encodes the chunks in a given
            tagged sentence.  A chunk is a non-overlapping linguistic
            group, such as a noun phrase.  The set of chunks
            identified in the chunk structure depends on the rules
            used to define this ``RegexpChunkParser``.
     * 
     */
    public Node parse(Node chunk_struct, Integer trace) {
        if (x.len(chunk_struct) == 0) {
            x.print("Warning: parsing empty text");
            return new Node(this._root_label, x.<Node>list());	
        }

        try {
            chunk_struct.getLabel();
        } catch (Exception e) {
            chunk_struct = new Node(this._root_label, chunk_struct.getLeaves());	
        }

        //# Use the default trace value?
        if (trace == null) trace = this._trace;

        ChunkString chunkstr = new ChunkString(chunk_struct);

        // Apply the sequence of rules to the chunkstring.
        this._notrace_apply(chunkstr);

        // Use the chunkstring to create a chunk structure.
        return chunkstr.to_chunkstruct(this._chunk_label);	
    }

    /**
        @return: the sequence of rules used by ``RegexpChunkParser``.
     */
    public list<RegexpChunkRule> rules() {
        return this._rules;	
    }

}
