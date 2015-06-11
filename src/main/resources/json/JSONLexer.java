// Generated from JSON.g by ANTLR 4.5

package com.wantedtech.common.xpresso.json2;

import java.util.regex.Pattern;
  

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JSONLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, Number=6, Exponent=7, String=8, 
		WS=9, COMMA=10, TRUE=11, FALSE=12, NULL=13;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "Number", "Exponent", "String", 
		"WS", "EscapeSequence", "UnicodeEscape", "HexDigit", "Digit", "COMMA", 
		"TRUE", "FALSE", "NULL"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'['", "']'", "':'", null, null, null, null, "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "Number", "Exponent", "String", "WS", 
		"COMMA", "TRUE", "FALSE", "NULL"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	 
	protected void mismatch(IntStream input, int ttype, BitSet follow) 
	throws RecognitionException 
	{ 
	throw new MismatchedTokenException(ttype, input); 
	} 
	public Object recoverFromMismatchedSet(IntStream input, 
	RecognitionException e, 
	BitSet follow) 
	throws RecognitionException 
	{ 
	throw e; 
	} 


	public JSONLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "JSON.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\17|\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\5\7\61\n\7\3\7\6\7\64\n\7"+
		"\r\7\16\7\65\3\7\3\7\6\7:\n\7\r\7\16\7;\5\7>\n\7\3\b\3\b\5\bB\n\b\3\b"+
		"\3\b\7\bF\n\b\f\b\16\bI\13\b\3\t\3\t\3\t\7\tN\n\t\f\t\16\tQ\13\t\3\t\3"+
		"\t\3\n\6\nV\n\n\r\n\16\nW\3\n\3\n\3\13\3\13\3\13\5\13_\n\13\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\2\2\23\3\3\5\4\7"+
		"\5\t\6\13\7\r\b\17\t\21\n\23\13\25\2\27\2\31\2\33\2\35\f\37\r!\16#\17"+
		"\3\2\7\4\2GGgg\5\2\2!$$^^\5\2\13\f\17\17\"\"\13\2$$))\61\61^^ddhhpptt"+
		"vv\5\2\62;CHch\u0081\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\35\3"+
		"\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5\'\3\2\2\2\7)\3"+
		"\2\2\2\t+\3\2\2\2\13-\3\2\2\2\r\60\3\2\2\2\17?\3\2\2\2\21J\3\2\2\2\23"+
		"U\3\2\2\2\25[\3\2\2\2\27`\3\2\2\2\31f\3\2\2\2\33h\3\2\2\2\35j\3\2\2\2"+
		"\37l\3\2\2\2!q\3\2\2\2#w\3\2\2\2%&\7}\2\2&\4\3\2\2\2\'(\7\177\2\2(\6\3"+
		"\2\2\2)*\7]\2\2*\b\3\2\2\2+,\7_\2\2,\n\3\2\2\2-.\7<\2\2.\f\3\2\2\2/\61"+
		"\7/\2\2\60/\3\2\2\2\60\61\3\2\2\2\61\63\3\2\2\2\62\64\5\33\16\2\63\62"+
		"\3\2\2\2\64\65\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\66=\3\2\2\2\679\7\60"+
		"\2\28:\5\33\16\298\3\2\2\2:;\3\2\2\2;9\3\2\2\2;<\3\2\2\2<>\3\2\2\2=\67"+
		"\3\2\2\2=>\3\2\2\2>\16\3\2\2\2?A\t\2\2\2@B\7/\2\2A@\3\2\2\2AB\3\2\2\2"+
		"BC\3\2\2\2CG\4\63;\2DF\5\33\16\2ED\3\2\2\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2"+
		"\2H\20\3\2\2\2IG\3\2\2\2JO\7$\2\2KN\5\25\13\2LN\n\3\2\2MK\3\2\2\2ML\3"+
		"\2\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2\2\2PR\3\2\2\2QO\3\2\2\2RS\7$\2\2S\22"+
		"\3\2\2\2TV\t\4\2\2UT\3\2\2\2VW\3\2\2\2WU\3\2\2\2WX\3\2\2\2XY\3\2\2\2Y"+
		"Z\b\n\2\2Z\24\3\2\2\2[^\7^\2\2\\_\5\27\f\2]_\t\5\2\2^\\\3\2\2\2^]\3\2"+
		"\2\2_\26\3\2\2\2`a\7w\2\2ab\5\31\r\2bc\5\31\r\2cd\5\31\r\2de\5\31\r\2"+
		"e\30\3\2\2\2fg\t\6\2\2g\32\3\2\2\2hi\4\62;\2i\34\3\2\2\2jk\7.\2\2k\36"+
		"\3\2\2\2lm\7v\2\2mn\7t\2\2no\7w\2\2op\7g\2\2p \3\2\2\2qr\7h\2\2rs\7c\2"+
		"\2st\7n\2\2tu\7u\2\2uv\7g\2\2v\"\3\2\2\2wx\7p\2\2xy\7w\2\2yz\7n\2\2z{"+
		"\7n\2\2{$\3\2\2\2\r\2\60\65;=AGMOW^\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}