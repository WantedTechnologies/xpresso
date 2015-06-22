package com.wantedtech.common.xpresso.en.sentence.chunker;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.regex.Regex;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;

/**
    """
    A string-based encoding of a particular chunking of a text.
    Internally, the ``ChunkString`` class uses a single string to
    encode the chunking of the input text.  This string contains a
    sequence of angle-bracket delimited tags, with chunking indicated
    by braces.  An example of this encoding is::

        {<DT><JJ><NN>}<VBN><IN>{<DT><NN>}<.>{<DT><NN>}<VBD><.>

    ``ChunkString`` are created from tagged texts (i.e., lists of
    ``tokens`` whose type is ``TaggedType``).  Initially, nothing is
    chunked.

    The chunking of a ``ChunkString`` can be modified with the ``xform()``
    method, which uses a regular expression to transform the string
    representation.  These transformations should only add and remove
    braces; they should *not* modify the sequence of angle-bracket
    delimited tags.

    :type _str: str
    :ivar _str: The internal string representation of the text's
        encoding.  This string representation contains a sequence of
        angle-bracket delimited tags, with chunking indicated by
        braces.  An example of this encoding is::

            {<DT><JJ><NN>}<VBN><IN>{<DT><NN>}<.>{<DT><NN>}<VBD><.>

    :type _pieces: list(tagged tokens and chunks)
    :ivar _pieces: The tagged tokens and chunks encoded by this ``ChunkString``.
    :ivar _debug: The debug level.  See the constructor docs.

    :cvar IN_CHUNK_PATTERN: A zero-width regexp pattern string that
        will only match positions that are in chunks.
    :cvar IN_CHINK_PATTERN: A zero-width regexp pattern string that
        will only match positions that are in chinks.
    """
 * @author andriy.burkov
 *
 */
public class ChunkString {

	list<Node> _pieces;
	String _root_label;
	String _str;

    static String CHUNK_TAG_CHAR = "[^\\{\\}<>]";
    static String CHUNK_TAG = "(<" + CHUNK_TAG_CHAR + "+?>)";

    static String IN_CHUNK_PATTERN = "(?=[^\\{]*\\})";
    static String IN_CHINK_PATTERN = "(?=[^\\}]*(\\{|$))";

    // These are used by _verify
    static String _CHUNK = "(\\{" + CHUNK_TAG + "+?\\})+?";
    static String _CHINK = "(" + CHUNK_TAG + "+?)+?"; 
    static Regex _VALID = x.Regex("^(\\{?" + CHUNK_TAG + "\\}?)*?$");
    static Regex _BRACKETS = x.Regex("[^\\{\\}]+");
    static Regex _BALANCED_BRACKETS = x.Regex("(\\{\\})*$");

    Function<Object,String> _tag = new Function<Object,String>() {
    	public String apply(Object tok) {
            if (tok instanceof tuple) {
        		return ((tuple)tok).getString(0);            	
            } else if (tok instanceof Node) {
                return ((Node)tok).label();
            } else {
            	throw new IllegalArgumentException();
            }
            
    	}
    };
    
    /**
        """
        Construct a new ``ChunkString`` that encodes the chunking of
        the text ``tagged_tokens``.

        :type chunk_struct: Tree
        :param chunk_struct: The chunk structure to be further chunked.
        :type debug_level: int
        :param debug_level: The level of debugging which should be
            applied to transformations on the ``ChunkString``.  The
            valid levels are:
                - 0: no checks
                - 1: full check on to_chunkstruct
                - 2: full check on to_chunkstruct and cursory check after
                   each transformation.
                - 3: full check on to_chunkstruct and full check after
                   each transformation.
            We recommend you use at least level 1.  You should
            probably use level 3 if you use any non-standard
            subclasses of ``RegexpChunkRule``.
        """
     */
    public ChunkString(Node chunk_struct) {
    	this._root_label = chunk_struct.label();
        this._pieces = chunk_struct.elements();
        list<String> tags = x.list(x.<String>yield().apply(_tag).forEach(this._pieces));
        this._str = "<" + x.String("><").join(tags) + ">";
    }

