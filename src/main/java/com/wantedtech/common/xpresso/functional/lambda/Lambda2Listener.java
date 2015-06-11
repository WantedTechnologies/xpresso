// Generated from Lambda2.g by ANTLR 4.5

package com.wantedtech.common.xpresso.functional.lambda;

import java.util.*;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.experimental.helpers.Helpers;
import com.wantedtech.common.xpresso.types.tuples.tuple2;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.x;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Lambda2Parser}.
 */
public interface Lambda2Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Lambda2Parser#eval}.
	 * @param ctx the parse tree
	 */
	void enterEval(Lambda2Parser.EvalContext ctx);
	/**
	 * Exit a parse tree produced by {@link Lambda2Parser#eval}.
	 * @param ctx the parse tree
	 */
	void exitEval(Lambda2Parser.EvalContext ctx);
	/**
	 * Enter a parse tree produced by {@link Lambda2Parser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(Lambda2Parser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Lambda2Parser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(Lambda2Parser.LambdaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Lambda2Parser#anyExpression}.
	 * @param ctx the parse tree
	 */
	void enterAnyExpression(Lambda2Parser.AnyExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Lambda2Parser#anyExpression}.
	 * @param ctx the parse tree
	 */
	void exitAnyExpression(Lambda2Parser.AnyExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Lambda2Parser#arithmeticExpressionE}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticExpressionE(Lambda2Parser.ArithmeticExpressionEContext ctx);
	/**
	 * Exit a parse tree produced by {@link Lambda2Parser#arithmeticExpressionE}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticExpressionE(Lambda2Parser.ArithmeticExpressionEContext ctx);
	/**
	 * Enter a parse tree produced by {@link Lambda2Parser#booleanExpressionB}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpressionB(Lambda2Parser.BooleanExpressionBContext ctx);
	/**
	 * Exit a parse tree produced by {@link Lambda2Parser#booleanExpressionB}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpressionB(Lambda2Parser.BooleanExpressionBContext ctx);
	/**
	 * Enter a parse tree produced by {@link Lambda2Parser#productExpressionT}.
	 * @param ctx the parse tree
	 */
	void enterProductExpressionT(Lambda2Parser.ProductExpressionTContext ctx);
	/**
	 * Exit a parse tree produced by {@link Lambda2Parser#productExpressionT}.
	 * @param ctx the parse tree
	 */
	void exitProductExpressionT(Lambda2Parser.ProductExpressionTContext ctx);
	/**
	 * Enter a parse tree produced by {@link Lambda2Parser#basicExpressionF}.
	 * @param ctx the parse tree
	 */
	void enterBasicExpressionF(Lambda2Parser.BasicExpressionFContext ctx);
	/**
	 * Exit a parse tree produced by {@link Lambda2Parser#basicExpressionF}.
	 * @param ctx the parse tree
	 */
	void exitBasicExpressionF(Lambda2Parser.BasicExpressionFContext ctx);
	/**
	 * Enter a parse tree produced by {@link Lambda2Parser#complexIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterComplexIdentifier(Lambda2Parser.ComplexIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link Lambda2Parser#complexIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitComplexIdentifier(Lambda2Parser.ComplexIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link Lambda2Parser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(Lambda2Parser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Lambda2Parser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(Lambda2Parser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Lambda2Parser#inputVars}.
	 * @param ctx the parse tree
	 */
	void enterInputVars(Lambda2Parser.InputVarsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Lambda2Parser#inputVars}.
	 * @param ctx the parse tree
	 */
	void exitInputVars(Lambda2Parser.InputVarsContext ctx);
}