// Generated from Lambda.g by ANTLR 4.5

package com.wantedtech.common.xpresso.lambda;

import java.util.*;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;

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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, True=7, False=8, Equals=9, 
		LessOrEquals=10, Less=11, MoreOrEquals=12, More=13, NotEquals=14, And=15, 
		Or=16, SimpleType=17, ComplexType=18, FTOKEN=19, PTOKEN=20, OPENPAR=21, 
		CLOSEDPAR=22, Identifier=23, Number=24, String=25, Space=26, OPENBRACKET=27, 
		CLOSEDBRACKET=28, WS=29;
	public static final int
		RULE_eval = 0, RULE_lambdaExp = 1, RULE_anyExp = 2, RULE_booleanExp = 3, 
		RULE_arithmeticExp = 4, RULE_multiplyExp = 5, RULE_atomMathExp = 6, RULE_complexIdentifier = 7, 
		RULE_function = 8, RULE_predicate = 9;
	public static final String[] ruleNames = {
		"eval", "lambdaExp", "anyExp", "booleanExp", "arithmeticExp", "multiplyExp", 
		"atomMathExp", "complexIdentifier", "function", "predicate"
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
	    Predicate<Object>[] inputPredicate;


	public LambdaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class EvalContext extends ParserRuleContext {
		public Value value;
		public LambdaExpContext exp0;
		public Token exp1;
		public LambdaExpContext lambdaExp() {
			return getRuleContext(LambdaExpContext.class,0);
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
			setState(24);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(20);
				((EvalContext)_localctx).exp0 = lambdaExp();
				((EvalContext)_localctx).value =  ((EvalContext)_localctx).exp0.value;
				}
				break;
			case EOF:
				enterOuterAlt(_localctx, 2);
				{
				setState(23);
				((EvalContext)_localctx).exp1 = match(EOF);
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

	public static class LambdaExpContext extends ParserRuleContext {
		public Value value;
		public AnyExpContext a00;
		public TerminalNode Identifier() { return getToken(LambdaParser.Identifier, 0); }
		public AnyExpContext anyExp() {
			return getRuleContext(AnyExpContext.class,0);
		}
		public LambdaExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterLambdaExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitLambdaExp(this);
		}
	}

	public final LambdaExpContext lambdaExp() throws RecognitionException {
		LambdaExpContext _localctx = new LambdaExpContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_lambdaExp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			match(Identifier);
			setState(30);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Space) {
				{
				{
				setState(27);
				match(Space);
				}
				}
				setState(32);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(33);
			match(T__0);
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Space) {
				{
				{
				setState(34);
				match(Space);
				}
				}
				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(40);
			((LambdaExpContext)_localctx).a00 = anyExp();
			((LambdaExpContext)_localctx).value = ((LambdaExpContext)_localctx).a00.value;
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

	public static class AnyExpContext extends ParserRuleContext {
		public Value value;
		public ArithmeticExpContext ad0;
		public BooleanExpContext or0;
		public ArithmeticExpContext arithmeticExp() {
			return getRuleContext(ArithmeticExpContext.class,0);
		}
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public AnyExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anyExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterAnyExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitAnyExp(this);
		}
	}

	public final AnyExpContext anyExp() throws RecognitionException {
		AnyExpContext _localctx = new AnyExpContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_anyExp);
		try {
			setState(49);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				((AnyExpContext)_localctx).ad0 = arithmeticExp(0);
				((AnyExpContext)_localctx).value =  ((AnyExpContext)_localctx).ad0.value;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(46);
				((AnyExpContext)_localctx).or0 = booleanExp(0);
				((AnyExpContext)_localctx).value =  ((AnyExpContext)_localctx).or0.value;
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

	public static class BooleanExpContext extends ParserRuleContext {
		public Value value;
		public BooleanExpContext id87;
		public BooleanExpContext id97;
		public ArithmeticExpContext and31;
		public ArithmeticExpContext and32;
		public ArithmeticExpContext id77;
		public ArithmeticExpContext id79;
		public ComplexIdentifierContext id001;
		public PredicateContext pr2;
		public Token tr2;
		public BooleanExpContext id89;
		public List<BooleanExpContext> booleanExp() {
			return getRuleContexts(BooleanExpContext.class);
		}
		public BooleanExpContext booleanExp(int i) {
			return getRuleContext(BooleanExpContext.class,i);
		}
		public TerminalNode Equals() { return getToken(LambdaParser.Equals, 0); }
		public List<ArithmeticExpContext> arithmeticExp() {
			return getRuleContexts(ArithmeticExpContext.class);
		}
		public ArithmeticExpContext arithmeticExp(int i) {
			return getRuleContext(ArithmeticExpContext.class,i);
		}
		public TerminalNode NotEquals() { return getToken(LambdaParser.NotEquals, 0); }
		public TerminalNode LessOrEquals() { return getToken(LambdaParser.LessOrEquals, 0); }
		public TerminalNode Less() { return getToken(LambdaParser.Less, 0); }
		public TerminalNode MoreOrEquals() { return getToken(LambdaParser.MoreOrEquals, 0); }
		public TerminalNode More() { return getToken(LambdaParser.More, 0); }
		public TerminalNode OPENPAR() { return getToken(LambdaParser.OPENPAR, 0); }
		public TerminalNode CLOSEDPAR() { return getToken(LambdaParser.CLOSEDPAR, 0); }
		public ComplexIdentifierContext complexIdentifier() {
			return getRuleContext(ComplexIdentifierContext.class,0);
		}
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TerminalNode True() { return getToken(LambdaParser.True, 0); }
		public TerminalNode False() { return getToken(LambdaParser.False, 0); }
		public TerminalNode Or() { return getToken(LambdaParser.Or, 0); }
		public TerminalNode And() { return getToken(LambdaParser.And, 0); }
		public BooleanExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterBooleanExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitBooleanExp(this);
		}
	}

	public final BooleanExpContext booleanExp() throws RecognitionException {
		return booleanExp(0);
	}

	private BooleanExpContext booleanExp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BooleanExpContext _localctx = new BooleanExpContext(_ctx, _parentState);
		BooleanExpContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_booleanExp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(52);
				match(T__1);
				setState(53);
				((BooleanExpContext)_localctx).id97 = booleanExp(6);
				((BooleanExpContext)_localctx).value =  ((BooleanExpContext)_localctx).id97.value.not();
				}
				break;
			case 2:
				{
				setState(56);
				((BooleanExpContext)_localctx).and31 = arithmeticExp(0);
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(57);
					match(Space);
					}
					}
					setState(62);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(63);
				match(Equals);
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(64);
					match(Space);
					}
					}
					setState(69);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(70);
				((BooleanExpContext)_localctx).and32 = arithmeticExp(0);
				((BooleanExpContext)_localctx).value =  new Value(((BooleanExpContext)_localctx).and31.value.equals(((BooleanExpContext)_localctx).and32.value));
				}
				break;
			case 3:
				{
				setState(73);
				((BooleanExpContext)_localctx).id77 = arithmeticExp(0);
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(74);
					match(Space);
					}
					}
					setState(79);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(80);
				match(NotEquals);
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(81);
					match(Space);
					}
					}
					setState(86);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(87);
				((BooleanExpContext)_localctx).id79 = arithmeticExp(0);
				((BooleanExpContext)_localctx).value =  new Value(!((BooleanExpContext)_localctx).id77.value.equals(((BooleanExpContext)_localctx).id79.value));
				}
				break;
			case 4:
				{
				setState(90);
				((BooleanExpContext)_localctx).id77 = arithmeticExp(0);
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(91);
					match(Space);
					}
					}
					setState(96);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(97);
				match(LessOrEquals);
				setState(101);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(98);
					match(Space);
					}
					}
					setState(103);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(104);
				((BooleanExpContext)_localctx).id79 = arithmeticExp(0);
				((BooleanExpContext)_localctx).value =  new Value(((BooleanExpContext)_localctx).id77.value.lessorequals(((BooleanExpContext)_localctx).id79.value));
				}
				break;
			case 5:
				{
				setState(107);
				((BooleanExpContext)_localctx).id77 = arithmeticExp(0);
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(108);
					match(Space);
					}
					}
					setState(113);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(114);
				match(Less);
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(115);
					match(Space);
					}
					}
					setState(120);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(121);
				((BooleanExpContext)_localctx).id79 = arithmeticExp(0);
				((BooleanExpContext)_localctx).value =  new Value(((BooleanExpContext)_localctx).id77.value.less(((BooleanExpContext)_localctx).id79.value));
				}
				break;
			case 6:
				{
				setState(124);
				((BooleanExpContext)_localctx).id77 = arithmeticExp(0);
				setState(128);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(125);
					match(Space);
					}
					}
					setState(130);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(131);
				match(MoreOrEquals);
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(132);
					match(Space);
					}
					}
					setState(137);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(138);
				((BooleanExpContext)_localctx).id79 = arithmeticExp(0);
				((BooleanExpContext)_localctx).value =  new Value(((BooleanExpContext)_localctx).id77.value.less(((BooleanExpContext)_localctx).id79.value).not());
				}
				break;
			case 7:
				{
				setState(141);
				((BooleanExpContext)_localctx).id77 = arithmeticExp(0);
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(142);
					match(Space);
					}
					}
					setState(147);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(148);
				match(More);
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(149);
					match(Space);
					}
					}
					setState(154);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(155);
				((BooleanExpContext)_localctx).id79 = arithmeticExp(0);
				((BooleanExpContext)_localctx).value =  new Value(((BooleanExpContext)_localctx).id77.value.lessorequals(((BooleanExpContext)_localctx).id79.value).not());
				}
				break;
			case 8:
				{
				setState(158);
				match(OPENPAR);
				setState(162);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(159);
					match(Space);
					}
					}
					setState(164);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(165);
				((BooleanExpContext)_localctx).id97 = booleanExp(0);
				setState(169);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(166);
					match(Space);
					}
					}
					setState(171);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(172);
				match(CLOSEDPAR);
				((BooleanExpContext)_localctx).value =  ((BooleanExpContext)_localctx).id97.value;
				}
				break;
			case 9:
				{
				setState(175);
				((BooleanExpContext)_localctx).id001 = complexIdentifier();
				((BooleanExpContext)_localctx).value =  ((BooleanExpContext)_localctx).id001.value;
				}
				break;
			case 10:
				{
				setState(178);
				((BooleanExpContext)_localctx).pr2 = predicate();
				((BooleanExpContext)_localctx).value =  ((BooleanExpContext)_localctx).pr2.value;
				}
				break;
			case 11:
				{
				setState(181);
				((BooleanExpContext)_localctx).tr2 = match(True);
				((BooleanExpContext)_localctx).value =  new Value(true);
				}
				break;
			case 12:
				{
				setState(183);
				((BooleanExpContext)_localctx).tr2 = match(False);
				((BooleanExpContext)_localctx).value =  new Value(false);
				}
				break;
			case 13:
				{
				setState(185);
				((BooleanExpContext)_localctx).pr2 = predicate();
				((BooleanExpContext)_localctx).value =  ((BooleanExpContext)_localctx).pr2.value;
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(226);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(224);
					switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
					case 1:
						{
						_localctx = new BooleanExpContext(_parentctx, _parentState);
						_localctx.id87 = _prevctx;
						_localctx.id87 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_booleanExp);
						setState(190);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(194);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(191);
							match(Space);
							}
							}
							setState(196);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(197);
						match(Or);
						setState(201);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(198);
							match(Space);
							}
							}
							setState(203);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(204);
						((BooleanExpContext)_localctx).id89 = booleanExp(10);
						((BooleanExpContext)_localctx).value =  ((BooleanExpContext)_localctx).id87.value.or(((BooleanExpContext)_localctx).id89.value);
						}
						break;
					case 2:
						{
						_localctx = new BooleanExpContext(_parentctx, _parentState);
						_localctx.id87 = _prevctx;
						_localctx.id87 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_booleanExp);
						setState(207);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(211);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(208);
							match(Space);
							}
							}
							setState(213);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(214);
						match(And);
						setState(218);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(215);
							match(Space);
							}
							}
							setState(220);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(221);
						((BooleanExpContext)_localctx).id89 = booleanExp(9);
						((BooleanExpContext)_localctx).value =  ((BooleanExpContext)_localctx).id87.value.and(((BooleanExpContext)_localctx).id89.value);
						}
						break;
					}
					} 
				}
				setState(228);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
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

	public static class ArithmeticExpContext extends ParserRuleContext {
		public Value value;
		public ArithmeticExpContext and31;
		public MultiplyExpContext and41;
		public MultiplyExpContext and32;
		public MultiplyExpContext multiplyExp() {
			return getRuleContext(MultiplyExpContext.class,0);
		}
		public ArithmeticExpContext arithmeticExp() {
			return getRuleContext(ArithmeticExpContext.class,0);
		}
		public ArithmeticExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterArithmeticExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitArithmeticExp(this);
		}
	}

	public final ArithmeticExpContext arithmeticExp() throws RecognitionException {
		return arithmeticExp(0);
	}

	private ArithmeticExpContext arithmeticExp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArithmeticExpContext _localctx = new ArithmeticExpContext(_ctx, _parentState);
		ArithmeticExpContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_arithmeticExp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(230);
			((ArithmeticExpContext)_localctx).and41 = multiplyExp(0);
			((ArithmeticExpContext)_localctx).value =  ((ArithmeticExpContext)_localctx).and41.value;
			}
			_ctx.stop = _input.LT(-1);
			setState(269);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(267);
					switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
					case 1:
						{
						_localctx = new ArithmeticExpContext(_parentctx, _parentState);
						_localctx.and31 = _prevctx;
						_localctx.and31 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_arithmeticExp);
						setState(233);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(237);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(234);
							match(Space);
							}
							}
							setState(239);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(240);
						match(T__2);
						setState(244);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(241);
							match(Space);
							}
							}
							setState(246);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(247);
						((ArithmeticExpContext)_localctx).and32 = multiplyExp(0);
						((ArithmeticExpContext)_localctx).value =  ((ArithmeticExpContext)_localctx).and31.value.plus(((ArithmeticExpContext)_localctx).and32.value);
						}
						break;
					case 2:
						{
						_localctx = new ArithmeticExpContext(_parentctx, _parentState);
						_localctx.and31 = _prevctx;
						_localctx.and31 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_arithmeticExp);
						setState(250);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(254);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(251);
							match(Space);
							}
							}
							setState(256);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(257);
						match(T__3);
						setState(261);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(258);
							match(Space);
							}
							}
							setState(263);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(264);
						((ArithmeticExpContext)_localctx).and32 = multiplyExp(0);
						((ArithmeticExpContext)_localctx).value =  ((ArithmeticExpContext)_localctx).and31.value.minus(((ArithmeticExpContext)_localctx).and32.value);
						}
						break;
					}
					} 
				}
				setState(271);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
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

	public static class MultiplyExpContext extends ParserRuleContext {
		public Value value;
		public MultiplyExpContext mu35;
		public AtomMathExpContext at35;
		public AtomMathExpContext at41;
		public AtomMathExpContext atomMathExp() {
			return getRuleContext(AtomMathExpContext.class,0);
		}
		public MultiplyExpContext multiplyExp() {
			return getRuleContext(MultiplyExpContext.class,0);
		}
		public MultiplyExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplyExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterMultiplyExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitMultiplyExp(this);
		}
	}

	public final MultiplyExpContext multiplyExp() throws RecognitionException {
		return multiplyExp(0);
	}

	private MultiplyExpContext multiplyExp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MultiplyExpContext _localctx = new MultiplyExpContext(_ctx, _parentState);
		MultiplyExpContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_multiplyExp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(273);
			((MultiplyExpContext)_localctx).at35 = atomMathExp();
			((MultiplyExpContext)_localctx).value = ((MultiplyExpContext)_localctx).at35.value;
			}
			_ctx.stop = _input.LT(-1);
			setState(312);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(310);
					switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplyExpContext(_parentctx, _parentState);
						_localctx.mu35 = _prevctx;
						_localctx.mu35 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_multiplyExp);
						setState(276);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(280);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(277);
							match(Space);
							}
							}
							setState(282);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(283);
						match(T__4);
						setState(287);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(284);
							match(Space);
							}
							}
							setState(289);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(290);
						((MultiplyExpContext)_localctx).at41 = atomMathExp();
						((MultiplyExpContext)_localctx).value = ((MultiplyExpContext)_localctx).mu35.value.multiplyBy(((MultiplyExpContext)_localctx).at41.value);
						}
						break;
					case 2:
						{
						_localctx = new MultiplyExpContext(_parentctx, _parentState);
						_localctx.mu35 = _prevctx;
						_localctx.mu35 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_multiplyExp);
						setState(293);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(297);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(294);
							match(Space);
							}
							}
							setState(299);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(300);
						match(T__5);
						setState(304);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==Space) {
							{
							{
							setState(301);
							match(Space);
							}
							}
							setState(306);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(307);
						((MultiplyExpContext)_localctx).at41 = atomMathExp();
						((MultiplyExpContext)_localctx).value = ((MultiplyExpContext)_localctx).mu35.value.diviseBy(((MultiplyExpContext)_localctx).at41.value);
						}
						break;
					}
					} 
				}
				setState(314);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
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

	public static class AtomMathExpContext extends ParserRuleContext {
		public Value value;
		public Token nu0;
		public Token st0;
		public ComplexIdentifierContext id2;
		public FunctionContext fu2;
		public ArithmeticExpContext ad1;
		public TerminalNode Number() { return getToken(LambdaParser.Number, 0); }
		public TerminalNode String() { return getToken(LambdaParser.String, 0); }
		public ComplexIdentifierContext complexIdentifier() {
			return getRuleContext(ComplexIdentifierContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public ArithmeticExpContext arithmeticExp() {
			return getRuleContext(ArithmeticExpContext.class,0);
		}
		public AtomMathExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomMathExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterAtomMathExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitAtomMathExp(this);
		}
	}

	public final AtomMathExpContext atomMathExp() throws RecognitionException {
		AtomMathExpContext _localctx = new AtomMathExpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_atomMathExp);
		int _la;
		try {
			setState(342);
			switch (_input.LA(1)) {
			case Number:
				enterOuterAlt(_localctx, 1);
				{
				setState(315);
				((AtomMathExpContext)_localctx).nu0 = match(Number);
				((AtomMathExpContext)_localctx).value =  new Value(Double.parseDouble((((AtomMathExpContext)_localctx).nu0!=null?((AtomMathExpContext)_localctx).nu0.getText():null)));
				}
				break;
			case String:
				enterOuterAlt(_localctx, 2);
				{
				setState(317);
				((AtomMathExpContext)_localctx).st0 = match(String);
				((AtomMathExpContext)_localctx).value =  new Value((((AtomMathExpContext)_localctx).st0!=null?((AtomMathExpContext)_localctx).st0.getText():null).substring(3).substring(0,(((AtomMathExpContext)_localctx).st0!=null?((AtomMathExpContext)_localctx).st0.getText():null).length()-6));
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 3);
				{
				setState(319);
				((AtomMathExpContext)_localctx).id2 = complexIdentifier();
				((AtomMathExpContext)_localctx).value =  ((AtomMathExpContext)_localctx).id2.value;
				}
				break;
			case FTOKEN:
				enterOuterAlt(_localctx, 4);
				{
				setState(322);
				((AtomMathExpContext)_localctx).fu2 = function();
				((AtomMathExpContext)_localctx).value =  ((AtomMathExpContext)_localctx).fu2.value;
				}
				break;
			case OPENPAR:
				enterOuterAlt(_localctx, 5);
				{
				setState(325);
				match(OPENPAR);
				setState(329);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(326);
					match(Space);
					}
					}
					setState(331);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(332);
				((AtomMathExpContext)_localctx).ad1 = arithmeticExp(0);
				setState(336);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(333);
					match(Space);
					}
					}
					setState(338);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(339);
				match(CLOSEDPAR);
				((AtomMathExpContext)_localctx).value =  ((AtomMathExpContext)_localctx).ad1.value;
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
		public Token id51;
		public Token nu52;
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
		int _la;
		try {
			setState(363);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(344);
				((ComplexIdentifierContext)_localctx).id51 = match(Identifier);
				{
				setState(345);
				match(OPENBRACKET);
				setState(349);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(346);
					match(Space);
					}
					}
					setState(351);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(352);
				((ComplexIdentifierContext)_localctx).nu52 = match(Number);
				setState(356);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Space) {
					{
					{
					setState(353);
					match(Space);
					}
					}
					setState(358);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(359);
				match(CLOSEDBRACKET);
				((ComplexIdentifierContext)_localctx).value =  new Value(((List<?>)inputValue.value).get(Integer.parseInt((((ComplexIdentifierContext)_localctx).nu52!=null?((ComplexIdentifierContext)_localctx).nu52.getText():null))));
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(361);
				((ComplexIdentifierContext)_localctx).id51 = match(Identifier);
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
		public ArithmeticExpContext ad80;
		public TerminalNode OPENPAR() { return getToken(LambdaParser.OPENPAR, 0); }
		public TerminalNode CLOSEDPAR() { return getToken(LambdaParser.CLOSEDPAR, 0); }
		public TerminalNode FTOKEN() { return getToken(LambdaParser.FTOKEN, 0); }
		public ArithmeticExpContext arithmeticExp() {
			return getRuleContext(ArithmeticExpContext.class,0);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
			((FunctionContext)_localctx).ft80 = match(FTOKEN);
			setState(366);
			match(OPENPAR);
			setState(370);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Space) {
				{
				{
				setState(367);
				match(Space);
				}
				}
				setState(372);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(373);
			((FunctionContext)_localctx).ad80 = arithmeticExp(0);
			((FunctionContext)_localctx).value =  new Value(inputFunction[Integer.parseInt((((FunctionContext)_localctx).ft80!=null?((FunctionContext)_localctx).ft80.getText():null).substring(1))].apply(((FunctionContext)_localctx).ad80.value.value));
			setState(378);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Space) {
				{
				{
				setState(375);
				match(Space);
				}
				}
				setState(380);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(381);
			match(CLOSEDPAR);
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

	public static class PredicateContext extends ParserRuleContext {
		public Value value;
		public Token pt90;
		public BooleanExpContext ad90;
		public TerminalNode OPENPAR() { return getToken(LambdaParser.OPENPAR, 0); }
		public TerminalNode CLOSEDPAR() { return getToken(LambdaParser.CLOSEDPAR, 0); }
		public TerminalNode PTOKEN() { return getToken(LambdaParser.PTOKEN, 0); }
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).enterPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LambdaListener ) ((LambdaListener)listener).exitPredicate(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_predicate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(383);
			((PredicateContext)_localctx).pt90 = match(PTOKEN);
			setState(384);
			match(OPENPAR);
			setState(388);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Space) {
				{
				{
				setState(385);
				match(Space);
				}
				}
				setState(390);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(391);
			((PredicateContext)_localctx).ad90 = booleanExp(0);
			((PredicateContext)_localctx).value =  new Value(inputPredicate[Integer.parseInt((((PredicateContext)_localctx).pt90!=null?((PredicateContext)_localctx).pt90.getText():null).substring(1))].apply(((PredicateContext)_localctx).ad90.value.value));
			setState(396);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Space) {
				{
				{
				setState(393);
				match(Space);
				}
				}
				setState(398);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(399);
			match(CLOSEDPAR);
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
			return booleanExp_sempred((BooleanExpContext)_localctx, predIndex);
		case 4:
			return arithmeticExp_sempred((ArithmeticExpContext)_localctx, predIndex);
		case 5:
			return multiplyExp_sempred((MultiplyExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean booleanExp_sempred(BooleanExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 9);
		case 1:
			return precpred(_ctx, 8);
		}
		return true;
	}
	private boolean arithmeticExp_sempred(ArithmeticExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 3);
		case 3:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean multiplyExp_sempred(MultiplyExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 3);
		case 5:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\37\u0194\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\3\2\3\2\3\2\3\2\5\2\33\n\2\3\3\3\3\7\3\37\n\3\f\3\16\3\"\13\3\3"+
		"\3\3\3\7\3&\n\3\f\3\16\3)\13\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4"+
		"\64\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5=\n\5\f\5\16\5@\13\5\3\5\3\5\7"+
		"\5D\n\5\f\5\16\5G\13\5\3\5\3\5\3\5\3\5\3\5\7\5N\n\5\f\5\16\5Q\13\5\3\5"+
		"\3\5\7\5U\n\5\f\5\16\5X\13\5\3\5\3\5\3\5\3\5\3\5\7\5_\n\5\f\5\16\5b\13"+
		"\5\3\5\3\5\7\5f\n\5\f\5\16\5i\13\5\3\5\3\5\3\5\3\5\3\5\7\5p\n\5\f\5\16"+
		"\5s\13\5\3\5\3\5\7\5w\n\5\f\5\16\5z\13\5\3\5\3\5\3\5\3\5\3\5\7\5\u0081"+
		"\n\5\f\5\16\5\u0084\13\5\3\5\3\5\7\5\u0088\n\5\f\5\16\5\u008b\13\5\3\5"+
		"\3\5\3\5\3\5\3\5\7\5\u0092\n\5\f\5\16\5\u0095\13\5\3\5\3\5\7\5\u0099\n"+
		"\5\f\5\16\5\u009c\13\5\3\5\3\5\3\5\3\5\3\5\7\5\u00a3\n\5\f\5\16\5\u00a6"+
		"\13\5\3\5\3\5\7\5\u00aa\n\5\f\5\16\5\u00ad\13\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u00bf\n\5\3\5\3\5\7\5\u00c3"+
		"\n\5\f\5\16\5\u00c6\13\5\3\5\3\5\7\5\u00ca\n\5\f\5\16\5\u00cd\13\5\3\5"+
		"\3\5\3\5\3\5\3\5\7\5\u00d4\n\5\f\5\16\5\u00d7\13\5\3\5\3\5\7\5\u00db\n"+
		"\5\f\5\16\5\u00de\13\5\3\5\3\5\3\5\7\5\u00e3\n\5\f\5\16\5\u00e6\13\5\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\7\6\u00ee\n\6\f\6\16\6\u00f1\13\6\3\6\3\6\7\6\u00f5"+
		"\n\6\f\6\16\6\u00f8\13\6\3\6\3\6\3\6\3\6\3\6\7\6\u00ff\n\6\f\6\16\6\u0102"+
		"\13\6\3\6\3\6\7\6\u0106\n\6\f\6\16\6\u0109\13\6\3\6\3\6\3\6\7\6\u010e"+
		"\n\6\f\6\16\6\u0111\13\6\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u0119\n\7\f\7\16"+
		"\7\u011c\13\7\3\7\3\7\7\7\u0120\n\7\f\7\16\7\u0123\13\7\3\7\3\7\3\7\3"+
		"\7\3\7\7\7\u012a\n\7\f\7\16\7\u012d\13\7\3\7\3\7\7\7\u0131\n\7\f\7\16"+
		"\7\u0134\13\7\3\7\3\7\3\7\7\7\u0139\n\7\f\7\16\7\u013c\13\7\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u014a\n\b\f\b\16\b\u014d\13"+
		"\b\3\b\3\b\7\b\u0151\n\b\f\b\16\b\u0154\13\b\3\b\3\b\3\b\5\b\u0159\n\b"+
		"\3\t\3\t\3\t\7\t\u015e\n\t\f\t\16\t\u0161\13\t\3\t\3\t\7\t\u0165\n\t\f"+
		"\t\16\t\u0168\13\t\3\t\3\t\3\t\3\t\5\t\u016e\n\t\3\n\3\n\3\n\7\n\u0173"+
		"\n\n\f\n\16\n\u0176\13\n\3\n\3\n\3\n\7\n\u017b\n\n\f\n\16\n\u017e\13\n"+
		"\3\n\3\n\3\13\3\13\3\13\7\13\u0185\n\13\f\13\16\13\u0188\13\13\3\13\3"+
		"\13\3\13\7\13\u018d\n\13\f\13\16\13\u0190\13\13\3\13\3\13\3\13\2\5\b\n"+
		"\f\f\2\4\6\b\n\f\16\20\22\24\2\2\u01c6\2\32\3\2\2\2\4\34\3\2\2\2\6\63"+
		"\3\2\2\2\b\u00be\3\2\2\2\n\u00e7\3\2\2\2\f\u0112\3\2\2\2\16\u0158\3\2"+
		"\2\2\20\u016d\3\2\2\2\22\u016f\3\2\2\2\24\u0181\3\2\2\2\26\27\5\4\3\2"+
		"\27\30\b\2\1\2\30\33\3\2\2\2\31\33\7\2\2\3\32\26\3\2\2\2\32\31\3\2\2\2"+
		"\33\3\3\2\2\2\34 \7\31\2\2\35\37\7\34\2\2\36\35\3\2\2\2\37\"\3\2\2\2 "+
		"\36\3\2\2\2 !\3\2\2\2!#\3\2\2\2\" \3\2\2\2#\'\7\3\2\2$&\7\34\2\2%$\3\2"+
		"\2\2&)\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2(*\3\2\2\2)\'\3\2\2\2*+\5\6\4\2+,"+
		"\b\3\1\2,\5\3\2\2\2-.\5\n\6\2./\b\4\1\2/\64\3\2\2\2\60\61\5\b\5\2\61\62"+
		"\b\4\1\2\62\64\3\2\2\2\63-\3\2\2\2\63\60\3\2\2\2\64\7\3\2\2\2\65\66\b"+
		"\5\1\2\66\67\7\4\2\2\678\5\b\5\b89\b\5\1\29\u00bf\3\2\2\2:>\5\n\6\2;="+
		"\7\34\2\2<;\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2\2\2?A\3\2\2\2@>\3\2\2\2"+
		"AE\7\13\2\2BD\7\34\2\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2FH\3\2\2"+
		"\2GE\3\2\2\2HI\5\n\6\2IJ\b\5\1\2J\u00bf\3\2\2\2KO\5\n\6\2LN\7\34\2\2M"+
		"L\3\2\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2\2\2PR\3\2\2\2QO\3\2\2\2RV\7\20\2\2"+
		"SU\7\34\2\2TS\3\2\2\2UX\3\2\2\2VT\3\2\2\2VW\3\2\2\2WY\3\2\2\2XV\3\2\2"+
		"\2YZ\5\n\6\2Z[\b\5\1\2[\u00bf\3\2\2\2\\`\5\n\6\2]_\7\34\2\2^]\3\2\2\2"+
		"_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2ac\3\2\2\2b`\3\2\2\2cg\7\f\2\2df\7\34\2"+
		"\2ed\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2\2hj\3\2\2\2ig\3\2\2\2jk\5\n\6"+
		"\2kl\b\5\1\2l\u00bf\3\2\2\2mq\5\n\6\2np\7\34\2\2on\3\2\2\2ps\3\2\2\2q"+
		"o\3\2\2\2qr\3\2\2\2rt\3\2\2\2sq\3\2\2\2tx\7\r\2\2uw\7\34\2\2vu\3\2\2\2"+
		"wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y{\3\2\2\2zx\3\2\2\2{|\5\n\6\2|}\b\5\1\2"+
		"}\u00bf\3\2\2\2~\u0082\5\n\6\2\177\u0081\7\34\2\2\u0080\177\3\2\2\2\u0081"+
		"\u0084\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0085\3\2"+
		"\2\2\u0084\u0082\3\2\2\2\u0085\u0089\7\16\2\2\u0086\u0088\7\34\2\2\u0087"+
		"\u0086\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2"+
		"\2\2\u008a\u008c\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008d\5\n\6\2\u008d"+
		"\u008e\b\5\1\2\u008e\u00bf\3\2\2\2\u008f\u0093\5\n\6\2\u0090\u0092\7\34"+
		"\2\2\u0091\u0090\3\2\2\2\u0092\u0095\3\2\2\2\u0093\u0091\3\2\2\2\u0093"+
		"\u0094\3\2\2\2\u0094\u0096\3\2\2\2\u0095\u0093\3\2\2\2\u0096\u009a\7\17"+
		"\2\2\u0097\u0099\7\34\2\2\u0098\u0097\3\2\2\2\u0099\u009c\3\2\2\2\u009a"+
		"\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009d\3\2\2\2\u009c\u009a\3\2"+
		"\2\2\u009d\u009e\5\n\6\2\u009e\u009f\b\5\1\2\u009f\u00bf\3\2\2\2\u00a0"+
		"\u00a4\7\27\2\2\u00a1\u00a3\7\34\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a6\3"+
		"\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a7\3\2\2\2\u00a6"+
		"\u00a4\3\2\2\2\u00a7\u00ab\5\b\5\2\u00a8\u00aa\7\34\2\2\u00a9\u00a8\3"+
		"\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac"+
		"\u00ae\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00af\7\30\2\2\u00af\u00b0\b"+
		"\5\1\2\u00b0\u00bf\3\2\2\2\u00b1\u00b2\5\20\t\2\u00b2\u00b3\b\5\1\2\u00b3"+
		"\u00bf\3\2\2\2\u00b4\u00b5\5\24\13\2\u00b5\u00b6\b\5\1\2\u00b6\u00bf\3"+
		"\2\2\2\u00b7\u00b8\7\t\2\2\u00b8\u00bf\b\5\1\2\u00b9\u00ba\7\n\2\2\u00ba"+
		"\u00bf\b\5\1\2\u00bb\u00bc\5\24\13\2\u00bc\u00bd\b\5\1\2\u00bd\u00bf\3"+
		"\2\2\2\u00be\65\3\2\2\2\u00be:\3\2\2\2\u00beK\3\2\2\2\u00be\\\3\2\2\2"+
		"\u00bem\3\2\2\2\u00be~\3\2\2\2\u00be\u008f\3\2\2\2\u00be\u00a0\3\2\2\2"+
		"\u00be\u00b1\3\2\2\2\u00be\u00b4\3\2\2\2\u00be\u00b7\3\2\2\2\u00be\u00b9"+
		"\3\2\2\2\u00be\u00bb\3\2\2\2\u00bf\u00e4\3\2\2\2\u00c0\u00c4\f\13\2\2"+
		"\u00c1\u00c3\7\34\2\2\u00c2\u00c1\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4\u00c2"+
		"\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c7\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c7"+
		"\u00cb\7\22\2\2\u00c8\u00ca\7\34\2\2\u00c9\u00c8\3\2\2\2\u00ca\u00cd\3"+
		"\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00ce\3\2\2\2\u00cd"+
		"\u00cb\3\2\2\2\u00ce\u00cf\5\b\5\f\u00cf\u00d0\b\5\1\2\u00d0\u00e3\3\2"+
		"\2\2\u00d1\u00d5\f\n\2\2\u00d2\u00d4\7\34\2\2\u00d3\u00d2\3\2\2\2\u00d4"+
		"\u00d7\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d8\3\2"+
		"\2\2\u00d7\u00d5\3\2\2\2\u00d8\u00dc\7\21\2\2\u00d9\u00db\7\34\2\2\u00da"+
		"\u00d9\3\2\2\2\u00db\u00de\3\2\2\2\u00dc\u00da\3\2\2\2\u00dc\u00dd\3\2"+
		"\2\2\u00dd\u00df\3\2\2\2\u00de\u00dc\3\2\2\2\u00df\u00e0\5\b\5\13\u00e0"+
		"\u00e1\b\5\1\2\u00e1\u00e3\3\2\2\2\u00e2\u00c0\3\2\2\2\u00e2\u00d1\3\2"+
		"\2\2\u00e3\u00e6\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5"+
		"\t\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e7\u00e8\b\6\1\2\u00e8\u00e9\5\f\7\2"+
		"\u00e9\u00ea\b\6\1\2\u00ea\u010f\3\2\2\2\u00eb\u00ef\f\5\2\2\u00ec\u00ee"+
		"\7\34\2\2\u00ed\u00ec\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed\3\2\2\2"+
		"\u00ef\u00f0\3\2\2\2\u00f0\u00f2\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00f6"+
		"\7\5\2\2\u00f3\u00f5\7\34\2\2\u00f4\u00f3\3\2\2\2\u00f5\u00f8\3\2\2\2"+
		"\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f9\3\2\2\2\u00f8\u00f6"+
		"\3\2\2\2\u00f9\u00fa\5\f\7\2\u00fa\u00fb\b\6\1\2\u00fb\u010e\3\2\2\2\u00fc"+
		"\u0100\f\4\2\2\u00fd\u00ff\7\34\2\2\u00fe\u00fd\3\2\2\2\u00ff\u0102\3"+
		"\2\2\2\u0100\u00fe\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u0103\3\2\2\2\u0102"+
		"\u0100\3\2\2\2\u0103\u0107\7\6\2\2\u0104\u0106\7\34\2\2\u0105\u0104\3"+
		"\2\2\2\u0106\u0109\3\2\2\2\u0107\u0105\3\2\2\2\u0107\u0108\3\2\2\2\u0108"+
		"\u010a\3\2\2\2\u0109\u0107\3\2\2\2\u010a\u010b\5\f\7\2\u010b\u010c\b\6"+
		"\1\2\u010c\u010e\3\2\2\2\u010d\u00eb\3\2\2\2\u010d\u00fc\3\2\2\2\u010e"+
		"\u0111\3\2\2\2\u010f\u010d\3\2\2\2\u010f\u0110\3\2\2\2\u0110\13\3\2\2"+
		"\2\u0111\u010f\3\2\2\2\u0112\u0113\b\7\1\2\u0113\u0114\5\16\b\2\u0114"+
		"\u0115\b\7\1\2\u0115\u013a\3\2\2\2\u0116\u011a\f\5\2\2\u0117\u0119\7\34"+
		"\2\2\u0118\u0117\3\2\2\2\u0119\u011c\3\2\2\2\u011a\u0118\3\2\2\2\u011a"+
		"\u011b\3\2\2\2\u011b\u011d\3\2\2\2\u011c\u011a\3\2\2\2\u011d\u0121\7\7"+
		"\2\2\u011e\u0120\7\34\2\2\u011f\u011e\3\2\2\2\u0120\u0123\3\2\2\2\u0121"+
		"\u011f\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0124\3\2\2\2\u0123\u0121\3\2"+
		"\2\2\u0124\u0125\5\16\b\2\u0125\u0126\b\7\1\2\u0126\u0139\3\2\2\2\u0127"+
		"\u012b\f\4\2\2\u0128\u012a\7\34\2\2\u0129\u0128\3\2\2\2\u012a\u012d\3"+
		"\2\2\2\u012b\u0129\3\2\2\2\u012b\u012c\3\2\2\2\u012c\u012e\3\2\2\2\u012d"+
		"\u012b\3\2\2\2\u012e\u0132\7\b\2\2\u012f\u0131\7\34\2\2\u0130\u012f\3"+
		"\2\2\2\u0131\u0134\3\2\2\2\u0132\u0130\3\2\2\2\u0132\u0133\3\2\2\2\u0133"+
		"\u0135\3\2\2\2\u0134\u0132\3\2\2\2\u0135\u0136\5\16\b\2\u0136\u0137\b"+
		"\7\1\2\u0137\u0139\3\2\2\2\u0138\u0116\3\2\2\2\u0138\u0127\3\2\2\2\u0139"+
		"\u013c\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u013b\3\2\2\2\u013b\r\3\2\2\2"+
		"\u013c\u013a\3\2\2\2\u013d\u013e\7\32\2\2\u013e\u0159\b\b\1\2\u013f\u0140"+
		"\7\33\2\2\u0140\u0159\b\b\1\2\u0141\u0142\5\20\t\2\u0142\u0143\b\b\1\2"+
		"\u0143\u0159\3\2\2\2\u0144\u0145\5\22\n\2\u0145\u0146\b\b\1\2\u0146\u0159"+
		"\3\2\2\2\u0147\u014b\7\27\2\2\u0148\u014a\7\34\2\2\u0149\u0148\3\2\2\2"+
		"\u014a\u014d\3\2\2\2\u014b\u0149\3\2\2\2\u014b\u014c\3\2\2\2\u014c\u014e"+
		"\3\2\2\2\u014d\u014b\3\2\2\2\u014e\u0152\5\n\6\2\u014f\u0151\7\34\2\2"+
		"\u0150\u014f\3\2\2\2\u0151\u0154\3\2\2\2\u0152\u0150\3\2\2\2\u0152\u0153"+
		"\3\2\2\2\u0153\u0155\3\2\2\2\u0154\u0152\3\2\2\2\u0155\u0156\7\30\2\2"+
		"\u0156\u0157\b\b\1\2\u0157\u0159\3\2\2\2\u0158\u013d\3\2\2\2\u0158\u013f"+
		"\3\2\2\2\u0158\u0141\3\2\2\2\u0158\u0144\3\2\2\2\u0158\u0147\3\2\2\2\u0159"+
		"\17\3\2\2\2\u015a\u015b\7\31\2\2\u015b\u015f\7\35\2\2\u015c\u015e\7\34"+
		"\2\2\u015d\u015c\3\2\2\2\u015e\u0161\3\2\2\2\u015f\u015d\3\2\2\2\u015f"+
		"\u0160\3\2\2\2\u0160\u0162\3\2\2\2\u0161\u015f\3\2\2\2\u0162\u0166\7\32"+
		"\2\2\u0163\u0165\7\34\2\2\u0164\u0163\3\2\2\2\u0165\u0168\3\2\2\2\u0166"+
		"\u0164\3\2\2\2\u0166\u0167\3\2\2\2\u0167\u0169\3\2\2\2\u0168\u0166\3\2"+
		"\2\2\u0169\u016a\7\36\2\2\u016a\u016e\b\t\1\2\u016b\u016c\7\31\2\2\u016c"+
		"\u016e\b\t\1\2\u016d\u015a\3\2\2\2\u016d\u016b\3\2\2\2\u016e\21\3\2\2"+
		"\2\u016f\u0170\7\25\2\2\u0170\u0174\7\27\2\2\u0171\u0173\7\34\2\2\u0172"+
		"\u0171\3\2\2\2\u0173\u0176\3\2\2\2\u0174\u0172\3\2\2\2\u0174\u0175\3\2"+
		"\2\2\u0175\u0177\3\2\2\2\u0176\u0174\3\2\2\2\u0177\u0178\5\n\6\2\u0178"+
		"\u017c\b\n\1\2\u0179\u017b\7\34\2\2\u017a\u0179\3\2\2\2\u017b\u017e\3"+
		"\2\2\2\u017c\u017a\3\2\2\2\u017c\u017d\3\2\2\2\u017d\u017f\3\2\2\2\u017e"+
		"\u017c\3\2\2\2\u017f\u0180\7\30\2\2\u0180\23\3\2\2\2\u0181\u0182\7\26"+
		"\2\2\u0182\u0186\7\27\2\2\u0183\u0185\7\34\2\2\u0184\u0183\3\2\2\2\u0185"+
		"\u0188\3\2\2\2\u0186\u0184\3\2\2\2\u0186\u0187\3\2\2\2\u0187\u0189\3\2"+
		"\2\2\u0188\u0186\3\2\2\2\u0189\u018a\5\b\5\2\u018a\u018e\b\13\1\2\u018b"+
		"\u018d\7\34\2\2\u018c\u018b\3\2\2\2\u018d\u0190\3\2\2\2\u018e\u018c\3"+
		"\2\2\2\u018e\u018f\3\2\2\2\u018f\u0191\3\2\2\2\u0190\u018e\3\2\2\2\u0191"+
		"\u0192\7\30\2\2\u0192\25\3\2\2\2\61\32 \'\63>EOV`gqx\u0082\u0089\u0093"+
		"\u009a\u00a4\u00ab\u00be\u00c4\u00cb\u00d5\u00dc\u00e2\u00e4\u00ef\u00f6"+
		"\u0100\u0107\u010d\u010f\u011a\u0121\u012b\u0132\u0138\u013a\u014b\u0152"+
		"\u0158\u015f\u0166\u016d\u0174\u017c\u0186\u018e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}