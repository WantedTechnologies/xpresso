package com.wantedtech.common.xpresso.strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.tuples.tuple2;
import com.wantedtech.common.xpresso.types.tuples.tuple3;
import com.wantedtech.common.xpresso.types.tuples.tuple4;

/*
SequenceMatcher is a flexible class for comparing pairs of sequences of
any type, so long as the sequence elements are hashable.  The basic
algorithm predates, and is a little fancier than, an algorithm
published in the late 1980's by Ratcliff and Obershelp under the
hyperbolic name "gestalt pattern matching".  The basic idea is to find
the longest contiguous matching subsequence that contains no "junk"
elements (R-O doesn't address junk).  The same idea is then applied
recursively to the pieces of the sequences to the left and to the right
of the matching subsequence.  This does not yield minimal edit
sequences, but does tend to yield matches that "look right" to people.
 
SequenceMatcher tries to compute a "human-friendly diff" between two
sequences.  Unlike e.g. UNIX(tm) diff, the fundamental notion is the
longest contiguous & junk-free matching subsequence.  That's what
catches peoples' eyes.  The Windows(tm) windiff has another interesting
notion, pairing up elements that appear uniquely in each sequence.
That, and the method here, appear to yield more intuitive difference
reports than does diff.  This method appears to be the least vulnerable
to synching up on blocks of "junk lines", though (like blank lines in
ordinary text files, or maybe "<P>" lines in HTML files).  That may be
because this is the only method of the 3 that has a concept of
"junk" <wink>.
*/

public class SequenceMatcher {

	dict<Integer> populardict;
	dict<list<Integer>> b2j;
	String a;
	String b;
	list<tuple3<Integer,Integer,Integer>> matching_blocks;

    /**
	Construct a SequenceMatcher.

    Optional arg isjunk is None (the default), or a one-argument
    function that takes a sequence element and returns true iff the
    element is junk.  None is equivalent to passing "lambda x: 0", i.e.
    no elements are considered to be junk.  For example, pass
        lambda x: x in " \\t"
    if you're comparing lines as sequences of characters, and don't
    want to synch up on blanks or hard tabs.

    Optional arg a is the first of two sequences to be compared.  By
    default, an empty string.  The elements of a must be hashable.  See
    also .set_seqs() and .set_seq1().

    Optional arg b is the second of two sequences to be compared.  By
    default, an empty string.  The elements of b must be hashable. See
    also .set_seqs() and .set_seq2().
    """

    # Members:
    # a
    #      first sequence
    # b
    #      second sequence; differences are computed as "what do
    #      we need to do to 'a' to change it into 'b'?"
    # b2j
    #      for x in b, b2j[x] is a list of the indices (into b)
    #      at which x appears; junk elements do not appear
    # fullbcount
    #      for x in b, fullbcount[x] == the number of times x
    #      appears in b; only materialized if really needed (used
    #      only for computing quick_ratio())
    # matching_blocks
    #      a list of (i, j, k) triples, where a[i:i+k] == b[j:j+k];
    #      ascending & non-overlapping in i and in j; terminated by
    #      a dummy (len(a), len(b), 0) sentinel
    # opcodes
    #      a list of (tag, i1, i2, j1, j2) tuples, where tag is
    #      one of
    #          'replace'   a[i1:i2] should be replaced by b[j1:j2]
    #          'delete'    a[i1:i2] should be deleted
    #          'insert'    b[j1:j2] should be inserted
    #          'equal'     a[i1:i2] == b[j1:j2]
    # isjunk
    #      a user-supplied function taking a sequence element and
    #      returning true iff the element is "junk" -- this has
    #      subtle but helpful effects on the algorithm, which I'll
    #      get around to writing up someday <0.9 wink>.
    #      DON'T USE!  Only __chain_b uses this.  Use isbjunk.
    # isbjunk
    #      for x in b, isbjunk(x) == isjunk(x) but much faster;
    #      it's really the __contains__ method of a hidden dict.
    #      DOES NOT WORK for x in a!
    # isbpopular
    #      for x in b, isbpopular(x) is true iff b is reasonably long
    #      (at least 200 elements) and x accounts for more than 1% of
    #      its elements.  DOES NOT WORK for x in a!
    */
    public SequenceMatcher(String a, String b) {
        this.set_seqs(a, b);	    	
    }

    /**
     * 
        """Set the two sequences to be compared.

        >>> s = SequenceMatcher()
        >>> s.set_seqs("abcd", "bcde")
        >>> s.ratio()
        0.75
        """
     * 
     */
    public void set_seqs(String a, String b) {
        this.set_seq1(a);
        this.set_seq2(b);	    	
    }

