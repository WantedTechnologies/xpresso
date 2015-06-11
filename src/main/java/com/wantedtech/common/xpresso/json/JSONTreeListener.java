// Generated from JSONTree.g by ANTLR 4.5

package com.wantedtech.common.xpresso.json;

import com.wantedtech.common.xpresso.types.*;
import com.wantedtech.common.xpresso.x;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;


import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JSONTreeParser}.
 */
public interface JSONTreeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JSONTreeParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(JSONTreeParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONTreeParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(JSONTreeParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONTreeParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(JSONTreeParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONTreeParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(JSONTreeParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONTreeParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(JSONTreeParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONTreeParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(JSONTreeParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONTreeParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(JSONTreeParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONTreeParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(JSONTreeParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONTreeParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(JSONTreeParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONTreeParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(JSONTreeParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONTreeParser#elements}.
	 * @param ctx the parse tree
	 */
	void enterElements(JSONTreeParser.ElementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONTreeParser#elements}.
	 * @param ctx the parse tree
	 */
	void exitElements(JSONTreeParser.ElementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONTreeParser#members}.
	 * @param ctx the parse tree
	 */
	void enterMembers(JSONTreeParser.MembersContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONTreeParser#members}.
	 * @param ctx the parse tree
	 */
	void exitMembers(JSONTreeParser.MembersContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONTreeParser#pair}.
	 * @param ctx the parse tree
	 */
	void enterPair(JSONTreeParser.PairContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONTreeParser#pair}.
	 * @param ctx the parse tree
	 */
	void exitPair(JSONTreeParser.PairContext ctx);
}