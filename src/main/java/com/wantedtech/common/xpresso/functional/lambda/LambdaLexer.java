// Generated from Lambda.g by ANTLR 4.5

package com.wantedtech.common.xpresso.functional.lambda;

import java.util.*;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.x;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LambdaLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, FTOK=14, And=15, Or=16, Identifier=17, 
		Number=18, JavaString=19, PythonString=20, Space=21, OPENBRACKET=22, CLOSEDBRACKET=23, 
		WS=24;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "FTOK", "And", "Or", "Identifier", 
		"Number", "JavaString", "PythonString", "Space", "OPENBRACKET", "CLOSEDBRACKET", 
		"WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "':'", "'+'", "'-'", "'='", "'<='", "'<'", "'>='", "'>'", "'!'", 
		"'('", "')'", "'*'", "'/'", null, null, null, null, null, null, null, 
		"' '", "'['", "']'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "FTOK", "And", "Or", "Identifier", "Number", "JavaString", 
		"PythonString", "Space", "OPENBRACKET", "CLOSEDBRACKET", "WS"
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



	    Value inputValue;
	    Function<Object,?>[] inputFunction;
	    String inputVarName;



	public LambdaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Lambda.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\32\u0096\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b"+
		"\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\6\17R\n"+
		"\17\r\17\16\17S\3\20\3\20\3\20\3\21\3\21\3\21\3\22\6\22]\n\22\r\22\16"+
		"\22^\3\22\7\22b\n\22\f\22\16\22e\13\22\3\23\6\23h\n\23\r\23\16\23i\3\23"+
		"\3\23\6\23n\n\23\r\23\16\23o\5\23r\n\23\3\24\3\24\7\24v\n\24\f\24\16\24"+
		"y\13\24\3\24\3\24\3\25\3\25\3\25\3\25\7\25\u0081\n\25\f\25\16\25\u0084"+
		"\13\25\3\25\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\6\31\u0091"+
		"\n\31\r\31\16\31\u0092\3\31\3\31\4w\u0082\2\32\3\3\5\4\7\5\t\6\13\7\r"+
		"\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27-\30/\31\61\32\3\2\6\3\2\62;\4\2C\\c|\5\2\62;C\\c|\4\2\13\f\17"+
		"\17\u009e\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2"+
		"\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\3\63\3\2\2\2\5\65\3\2\2\2\7\67\3\2\2\2\t9\3\2"+
		"\2\2\13;\3\2\2\2\r>\3\2\2\2\17@\3\2\2\2\21C\3\2\2\2\23E\3\2\2\2\25G\3"+
		"\2\2\2\27I\3\2\2\2\31K\3\2\2\2\33M\3\2\2\2\35O\3\2\2\2\37U\3\2\2\2!X\3"+
		"\2\2\2#\\\3\2\2\2%g\3\2\2\2\'s\3\2\2\2)|\3\2\2\2+\u0089\3\2\2\2-\u008b"+
		"\3\2\2\2/\u008d\3\2\2\2\61\u0090\3\2\2\2\63\64\7<\2\2\64\4\3\2\2\2\65"+
		"\66\7-\2\2\66\6\3\2\2\2\678\7/\2\28\b\3\2\2\29:\7?\2\2:\n\3\2\2\2;<\7"+
		">\2\2<=\7?\2\2=\f\3\2\2\2>?\7>\2\2?\16\3\2\2\2@A\7@\2\2AB\7?\2\2B\20\3"+
		"\2\2\2CD\7@\2\2D\22\3\2\2\2EF\7#\2\2F\24\3\2\2\2GH\7*\2\2H\26\3\2\2\2"+
		"IJ\7+\2\2J\30\3\2\2\2KL\7,\2\2L\32\3\2\2\2MN\7\61\2\2N\34\3\2\2\2OQ\7"+
		"h\2\2PR\t\2\2\2QP\3\2\2\2RS\3\2\2\2SQ\3\2\2\2ST\3\2\2\2T\36\3\2\2\2UV"+
		"\7(\2\2VW\7(\2\2W \3\2\2\2XY\7~\2\2YZ\7~\2\2Z\"\3\2\2\2[]\t\3\2\2\\[\3"+
		"\2\2\2]^\3\2\2\2^\\\3\2\2\2^_\3\2\2\2_c\3\2\2\2`b\t\4\2\2a`\3\2\2\2be"+
		"\3\2\2\2ca\3\2\2\2cd\3\2\2\2d$\3\2\2\2ec\3\2\2\2fh\4\62;\2gf\3\2\2\2h"+
		"i\3\2\2\2ig\3\2\2\2ij\3\2\2\2jq\3\2\2\2km\7\60\2\2ln\4\62;\2ml\3\2\2\2"+
		"no\3\2\2\2om\3\2\2\2op\3\2\2\2pr\3\2\2\2qk\3\2\2\2qr\3\2\2\2r&\3\2\2\2"+
		"sw\7$\2\2tv\4\2\0\2ut\3\2\2\2vy\3\2\2\2wx\3\2\2\2wu\3\2\2\2xz\3\2\2\2"+
		"yw\3\2\2\2z{\7$\2\2{(\3\2\2\2|}\7)\2\2}~\7)\2\2~\u0082\7)\2\2\177\u0081"+
		"\4\2\0\2\u0080\177\3\2\2\2\u0081\u0084\3\2\2\2\u0082\u0083\3\2\2\2\u0082"+
		"\u0080\3\2\2\2\u0083\u0085\3\2\2\2\u0084\u0082\3\2\2\2\u0085\u0086\7)"+
		"\2\2\u0086\u0087\7)\2\2\u0087\u0088\7)\2\2\u0088*\3\2\2\2\u0089\u008a"+
		"\7\"\2\2\u008a,\3\2\2\2\u008b\u008c\7]\2\2\u008c.\3\2\2\2\u008d\u008e"+
		"\7_\2\2\u008e\60\3\2\2\2\u008f\u0091\t\5\2\2\u0090\u008f\3\2\2\2\u0091"+
		"\u0092\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0094\3\2"+
		"\2\2\u0094\u0095\b\31\2\2\u0095\62\3\2\2\2\f\2S^cioqw\u0082\u0092\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}