    /**
     * 
        Set the first sequence to be compared.
        The second sequence to be compared is not changed.
        
        >>> s = SequenceMatcher(None, "abcd", "bcde")
        >>> s.ratio()
        0.75
        >>> s.set_seq1("bcde")
        >>> s.ratio()
        1.0
        >>>

        SequenceMatcher computes and caches detailed information about the
        second sequence, so if you want to compare one sequence S against
        many sequences, use .set_seq2(S) once and call .set_seq1(x)
        repeatedly for each of the other sequences.

        See also set_seqs() and set_seq2().
     */
    public void set_seq1(String a) {
        if (a.equals(this.a))
            return;
        this.a = a;	    	
    }

    /**
        Set the second sequence to be compared.

        The first sequence to be compared is not changed.

        >>> s = SequenceMatcher(None, "abcd", "bcde")
        >>> s.ratio()
        0.75
        >>> s.set_seq2("abcd")
        >>> s.ratio()
        1.0
        >>>

        SequenceMatcher computes and caches detailed information about the
        second sequence, so if you want to compare one sequence S against
        many sequences, use .set_seq2(S) once and call .set_seq1(x)
        repeatedly for each of the other sequences.

        See also set_seqs() and set_seq1().
     */
    public void set_seq2(String b) {
        if (b.equals(this.b))
            return;
        this.b = b;
        this.__chain_b();	    	
    }
    /**
    # For each element x in b, set b2j[x] to a list of the indices in
    # b where x appears; the indices are in increasing order; note that
    # the number of times x appears in b is len(b2j[x]) ...
    # when self.isjunk is defined, junk elements don't show up in this
    # map at all, which stops the central find_longest_match method
    # from starting any matching block at a junk element ...
    # also creates the fast isbjunk function ...
    # b2j also does not contain entries for "popular" elements, meaning
    # elements that account for more than 1% of the total elements, and
    # when the sequence is reasonably large (>= 200 elements); this can
    # be viewed as an adaptive notion of semi-junk, and yields an enormous
    # speedup when, e.g., comparing program files with hundreds of
    # instances of "return NULL;" ...
    # note that this is only called when b changes; so for cross-product
    # kinds of matches, it's best to call set_seq2 once, then set_seq1
    # repeatedly
    */

    public void __chain_b() {
    	/*
        # Because isjunk is a user-defined (not C) function, and we test
        # for junk a LOT, it's important to minimize the number of calls.
        # Before the tricks described here, __chain_b was by far the most
        # time-consuming routine in the whole module!  If anyone sees
        # Jim Roskind, thank him again for profile.py -- I never would
        # have guessed that.
        # The first trick is to build b2j ignoring the possibility
        # of junk.  I.e., we don't call isjunk at all yet.  Throwing
        # out the junk later is much cheaper than building b2j "right"
        # from the start.
        */
        String b = this.b;
        int n = x.len(b);
        this.b2j = x.dict();
        dict<list<Integer>> b2j = this.b2j; 
        populardict = x.dict();
        for (tuple2<Integer,String> item : x.enumerate(b)) {
        	int i = item.index; String elt = item.value;
            if (x.String(elt).in(b2j)) {
                list<Integer> indices = x.list();
				indices = b2j.get(elt);
                if (n >= 200 && x.len(indices) * 100 > n) {
                    populardict.setAt(elt).value(1);	                	
                } else {
                    indices.append(i);	                	
                }
            } else {
                b2j.setAt(elt).value(x.listOf(i));	            	
            }

        }

        // Purge leftover indices for popular elements.
        for (String elt : populardict) {
            b2j.del(elt);	        	
        }


        /*
        # Now b2j.keys() contains elements uniquely, and especially when
        # the sequence is a string, that's usually a good deal smaller
        # than len(string).  The difference is the number of isjunk calls
        # saved.
        */
    }

    private boolean ispopular(String key) {
    	return x.String(key).in(this.populardict);
    }

    /**
     * 
        """Return a measure of the sequences' similarity (float in [0,1]).

        Where T is the total number of elements in both sequences, and
        M is the number of matches, this is 2.0*M / T.
        Note that this is 1 if the sequences are identical, and 0 if
        they have nothing in common.

        .ratio() is expensive to compute if you haven't already computed
        .get_matching_blocks() or .get_opcodes(), in which case you may
        want to try .quick_ratio() or .real_quick_ratio() first to get an
        upper bound.

        >>> s = SequenceMatcher(None, "abcd", "bcde")
        >>> s.ratio()
        0.75
        >>> s.quick_ratio()
        0.75
        >>> s.real_quick_ratio()
        1.0
        """
     * 
     */
    public double ratio() {
        int matches = x.reduce(x.<Integer>lambdaF("sum, triple : sum + triple[2]"), get_matching_blocks(), 0);
        return _calculate_ratio(matches, x.len(this.a) + x.len(this.b));
    }

