grammar Lambda2;

@header {
package com.wantedtech.common.xpresso.functional.lambda;

import java.util.*;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.experimental.helpers.Helpers;
import com.wantedtech.common.xpresso.types.tuples.tuple2;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.x;
}

@members {

    Value inputValue;
    HashMap<String,Value> inputValues;
    Function<Object,?>[] inputFunction;
    ArrayList<String> inputVarNames;

}

/* This is the entry point of our parser. */
eval returns [Value value]
    :   exp00=lambdaExpression {$value = $exp00.value;}
    |   exp10=EOF
    ;

lambdaExpression returns [Value value]
    :   id10=inputVars {
                            inputVarNames = $id10.value;
                            inputValues = new HashMap<String,Value>();
                            if(x.len(inputVarNames) > 1 && inputValue.value instanceof Iterable<?>){
                                for (tuple2<Integer,String> index__var : x.enumerate(inputVarNames)){
                                    inputValues.put(index__var.value1,new Value(x.list((Iterable<?>)(inputValue.value)).get(index__var.value0)));
                                }
                            }else if(x.len(inputVarNames) > 1 && inputValue.value instanceof tuple){
                                for (tuple2<Integer,String> index__var : x.enumerate(inputVarNames)){
                                    inputValues.put(index__var.value1,new Value(((tuple)inputValue.value).get(index__var.value0)));
                                }
                            }else{
                                if(x.len(inputVarNames) != x.len(inputVarNames)){
                                    throw new IllegalArgumentException("Expected the input value of dimensionality "+x.len(inputVarNames) + " Got "+x.len(inputVarNames));
                                }
                                inputValues.put(inputVarNames.get(0),inputValue);
                            }
                         }
        ' '* ':' ' '* an10=anyExpression {$value = $an10.value;}
    ;

anyExpression returns [Value value]
    :   be20=booleanExpressionB {$value = $be20.value;}
    |   ae20=arithmeticExpressionE {$value = $ae20.value;}
    ;

arithmeticExpressionE returns [Value value]
    :   ae40=arithmeticExpressionE ' '* '+' ' '* pe40=productExpressionT {$value = $ae40.value.plus($pe40.value);}
    |   ae40=arithmeticExpressionE ' '* '-' ' '* pe40=productExpressionT {$value = $ae40.value.minus($pe40.value);}
    |   pe40=productExpressionT {$value = $pe40.value;}
    ;

booleanExpressionB returns [Value value]
    :   ae30=arithmeticExpressionE ' '* '=''=' ' '* ae31=arithmeticExpressionE {$value = new Value($ae30.value.equals($ae31.value));}
    |   ae30=arithmeticExpressionE ' '* '<=' ' '* ae31=arithmeticExpressionE {$value = $ae30.value.lessorequals($ae31.value);}
    |   ae30=arithmeticExpressionE ' '* '<' ' '* ae31=arithmeticExpressionE {$value = $ae30.value.less($ae31.value);}
    |   ae30=arithmeticExpressionE ' '* '>=' ' '* ae31=arithmeticExpressionE {$value = $ae30.value.less($ae31.value).not();}
    |   ae30=arithmeticExpressionE ' '* '>' ' '* ae31=arithmeticExpressionE {$value = $ae30.value.lessorequals($ae31.value).not();}
    |   ae30=arithmeticExpressionE ' '* '!''=' ' '* ae31=arithmeticExpressionE {$value = new Value(!$ae30.value.equals($ae31.value));}
    |   be30=booleanExpressionB ' '* And ' '* be31=booleanExpressionB {$value = $be30.value.and($be31.value);}
    |   be30=booleanExpressionB ' '* Or ' '* be31=booleanExpressionB {$value = $be30.value.or($be31.value);}
    |   '(' be30=booleanExpressionB ')'  {$value = $be30.value;}
    |   '!' be30=booleanExpressionB {$value = $be30.value.not();}
    |   ce30=complexIdentifier {$value = $ce30.value;}
    |   fu30=function {$value = $fu30.value;}
    ;

