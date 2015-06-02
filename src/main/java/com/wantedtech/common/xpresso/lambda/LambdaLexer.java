// Generated from Lambda.g by ANTLR 4.5

package com.wantedtech.common.xpresso.lambda;

import java.util.*;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;

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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, True=7, False=8, Equals=9, 
		LessOrEquals=10, Less=11, MoreOrEquals=12, More=13, NotEquals=14, And=15, 
		Or=16, SimpleType=17, ComplexType=18, FTOKEN=19, PTOKEN=20, OPENPAR=21, 
		CLOSEDPAR=22, Identifier=23, Number=24, String=25, Space=26, OPENBRACKET=27, 
		CLOSEDBRACKET=28, WS=29;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "True", "False", "Equals", 
		"LessOrEquals", "Less", "MoreOrEquals", "More", "NotEquals", "And", "Or", 
		"SimpleType", "ComplexType", "FTOKEN", "PTOKEN", "OPENPAR", "CLOSEDPAR", 
		"Identifier", "Number", "String", "Space", "OPENBRACKET", "CLOSEDBRACKET", 
		"WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "':'", "'!'", "'+'", "'-'", "'*'", "'/'", null, null, null, null, 
		"'<'", null, "'>'", null, null, null, null, null, null, null, "'('", "')'", 
		null, null, null, "' '", "'['", "']'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "True", "False", "Equals", "LessOrEquals", 
		"Less", "MoreOrEquals", "More", "NotEquals", "And", "Or", "SimpleType", 
		"ComplexType", "FTOKEN", "PTOKEN", "OPENPAR", "CLOSEDPAR", "Identifier", 
		"Number", "String", "Space", "OPENBRACKET", "CLOSEDBRACKET", "WS"
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
	    Predicate<Object>[] inputPredicate;



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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\37\u00c2\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\17"+
		"\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22z\n\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0085\n\23\3\24\3\24\3\24\3\25\3\25"+
		"\3\25\3\26\3\26\3\27\3\27\3\30\6\30\u0092\n\30\r\30\16\30\u0093\3\30\7"+
		"\30\u0097\n\30\f\30\16\30\u009a\13\30\3\31\6\31\u009d\n\31\r\31\16\31"+
		"\u009e\3\31\3\31\6\31\u00a3\n\31\r\31\16\31\u00a4\5\31\u00a7\n\31\3\32"+
		"\3\32\3\32\3\32\7\32\u00ad\n\32\f\32\16\32\u00b0\13\32\3\32\3\32\3\32"+
		"\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\6\36\u00bd\n\36\r\36\16\36\u00be"+
		"\3\36\3\36\3\u00ae\2\37\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f"+
		"\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63"+
		"\33\65\34\67\359\36;\37\3\2\26\3\2vv\3\2tt\3\2ww\3\2gg\3\2hh\3\2cc\3\2"+
		"nn\3\2uu\3\2kk\3\2pp\3\2ff\3\2qq\3\2dd\3\2UU\3\2ii\3\2rr\3\2\62;\4\2C"+
		"\\c|\5\2\62;C\\c|\4\2\13\f\17\17\u00cb\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2"+
		"\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2"+
		"\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3"+
		"\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3"+
		"\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65"+
		"\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\3=\3\2\2\2\5?\3\2\2\2\7A\3"+
		"\2\2\2\tC\3\2\2\2\13E\3\2\2\2\rG\3\2\2\2\17I\3\2\2\2\21N\3\2\2\2\23T\3"+
		"\2\2\2\25W\3\2\2\2\27Z\3\2\2\2\31\\\3\2\2\2\33_\3\2\2\2\35a\3\2\2\2\37"+
		"d\3\2\2\2!g\3\2\2\2#y\3\2\2\2%\u0084\3\2\2\2\'\u0086\3\2\2\2)\u0089\3"+
		"\2\2\2+\u008c\3\2\2\2-\u008e\3\2\2\2/\u0091\3\2\2\2\61\u009c\3\2\2\2\63"+
		"\u00a8\3\2\2\2\65\u00b5\3\2\2\2\67\u00b7\3\2\2\29\u00b9\3\2\2\2;\u00bc"+
		"\3\2\2\2=>\7<\2\2>\4\3\2\2\2?@\7#\2\2@\6\3\2\2\2AB\7-\2\2B\b\3\2\2\2C"+
		"D\7/\2\2D\n\3\2\2\2EF\7,\2\2F\f\3\2\2\2GH\7\61\2\2H\16\3\2\2\2IJ\t\2\2"+
		"\2JK\t\3\2\2KL\t\4\2\2LM\t\5\2\2M\20\3\2\2\2NO\t\6\2\2OP\t\7\2\2PQ\t\b"+
		"\2\2QR\t\t\2\2RS\t\5\2\2S\22\3\2\2\2TU\7?\2\2UV\7?\2\2V\24\3\2\2\2WX\7"+
		">\2\2XY\7?\2\2Y\26\3\2\2\2Z[\7>\2\2[\30\3\2\2\2\\]\7@\2\2]^\7?\2\2^\32"+
		"\3\2\2\2_`\7@\2\2`\34\3\2\2\2ab\7#\2\2bc\7?\2\2c\36\3\2\2\2de\7(\2\2e"+
		"f\7(\2\2f \3\2\2\2gh\7~\2\2hi\7~\2\2i\"\3\2\2\2jk\t\n\2\2kl\t\13\2\2l"+
		"z\t\2\2\2mn\t\f\2\2no\t\r\2\2op\t\4\2\2pq\t\16\2\2qr\t\b\2\2rz\t\5\2\2"+
		"st\t\17\2\2tu\t\2\2\2uv\t\3\2\2vw\t\n\2\2wx\t\13\2\2xz\t\20\2\2yj\3\2"+
		"\2\2ym\3\2\2\2ys\3\2\2\2z$\3\2\2\2{|\t\2\2\2|}\t\4\2\2}~\t\21\2\2~\177"+
		"\t\b\2\2\177\u0085\t\5\2\2\u0080\u0081\t\b\2\2\u0081\u0082\t\n\2\2\u0082"+
		"\u0083\t\t\2\2\u0083\u0085\t\2\2\2\u0084{\3\2\2\2\u0084\u0080\3\2\2\2"+
		"\u0085&\3\2\2\2\u0086\u0087\7h\2\2\u0087\u0088\t\22\2\2\u0088(\3\2\2\2"+
		"\u0089\u008a\7r\2\2\u008a\u008b\t\22\2\2\u008b*\3\2\2\2\u008c\u008d\7"+
		"*\2\2\u008d,\3\2\2\2\u008e\u008f\7+\2\2\u008f.\3\2\2\2\u0090\u0092\t\23"+
		"\2\2\u0091\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0091\3\2\2\2\u0093"+
		"\u0094\3\2\2\2\u0094\u0098\3\2\2\2\u0095\u0097\t\24\2\2\u0096\u0095\3"+
		"\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099"+
		"\60\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009d\4\62;\2\u009c\u009b\3\2\2"+
		"\2\u009d\u009e\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a6"+
		"\3\2\2\2\u00a0\u00a2\7\60\2\2\u00a1\u00a3\4\62;\2\u00a2\u00a1\3\2\2\2"+
		"\u00a3\u00a4\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a7"+
		"\3\2\2\2\u00a6\u00a0\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\62\3\2\2\2\u00a8"+
		"\u00a9\7)\2\2\u00a9\u00aa\7)\2\2\u00aa\u00ae\7)\2\2\u00ab\u00ad\13\2\2"+
		"\2\u00ac\u00ab\3\2\2\2\u00ad\u00b0\3\2\2\2\u00ae\u00af\3\2\2\2\u00ae\u00ac"+
		"\3\2\2\2\u00af\u00b1\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b1\u00b2\7)\2\2\u00b2"+
		"\u00b3\7)\2\2\u00b3\u00b4\7)\2\2\u00b4\64\3\2\2\2\u00b5\u00b6\7\"\2\2"+
		"\u00b6\66\3\2\2\2\u00b7\u00b8\7]\2\2\u00b88\3\2\2\2\u00b9\u00ba\7_\2\2"+
		"\u00ba:\3\2\2\2\u00bb\u00bd\t\25\2\2\u00bc\u00bb\3\2\2\2\u00bd\u00be\3"+
		"\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0"+
		"\u00c1\b\36\2\2\u00c1<\3\2\2\2\f\2y\u0084\u0093\u0098\u009e\u00a4\u00a6"+
		"\u00ae\u00be\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}