    private static double _calculate_ratio(int matches, int length) {
        if (length > 0)
            return 2.0 * (double)matches / (double)length;
        return 1.0;
    }

    /**
     * 
        """Return list of triples describing matching subsequences.

        Each triple is of the form (i, j, n), and means that
        a[i:i+n] == b[j:j+n].  The triples are monotonically increasing in
        i and in j.  New in Python 2.5, it's also guaranteed that if
        (i, j, n) and (i', j', n') are adjacent triples in the list, and
        the second is not the last triple in the list, then i+n != i' or
        j+n != j'.  IOW, adjacent triples never describe adjacent equal
        blocks.

        The last triple is a dummy, (len(a), len(b), 0), and is the only
        triple with n==0.

        >>> s = SequenceMatcher(None, "abxcd", "abcd")
        >>> s.get_matching_blocks()
        [Match(a=0, b=0, size=2), Match(a=3, b=2, size=2), Match(a=5, b=4, size=0)]
        """
     * @return list of triples describing matching subsequences
     */
    list<tuple3<Integer,Integer,Integer>> get_matching_blocks() {
        if (this.matching_blocks != null)
            return this.matching_blocks;
        
        int la = x.len(this.a);
        int lb = x.len(this.b);

        /*
        # This is most naturally expressed as a recursive algorithm, but
        # at least one user bumped into extreme use cases that exceeded
        # the recursion limit on their box.  So, now we maintain a list
        # ('queue`) of blocks we still need to look at, and append partial
        # results to `matching_blocks` in a loop; the matches are sorted
        # at the end.
        */
        list<tuple4<Integer,Integer,Integer,Integer>> queue = x.list();
        queue.append(tuple4.valueOf(0, la, 0, lb));
        
        list<tuple3<Integer,Integer,Integer>> matching_blocks = x.list();
        while (x.isTrue(queue)) {
        	tuple4<Integer,Integer,Integer,Integer> val = queue.pop(); 
            int alo = val.value0; int ahi = val.value1; int blo = val.value2; int bhi = val.value3;
            tuple3<Integer,Integer,Integer> x = find_longest_match(alo, ahi, blo, bhi);
            int i = x.value0; int j = x.value1; int k = x.value2;
            /*
            # a[alo:i] vs b[blo:j] unknown
            # a[i:i+k] same as b[j:j+k]
            # a[i+k:ahi] vs b[j+k:bhi] unknown
            */
            if (k > 0) {   // if k is 0, there was no matching block
                matching_blocks.append(x);
                if (alo < i && blo < j) {
                    queue.append(tuple4.valueOf(alo, i, blo, j));	                	
                }
                if (i+k < ahi && j+k < bhi) {
                    queue.append(tuple4.valueOf(i+k, ahi, j+k, bhi));	                	
                }

            }
        }

        matching_blocks = x.list(x.sort(matching_blocks));

        /*
        # It's possible that we have adjacent equal blocks in the
        # matching_blocks list now.  Starting with 2.5, this code was added
        # to collapse them.
        */
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;
        list<tuple3<Integer,Integer,Integer>> non_adjacent = x.list();
        for (tuple3<Integer,Integer,Integer> item : matching_blocks) {
        	int i2 = item.value0; int j2 = item.value1; int k2 = item.value2;
            //# Is this block adjacent to i1, j1, k1?
            if (i1 + k1 == i2 && j1 + k1 == j2) {
            	/*
                # Yes, so collapse them -- this just increases the length of
                # the first block by the length of the second, and the first
                # block so lengthened remains the block to compare against.
                */
                k1 += k2;	            	
            } else {
            	/*
                # Not adjacent.  Remember the first block (k1==0 means it's
                # the dummy we started with), and make the second block the
                # new block to compare against.
                */
                if (k1 > 0) {
                    non_adjacent.append(tuple3.valueOf(i1, j1, k1));	                	
                }

                i1 = i2; j1 = j2; k1 = k2;
            }
        }

        if (k1 > 0) {
            non_adjacent.append(tuple3.valueOf(i1, j1, k1));	        	
        }

        non_adjacent.append(tuple3.valueOf(la, lb, 0));
        this.matching_blocks = non_adjacent;
        return this.matching_blocks;
    }
    
