grammar JSONTree;

options { 
tokenVocab=JSON; // reuse token types 
} 

@header {
package com.wantedtech.common.xpresso.json;

import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.x;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

}

@members {
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

}

value returns [Object result]
	: s=string { $result = $s.result; } 
	| n=number { $result = $n.result; }
	| o=object { $result = $o.result; }
	| a=array { $result = $a.lst; }
	| TRUE { $result=Boolean.TRUE; }
	| FALSE { $result = Boolean.FALSE; }
	| NULL { $result = null; }
	;

string returns [String result]
	: st=String { $result = extractString($st.text); }
	;
	
object returns [dict<Object> result]
@init {
    $result = x.dict();
}
    : '{'
       (
       me0=members {$result = $me0.dct;}
       )?
      '}'
	;

number	returns [Object result] 
	: nu=Number ex=Exponent? { $result = extractNumber($nu.text, $ex.text); }
	;

array	returns [list<Object> lst]
@init {
    $lst = x.list();
}
    : '['
       (
       el=elements {$lst = $el.lst;}
       )?
      ']'
	;

elements    returns [list<Object> lst]
@init {
    $lst = x.list();
}
    : v0=value {$lst.append($v0.result);}
        (
            COMMA v1=value {$lst.append($v1.result);}
        )*
    ;
    
members    returns [dict<Object> dct]
@init {
    $dct = x.dict();
}
    : pa0=pair {$dct.update($pa0.dct);}
      (
      COMMA pa1=pair {$dct.update($pa1.dct);}
      )*
    ;

pair    returns [dict dct]
@init {
    $dct = x.dict();
}
    : key=String ':' v=value { $dct.setAt(extractString($key.text)).value($v.result); }
    ;

Number  : '-'? Digit+ ( '.' Digit+)?;

Exponent: ('e'|'E') '-'? ('1'..'9') Digit*;

String  :
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