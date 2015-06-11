// Generated from JSON.g by ANTLR 4.5

package com.wantedtech.common.xpresso.json2;

import java.util.regex.Pattern;
  

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JSONParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, Number=6, Exponent=7, String=8, 
		WS=9, COMMA=10, TRUE=11, FALSE=12, NULL=13, STRING=14, NUMBER=15, OBJECT=16, 
		FIELD=17, ARRAY=18;
	public static final int
		RULE_value = 0, RULE_string = 1, RULE_number = 2, RULE_object = 3, RULE_array = 4, 
		RULE_elements = 5, RULE_members = 6, RULE_pair = 7;
	public static final String[] ruleNames = {
		"value", "string", "number", "object", "array", "elements", "members", 
		"pair"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'['", "']'", "':'", null, null, null, null, "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "Number", "Exponent", "String", "WS", 
		"COMMA", "TRUE", "FALSE", "NULL", "STRING", "NUMBER", "OBJECT", "FIELD", 
		"ARRAY"
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

	@Override
	public String getGrammarFileName() { return "JSON.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	 
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

	public JSONParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ValueContext extends ParserRuleContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public ObjectContext object() {
			return getRuleContext(ObjectContext.class,0);
		}
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public TerminalNode TRUE() { return getToken(JSONParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(JSONParser.FALSE, 0); }
		public TerminalNode NULL() { return getToken(JSONParser.NULL, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_value);
		try {
			setState(23);
			switch (_input.LA(1)) {
			case String:
				enterOuterAlt(_localctx, 1);
				{
				setState(16);
				string();
				}
				break;
			case Number:
				enterOuterAlt(_localctx, 2);
				{
				setState(17);
				number();
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 3);
				{
				setState(18);
				object();
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 4);
				{
				setState(19);
				array();
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 5);
				{
				setState(20);
				match(TRUE);
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 6);
				{
				setState(21);
				match(FALSE);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 7);
				{
				setState(22);
				match(NULL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public TerminalNode String() { return getToken(JSONParser.String, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitString(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			match(String);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public Token n;
		public TerminalNode Number() { return getToken(JSONParser.Number, 0); }
		public TerminalNode Exponent() { return getToken(JSONParser.Exponent, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			((NumberContext)_localctx).n = match(Number);
			setState(28);
			if (!(Pattern.matches("(0|(-?[1-9]\\d*))(\\.\\d+)?", n.getText()))) throw new FailedPredicateException(this, "Pattern.matches(\"(0|(-?[1-9]\\\\d*))(\\\\.\\\\d+)?\", n.getText())");
			setState(30);
			_la = _input.LA(1);
			if (_la==Exponent) {
				{
				setState(29);
				match(Exponent);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectContext extends ParserRuleContext {
		public MembersContext members() {
			return getRuleContext(MembersContext.class,0);
		}
		public ObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitObject(this);
		}
	}

	public final ObjectContext object() throws RecognitionException {
		ObjectContext _localctx = new ObjectContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_object);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			match(T__0);
			setState(34);
			_la = _input.LA(1);
			if (_la==String) {
				{
				setState(33);
				members();
				}
			}

			setState(36);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayContext extends ParserRuleContext {
		public ElementsContext elements() {
			return getRuleContext(ElementsContext.class,0);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitArray(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_array);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(T__2);
			setState(40);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << Number) | (1L << String) | (1L << TRUE) | (1L << FALSE) | (1L << NULL))) != 0)) {
				{
				setState(39);
				elements();
				}
			}

			setState(42);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementsContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JSONParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JSONParser.COMMA, i);
		}
		public ElementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterElements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitElements(this);
		}
	}

	public final ElementsContext elements() throws RecognitionException {
		ElementsContext _localctx = new ElementsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_elements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			value();
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(45);
				match(COMMA);
				setState(46);
				value();
				}
				}
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MembersContext extends ParserRuleContext {
		public List<PairContext> pair() {
			return getRuleContexts(PairContext.class);
		}
		public PairContext pair(int i) {
			return getRuleContext(PairContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JSONParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JSONParser.COMMA, i);
		}
		public MembersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_members; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterMembers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitMembers(this);
		}
	}

	public final MembersContext members() throws RecognitionException {
		MembersContext _localctx = new MembersContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_members);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			pair();
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(53);
				match(COMMA);
				setState(54);
				pair();
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairContext extends ParserRuleContext {
		public TerminalNode String() { return getToken(JSONParser.String, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public PairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterPair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitPair(this);
		}
	}

	public final PairContext pair() throws RecognitionException {
		PairContext _localctx = new PairContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_pair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(String);
			setState(61);
			match(T__4);
			setState(62);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return number_sempred((NumberContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean number_sempred(NumberContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return Pattern.matches("(0|(-?[1-9]\\d*))(\\.\\d+)?", n.getText());
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\24C\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\5\2\32\n\2\3\3\3\3\3\4\3\4\3\4\5\4!\n\4\3\5\3\5\5\5%\n\5\3\5"+
		"\3\5\3\6\3\6\5\6+\n\6\3\6\3\6\3\7\3\7\3\7\7\7\62\n\7\f\7\16\7\65\13\7"+
		"\3\b\3\b\3\b\7\b:\n\b\f\b\16\b=\13\b\3\t\3\t\3\t\3\t\3\t\2\2\n\2\4\6\b"+
		"\n\f\16\20\2\2E\2\31\3\2\2\2\4\33\3\2\2\2\6\35\3\2\2\2\b\"\3\2\2\2\n("+
		"\3\2\2\2\f.\3\2\2\2\16\66\3\2\2\2\20>\3\2\2\2\22\32\5\4\3\2\23\32\5\6"+
		"\4\2\24\32\5\b\5\2\25\32\5\n\6\2\26\32\7\r\2\2\27\32\7\16\2\2\30\32\7"+
		"\17\2\2\31\22\3\2\2\2\31\23\3\2\2\2\31\24\3\2\2\2\31\25\3\2\2\2\31\26"+
		"\3\2\2\2\31\27\3\2\2\2\31\30\3\2\2\2\32\3\3\2\2\2\33\34\7\n\2\2\34\5\3"+
		"\2\2\2\35\36\7\b\2\2\36 \6\4\2\2\37!\7\t\2\2 \37\3\2\2\2 !\3\2\2\2!\7"+
		"\3\2\2\2\"$\7\3\2\2#%\5\16\b\2$#\3\2\2\2$%\3\2\2\2%&\3\2\2\2&\'\7\4\2"+
		"\2\'\t\3\2\2\2(*\7\5\2\2)+\5\f\7\2*)\3\2\2\2*+\3\2\2\2+,\3\2\2\2,-\7\6"+
		"\2\2-\13\3\2\2\2.\63\5\2\2\2/\60\7\f\2\2\60\62\5\2\2\2\61/\3\2\2\2\62"+
		"\65\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\r\3\2\2\2\65\63\3\2\2\2\66"+
		";\5\20\t\2\678\7\f\2\28:\5\20\t\29\67\3\2\2\2:=\3\2\2\2;9\3\2\2\2;<\3"+
		"\2\2\2<\17\3\2\2\2=;\3\2\2\2>?\7\n\2\2?@\7\7\2\2@A\5\2\2\2A\21\3\2\2\2"+
		"\b\31 $*\63;";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}