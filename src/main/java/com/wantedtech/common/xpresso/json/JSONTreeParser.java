// Generated from JSONTree.g by ANTLR 4.5

package com.wantedtech.common.xpresso.json;

import com.wantedtech.common.xpresso.types.*;
import com.wantedtech.common.xpresso.x;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;


import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JSONTreeParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, Number=6, Exponent=7, String=8, 
		WS=9, COMMA=10, TRUE=11, FALSE=12, NULL=13, STRING=14, NUMBER=15, OBJECT=16, 
		FIELD=17, ARRAY=18;
	public static final int
		RULE_value = 0, RULE_string = 1, RULE_object = 2, RULE_number = 3, RULE_array = 4, 
		RULE_elements = 5, RULE_members = 6, RULE_pair = 7;
	public static final String[] ruleNames = {
		"value", "string", "object", "number", "array", "elements", "members", 
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
	public String getGrammarFileName() { return "JSONTree.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    private Object extractNumber(String rawNumber, String rawExponent) {
	        String numberBody = rawNumber;
	        String exponent = (rawExponent == null) ? null : rawExponent.substring(1); // remove the 'e' prefix if there
	        boolean isReal = numberBody.indexOf('.') >= 0 || exponent != null;
	        if (!isReal) {
	            return new Integer(numberBody);
	        } else {
	            double result = Double.parseDouble(numberBody);
	            if (exponent != null) {
	                result = result * Math.pow(10.0f, Double.parseDouble(exponent));
	            }
	            return new Double(result);
	        }
	    }
	    
	    private String extractString(String rawString) {
	        // StringBuffers are an efficient way to modify strings
	        StringBuffer sb = new StringBuffer(rawString);
	        // Process character escapes
	        int startPoint = 1; // skip initial quotation mark
	        for (;;) {
	            int slashIndex = sb.indexOf("\\", startPoint); // search for a single backslash
	            if (slashIndex == -1) break;
	            // Else, we have a backslash
	            char escapeType = sb.charAt(slashIndex + 1);
	            switch (escapeType) {
	                case'u':
	                    // Unicode escape.
	                    String unicode = extractUnicode(sb, slashIndex);
	                    sb.replace(slashIndex, slashIndex + 6, unicode); // backspace
	                    break; // back to the loop

	                    // note: Java's character escapes match JSON's, which is why it looks like we're replacing
	                // "\b" with "\b". We're actually replacing 2 characters (slash-b) with one (backspace).
	                case 'b':
	                    sb.replace(slashIndex, slashIndex + 2, "\b"); // backspace
	                    break;

	                case 't':
	                    sb.replace(slashIndex, slashIndex + 2, "\t"); // tab
	                    break;

	                case 'n':
	                    sb.replace(slashIndex, slashIndex + 2, "\n"); // newline
	                    break;

	                case 'f':
	                    sb.replace(slashIndex, slashIndex + 2, "\f"); // form feed
	                    break;

	                case 'r':
	                    sb.replace(slashIndex, slashIndex + 2, "\r"); // return
	                    break;

	                case '\'':
	                    sb.replace(slashIndex, slashIndex + 2, "\'"); // single quote
	                    break;

	                case '\"':
	                    sb.replace(slashIndex, slashIndex + 2, "\""); // double quote
	                    break;

	                case '\\':
	                    sb.replace(slashIndex, slashIndex + 2, "\\"); // backslash
	                    break;
	                    
	                case '/':
	                    sb.replace(slashIndex, slashIndex + 2, "/"); // solidus
	                    break;

	            }
	            startPoint = slashIndex+1;

	        }

	        // remove surrounding quotes
	        sb.deleteCharAt(0);
	        sb.deleteCharAt(sb.length() - 1);

	        return sb.toString();
	    }

	    private String extractUnicode(StringBuffer sb, int slashIndex) {
	        // Gather the 4 hex digits, convert to an integer, translate the number to a unicode char, replace
	        String result;
	        String code = sb.substring(slashIndex + 2, slashIndex + 6);
	        int charNum = Integer.parseInt(code, 16); // hex to integer
	        // There's no simple way to go from an int to a unicode character.
	        // We'll have to pass this through an output stream writer to do
	        // the conversion.
	        try {
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            OutputStreamWriter osw = new OutputStreamWriter(baos, "UTF-8");
	            osw.write(charNum);
	            osw.flush();
	            result = baos.toString("UTF-8"); // Thanks to Silvester Pozarnik for the tip about adding "UTF-8" here
	        } catch (Exception e) {
	            e.printStackTrace();
	            result = null;
	        }
	        return result;
	    }


	public JSONTreeParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ValueContext extends ParserRuleContext {
		public Object result;
		public StringContext s;
		public NumberContext n;
		public ObjectContext o;
		public ArrayContext a;
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
		public TerminalNode TRUE() { return getToken(JSONTreeParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(JSONTreeParser.FALSE, 0); }
		public TerminalNode NULL() { return getToken(JSONTreeParser.NULL, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_value);
		try {
			setState(34);
			switch (_input.LA(1)) {
			case String:
				enterOuterAlt(_localctx, 1);
				{
				setState(16);
				((ValueContext)_localctx).s = string();
				 ((ValueContext)_localctx).result =  ((ValueContext)_localctx).s.result; 
				}
				break;
			case Number:
				enterOuterAlt(_localctx, 2);
				{
				setState(19);
				((ValueContext)_localctx).n = number();
				 ((ValueContext)_localctx).result =  ((ValueContext)_localctx).n.result; 
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 3);
				{
				setState(22);
				((ValueContext)_localctx).o = object();
				 ((ValueContext)_localctx).result =  ((ValueContext)_localctx).o.result; 
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 4);
				{
				setState(25);
				((ValueContext)_localctx).a = array();
				 ((ValueContext)_localctx).result =  ((ValueContext)_localctx).a.lst; 
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 5);
				{
				setState(28);
				match(TRUE);
				 ((ValueContext)_localctx).result = Boolean.TRUE; 
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 6);
				{
				setState(30);
				match(FALSE);
				 ((ValueContext)_localctx).result =  Boolean.FALSE; 
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 7);
				{
				setState(32);
				match(NULL);
				 ((ValueContext)_localctx).result =  null; 
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
		public String result;
		public Token st;
		public TerminalNode String() { return getToken(JSONTreeParser.String, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).exitString(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			((StringContext)_localctx).st = match(String);
			 ((StringContext)_localctx).result =  extractString((((StringContext)_localctx).st!=null?((StringContext)_localctx).st.getText():null)); 
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
		public dict<Object> result;
		public MembersContext me0;
		public MembersContext members() {
			return getRuleContext(MembersContext.class,0);
		}
		public ObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).enterObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).exitObject(this);
		}
	}

	public final ObjectContext object() throws RecognitionException {
		ObjectContext _localctx = new ObjectContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_object);

		    ((ObjectContext)_localctx).result =  x.dict();

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			match(T__0);
			setState(43);
			_la = _input.LA(1);
			if (_la==String) {
				{
				setState(40);
				((ObjectContext)_localctx).me0 = members();
				((ObjectContext)_localctx).result =  ((ObjectContext)_localctx).me0.dct;
				}
			}

			setState(45);
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

	public static class NumberContext extends ParserRuleContext {
		public Object result;
		public Token nu;
		public Token ex;
		public TerminalNode Number() { return getToken(JSONTreeParser.Number, 0); }
		public TerminalNode Exponent() { return getToken(JSONTreeParser.Exponent, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			((NumberContext)_localctx).nu = match(Number);
			setState(49);
			_la = _input.LA(1);
			if (_la==Exponent) {
				{
				setState(48);
				((NumberContext)_localctx).ex = match(Exponent);
				}
			}

			 ((NumberContext)_localctx).result =  extractNumber((((NumberContext)_localctx).nu!=null?((NumberContext)_localctx).nu.getText():null), (((NumberContext)_localctx).ex!=null?((NumberContext)_localctx).ex.getText():null)); 
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
		public list<Object> lst;
		public ElementsContext el;
		public ElementsContext elements() {
			return getRuleContext(ElementsContext.class,0);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).exitArray(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_array);

		    ((ArrayContext)_localctx).lst =  x.list();

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(T__2);
			setState(57);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << Number) | (1L << String) | (1L << TRUE) | (1L << FALSE) | (1L << NULL))) != 0)) {
				{
				setState(54);
				((ArrayContext)_localctx).el = elements();
				((ArrayContext)_localctx).lst =  ((ArrayContext)_localctx).el.lst;
				}
			}

			setState(59);
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
		public list<Object> lst;
		public ValueContext v0;
		public ValueContext v1;
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JSONTreeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JSONTreeParser.COMMA, i);
		}
		public ElementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).enterElements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).exitElements(this);
		}
	}

	public final ElementsContext elements() throws RecognitionException {
		ElementsContext _localctx = new ElementsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_elements);

		    ((ElementsContext)_localctx).lst =  x.list();

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			((ElementsContext)_localctx).v0 = value();
			_localctx.lst.append(((ElementsContext)_localctx).v0.result);
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(63);
				match(COMMA);
				setState(64);
				((ElementsContext)_localctx).v1 = value();
				_localctx.lst.append(((ElementsContext)_localctx).v1.result);
				}
				}
				setState(71);
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
		public dict<Object> dct;
		public PairContext pa0;
		public PairContext pa1;
		public List<PairContext> pair() {
			return getRuleContexts(PairContext.class);
		}
		public PairContext pair(int i) {
			return getRuleContext(PairContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JSONTreeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JSONTreeParser.COMMA, i);
		}
		public MembersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_members; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).enterMembers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).exitMembers(this);
		}
	}

	public final MembersContext members() throws RecognitionException {
		MembersContext _localctx = new MembersContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_members);

		    ((MembersContext)_localctx).dct =  x.dict();

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			((MembersContext)_localctx).pa0 = pair();
			_localctx.dct.update(((MembersContext)_localctx).pa0.dct);
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(74);
				match(COMMA);
				setState(75);
				((MembersContext)_localctx).pa1 = pair();
				_localctx.dct.update(((MembersContext)_localctx).pa1.dct);
				}
				}
				setState(82);
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
		public dict dct;
		public Token key;
		public ValueContext v;
		public TerminalNode String() { return getToken(JSONTreeParser.String, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public PairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).enterPair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONTreeListener ) ((JSONTreeListener)listener).exitPair(this);
		}
	}

	public final PairContext pair() throws RecognitionException {
		PairContext _localctx = new PairContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_pair);

		    ((PairContext)_localctx).dct =  x.dict();

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			((PairContext)_localctx).key = match(String);
			setState(84);
			match(T__4);
			setState(85);
			((PairContext)_localctx).v = value();
			 _localctx.dct.setAt(extractString((((PairContext)_localctx).key!=null?((PairContext)_localctx).key.getText():null))).value(((PairContext)_localctx).v.result); 
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\24[\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2%\n\2\3\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\5\4.\n\4\3\4\3\4\3\5\3\5\5\5\64\n\5\3\5\3\5\3\6\3"+
		"\6\3\6\3\6\5\6<\n\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\7\7F\n\7\f\7\16\7"+
		"I\13\7\3\b\3\b\3\b\3\b\3\b\3\b\7\bQ\n\b\f\b\16\bT\13\b\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\2\2\n\2\4\6\b\n\f\16\20\2\2]\2$\3\2\2\2\4&\3\2\2\2\6)\3\2\2\2"+
		"\b\61\3\2\2\2\n\67\3\2\2\2\f?\3\2\2\2\16J\3\2\2\2\20U\3\2\2\2\22\23\5"+
		"\4\3\2\23\24\b\2\1\2\24%\3\2\2\2\25\26\5\b\5\2\26\27\b\2\1\2\27%\3\2\2"+
		"\2\30\31\5\6\4\2\31\32\b\2\1\2\32%\3\2\2\2\33\34\5\n\6\2\34\35\b\2\1\2"+
		"\35%\3\2\2\2\36\37\7\r\2\2\37%\b\2\1\2 !\7\16\2\2!%\b\2\1\2\"#\7\17\2"+
		"\2#%\b\2\1\2$\22\3\2\2\2$\25\3\2\2\2$\30\3\2\2\2$\33\3\2\2\2$\36\3\2\2"+
		"\2$ \3\2\2\2$\"\3\2\2\2%\3\3\2\2\2&\'\7\n\2\2\'(\b\3\1\2(\5\3\2\2\2)-"+
		"\7\3\2\2*+\5\16\b\2+,\b\4\1\2,.\3\2\2\2-*\3\2\2\2-.\3\2\2\2./\3\2\2\2"+
		"/\60\7\4\2\2\60\7\3\2\2\2\61\63\7\b\2\2\62\64\7\t\2\2\63\62\3\2\2\2\63"+
		"\64\3\2\2\2\64\65\3\2\2\2\65\66\b\5\1\2\66\t\3\2\2\2\67;\7\5\2\289\5\f"+
		"\7\29:\b\6\1\2:<\3\2\2\2;8\3\2\2\2;<\3\2\2\2<=\3\2\2\2=>\7\6\2\2>\13\3"+
		"\2\2\2?@\5\2\2\2@G\b\7\1\2AB\7\f\2\2BC\5\2\2\2CD\b\7\1\2DF\3\2\2\2EA\3"+
		"\2\2\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2\2H\r\3\2\2\2IG\3\2\2\2JK\5\20\t\2K"+
		"R\b\b\1\2LM\7\f\2\2MN\5\20\t\2NO\b\b\1\2OQ\3\2\2\2PL\3\2\2\2QT\3\2\2\2"+
		"RP\3\2\2\2RS\3\2\2\2S\17\3\2\2\2TR\3\2\2\2UV\7\n\2\2VW\7\7\2\2WX\5\2\2"+
		"\2XY\b\t\1\2Y\21\3\2\2\2\b$-\63;GR";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}