    /**
        """
        Check to make sure that ``s`` still corresponds to some chunked
        version of ``_pieces``.

        :type verify_tags: bool
        :param verify_tags: Whether the individual tags should be
            checked.  If this is false, ``_verify`` will check to make
            sure that ``_str`` encodes a chunked version of *some*
            list of tokens.  If this is true, then ``_verify`` will
            check to make sure that the tags in ``_str`` match those in
            ``_pieces``.

        :raise ValueError: if the internal string representation of
            this ``ChunkString`` is invalid or not consistent with _pieces.
        """
     * @return
     */
    void _verify(String s, int verify_tags) {
        // Check overall form
        if (x.isFalse(ChunkString._VALID.find(s))) {
            throw new IllegalArgumentException("Transformation generated invalid chunkstring:\n" + s);
        }


        // Check that parens are balanced.  If the string is long, we
        // have to do this in pieces, to avoid a maximum recursion
        // depth limit for regular expressions.
        String brackets = ChunkString._BRACKETS.sub("", s);
        for (int i : x.countTo((int)(1 + x.len(brackets) / 5000))) {
            String substr = x.String(brackets).slice(i*5000,i*5000+5000);
            if (x.isFalse(ChunkString._BALANCED_BRACKETS.find(substr))) {
                throw new IllegalArgumentException("Transformation generated invalid chunkstring:\n" + s);	
            }        	
        }


        if (verify_tags <= 0) return;

        list<String> tags1 = (x.Regex("[\\{\\}<>]+").split(s)).slice(1,-1);
        list<String> tags2 = x.list(x.<String>yield().apply(_tag).forEach(this._pieces));
        if (!x.Object(tags1).equals(tags2)) {
            throw new IllegalArgumentException("Transformation generated invalid chunkstring: tag changed");        	
        }
    }



	/**
	        """
	        Return the chunk structure encoded by this ``ChunkString``.
	
	        :rtype: Tree
	        :raise ValueError: If a transformation has generated an
	            invalid chunkstring.
	        """
	 * @return
	 */
    public Node to_chunkstruct(String label) {

        //# Use this alternating list to create the chunkstruct.
        list<Node> pieces = x.list();
        int index = 0;
        boolean piece_in_chunk = false;
        for (String piece : x.Regex("[{}]").split(this._str)) {
            // Find the list of tokens contained in this piece.
            int length = x.String(piece).count("<");
            list<Node> subsequence = this._pieces.slice(index,index+length);

            // Add this list of tokens to our pieces.
            if (piece_in_chunk) {
            	list<Node> lst = x.list();
            	for (Node leaf : subsequence) {
            		lst.append(leaf);
            	}
                pieces.append(new Node(label, lst));	
            } else {
            	for (Node leaf : subsequence) {
                    pieces.append(leaf);            		
            	}
            	
            }
            // Update index
            index += length;  
            piece_in_chunk = !piece_in_chunk;
        }

        return new Node(this._root_label, pieces);
    }

    /**
        """
        Apply the given transformation to the string encoding of this
        ``ChunkString``.  In particular, find all occurrences that match
        ``regexp``, and replace them using ``repl`` (as done by
        ``re.sub``).

        This transformation should only add and remove braces; it
        should *not* modify the sequence of angle-bracket delimited
        tags.  Furthermore, this transformation may not result in
        improper bracketing.  Note, in particular, that bracketing may
        not be nested.

        :type regexp: str or regexp
        :param regexp: A regular expression matching the substring
            that should be replaced.  This will typically include a
            named group, which can be used by ``repl``.
        :type repl: str
        :param repl: An expression specifying what should replace the
            matched substring.  Typically, this will include a named
            replacement group, specified by ``regexp``.
        :rtype: None
        :raise ValueError: If this transformation generated an
            invalid chunkstring.
        """
     * @return
     */
    public void xform(Regex regexp, String repl) {
        // Do the actual substitution
        String s = regexp.sub(repl, this._str);

        //# The substitution might have generated "empty chunks"
        //# (substrings of the form "{}").  Remove them, so they don't
        //# interfere with other transformations.
        s = x.Regex("\\{\\}").sub("", s);
        
        // Commit the transformation.
        this._str = s;    	
    }

    @Override
    public String toString() {
    	return this._str;
    }


}