    /**
     * 
        """Find longest matching block in a[alo:ahi] and b[blo:bhi].

        If isjunk is not defined:

        Return (i,j,k) such that a[i:i+k] is equal to b[j:j+k], where
            alo <= i <= i+k <= ahi
            blo <= j <= j+k <= bhi
        and for all (i',j',k') meeting those conditions,
            k >= k'
            i <= i'
            and if i == i', j <= j'

        In other words, of all maximal matching blocks, return one that
        starts earliest in a, and of all those maximal matching blocks that
        start earliest in a, return the one that starts earliest in b.

        >>> s = SequenceMatcher(None, " abcd", "abcd abcd")
        >>> s.find_longest_match(0, 5, 0, 9)
        Match(a=0, b=4, size=5)

        If isjunk is defined, first the longest matching block is
        determined as above, but with the additional restriction that no
        junk element appears in the block.  Then that block is extended as
        far as possible by matching (only) junk elements on both sides.  So
        the resulting block never matches on junk except as identical junk
        happens to be adjacent to an "interesting" match.

        Here's the same example as before, but considering blanks to be
        junk.  That prevents " abcd" from matching the " abcd" at the tail
        end of the second sequence directly.  Instead only the "abcd" can
        match, and matches the leftmost "abcd" in the second sequence:

        >>> s = SequenceMatcher(lambda x: x==" ", " abcd", "abcd abcd")
        >>> s.find_longest_match(0, 5, 0, 9)
        Match(a=1, b=0, size=4)

        If no blocks match, return (alo, blo, 0).

        >>> s = SequenceMatcher(None, "ab", "c")
        >>> s.find_longest_match(0, 2, 0, 1)
        Match(a=0, b=0, size=0)
        """
     * 
     * @return
     */
    public tuple3<Integer,Integer,Integer> find_longest_match(int alo, int ahi, int blo, int bhi) {
    	/*
        # CAUTION:  stripping common prefix or suffix would be incorrect.
        # E.g.,
        #    ab
        #    acab
        # Longest matching block is "ab", but if common prefix is
        # stripped, it's "a" (tied with "b").  UNIX(tm) diff does so
        # strip, so ends up claiming that ab is changed to acab by
        # inserting "ca" in the middle.  That's minimal but unintuitive:
        # "it's obvious" that someone inserted "ac" at the front.
        # Windiff ends up at the same place as diff, but by pairing up
        # the unique 'b's and then matching the first two 'a's.
        */

        int besti = alo; int bestj = blo; int bestsize = 0;
        /*
        # find longest junk-free match
        # during an iteration of the loop, j2len[j] = length of longest
        # junk-free match ending with a[i-1] and b[j]
        */
        Map<Integer,Integer> j2len = new HashMap<Integer,Integer>();
        list<Integer> nothing = x.list();
        for (int i : x.range(alo, ahi)) {
        	
        	/*
            # look at all instances of a[i] in b; note that because
            # b2j has no junk keys, the loop is skipped if a[i] is junk
            */ 
        	Map<Integer,Integer> newj2len = new HashMap<Integer,Integer>();
            for (int j : b2j.get(x.String(a).get(i), nothing)) {
                // a[i] matches b[j]
                if (j < blo) {
                    continue;
                }
                if (j >= bhi) {
                    break;
                }

                newj2len.put(j,j2len.getOrDefault(j-1,0) + 1);
                int k = newj2len.get(j);
                if (k > bestsize) {
                    besti = i-k+1; bestj = j-k+1; bestsize = k;
                }
            }
            j2len = newj2len;
        }

        /*
        # Extend the best by non-junk elements on each end.  In particular,
        # "popular" non-junk elements aren't in b2j, which greatly speeds
        # the inner loop above, but also means "the best" match so far
        # doesn't contain any junk *or* popular non-junk elements.
        */
        while (besti > alo && bestj > blo && x.String(a).get(besti-1).equals(x.String(b).get(bestj-1))) {
            besti = besti-1; bestj = bestj-1; bestsize = bestsize+1;
        }

        while (besti+bestsize < ahi && bestj+bestsize < bhi && x.String(a).get(besti+bestsize).equals(x.String(b).get(bestj+bestsize))) {
            bestsize += 1;
        }

        /*
        # Now that we have a wholly interesting match (albeit possibly
        # empty!), we may as well suck up the matching junk on each
        # side of it too.  Can't think of a good reason not to, and it
        # saves post-processing the (possibly considerable) expense of
        # figuring out what to do with it.  In the case of an empty
        # interesting match, this is clearly the right thing to do,
        # because no other kind of match is possible in the regions.
        */
        while (besti > alo && bestj > blo && x.String(a).get(besti-1).equals(x.String(b).get(bestj-1))) {
            besti = besti-1; bestj = bestj-1; bestsize = bestsize+1;
        }

        while (besti+bestsize < ahi && bestj+bestsize < bhi && x.String(a).get(besti+bestsize).equals(x.String(b).get(bestj+bestsize))) {
            bestsize = bestsize + 1;
        }

        return tuple3.valueOf(besti, bestj, bestsize);
    }
}