productExpressionT returns [Value value]
    :   pe50=productExpressionT ' '* '*' ' '* be50=basicExpressionF {$value = $pe50.value.multiplyBy($be50.value);}
    |   pe50=productExpressionT ' '* '/' ' '* be50=basicExpressionF {$value = $pe50.value.diviseBy($be50.value);}
    |   be50=basicExpressionF {$value = $be50.value;}
    ;

basicExpressionF returns [Value value]
    :   '(' ae60=arithmeticExpressionE ')' {$value = $ae60.value;}
    |   nu60=Number {
                        if(x.String(".").in($nu60.text))
                            $value = new Value(Double.parseDouble($nu60.text));
                        else
                            $value = new Value(Integer.parseInt($nu60.text));
                    }
    |   js60=JavaString {$value = new Value(x.String($js60.text).slice(1,-1));}
    |   ps60=PythonString {$value = new Value(x.String($ps60.text).slice(3,-3));}
    |   ci60=complexIdentifier {$value = $ci60.value;}
    |   fu60=function {$value = $fu60.value;}
    ;

complexIdentifier returns [Value value]
    :   id70=Identifier OPENBRACKET nu70=Number CLOSEDBRACKET {
                        if(!inputVarNames.contains($id70.text)) throw new IllegalArgumentException("Undefined variable: "+$id70.text);

                        if($nu70.text.contains("."))
                            throw new IllegalArgumentException("The index of the input variable has to be an integer.");
                        else if(Integer.parseInt($nu70.text) >= (x.len(inputValues.get($id70.text).value)))
                            throw new IndexOutOfBoundsException("Illegal dimension parameter: "+$nu70.text+". The input variable only has "+x.len(x.len(inputValues.get($id70.text)))+" dimensions.");

                        if(inputValues.get($id70.text).value instanceof Iterable<?>)
                            $value = new Value((x.list((Iterable<?>)(inputValues.get($id70.text).value)).get(Integer.parseInt($nu70.text))));
                        else
                            throw new IllegalArgumentException("The input variable is not multi-dimensional, you cannot use the [...] notation in your lambda expression.");
                      }
    |   id70=Identifier {$value = new Value(inputValues.get($id70.text));}
    ;

function returns [Value value]
    :   ft80=FTOK '(' ae80=anyExpression ')' {
                                                int funNum = Integer.parseInt(x.String($ft80.text).sliceFrom(1));
                                                if(funNum >= inputFunction.length)
                                                    throw new IllegalArgumentException("The unknown function "+$ft80.text+".");
                                                else
                                                    $value = new Value(inputFunction[funNum].apply($ae80.value.value));
                                             }
    ;

inputVars returns [ArrayList<String> value]
    :   iv90=Identifier {
                            ArrayList fields = Helpers.newArrayList();
                            fields.add($iv90.text);
                            $value = fields;
                        }
    |   iv92=inputVars ' '* ',' ' '* iv93=Identifier {ArrayList<String> fields = Helpers.newArrayList($iv92.value); fields.add($iv93.text); $value = fields;}
    ;

FTOK
    :   'f' [0-9]+
    ;

And
    :   '&' '&'
    ;

Or
    :   '|' '|'
    ;

Identifier
    :   [a-zA-Z]+ [0-9a-zA-Z]*
    ;

Number
    :   ('0'..'9')+ ('.' ('0'..'9')+)?
    ;

JavaString
    :   '"' '\u0000'..'\uFFFE'*? '"'
    ;

PythonString
    :   '\'' '\'' '\'' '\u0000'..'\uFFFE'*? '\'' '\'' '\''
    ;

Space
    :   ' '
    ;

OPENBRACKET
    :   '['
    ;

CLOSEDBRACKET
    :   ']'
    ;

WS  :   [\r\t\n]+ -> skip ;