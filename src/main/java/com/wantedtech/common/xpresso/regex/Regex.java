package com.wantedtech.common.xpresso.regex;
import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.str;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -5558401165270154328L;

		
	public Pattern pattern;
	dict<String> replacements;
	dict<Pattern> searches;
	
	/******************************************************
	 * Translate
	 *    \w \W \s \S \v \V \h \H \d \D \b \B \X \R
	 * into Unicode-correct code.
	 ******************************************************/
	
    private final static String
    identifier_chars = "\\pL"          /* all Letters      */
                     + "\\pM"          /* all Marks        */
                     + "\\p{Nd}"       /* Decimal Number   */
                     + "\\p{Nl}"       /* Letter Number    */
                     + "\\p{Pc}"       /* Connector Punctuation           */
                     + "["             /*    or else chars which are both */
                     +     "\\p{InEnclosedAlphanumerics}"
                     +   "&&"          /*    and also      */
                     +     "\\p{So}"   /* Other Symbol     */
                     + "]";

    public final static String identifier_charclass = "["  + identifier_chars + "]";       /* \w */

    public final static String not_identifier_charclass = "[^" + identifier_chars + "]";       /* \W */

    /*
     * Because Java's \b is unusable.
     *
     * If only \b worked, we could have just one boundary.
     *
     * And if conditionals worked, we could have just two:
     *
     *     boundary_before      is  (?(?=\w)(?<!\w)|(?<=\w))
     *     boundary_after       is  (?(?<=\w)(?!\w)|(?=\w))
     *
     * But this is Java, so they don't, which means we need four:
     *
     *  boundary_before_word     is  (?<!\w)
     *  boundary_before_not_word is  (?<=\w)
     *  boundary_after_word      is  (?!\w)
     *  boundary_after_not_word  is  (?=\w)
     *
     * Because Java's \B is unusable.
     *
     * If only \B worked, we could have just one not_boundary.
     *
     * And if conditionals worked, we could have just two:
     *
     *      not_boundary_after       is  (?(?<=\w)(?=\w)|(?!\w))
     *      not_boundary_before      is  (?(?=\w)(?<=\w)|(?<!\w))
     *
     * But this is Java, so they don't, which means we need four:
     *
     *      not_boundary_before_word      is  (?<=\w)
     *      not_boundary_before_not_word  is  (?<!\w)
     *      not_boundary_after_word       is  (?=\w)
     *      not_boundary_after_not_word   is  (?!\w)
     *
     */

    private final static String boundary_after_not_word = "(?="  + identifier_charclass + ")";

    public final static String precedes_word = boundary_after_not_word;

    private final static String boundary_after_word = "(?!"  + identifier_charclass + ")";

    public final static String not_precedes_word = boundary_after_word;

    private final static String boundary_before_not_word = "(?<=" + identifier_charclass + ")";

    public final static String follows_word = boundary_before_not_word;

    private final static String boundary_before_word  = "(?<!" + identifier_charclass + ")";

    public final static String not_follows_word = boundary_before_word;

	/*
	 * a \b is the same as (?:(?<=\w)(?!\w)|(?<!\w)(?=\w))
	 *
	 */
	public final static String
	boundary        = "(?:"                                         /* \b */
	                    // IF
	                    +       follows_word
	                    // THEN
	                    +       not_precedes_word
	                    +   "|"  // ELSE
	                    // IF
	                    +       not_follows_word
	                    // THEN
	                    +       precedes_word
	                    +  ")"
	                    ;

	/*
	 * a \B is the same as (?:(?<=\w)(?=\w)|(?<!\w)(?!\w))
	 */
	public final static String
	not_boundary    = "(?:"                                         /* \B */
	                    // IF
	                    +       follows_word
	                    // THEN
	                    +       precedes_word
	                    +   "|"  // ELSE
	                    // IF
	                    +       not_follows_word
	                    // THEN
	                    +       not_precedes_word
	                    +  ")"
	                    ;

    /*
     * Because Java's \s and \S and \p{Space} are all unusable.
     */
    private final static String whitespace_chars =  ""       /* dummy empty string for homogeneity */
        + "\\u000A" // LINE FEED (LF)
        + "\\u000B" // LINE TABULATION
        + "\\u000C" // FORM FEED (FF)
        + "\\u000D" // CARRIAGE RETURN (CR)
        + "\\u0020" // SPACE
        + "\\u0085" // NEXT LINE (NEL)
        + "\\u00A0" // NO-BREAK SPACE
        + "\\u1680" // OGHAM SPACE MARK
        + "\\u180E" // MONGOLIAN VOWEL SEPARATOR
        + "\\u2000" // EN QUAD
        + "\\u2001" // EM QUAD
        + "\\u2002" // EN SPACE
        + "\\u2003" // EM SPACE
        + "\\u2004" // THREE-PER-EM SPACE
        + "\\u2005" // FOUR-PER-EM SPACE
        + "\\u2006" // SIX-PER-EM SPACE
        + "\\u2007" // FIGURE SPACE
        + "\\u2008" // PUNCTUATION SPACE
        + "\\u2009" // THIN SPACE
        + "\\u200A" // HAIR SPACE
        + "\\u2028" // LINE SEPARATOR
        + "\\u2029" // PARAGRAPH SEPARATOR
        + "\\u202F" // NARROW NO-BREAK SPACE
        + "\\u205F" // MEDIUM MATHEMATICAL SPACE
        + "\\u3000" // IDEOGRAPHIC SPACE
        ;

    public final static String whitespace_charclass  = "["  + whitespace_chars + "]";

    public final static String not_whitespace_charclass = "[^" + whitespace_chars + "]";

	/*
	 * this is to avoid variable length lookbehind
	 */
	public final static String               /********************/
	space_edge_left = "(?:"                  /* an "improved" \b */
	                       +     "(?<=^)"    /* to the left      */
	                       +   "|"           /********************/
	                       +     "(?<="
	                       +           whitespace_charclass
	                       +     ")"
	                       +  ")";

	public final static String                   /********************/
	space_edge_right = "(?="                     /* an "improved" \b */
	                        +       "$"          /* to the right     */
	                        +  "|"               /********************/
	                        +        whitespace_charclass
	                        + ")";

    /*
     * Because Java's \p{Alpha} is unusably ASCII-only.
     */
    private final static String
    alphabetic_chars = "\\pL"                   /* all Letters    */
                            + "\\pM"            /* all Marks      */
                            + "\\p{Nl}"         /* Letter Number  */
                            ;

	public final static String alphabetic_charclass     = "["  + alphabetic_chars + "]"; /* \p{Alpha} */
	
	public final static String not_alphabetic_charclass = "[^" + alphabetic_chars + "]"; /* \P{Alpha} */
	
	/*
	 * Because Java's \d is ASCII-only.
	 */
	public final static String
	digits_charclass     = "\\p{Nd}";  /* \d */
	
	public final static String
	not_digits_charclass = "\\P{Nd}";  /* \D */

    /*
     * Because Java's \p{Hyphen} is missing.
     */
    private final static String
    hyphen_chars = ""        /* dummy empty string for homogeneity */
        + "\\u002D" // HYPHEN-MINUS
        + "\\u00AD" // SOFT HYPHEN
        + "\\u058A" // ARMENIAN HYPHEN
        + "\\u1806" // MONGOLIAN TODO SOFT HYPHEN
        + "\\u2010" // HYPHEN
        + "\\u2011" // NON-BREAKING HYPHEN
        + "\\u2E17" // DOUBLE OBLIQUE HYPHEN
        + "\\u30FB" // KATAKANA MIDDLE DOT
        + "\\uFE63" // SMALL HYPHEN-MINUS
        + "\\uFF0D" // FULLWIDTH HYPHEN-MINUS
        + "\\uFF65" // HALFWIDTH KATAKANA MIDDLE DOT
        ;

	public final static String
	hyphen_charclass = "["  + hyphen_chars + "]"; /* \p{Hyphen} */
	
	public final static String
	not_hyphen_charclass = "[^" + hyphen_chars + "]"; /* \P{Hyphen} */

    /*
     * Because Java's \p{Dash} is missing,
     * and \p{Pd} is missing important
     * things like MINUS SIGN.
     */

    private final static String
    dash_chars     =  ""        /* dummy empty string for homogeneity */
         +  "\\u002D" // HYPHEN-MINUS
         +  "\\u058A" // ARMENIAN HYPHEN
         +  "\\u05BE" // HEBREW PUNCTUATION MAQAF
         +  "\\u1400" // CANADIAN SYLLABICS HYPHEN
         +  "\\u1806" // MONGOLIAN TODO SOFT HYPHEN
         +  "\\u2010" // HYPHEN
         +  "\\u2011" // NON-BREAKING HYPHEN
         +  "\\u2012" // FIGURE DASH
         +  "\\u2013" // EN DASH
         +  "\\u2014" // EM DASH
         +  "\\u2015" // HORIZONTAL BAR
         +  "\\u2053" // SWUNG DASH
         +  "\\u207B" // SUPERSCRIPT MINUS
         +  "\\u208B" // SUBSCRIPT MINUS
         +  "\\u2212" // MINUS SIGN
         +  "\\u2E17" // DOUBLE OBLIQUE HYPHEN
         +  "\\u2E1A" // HYPHEN WITH DIAERESIS
         +  "\\u301C" // WAVE DASH
         +  "\\u3030" // WAVY DASH
         +  "\\u30A0" // KATAKANA-HIRAGANA DOUBLE HYPHEN
         +  "\\uFE31" // PRESENTATION FORM FOR VERTICAL EM DASH
         +  "\\uFE32" // PRESENTATION FORM FOR VERTICAL EN DASH
         +  "\\uFE58" // SMALL EM DASH
         +  "\\uFE63" // SMALL HYPHEN-MINUS
         +  "\\uFF0D" // FULLWIDTH HYPHEN-MINUS
         ;

	public final static String
	dash_charclass = "["  + dash_chars + "]"; /* \p{Dash} */
	
	public final static String
	not_dash_charclass = "[^" + dash_chars + "]"; /* \P{Dash} */

    /*
     * Because Java's \p{QMark} is missing.
     */

    private final static String
    quotation_mark_chars = ""    /* dummy empty string for homogeneity */
         +  "\\u0022"   // QUOTATION MARK
         +  "\\u0027"   // APOSTROPHE
         +  "\\u00AB"   // LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
         +  "\\u00BB"   // RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
         +  "\\u2018"   // LEFT SINGLE QUOTATION MARK
         +  "\\u2019"   // RIGHT SINGLE QUOTATION MARK
         +  "\\u201A"   // SINGLE LOW-9 QUOTATION MARK
         +  "\\u201B"   // SINGLE HIGH-REVERSED-9 QUOTATION MARK
         +  "\\u201C"   // LEFT DOUBLE QUOTATION MARK
         +  "\\u201D"   // RIGHT DOUBLE QUOTATION MARK
         +  "\\u201E"   // DOUBLE LOW-9 QUOTATION MARK
         +  "\\u201F"   // DOUBLE HIGH-REVERSED-9 QUOTATION MARK
         +  "\\u2039"   // SINGLE LEFT-POINTING ANGLE QUOTATION MARK
         +  "\\u203A"   // SINGLE RIGHT-POINTING ANGLE QUOTATION MARK
         +  "\\u300C"   // LEFT CORNER BRACKET
         +  "\\u300D"   // RIGHT CORNER BRACKET
         +  "\\u300E"   // LEFT WHITE CORNER BRACKET
         +  "\\u300F"   // RIGHT WHITE CORNER BRACKET
         +  "\\u301D"   // REVERSED DOUBLE PRIME QUOTATION MARK
         +  "\\u301E"   // DOUBLE PRIME QUOTATION MARK
         +  "\\u301F"   // LOW DOUBLE PRIME QUOTATION MARK
         +  "\\uFE41"   // PRESENTATION FORM FOR VERTICAL LEFT CORNER BRACKET
         +  "\\uFE42"   // PRESENTATION FORM FOR VERTICAL RIGHT CORNER BRACKET
         +  "\\uFE43"   // PRESENTATION FORM FOR VERTICAL LEFT WHITE CORNER BRACKET
         +  "\\uFE44"   // PRESENTATION FORM FOR VERTICAL RIGHT WHITE CORNER BRACKET
         +  "\\uFF02"   // FULLWIDTH QUOTATION MARK
         +  "\\uFF07"   // FULLWIDTH APOSTROPHE
         +  "\\uFF62"   // HALFWIDTH LEFT CORNER BRACKET
         +  "\\uFF63"   // HALFWIDTH RIGHT CORNER BRACKET
         ;

	public final static String quotation_mark_charclass =  "["  + quotation_mark_chars + "]";
	
	public final static String not_quotation_mark_charclass = "[^" + quotation_mark_chars + "]";

    private final static String
    apostrophic_chars =  ""        /* dummy empty string for homogeneity */
         +  "\\u0027"   // APOSTROPHE
         +  "\\u02BC"   // MODIFIER LETTER APOSTROPHE
         +  "\\u2019"   // RIGHT SINGLE QUOTATION MARK
         ;

	public final static String apostrophic_charclass =  "[" + apostrophic_chars + "]";
	
	public final static String not_apostrophic_charclass = "[^" + apostrophic_chars + "]";
	
    private final static String natural_word_chars = alphabetic_chars + apostrophic_chars + dash_chars;

	public final static String natural_word_charclass  =  "["  + natural_word_chars + "]";

	public final static String not_natural_word_charclass =  "[^" + natural_word_chars + "]";

    private final static String
    vertical_whitespace_chars = ""   /* \v */
         + "\\u000A"     // LINE FEED (LF)
         + "\\u000B"     // LINE TABULATION
         + "\\u000C"     // FORM FEED (FF)
         + "\\u000D"     // CARRIAGE RETURN (CR)
         + "\\u0085"     // NEXT LINE (NEL)
         + "\\u2028"     // LINE SEPARATOR
         + "\\u2029"     // PARAGRAPH SEPARATOR
         ;

	public final static String vertical_whitespace_charclass = "[" + vertical_whitespace_chars + "]";
	
	public final static String not_vertical_whitespace_charclass = "[^" + vertical_whitespace_chars + "]";

    private final static String
    horizontal_whitespace_chars = ""
         + "\\u0009"    // CHARACTER TABULATION
         + "\\u0020"    // SPACE
         + "\\u00A0"    // NO-BREAK SPACE
         + "\\u1680"    // OGHAM SPACE MARK
         + "\\u180E"    // MONGOLIAN VOWEL SEPARATOR
         + "\\u2000"    // EN QUAD
         + "\\u2001"    // EM QUAD
         + "\\u2002"    // EN SPACE
         + "\\u2003"    // EM SPACE
         + "\\u2004"    // THREE-PER-EM SPACE
         + "\\u2005"    // FOUR-PER-EM SPACE
         + "\\u2006"    // SIX-PER-EM SPACE
         + "\\u2007"    // FIGURE SPACE
         + "\\u2008"    // PUNCTUATION SPACE
         + "\\u2009"    // THIN SPACE
         + "\\u200A"    // HAIR SPACE
         + "\\u202F"    // NARROW NO-BREAK SPACE
         + "\\u205F"    // MEDIUM MATHEMATICAL SPACE
         + "\\u3000"    // IDEOGRAPHIC SPACE
         ;

	public final static String
	horizontal_whitespace_charclass = "[" + horizontal_whitespace_chars + "]";
	
	public final static String not_horizontal_whitespace_charclass = "[^" + horizontal_whitespace_chars + "]";
	
	public final static String
	linebreak = "(?:"                            /* \R */
	          +      "(?>\\u000D\\u000A)"
	          +   "|"
	          +      vertical_whitespace_charclass
	          + ")";

	public final static String legacy_grapheme_cluster = "(?>\\PM\\pM*)";   /* old \X */

    /*
     * Extended Grapheme Cluster rules from
     *      http://www.unicode.org/reports/tr29/#Default_Grapheme_Cluster_Table
     *
     *  EGC = ( CR LF )
     *    | ( Prepend*
     *        ( L+ | (L* ( ( V | LV ) V* | LVT ) T*) | T+ | [^ Control CR LF ] )
     *        ( Extend | SpacingMark )*
     *       )
     *    | .
     *
     */

    private final static String
    GCB_CR = "\\u000D";         // CARRIAGE RETURN (CR)

    private final static String
    GCB_LF = "\\u000A";         // LINE FEED (LF)

    private final static String
    GCB_CRLF = GCB_CR + GCB_LF;

     /*
      * % unichars -ua '[\p{Zl}\p{Zp}\p{Cc}\p{Cf}]' '[^\x{000D}\x{000A}\x{200C}\x{200D}]' | wc -l
      * 203
      */
    private final static String
    GCB_Control = "["
        + "\\p{Zl}"             // Line Separator
        + "\\p{Zp}"             // Paragraph Separator
        + "\\p{Cc}"             // Control
        + "\\p{Cf}"             // Format
        + "&&[^"                //    and not
        +       "\\u000D"       // CARRIAGE RETURN (CR)
        +       "\\u000A"       // LINE FEED (LF)
        +       "\\u200C"       // ZERO WIDTH NON-JOINER
        +       "\\u200D"       // ZERO WIDTH JOINER
        + "]]";

    /*
     * % unichars -u '\p{Grapheme_Extend = true}'|wc -l
     *    925
     */
    private final static String
    GCB_Extend = "["
        + "\\p{Mn}"      // Nonspacing_Mark
        + "\\p{Me}"      // Enclosing_Mark
        + "\\u200C"     // ZERO WIDTH NON-JOINER
        + "\\u200D"     // ZERO WIDTH JOINER
    // plus a few Spacing_Marks needed for canonical equivalence.
        + "\\u0488"     // COMBINING CYRILLIC HUNDRED THOUSANDS SIGN
        + "\\u0489"     // COMBINING CYRILLIC MILLIONS SIGN
        + "\\u20DD"     // COMBINING ENCLOSING CIRCLE
        + "\\u20DE"     // COMBINING ENCLOSING SQUARE
        + "\\u20DF"     // COMBINING ENCLOSING DIAMOND
        + "\\u20E0"     // COMBINING ENCLOSING CIRCLE BACKSLASH
        + "\\u20E2"     // COMBINING ENCLOSING SCREEN
        + "\\u20E3"     // COMBINING ENCLOSING KEYCAP
        + "\\u20E4"     // COMBINING ENCLOSING UPWARD POINTING TRIANGLE
        + "\\uA670"     // COMBINING CYRILLIC TEN MILLIONS SIGN
        + "\\uA671"     // COMBINING CYRILLIC HUNDRED MILLIONS SIGN
        + "\\uA672"     // COMBINING CYRILLIC THOUSAND MILLIONS SIGN
        + "\\uFF9E"     // HALFWIDTH KATAKANA VOICED SOUND MARK
        + "\\uFF9F"     // HALFWIDTH KATAKANA SEMI-VOICED SOUND MARK
        + "]";

    private final static String
    GCB_Prepend = "["
        + "\\u0E40"     // THAI CHARACTER SARA E
        + "\\u0E41"     // THAI CHARACTER SARA AE
        + "\\u0E42"     // THAI CHARACTER SARA O
        + "\\u0E43"     // THAI CHARACTER SARA AI MAIMUAN
        + "\\u0E44"     // THAI CHARACTER SARA AI MAIMALAI
        + "\\u0EC0"     // LAO VOWEL SIGN E
        + "\\u0EC1"     // LAO VOWEL SIGN EI
        + "\\u0EC2"     // LAO VOWEL SIGN O
        + "\\u0EC3"     // LAO VOWEL SIGN AY
        + "\\u0EC4"     // LAO VOWEL SIGN AI
        + "\\uAAB5"     // TAI VIET VOWEL E
        + "\\uAAB6"     // TAI VIET VOWEL O
        + "\\uAAB9"     // TAI VIET VOWEL UEA
        + "\\uAABB"     // TAI VIET VOWEL AUE
        + "\\uAABC"     // TAI VIET VOWEL AY
        + "]";

    private final static String
    GCB_Spacing_Mark = "["
        + "\\p{Mc}"       // Spacing_Mark
        + "\\u0E30"     // THAI CHARACTER SARA A
        + "\\u0E32"     // THAI CHARACTER SARA AA
        + "\\u0E33"     // THAI CHARACTER SARA AM
        + "\\u0E45"     // THAI CHARACTER LAKKHANGYAO
        + "\\u0EB0"     // LAO VOWEL SIGN A
        + "\\u0EB2"     // LAO VOWEL SIGN AA
        + "\\u0EB3"     // LAO VOWEL SIGN AM
        + ""            // XXX: MISSING!
        /*
         XXX: * too big to enumerate Grapheme_Cluster_Break != Extend
         *  % unichars -au '\p{Mc}' '\P{Grapheme_Cluster_Break=Extend}' | wc -l
         *       268
         */
        + "]";

    /*
     * L        Hangul_Syllable_Type=L, that is:
     *     U+1100 HANGUL CHOSEONG KIYEOK
     *     .. U+115F HANGUL CHOSEONG FILLER
     *     U+A960 HANGUL CHOSEONG TIKEUT-MIEUM
     *     ..U+A97C HANGUL CHOSEONG SSANGYEORINHIEUH
     *
     * % unichars -ua '\p{Hangul_Syllable_Type=L}' | wc -l
     *      125
     */
    private final static String
    GCB_L = "[\\u1100-\\u115F\\uA960-\\uA97C]";

    /*
     * V        Hangul_Syllable_Type=V, that is:
     *     U+1160 HANGUL JUNGSEONG FILLER
     *     ..U+11A2 HANGUL JUNGSEONG SSANGARAEA
     *     U+D7B0 HANGUL JUNGSEONG O-YEO
     *     ..U+D7C6 HANGUL JUNGSEONG ARAEA-E
     *
     * % unichars -ua '\p{Hangul_Syllable_Type=V}' | wc -l
     *       95
     */
    private final static String
    GCB_V = "[\\u1160-\\u11A2\\uD7B0-\\uD7C6]";


    /*
     * T        Hangul_Syllable_Type=T, that is:
     *     U+11A8 HANGUL JONGSEONG KIYEOK
     *     ..U+11F9 HANGUL JONGSEONG YEORINHIEUH
     *     U+D7CB HANGUL JONGSEONG NIEUN-RIEUL
     *     ..U+D7FB HANGUL JONGSEONG PHIEUPH-THIEUTH
     *
     * % unichars -ua '\p{Hangul_Syllable_Type=T}' | wc -l
     *      137
     */
    private final static String
    GCB_T = "[\\u11A8-\\u11F9\\uD7CB-\\uD7FB]";


    /*
     * LV       Hangul_Syllable_Type=LV, that is:
     *   U+AC00 HANGUL SYLLABLE GA
     *   U+AC1C HANGUL SYLLABLE GAE
     *   U+AC38 HANGUL SYLLABLE GYA
     *   ...
     */
    private final static String
    GCB_LV = "["
        + "\\uAC00"     // HANGUL SYLLABLE GA
        + "\\uAC1C"     // HANGUL SYLLABLE GAE
        + "\\uAC38"     // HANGUL SYLLABLE GYA
        + ""            // XXX: MISSING!
    /*
     *  XXX: missing lots of them
     *  % unichars -ua '\p{Hangul_Syllable_Type=LV}' | wc -l
     *    399
     */
        + "]";

    /*
     * Hangul_Syllable_Type=LVT, that is:
     *     U+AC01 HANGUL SYLLABLE GAG
     *     U+AC02 HANGUL SYLLABLE GAGG
     *     U+AC03 HANGUL SYLLABLE GAGS
     *     U+AC04 HANGUL SYLLABLE GAN
     *     ...
     */
    private final static String
    GCB_LVT = "["
        + "\\uAC01"     // HANGUL SYLLABLE GAG
        + "\\uAC02"     // HANGUL SYLLABLE GAGG
        + "\\uAC03"     // HANGUL SYLLABLE GAGS
        + "\\uAC04"     // HANGUL SYLLABLE GAN
        + ""            // XXX: MISSING!
    /*
     *  XXX: missing a *MYRIAD*
     *  % unichars -ua '\p{Hangul_Syllable_Type=LVT}' | wc -l
     *    10773
     */
        + "]";

    /*
     * WHEW! Now we're ready to build the ECG, which as I'm sure
     *       you have by now forgotten, goes this way:
     *
     *  EGC =   ( CR LF )
     *        | ( Prepend*
     *            ( L+ | (L* ( ( V | LV ) V* | LVT ) T*) | T+ | [^ Control CR LF ] )
     *            ( Extend | SpacingMark )*
     *          )
     *        | .
     *
     *
     *   Which breaks out like this:
     *
     *     # 1          EGC =  (
     *     # 2              ( CR LF )
     *     # 3            | ( Prepend*
     *     # 4                (
     *     # 5                      L+
     *     # 6                  |
     *     # 7                    (
     *     # 8                      L*
     *     # 9                      (
     *     #10                          ( V | LV ) V*
     *     #11                         | LVT
     *     #12                      )
     *     #13                      T*
     *     #14                    )
     *     #15                 | T+
     *     #16                 | [^ Control CR LF ]
     *     #17                )
     *     #18                ( Extend | SpacingMark )*
     *     #19               )
     *     #20            | .
     *     #21          )
     *
     * Which in turn corresponds to this:
     *
     */

	public final static String              /* new \X */
	extended_grapheme_cluster =
	        /* #01 */   "(?:"
	        /* #02 */ +       "(?:" + GCB_CRLF + ")"
	        /* #03 */ +    "|"
	        /* #03 */ +       "(?:"
	        /* #03 */ +            GCB_Prepend  + "*"
	        /* #04 */ +           "(?:"
	        /* #05 */ +                  GCB_L + "+"
	        /* #06 */ +                 "|"
	        /* #07 */ +                   "("
	        /* #08 */ +                       GCB_L + "*"
	        /* #09 */ +                       "("
	        /* #10 */ +                          "(?:[" + GCB_V + GCB_LV + "]"
	        /* #10 */ +                                                       GCB_V + "*"
	        /* #11 */ +                             "|" + GCB_LVT
	        /* #12 */ +                          ")"
	        /* #13 */ +                           GCB_T + "*"
	        /* #14 */ +                     ")"
	        /* #14 */ +                   ")"
	        /* #15 */ +                 "|"
	        /* #15 */ +                  GCB_T + "+"
	        /* #16 */ +              "|"
	        /* #16 */ +                  "[^" + GCB_Control + GCB_CRLF + "]"
	        /* #17 */ +       ")"
	        /* #18 */ +       "[" + GCB_Extend + GCB_Spacing_Mark + "]*"
	        /* #19 */ +       ")"
	        /* #20 */ +     "|(?s:.)"
	        /* #21 */ +  ")"
	                  ;

	
	public static final String
	unicodefy(String oldstr) {

	    StringBuffer newstr; {
	        /*
	         * Collectively these 14 recognized escapes...
	         *
	         *   \w \W \s \S \v \V \h \H \d \D \b \B \X \R
	         *
	         * ...go from needing 2 chars each on avg to needing 99.
	         * So quickly count up backslashes, adding 100 chars
	         * to initial buffer size per backslash encountered.
	         *
	         * Don't worry about surrogates here.
	         * 
			 *      Return new copy of argument with these 14 escapes:
			 *
			 *        --   \s \S       \v \V       \h \H
			 *        --   \w \W       \b \B       \d \D
			 *        --   \X          \R
			 *
			 *      converted into equivalents that work with Unicode.
	         * 
	         */
	        int newlen = oldstr.length();
	        for (int i = 0; i < oldstr.length(); i++) {
	            if (oldstr.charAt(i) == '\\') {
	                newlen += 100;
	            }
	        }
	        newstr = new StringBuffer(newlen);
	    }

	    boolean saw_backslash = false;
	    boolean saw_open_bracket = false;
	    boolean saw_power = false;

	    for (int curpos = 0; curpos < oldstr.length(); curpos++) {
	        int curchar = oldstr.codePointAt(curpos);

	        if (oldstr.codePointAt(curpos) > Character.MAX_VALUE) {
	            curpos++; /****WE HATES UTF-16! WE HATES IT FOREVERSES!!!****/
	        }

	        if (!saw_backslash) {
	            if (curchar == '[') {
	            	saw_open_bracket = true;
	            }
	        }
	        if (!saw_backslash) {
	            if (curchar == '^') {
	            	saw_power = true;
	            }
	        }
	        if (!saw_backslash) {
	            if (curchar == ']') {
	            	if (saw_open_bracket) {
	            		saw_open_bracket = false;
	            		saw_power = false;
	            	}
	            }
	        }
	        
	        if (!saw_backslash) {
	            if (curchar == '\\') {
	                saw_backslash = true;
	            } else {
	                newstr.append(Character.toChars(curchar));
	            }
	            continue; /* for */
	        }

	        if (curchar == '\\') {
	            saw_backslash = false;
	            newstr.append("\\\\");
	            continue; /* for */
	        }

	        switch (curchar) {

	            case 'b':  newstr.append(boundary);
	                       break; /* switch */
	            case 'B':  newstr.append(not_boundary);
	                       break; /* switch */

	            case 'd':  newstr.append(digits_charclass);
	                       break; /* switch */
	            case 'D':  newstr.append(not_digits_charclass);
	                       break; /* switch */

	            case 'h':  newstr.append(horizontal_whitespace_charclass);
	                       break; /* switch */
	            case 'H':  newstr.append(not_horizontal_whitespace_charclass);
	                       break; /* switch */

	            case 'v':  newstr.append(vertical_whitespace_charclass);
	                       break; /* switch */
	            case 'V':  newstr.append(not_vertical_whitespace_charclass);
	                       break; /* switch */

	            case 'R':  newstr.append(linebreak);
	                       break; /* switch */

	            case 's':  newstr.append(whitespace_charclass);
	                       break; /* switch */
	            case 'S':  newstr.append(not_whitespace_charclass);
	                       break; /* switch */

	            case 'w':  if (saw_open_bracket && saw_power) { newstr.append(not_identifier_charclass); } else { newstr.append(identifier_charclass); }
	                       break; /* switch */
	            case 'W':  if (saw_open_bracket && saw_power) { newstr.append(identifier_charclass); } else { newstr.append(not_identifier_charclass); }
	                       break; /* switch */

	            case 'Y':  newstr.append(legacy_grapheme_cluster);
	                       break; /* switch */

	            case 'X':  newstr.append(extended_grapheme_cluster);
	                       break; /* switch */

	            default:   newstr.append('\\');
	                       newstr.append(Character.toChars(curchar));
	                       break; /* switch */

	        }
	        saw_backslash = false;
	    }

	    if (saw_backslash) {
	        /*
	         * Huh! An Unbackslashed backslash was the last character.
	         * Good luck with getting *that* past the regex compiler!
	         */
	        newstr.append('\\');
	    }

	    return newstr.toString();
	}
	
	public Regex(String regularExpression,int flags){
		pattern = Pattern.compile(regularExpression,flags | Pattern.UNICODE_CHARACTER_CLASS);
	}
	
	public Regex(String regularExpression){
		pattern = Pattern.compile(regularExpression,0 | Pattern.UNICODE_CHARACTER_CLASS);
	}
	
	public Regex(dict<String> translator,int flags){
		list<String> keys = x.list(translator.keys());
		this.replacements = x.dict();
		this.searches = x.dict();
		StringBuilder patternBuilder = new StringBuilder();
		for (tuple2<Integer,String> idx__key : x.enumerate(keys)) {
			patternBuilder.append("(?<g" + idx__key.value0 + ">" +idx__key.value1 + ")|");
			this.replacements.setAt("g"+idx__key.value0).value(translator.get(idx__key.value1));
			this.searches.setAt("g"+idx__key.value0).value(Pattern.compile(idx__key.value1,flags | Pattern.UNICODE_CHARACTER_CLASS));
		}
		pattern = Pattern.compile(x.String(patternBuilder.toString()).sliceTo(-1),flags | Pattern.UNICODE_CHARACTER_CLASS);
	}
		
	public String sub(String replacement,String string){
		return pattern.matcher(string).replaceAll(replacement);
	}
	public str sub(String replacement,str str){
		return x.str(sub(replacement,str.toString()));
	}
	public Function<Object,String> sub(final String replacement) {
		return new Function<Object,String> () {
			public String apply(Object string) {
				return sub(replacement, string.toString());
			} 
		};
	}
	
	public String sub(Function<Match,String> replacer,String string){
		int currentIndex = 0;
		StringBuilder resultString = new StringBuilder();
		for(Match m:findIter(string)){
			resultString.append(x.String(string).slice(currentIndex,m.start(0)));
			resultString.append(replacer.apply(m));
			currentIndex = m.end(0);
		}
		resultString.append(x.String(string).sliceFrom(currentIndex));
		return resultString.toString();
	}
	public str sub(Function<Match,String> replacer,str str){
		return x.str(sub(replacer,str.toString()));
	}
	
	public String sub(dict<String> translator,String string){
		int currentIndex = 0;
		StringBuilder resultString = new StringBuilder();
		for(Match m:findIter(string)){
			resultString.append(x.String(string).slice(currentIndex,m.start(0)));
			try{
				resultString.append(translator.get(m.group(0)));	
			}catch(Exception e){
				resultString.append(m.group(0));
			}
			currentIndex = m.end(0);
		}
		resultString.append(x.String(string).sliceFrom(currentIndex));
		return resultString.toString();
	}
	public str sub(dict<String> translator,str str){
		return x.str(sub(translator,str.toString()));
	}
	
	public String translate(String string){
		int currentIndex = 0;
		StringBuilder resultString = new StringBuilder();
		for(Match m:findIter(string)){
			resultString.append(x.String(string).slice(currentIndex,m.start(0)));
			for(String groupName : m.groupDict){
				//if(groupName.equals("g0")){
				//	continue;
				//}
				String groupMatch = m.group(groupName);
				if(groupMatch != null){
					if (x.String("$").in(this.replacements.get(groupName))) {
						resultString.append(this.searches.get(groupName).matcher(m.groupDict.get(groupName)).replaceAll(this.replacements.get(groupName)));
					} else {
						resultString.append(this.replacements.get(groupName));
					}	
					currentIndex = m.end(0);		
				}
			}
		}
		resultString.append(x.String(string).sliceFrom(currentIndex));
		return resultString.toString();
	}
	
	public str translate(str str){
		return x.str(translate(str.toString()));
	}
	
	/**
	 * a shortcut for {@link Regex#sub(String, String)}(emptyString, string)
	 * @param string	a {@link String} in which the pattern will be searched and, 
	 * 					if found, then deleted 
	 * @return			the copy of the input string in which all the matches of the 
	 * 					pattern have been deleted 
	 */
	public String clean(String string){
		return sub("", string);
	}
	
	/**
	 * a shortcut for {@link Regex#sub(String, str)}(emptyString, str)
	 * @param str	a {@link str} in which the pattern will be searched and,
	 * 				if found, then deleted
	 * @return		the copy of the input str in which all the matches of the 
	 * 				pattern have been deleted
	 */
	public str clean(str str){
		return sub("", str);
	}
	
	public MatchIterator findIter(String string){
		Matcher m = pattern.matcher(string);
		return new MatchIterator(m);
	}
	public MatchIterator findIter(str str){
		return findIter(str.toString());
	}
	
	public Match find(String string){
		Matcher m = pattern.matcher(string);
		if(m.find()){
			return new Match(m);	
		}else{
			return null;
		}
	}
	
	private Match findFrom(String string, int startIndex){
		Matcher m = pattern.matcher(string);
		if(m.find()){
			return new Match(m, startIndex);	
		}else{
			return null;
		}
	}
	
	private Match findFrom(str str, int startIndex){
		return findFrom(str.toString(), startIndex);
	}
	
	public Match find(str str){
		return find(str.toString());
	}
	
	public Match find(String string, int startIndex){
		return findFrom(x.String(string).sliceFrom(startIndex), startIndex);
	}
	public Match find(str str, int startIndex){
		return findFrom(str.sliceFrom(startIndex), startIndex);
	}
	
	public Match find(String string, int startIndex, int endIndex){
		return findFrom(x.String(string).slice(startIndex, endIndex), startIndex);
	}
	public Match find(str str, int startIndex, int endIndex){
		return findFrom(str.slice(startIndex, endIndex), startIndex);
	}
	
	public list<String> split(String string,int limit){
		return x.list(pattern.split(string,limit));
	}
	public list<str> split(str str,int limit){
		list<String> listOfStrings = x.list(pattern.split(str.toString(),limit));
		list<str> listOfStrs = x.<str>list();
		for (String string : listOfStrings){
			listOfStrs = listOfStrs.append(x.str(string));
		}
		return listOfStrs;
	}
	public list<String> split(String string){
		return split(string,Integer.MAX_VALUE);
	}
	public list<str> split(str str){
		return split(str,Integer.MAX_VALUE);
	}
	
	public list<String> findAll(String string){
		Matcher m = pattern.matcher(string);
		list<String> listOfStringMatches = x.list();
		while (m.find()) {
			listOfStringMatches = listOfStringMatches.append(m.group(0));
		}
		return listOfStringMatches;
	}
	public list<str> findAll(str str){
		String stringInput = str.toString();
		list<String> lstStrings = findAll(stringInput);
		list<str> listStrs = x.<str>list();
		for (String string : lstStrings){
			listStrs = listStrs.append(x.str(string));
		}
		return listStrs;
	}
	
	@Override
	public String toString(){
		return pattern.pattern();
	}
}
