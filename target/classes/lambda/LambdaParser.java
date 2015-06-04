// Generated from Lambda.g by ANTLR 4.5

package com.wantedtech.common.xpresso.lambda;

import java.util.*;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.x;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LambdaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, FTOK=14, And=15, Or=16, Identifier=17, 
		Number=18, JavaString=19, PythonString=20, Space=21, OPENBRACKET=22, CLOSEDBRACKET=23, 
		WS=24;
	public static final int
		RULE_eval = 0, RULE_lambdaExpression = 1, RULE_anyExpression = 2, RULE_arithmeticExpressionE = 3, 
		RULE_booleanExpressionB = 4, RULE_productExpressionT = 5, RULE_basicExpressionF = 6, 
		RULE_complexIdentifier = 7, RULE_function = 8;
	public static final String[] ruleNames = {
		"eval", "lambdaExpression", "anyExpression", "arithmeticExpressionE", 
		"booleanExpressionB", "productExpressionT", "basicExpressionF", "complexIdentifier", 
		"function"
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

	@Override
	public String getGrammarFileName() { return "Lambda.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }



	    Value inputValue;
	    Function<Object,?>[] inputFunction;
	    String inputVarName;


	public LambdaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class EvalContext extends ParserRuleContext {
		public Value value;
		public LambdaExpressionContext exp00;
		public Token exp10;
		public LambdaExpressionContext lambdaExpression() {
			return getRuleContext(LambdaExpressionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(LambdaParser.EOF, 0); }
		public EvalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eval; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterEval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitEval(this);
		}
	}

	public final EvalContext eval() throws RecognitionException {
		EvalContext _localctx = new EvalContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_eval);
		try {
			setState(22);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(18);
				((EvalContext)_localctx).exp00 = lambdaExpression();
				((EvalContext)_localctx).value =  ((EvalContext)_localctx).exp00.value;
				}
				break;
			case EOF:
				enterOuterAlt(_localctx, 2);
				{
				setState(21);
				((EvalContext)_localctx).exp10 = match(EOF);
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

	public static class LambdaExpressionContext extends ParserRuleContext {
		public Value value;
		public Token id10;
		public AnyExpressionContext an10;
		public TerminalNode Identifier() { return getToken(LambdaParser.Identifier, 0); }
		public AnyExpressionContext anyExpression() {
			return getRuleContext(AnyExpressionContext.class,0);
		}
		public LambdaExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterLambdaExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitLambdaExpression(this);
		}
	}

	public final LambdaExpressionContext lambdaExpression() throws RecognitionException {
		LambdaExpressionContext _localctx = new LambdaExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_lambdaExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			((LambdaExpressionContext)_localctx).id10 = match(Identifier);
			inputVarName = (((LambdaExpressionContext)_localctx).id10!=null?((LambdaExpressionContext)_localctx).id10.getText():null);
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Space) {
				{
				{
				setState(26);
				match(Space);
				}
				}
				setState(31);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(32);
			match(T__0);
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Space) {
				{
				{
				setState(33);
				match(Space);
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(39);
			((LambdaExpressionContext)_localctx).an10 = anyExpression();
			((LambdaExpressionContext)_localctx).value =  ((LambdaExpressionContext)_localctx).an10.value;
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

	public static class AnyExpressionContext extends ParserRuleContext {
		public Value value;
		public BooleanExpressionBContext be20;
		public ArithmeticExpressionEContext ae20;
		public BooleanExpressionBContext booleanExpressionB() {
			return getRuleContext(BooleanExpressionBContext.class,0);
		}
		public ArithmeticExpressionEContext arithmeticExpressionE() {
			return getRuleContext(ArithmeticExpressionEContext.class,0);
		}
		public AnyExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anyExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterAnyExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitAnyExpression(this);
		}
	}

	public final AnyExpressionContext anyExpression() throws RecognitionException {
		AnyExpressionContext _localctx = new AnyExpressionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_anyExpression);
		try {
			setState(48);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(42);
				((AnyExpressionContext)_localctx).be20 = booleanExpressionB(0);
				((AnyExpressionContext)_localctx).value =  ((AnyExpressionContext)_localctx).be20.value;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				((AnyExpressionContext)_localctx).ae20 = arithmeticExpressionE(0);
				((AnyExpressionContext)_localctx).value =  ((AnyExpressionContext)_localctx).ae20.value;
				}
				break;
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

	public static class ArithmeticExpressionEContext extends ParserRuleContext {
		public Value value;
		public ArithmeticExpressionEContext ae40;
		public ProductExpressionTContext pe40;
		public ProductExpressionTContext productExpressionT() {
			return getRuleContext(ProductExpressionTContext.class,0);
		}
		public ArithmeticExpressionEContext arithmeticExpressionE() {
			return getRuleContext(ArithmeticExpressionEContext.class,0);
		}
		public ArithmeticExpressionEContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticExpressionE; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterArithmeticExpressionE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitArithmeticExpressionE(this);
		}
	}

	public final ArithmeticExpressionEContext arithmeticExpressionE() throws RecognitionException {
		return arithmeticExpressionE(0);
	}

	private ArithmeticExpressionEContext arithmeticExpressionE(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArithmeticExpressionEContext _localctx = new ArithmeticExpressionEContext(_ctx, _parentState);
		ArithmeticExpressionEContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_arithmeticExpressionE, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(51);
			((ArithmeticExpressionEContext)_localctx).pe40 = productExpressionT(0);
			((ArithmeticExpressionEContext)_localctx).value =  ((ArithmeticExpressionEContext)_localctx).pe40.value;
			}
			_ctx.stop = _input.LT(-1);
			setState(90);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(88);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new ArithmeticExpressionEContext(_parentctx, _parentState);
						_localctx.ae40 = _prevctx;
						_localctx.ae40 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_arithmeticExpressionE);
						setState(54);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(58);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(55);
							match(Space);
							}
							}
							setState(60);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(61);
						match(T__1);
						setState(65);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(62);
							match(Space);
							}
							}
							setState(67);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(68);
						((ArithmeticExpressionEContext)_localctx).pe40 = productExpressionT(0);
						((ArithmeticExpressionEContext)_localctx).value =  ((ArithmeticExpressionEContext)_localctx).ae40.value.plus(((ArithmeticExpressionEContext)_localctx).pe40.value);
						}
						break;
					case 2:
						{
						_localctx = new ArithmeticExpressionEContext(_parentctx, _parentState);
						_localctx.ae40 = _prevctx;
						_localctx.ae40 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_arithmeticExpressionE);
						setState(71);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(75);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(72);
							match(Space);
							}
							}
							setState(77);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(78);
						match(T__2);
						setState(82);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(79);
							match(Space);
							}
							}
							setState(84);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(85);
						((ArithmeticExpressionEContext)_localctx).pe40 = productExpressionT(0);
						((ArithmeticExpressionEContext)_localctx).value =  ((ArithmeticExpressionEContext)_localctx).ae40.value.minus(((ArithmeticExpressionEContext)_localctx).pe40.value);
						}
						break;
					}
					} 
				}
				setState(92);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BooleanExpressionBContext extends ParserRuleContext {
		public Value value;
		public BooleanExpressionBContext be30;
		public ArithmeticExpressionEContext ae30;
		public ArithmeticExpressionEContext ae31;
		public ComplexIdentifierContext ce30;
		public FunctionContext fu30;
		public BooleanExpressionBContext be31;
		public List<BooleanExpressionBContext> booleanExpressionB() {
			return getRuleContexts(BooleanExpressionBContext.class);
		}
		public BooleanExpressionBContext booleanExpressionB(int i) {
			return getRuleContext(BooleanExpressionBContext.class,i);
		}
		public List<ArithmeticExpressionEContext> arithmeticExpressionE() {
			return getRuleContexts(ArithmeticExpressionEContext.class);
		}
		public ArithmeticExpressionEContext arithmeticExpressionE(int i) {
			return getRuleContext(ArithmeticExpressionEContext.class,i);
		}
		public ComplexIdentifierContext complexIdentifier() {
			return getRuleContext(ComplexIdentifierContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public TerminalNode And() { return getToken(LambdaParser.And, 0); }
		public TerminalNode Or() { return getToken(LambdaParser.Or, 0); }
		public BooleanExpressionBContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExpressionB; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterBooleanExpressionB(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitBooleanExpressionB(this);
		}
	}

	public final BooleanExpressionBContext booleanExpressionB() throws RecognitionException {
		return booleanExpressionB(0);
	}

	private BooleanExpressionBContext booleanExpressionB(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BooleanExpressionBContext _localctx = new BooleanExpressionBContext(_ctx, _parentState);
		BooleanExpressionBContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_booleanExpressionB, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(94);
				match(T__8);
				setState(95);
				((BooleanExpressionBContext)_localctx).be30 = booleanExpressionB(3);
				((BooleanExpressionBContext)_localctx).value =  ((BooleanExpressionBContext)_localctx).be30.value.not();
				}
				break;
			case 2:
				{
				setState(98);
				((BooleanExpressionBContext)_localctx).ae30 = arithmeticExpressionE(0);
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(99);
					match(Space);
					}
					}
					setState(104);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(105);
				match(T__3);
				setState(106);
				match(T__3);
				setState(110);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(107);
					match(Space);
					}
					}
					setState(112);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(113);
				((BooleanExpressionBContext)_localctx).ae31 = arithmeticExpressionE(0);
				((BooleanExpressionBContext)_localctx).value =  new Value(((BooleanExpressionBContext)_localctx).ae30.value.equals(((BooleanExpressionBContext)_localctx).ae31.value));
				}
				break;
			case 3:
				{
				setState(116);
				((BooleanExpressionBContext)_localctx).ae30 = arithmeticExpressionE(0);
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(117);
					match(Space);
					}
					}
					setState(122);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(123);
				match(T__4);
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(124);
					match(Space);
					}
					}
					setState(129);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(130);
				((BooleanExpressionBContext)_localctx).ae31 = arithmeticExpressionE(0);
				((BooleanExpressionBContext)_localctx).value =  ((BooleanExpressionBContext)_localctx).ae30.value.lessorequals(((BooleanExpressionBContext)_localctx).ae31.value);
				}
				break;
			case 4:
				{
				setState(133);
				((BooleanExpressionBContext)_localctx).ae30 = arithmeticExpressionE(0);
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(134);
					match(Space);
					}
					}
					setState(139);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(140);
				match(T__5);
				setState(144);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(141);
					match(Space);
					}
					}
					setState(146);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(147);
				((BooleanExpressionBContext)_localctx).ae31 = arithmeticExpressionE(0);
				((BooleanExpressionBContext)_localctx).value =  ((BooleanExpressionBContext)_localctx).ae30.value.less(((BooleanExpressionBContext)_localctx).ae31.value);
				}
				break;
			case 5:
				{
				setState(150);
				((BooleanExpressionBContext)_localctx).ae30 = arithmeticExpressionE(0);
				setState(154);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(151);
					match(Space);
					}
					}
					setState(156);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(157);
				match(T__6);
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(158);
					match(Space);
					}
					}
					setState(163);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(164);
				((BooleanExpressionBContext)_localctx).ae31 = arithmeticExpressionE(0);
				((BooleanExpressionBContext)_localctx).value =  ((BooleanExpressionBContext)_localctx).ae30.value.less(((BooleanExpressionBContext)_localctx).ae31.value).not();
				}
				break;
			case 6:
				{
				setState(167);
				((BooleanExpressionBContext)_localctx).ae30 = arithmeticExpressionE(0);
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(168);
					match(Space);
					}
					}
					setState(173);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(174);
				match(T__7);
				setState(178);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(175);
					match(Space);
					}
					}
					setState(180);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(181);
				((BooleanExpressionBContext)_localctx).ae31 = arithmeticExpressionE(0);
				((BooleanExpressionBContext)_localctx).value =  ((BooleanExpressionBContext)_localctx).ae30.value.lessorequals(((BooleanExpressionBContext)_localctx).ae31.value).not();
				}
				break;
			case 7:
				{
				setState(184);
				((BooleanExpressionBContext)_localctx).ae30 = arithmeticExpressionE(0);
				setState(188);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(185);
					match(Space);
					}
					}
					setState(190);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(191);
				match(T__8);
				setState(192);
				match(T__3);
				setState(196);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(193);
					match(Space);
					}
					}
					setState(198);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(199);
				((BooleanExpressionBContext)_localctx).ae31 = arithmeticExpressionE(0);
				((BooleanExpressionBContext)_localctx).value =  new Value(!((BooleanExpressionBContext)_localctx).ae30.value.equals(((BooleanExpressionBContext)_localctx).ae31.value));
				}
				break;
			case 8:
				{
				setState(202);
				match(T__9);
				setState(203);
				((BooleanExpressionBContext)_localctx).be30 = booleanExpressionB(0);
				setState(204);
				match(T__10);
				((BooleanExpressionBContext)_localctx).value =  ((BooleanExpressionBContext)_localctx).be30.value;
				}
				break;
			case 9:
				{
				setState(207);
				((BooleanExpressionBContext)_localctx).ce30 = complexIdentifier();
				((BooleanExpressionBContext)_localctx).value =  ((BooleanExpressionBContext)_localctx).ce30.value;
				}
				break;
			case 10:
				{
				setState(210);
				((BooleanExpressionBContext)_localctx).fu30 = function();
				((BooleanExpressionBContext)_localctx).value =  ((BooleanExpressionBContext)_localctx).fu30.value;
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(251);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(249);
					switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
					case 1:
						{
						_localctx = new BooleanExpressionBContext(_parentctx, _parentState);
						_localctx.be30 = _prevctx;
						_localctx.be30 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_booleanExpressionB);
						setState(215);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(219);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(216);
							match(Space);
							}
							}
							setState(221);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(222);
						match(And);
						setState(226);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(223);
							match(Space);
							}
							}
							setState(228);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(229);
						((BooleanExpressionBContext)_localctx).be31 = booleanExpressionB(7);
						((BooleanExpressionBContext)_localctx).value =  ((BooleanExpressionBContext)_localctx).be30.value.and(((BooleanExpressionBContext)_localctx).be31.value);
						}
						break;
					case 2:
						{
						_localctx = new BooleanExpressionBContext(_parentctx, _parentState);
						_localctx.be30 = _prevctx;
						_localctx.be30 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_booleanExpressionB);
						setState(232);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(236);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(233);
							match(Space);
							}
							}
							setState(238);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(239);
						match(Or);
						setState(243);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(240);
							match(Space);
							}
							}
							setState(245);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(246);
						((BooleanExpressionBContext)_localctx).be31 = booleanExpressionB(6);
						((BooleanExpressionBContext)_localctx).value =  ((BooleanExpressionBContext)_localctx).be30.value.or(((BooleanExpressionBContext)_localctx).be31.value);
						}
						break;
					}
					} 
				}
				setState(253);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ProductExpressionTContext extends ParserRuleContext {
		public Value value;
		public ProductExpressionTContext pe50;
		public BasicExpressionFContext be50;
		public BasicExpressionFContext basicExpressionF() {
			return getRuleContext(BasicExpressionFContext.class,0);
		}
		public ProductExpressionTContext productExpressionT() {
			return getRuleContext(ProductExpressionTContext.class,0);
		}
		public ProductExpressionTContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_productExpressionT; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterProductExpressionT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitProductExpressionT(this);
		}
	}

	public final ProductExpressionTContext productExpressionT() throws RecognitionException {
		return productExpressionT(0);
	}

	private ProductExpressionTContext productExpressionT(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ProductExpressionTContext _localctx = new ProductExpressionTContext(_ctx, _parentState);
		ProductExpressionTContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_productExpressionT, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(255);
			((ProductExpressionTContext)_localctx).be50 = basicExpressionF();
			((ProductExpressionTContext)_localctx).value =  ((ProductExpressionTContext)_localctx).be50.value;
			}
			_ctx.stop = _input.LT(-1);
			setState(294);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(292);
					switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
					case 1:
						{
						_localctx = new ProductExpressionTContext(_parentctx, _parentState);
						_localctx.pe50 = _prevctx;
						_localctx.pe50 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_productExpressionT);
						setState(258);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(262);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(259);
							match(Space);
							}
							}
							setState(264);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(265);
						match(T__11);
						setState(269);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(266);
							match(Space);
							}
							}
							setState(271);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(272);
						((ProductExpressionTContext)_localctx).be50 = basicExpressionF();
						((ProductExpressionTContext)_localctx).value =  ((ProductExpressionTContext)_localctx).pe50.value.multiplyBy(((ProductExpressionTContext)_localctx).be50.value);
						}
						break;
					case 2:
						{
						_localctx = new ProductExpressionTContext(_parentctx, _parentState);
						_localctx.pe50 = _prevctx;
						_localctx.pe50 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_productExpressionT);
						setState(275);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(279);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(276);
							match(Space);
							}
							}
							setState(281);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(282);
						match(T__12);
						setState(286);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(283);
							match(Space);
							}
							}
							setState(288);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(289);
						((ProductExpressionTContext)_localctx).be50 = basicExpressionF();
						((ProductExpressionTContext)_localctx).value =  ((ProductExpressionTContext)_localctx).pe50.value.diviseBy(((ProductExpressionTContext)_localctx).be50.value);
						}
						break;
					}
					} 
				}
				setState(296);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BasicExpressionFContext extends ParserRuleContext {
		public Value value;
		public ArithmeticExpressionEContext ae60;
		public Token nu60;
		public Token js60;
		public Token ps60;
		public ComplexIdentifierContext ci60;
		public FunctionContext fu60;
		public ArithmeticExpressionEContext arithmeticExpressionE() {
			return getRuleContext(ArithmeticExpressionEContext.class,0);
		}
		public TerminalNode Number() { return getToken(LambdaParser.Number, 0); }
		public TerminalNode JavaString() { return getToken(LambdaParser.JavaString, 0); }
		public TerminalNode PythonString() { return getToken(LambdaParser.PythonString, 0); }
		public ComplexIdentifierContext complexIdentifier() {
			return getRuleContext(ComplexIdentifierContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public BasicExpressionFContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basicExpressionF; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterBasicExpressionF(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitBasicExpressionF(this);
		}
	}

	public final BasicExpressionFContext basicExpressionF() throws RecognitionException {
		BasicExpressionFContext _localctx = new BasicExpressionFContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_basicExpressionF);
		try {
			setState(314);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(297);
				match(T__9);
				setState(298);
				((BasicExpressionFContext)_localctx).ae60 = arithmeticExpressionE(0);
				setState(299);
				match(T__10);
				((BasicExpressionFContext)_localctx).value =  ((BasicExpressionFContext)_localctx).ae60.value;
				}
				break;
			case Number:
				enterOuterAlt(_localctx, 2);
				{
				setState(302);
				((BasicExpressionFContext)_localctx).nu60 = match(Number);

				                        if(x.String(".").in((((BasicExpressionFContext)_localctx).nu60!=null?((BasicExpressionFContext)_localctx).nu60.getText():null)))
				                            ((BasicExpressionFContext)_localctx).value =  new Value(Double.parseDouble((((BasicExpressionFContext)_localctx).nu60!=null?((BasicExpressionFContext)_localctx).nu60.getText():null)));
				                        else
				                            ((BasicExpressionFContext)_localctx).value =  new Value(Integer.parseInt((((BasicExpressionFContext)_localctx).nu60!=null?((BasicExpressionFContext)_localctx).nu60.getText():null)));
				                    
				}
				break;
			case JavaString:
				enterOuterAlt(_localctx, 3);
				{
				setState(304);
				((BasicExpressionFContext)_localctx).js60 = match(JavaString);
				((BasicExpressionFContext)_localctx).value =  new Value(x.String((((BasicExpressionFContext)_localctx).js60!=null?((BasicExpressionFContext)_localctx).js60.getText():null)).slice(1,-1));
				}
				break;
			case PythonString:
				enterOuterAlt(_localctx, 4);
				{
				setState(306);
				((BasicExpressionFContext)_localctx).ps60 = match(PythonString);
				((BasicExpressionFContext)_localctx).value =  new Value(x.String((((BasicExpressionFContext)_localctx).ps60!=null?((BasicExpressionFContext)_localctx).ps60.getText():null)).slice(3,-3));
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 5);
				{
				setState(308);
				((BasicExpressionFContext)_localctx).ci60 = complexIdentifier();
				((BasicExpressionFContext)_localctx).value =  ((BasicExpressionFContext)_localctx).ci60.value;
				}
				break;
			case FTOK:
				enterOuterAlt(_localctx, 6);
				{
				setState(311);
				((BasicExpressionFContext)_localctx).fu60 = function();
				((BasicExpressionFContext)_localctx).value =  ((BasicExpressionFContext)_localctx).fu60.value;
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

	public static class ComplexIdentifierContext extends ParserRuleContext {
		public Value value;
		public Token id70;
		public Token nu70;
		public TerminalNode Identifier() { return getToken(LambdaParser.Identifier, 0); }
		public TerminalNode OPENBRACKET() { return getToken(LambdaParser.OPENBRACKET, 0); }
		public TerminalNode CLOSEDBRACKET() { return getToken(LambdaParser.CLOSEDBRACKET, 0); }
		public TerminalNode Number() { return getToken(LambdaParser.Number, 0); }
		public ComplexIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_complexIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterComplexIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitComplexIdentifier(this);
		}
	}

	public final ComplexIdentifierContext complexIdentifier() throws RecognitionException {
		ComplexIdentifierContext _localctx = new ComplexIdentifierContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_complexIdentifier);
		try {
			setState(325);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(316);
				((ComplexIdentifierContext)_localctx).id70 = match(Identifier);
				if(!(((ComplexIdentifierContext)_localctx).id70!=null?((ComplexIdentifierContext)_localctx).id70.getText():null).equals(inputVarName)) throw new IllegalArgumentException("Undefined variable: "+(((ComplexIdentifierContext)_localctx).id70!=null?((ComplexIdentifierContext)_localctx).id70.getText():null));
				{
				setState(318);
				match(OPENBRACKET);
				setState(319);
				((ComplexIdentifierContext)_localctx).nu70 = match(Number);

				                        if((((ComplexIdentifierContext)_localctx).nu70!=null?((ComplexIdentifierContext)_localctx).nu70.getText():null).contains("."))
				                            throw new IllegalArgumentException("The index of the input variable has to be an integer.");
				                        else if(Integer.parseInt((((ComplexIdentifierContext)_localctx).nu70!=null?((ComplexIdentifierContext)_localctx).nu70.getText():null)) >= (x.len(inputValue)))
				                            throw new IndexOutOfBoundsException("Illegal dimension parameter: "+(((ComplexIdentifierContext)_localctx).nu70!=null?((ComplexIdentifierContext)_localctx).nu70.getText():null)+". The input variable only has "+x.len(inputValue)+" dimensions.");
				                    
				setState(321);
				match(CLOSEDBRACKET);
				 if(inputValue.value instanceof Iterable<?>)
				                            ((ComplexIdentifierContext)_localctx).value =  new Value(((List<?>)inputValue.value).get(Integer.parseInt((((ComplexIdentifierContext)_localctx).nu70!=null?((ComplexIdentifierContext)_localctx).nu70.getText():null))));
				                        else
				                            throw new IllegalArgumentException("The input variable is not multi-dimensional, you cannot use the [...] notation in your lambda expression.");
				                      
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(323);
				((ComplexIdentifierContext)_localctx).id70 = match(Identifier);
				((ComplexIdentifierContext)_localctx).value =  new Value(inputValue);
				}
				break;
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

	public static class FunctionContext extends ParserRuleContext {
		public Value value;
		public Token ft80;
		public AnyExpressionContext ae80;
		public TerminalNode FTOK() { return getToken(LambdaParser.FTOK, 0); }
		public AnyExpressionContext anyExpression() {
			return getRuleContext(AnyExpressionContext.class,0);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitFunction(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_function);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			((FunctionContext)_localctx).ft80 = match(FTOK);
			setState(328);
			match(T__9);
			setState(329);
			((FunctionContext)_localctx).ae80 = anyExpression();
			setState(330);
			match(T__10);

			                                                int funNum = Integer.parseInt(x.String((((FunctionContext)_localctx).ft80!=null?((FunctionContext)_localctx).ft80.getText():null)).sliceFrom(1));
			                                                if(funNum >= inputFunction.length)
			                                                    throw new IllegalArgumentException("The unknown function "+(((FunctionContext)_localctx).ft80!=null?((FunctionContext)_localctx).ft80.getText():null)+".");
			                                                else
			                                                    ((FunctionContext)_localctx).value =  new Value(inputFunction[funNum].apply(((FunctionContext)_localctx).ae80.value.value));
			                                             
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
		case 3:
			return arithmeticExpressionE_sempred((ArithmeticExpressionEContext)_localctx, predIndex);
		case 4:
			return booleanExpressionB_sempred((BooleanExpressionBContext)_localctx, predIndex);
		case 5:
			return productExpressionT_sempred((ProductExpressionTContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean arithmeticExpressionE_sempred(ArithmeticExpressionEContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean booleanExpressionB_sempred(BooleanExpressionBContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 5);
		}
		return true;
	}
	private boolean productExpressionT_sempred(ProductExpressionTContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 3);
		case 5:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\32\u0150\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3"+
		"\2\3\2\3\2\5\2\31\n\2\3\3\3\3\3\3\7\3\36\n\3\f\3\16\3!\13\3\3\3\3\3\7"+
		"\3%\n\3\f\3\16\3(\13\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4\63\n\4"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\7\5;\n\5\f\5\16\5>\13\5\3\5\3\5\7\5B\n\5\f\5"+
		"\16\5E\13\5\3\5\3\5\3\5\3\5\3\5\7\5L\n\5\f\5\16\5O\13\5\3\5\3\5\7\5S\n"+
		"\5\f\5\16\5V\13\5\3\5\3\5\3\5\7\5[\n\5\f\5\16\5^\13\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\7\6g\n\6\f\6\16\6j\13\6\3\6\3\6\3\6\7\6o\n\6\f\6\16\6r\13"+
		"\6\3\6\3\6\3\6\3\6\3\6\7\6y\n\6\f\6\16\6|\13\6\3\6\3\6\7\6\u0080\n\6\f"+
		"\6\16\6\u0083\13\6\3\6\3\6\3\6\3\6\3\6\7\6\u008a\n\6\f\6\16\6\u008d\13"+
		"\6\3\6\3\6\7\6\u0091\n\6\f\6\16\6\u0094\13\6\3\6\3\6\3\6\3\6\3\6\7\6\u009b"+
		"\n\6\f\6\16\6\u009e\13\6\3\6\3\6\7\6\u00a2\n\6\f\6\16\6\u00a5\13\6\3\6"+
		"\3\6\3\6\3\6\3\6\7\6\u00ac\n\6\f\6\16\6\u00af\13\6\3\6\3\6\7\6\u00b3\n"+
		"\6\f\6\16\6\u00b6\13\6\3\6\3\6\3\6\3\6\3\6\7\6\u00bd\n\6\f\6\16\6\u00c0"+
		"\13\6\3\6\3\6\3\6\7\6\u00c5\n\6\f\6\16\6\u00c8\13\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u00d8\n\6\3\6\3\6\7\6\u00dc"+
		"\n\6\f\6\16\6\u00df\13\6\3\6\3\6\7\6\u00e3\n\6\f\6\16\6\u00e6\13\6\3\6"+
		"\3\6\3\6\3\6\3\6\7\6\u00ed\n\6\f\6\16\6\u00f0\13\6\3\6\3\6\7\6\u00f4\n"+
		"\6\f\6\16\6\u00f7\13\6\3\6\3\6\3\6\7\6\u00fc\n\6\f\6\16\6\u00ff\13\6\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\7\7\u0107\n\7\f\7\16\7\u010a\13\7\3\7\3\7\7\7\u010e"+
		"\n\7\f\7\16\7\u0111\13\7\3\7\3\7\3\7\3\7\3\7\7\7\u0118\n\7\f\7\16\7\u011b"+
		"\13\7\3\7\3\7\7\7\u011f\n\7\f\7\16\7\u0122\13\7\3\7\3\7\3\7\7\7\u0127"+
		"\n\7\f\7\16\7\u012a\13\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\5\b\u013d\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\5\t\u0148\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\2\5\b\n\f\13\2\4\6\b\n\f"+
		"\16\20\22\2\2\u0177\2\30\3\2\2\2\4\32\3\2\2\2\6\62\3\2\2\2\b\64\3\2\2"+
		"\2\n\u00d7\3\2\2\2\f\u0100\3\2\2\2\16\u013c\3\2\2\2\20\u0147\3\2\2\2\22"+
		"\u0149\3\2\2\2\24\25\5\4\3\2\25\26\b\2\1\2\26\31\3\2\2\2\27\31\7\2\2\3"+
		"\30\24\3\2\2\2\30\27\3\2\2\2\31\3\3\2\2\2\32\33\7\23\2\2\33\37\b\3\1\2"+
		"\34\36\7\27\2\2\35\34\3\2\2\2\36!\3\2\2\2\37\35\3\2\2\2\37 \3\2\2\2 \""+
		"\3\2\2\2!\37\3\2\2\2\"&\7\3\2\2#%\7\27\2\2$#\3\2\2\2%(\3\2\2\2&$\3\2\2"+
		"\2&\'\3\2\2\2\')\3\2\2\2(&\3\2\2\2)*\5\6\4\2*+\b\3\1\2+\5\3\2\2\2,-\5"+
		"\n\6\2-.\b\4\1\2.\63\3\2\2\2/\60\5\b\5\2\60\61\b\4\1\2\61\63\3\2\2\2\62"+
		",\3\2\2\2\62/\3\2\2\2\63\7\3\2\2\2\64\65\b\5\1\2\65\66\5\f\7\2\66\67\b"+
		"\5\1\2\67\\\3\2\2\28<\f\5\2\29;\7\27\2\2:9\3\2\2\2;>\3\2\2\2<:\3\2\2\2"+
		"<=\3\2\2\2=?\3\2\2\2><\3\2\2\2?C\7\4\2\2@B\7\27\2\2A@\3\2\2\2BE\3\2\2"+
		"\2CA\3\2\2\2CD\3\2\2\2DF\3\2\2\2EC\3\2\2\2FG\5\f\7\2GH\b\5\1\2H[\3\2\2"+
		"\2IM\f\4\2\2JL\7\27\2\2KJ\3\2\2\2LO\3\2\2\2MK\3\2\2\2MN\3\2\2\2NP\3\2"+
		"\2\2OM\3\2\2\2PT\7\5\2\2QS\7\27\2\2RQ\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3"+
		"\2\2\2UW\3\2\2\2VT\3\2\2\2WX\5\f\7\2XY\b\5\1\2Y[\3\2\2\2Z8\3\2\2\2ZI\3"+
		"\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]\t\3\2\2\2^\\\3\2\2\2_`\b\6\1\2"+
		"`a\7\13\2\2ab\5\n\6\5bc\b\6\1\2c\u00d8\3\2\2\2dh\5\b\5\2eg\7\27\2\2fe"+
		"\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2ik\3\2\2\2jh\3\2\2\2kl\7\6\2\2l"+
		"p\7\6\2\2mo\7\27\2\2nm\3\2\2\2or\3\2\2\2pn\3\2\2\2pq\3\2\2\2qs\3\2\2\2"+
		"rp\3\2\2\2st\5\b\5\2tu\b\6\1\2u\u00d8\3\2\2\2vz\5\b\5\2wy\7\27\2\2xw\3"+
		"\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{}\3\2\2\2|z\3\2\2\2}\u0081\7\7\2"+
		"\2~\u0080\7\27\2\2\177~\3\2\2\2\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2"+
		"\u0081\u0082\3\2\2\2\u0082\u0084\3\2\2\2\u0083\u0081\3\2\2\2\u0084\u0085"+
		"\5\b\5\2\u0085\u0086\b\6\1\2\u0086\u00d8\3\2\2\2\u0087\u008b\5\b\5\2\u0088"+
		"\u008a\7\27\2\2\u0089\u0088\3\2\2\2\u008a\u008d\3\2\2\2\u008b\u0089\3"+
		"\2\2\2\u008b\u008c\3\2\2\2\u008c\u008e\3\2\2\2\u008d\u008b\3\2\2\2\u008e"+
		"\u0092\7\b\2\2\u008f\u0091\7\27\2\2\u0090\u008f\3\2\2\2\u0091\u0094\3"+
		"\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0095\3\2\2\2\u0094"+
		"\u0092\3\2\2\2\u0095\u0096\5\b\5\2\u0096\u0097\b\6\1\2\u0097\u00d8\3\2"+
		"\2\2\u0098\u009c\5\b\5\2\u0099\u009b\7\27\2\2\u009a\u0099\3\2\2\2\u009b"+
		"\u009e\3\2\2\2\u009c\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009f\3\2"+
		"\2\2\u009e\u009c\3\2\2\2\u009f\u00a3\7\t\2\2\u00a0\u00a2\7\27\2\2\u00a1"+
		"\u00a0\3\2\2\2\u00a2\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2"+
		"\2\2\u00a4\u00a6\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a6\u00a7\5\b\5\2\u00a7"+
		"\u00a8\b\6\1\2\u00a8\u00d8\3\2\2\2\u00a9\u00ad\5\b\5\2\u00aa\u00ac\7\27"+
		"\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad"+
		"\u00ae\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00b4\7\n"+
		"\2\2\u00b1\u00b3\7\27\2\2\u00b2\u00b1\3\2\2\2\u00b3\u00b6\3\2\2\2\u00b4"+
		"\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b7\3\2\2\2\u00b6\u00b4\3\2"+
		"\2\2\u00b7\u00b8\5\b\5\2\u00b8\u00b9\b\6\1\2\u00b9\u00d8\3\2\2\2\u00ba"+
		"\u00be\5\b\5\2\u00bb\u00bd\7\27\2\2\u00bc\u00bb\3\2\2\2\u00bd\u00c0\3"+
		"\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c1\3\2\2\2\u00c0"+
		"\u00be\3\2\2\2\u00c1\u00c2\7\13\2\2\u00c2\u00c6\7\6\2\2\u00c3\u00c5\7"+
		"\27\2\2\u00c4\u00c3\3\2\2\2\u00c5\u00c8\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c6"+
		"\u00c7\3\2\2\2\u00c7\u00c9\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c9\u00ca\5\b"+
		"\5\2\u00ca\u00cb\b\6\1\2\u00cb\u00d8\3\2\2\2\u00cc\u00cd\7\f\2\2\u00cd"+
		"\u00ce\5\n\6\2\u00ce\u00cf\7\r\2\2\u00cf\u00d0\b\6\1\2\u00d0\u00d8\3\2"+
		"\2\2\u00d1\u00d2\5\20\t\2\u00d2\u00d3\b\6\1\2\u00d3\u00d8\3\2\2\2\u00d4"+
		"\u00d5\5\22\n\2\u00d5\u00d6\b\6\1\2\u00d6\u00d8\3\2\2\2\u00d7_\3\2\2\2"+
		"\u00d7d\3\2\2\2\u00d7v\3\2\2\2\u00d7\u0087\3\2\2\2\u00d7\u0098\3\2\2\2"+
		"\u00d7\u00a9\3\2\2\2\u00d7\u00ba\3\2\2\2\u00d7\u00cc\3\2\2\2\u00d7\u00d1"+
		"\3\2\2\2\u00d7\u00d4\3\2\2\2\u00d8\u00fd\3\2\2\2\u00d9\u00dd\f\b\2\2\u00da"+
		"\u00dc\7\27\2\2\u00db\u00da\3\2\2\2\u00dc\u00df\3\2\2\2\u00dd\u00db\3"+
		"\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00e0\3\2\2\2\u00df\u00dd\3\2\2\2\u00e0"+
		"\u00e4\7\21\2\2\u00e1\u00e3\7\27\2\2\u00e2\u00e1\3\2\2\2\u00e3\u00e6\3"+
		"\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e7\3\2\2\2\u00e6"+
		"\u00e4\3\2\2\2\u00e7\u00e8\5\n\6\t\u00e8\u00e9\b\6\1\2\u00e9\u00fc\3\2"+
		"\2\2\u00ea\u00ee\f\7\2\2\u00eb\u00ed\7\27\2\2\u00ec\u00eb\3\2\2\2\u00ed"+
		"\u00f0\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f1\3\2"+
		"\2\2\u00f0\u00ee\3\2\2\2\u00f1\u00f5\7\22\2\2\u00f2\u00f4\7\27\2\2\u00f3"+
		"\u00f2\3\2\2\2\u00f4\u00f7\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f5\u00f6\3\2"+
		"\2\2\u00f6\u00f8\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f8\u00f9\5\n\6\b\u00f9"+
		"\u00fa\b\6\1\2\u00fa\u00fc\3\2\2\2\u00fb\u00d9\3\2\2\2\u00fb\u00ea\3\2"+
		"\2\2\u00fc\u00ff\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe"+
		"\13\3\2\2\2\u00ff\u00fd\3\2\2\2\u0100\u0101\b\7\1\2\u0101\u0102\5\16\b"+
		"\2\u0102\u0103\b\7\1\2\u0103\u0128\3\2\2\2\u0104\u0108\f\5\2\2\u0105\u0107"+
		"\7\27\2\2\u0106\u0105\3\2\2\2\u0107\u010a\3\2\2\2\u0108\u0106\3\2\2\2"+
		"\u0108\u0109\3\2\2\2\u0109\u010b\3\2\2\2\u010a\u0108\3\2\2\2\u010b\u010f"+
		"\7\16\2\2\u010c\u010e\7\27\2\2\u010d\u010c\3\2\2\2\u010e\u0111\3\2\2\2"+
		"\u010f\u010d\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0112\3\2\2\2\u0111\u010f"+
		"\3\2\2\2\u0112\u0113\5\16\b\2\u0113\u0114\b\7\1\2\u0114\u0127\3\2\2\2"+
		"\u0115\u0119\f\4\2\2\u0116\u0118\7\27\2\2\u0117\u0116\3\2\2\2\u0118\u011b"+
		"\3\2\2\2\u0119\u0117\3\2\2\2\u0119\u011a\3\2\2\2\u011a\u011c\3\2\2\2\u011b"+
		"\u0119\3\2\2\2\u011c\u0120\7\17\2\2\u011d\u011f\7\27\2\2\u011e\u011d\3"+
		"\2\2\2\u011f\u0122\3\2\2\2\u0120\u011e\3\2\2\2\u0120\u0121\3\2\2\2\u0121"+
		"\u0123\3\2\2\2\u0122\u0120\3\2\2\2\u0123\u0124\5\16\b\2\u0124\u0125\b"+
		"\7\1\2\u0125\u0127\3\2\2\2\u0126\u0104\3\2\2\2\u0126\u0115\3\2\2\2\u0127"+
		"\u012a\3\2\2\2\u0128\u0126\3\2\2\2\u0128\u0129\3\2\2\2\u0129\r\3\2\2\2"+
		"\u012a\u0128\3\2\2\2\u012b\u012c\7\f\2\2\u012c\u012d\5\b\5\2\u012d\u012e"+
		"\7\r\2\2\u012e\u012f\b\b\1\2\u012f\u013d\3\2\2\2\u0130\u0131\7\24\2\2"+
		"\u0131\u013d\b\b\1\2\u0132\u0133\7\25\2\2\u0133\u013d\b\b\1\2\u0134\u0135"+
		"\7\26\2\2\u0135\u013d\b\b\1\2\u0136\u0137\5\20\t\2\u0137\u0138\b\b\1\2"+
		"\u0138\u013d\3\2\2\2\u0139\u013a\5\22\n\2\u013a\u013b\b\b\1\2\u013b\u013d"+
		"\3\2\2\2\u013c\u012b\3\2\2\2\u013c\u0130\3\2\2\2\u013c\u0132\3\2\2\2\u013c"+
		"\u0134\3\2\2\2\u013c\u0136\3\2\2\2\u013c\u0139\3\2\2\2\u013d\17\3\2\2"+
		"\2\u013e\u013f\7\23\2\2\u013f\u0140\b\t\1\2\u0140\u0141\7\30\2\2\u0141"+
		"\u0142\7\24\2\2\u0142\u0143\b\t\1\2\u0143\u0144\7\31\2\2\u0144\u0148\b"+
		"\t\1\2\u0145\u0146\7\23\2\2\u0146\u0148\b\t\1\2\u0147\u013e\3\2\2\2\u0147"+
		"\u0145\3\2\2\2\u0148\21\3\2\2\2\u0149\u014a\7\20\2\2\u014a\u014b\7\f\2"+
		"\2\u014b\u014c\5\6\4\2\u014c\u014d\7\r\2\2\u014d\u014e\b\n\1\2\u014e\23"+
		"\3\2\2\2\'\30\37&\62<CMTZ\\hpz\u0081\u008b\u0092\u009c\u00a3\u00ad\u00b4"+
		"\u00be\u00c6\u00d7\u00dd\u00e4\u00ee\u00f5\u00fb\u00fd\u0108\u010f\u0119"+
		"\u0120\u0126\u0128\u013c\u0147";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}