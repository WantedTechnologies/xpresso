grammar JSON;

tokens {
	STRING, NUMBER, OBJECT, FIELD, ARRAY
}

@header {
package com.wantedtech.common.xpresso.json2;

import java.util.regex.Pattern;
  
}

// Optional step: Disable automatic error recovery
@members { 
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
} 
// Alter code generation so catch-clauses get replace with 
// this action. 
@rulecatch { 
catch (RecognitionException e) { 
throw e; 
} 
} 



value
	: string
	| number
	| object
	| array
	| TRUE
	| FALSE
	| NULL
	;

string
  : String
	;

// If you want to conform to the RFC, use a validating semantic predicate to check the result.
number
  : n=Number {Pattern.matches("(0|(-?[1-9]\\d*))(\\.\\d+)?", n.getText())}? 
    Exponent?
	;

object
  : '{' members? '}'
	;
	
array
  : '[' elements? ']'
	;

elements
  : value (COMMA value)*
	;
	
members	: pair (COMMA pair)*
	;
	 
pair
  : String ':' value
	;

Number	: '-'? Digit+ ( '.' Digit+)?;

Exponent: ('e'|'E') '-'? ('1'..'9') Digit*;

String 	:
	'"' ( EscapeSequence | ~('\u0000'..'\u001f' | '\\' | '\"' ) )* '"'
	;

WS : [ \t\r\n]+ -> skip ;

fragment EscapeSequence
    	:   '\\' (UnicodeEscape |'b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\'|'/')
    	;

fragment UnicodeEscape
	: 'u' HexDigit HexDigit HexDigit HexDigit
	;

fragment HexDigit
	: '0'..'9' | 'A'..'F' | 'a'..'f'
	;

fragment Digit
	: '0'..'9'
	;

COMMA
  : ','
  ;

TRUE
  : 't' 'r' 'u' 'e'
  ;

FALSE
  : 'f' 'a' 'l' 's' 'e'
  ;

NULL
  : 'n' 'u' 'l' 'l'
  ;