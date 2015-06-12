package com.wantedtech.common.xpresso.token;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.str;

public class Token {
	
	String token;
	
	public boolean isUpper;
	public boolean isLower;
	public boolean hasDigits;
	public boolean isDigits;
	public boolean hasSpecial;
	public boolean isSpecial;
	public boolean hasDash;
	public boolean isDash;
	public boolean isMoney;
	public boolean isCurrency;
	public boolean isCamel;
	public boolean hasAccent;
	public boolean hasLatinAlpha;
	public boolean isLatinAlpha;
	
	public boolean isMath;
	public boolean hasMath;
	
	public boolean isComma;
	public boolean isDot;
	public boolean isPunct;
	
	public boolean isGreek;
	public boolean hasGreek;
	
	public boolean isArabic;
	public boolean hasArabic;
	
	public boolean hasRussian;
	
	public boolean isCyrillic;
	public boolean hasCyrillic;
	
	public boolean isHangul;
	public boolean hasHangul;
	
	public boolean isHiragana;
	public boolean hasHiragana;
	
	public boolean isKatakana;
	public boolean hasKatakana;
	
	public boolean isHebrew;
	public boolean hasHebrew;
	
	public boolean hasTurkish;
	
	public String firstLetter;
	public String lastLetter;
	
	public String firstBigram;
	public String lastBigram;
	
	public String firstTrigram;
	public String lastTrigram;
	
	public int length;
	
	public String shape;
	public String compressedShape;
	
	public Token(String word){
		this.token = word;
		
		this.isUpper = word.toUpperCase().equals(word);
		
		this.isLower = word.toLowerCase().equals(word);
		
		this.hasDigits = x.Regex("[0-9]").search(word) != null;
		
		this.isDigits = x.Regex("^[0-9]+$").search(word) != null;
		
		this.hasSpecial = x.Regex("\\W").search(word) != null;
		
		this.isSpecial = x.Regex("^\\W+$").search(word) != null;
		
		this.hasDash = x.Regex("-").search(word) != null;
		
		this.isDash = x.Regex("^(-|–|—)$").search(word) != null;
		
		this.isMoney = (
							    x.Regex("^([\u20a0-\u20cf]|\\p{Sc}|¤|؋|Ar|฿|B/\\.|Br|Bs\\.|Bs\\.F\\.|GH₵|¢|c|Ch\\.|₡|D|ден|دج|.د.ب|د.ع|JD|د.ك|ل.د|дин|د.ت|د.م.|د.إ|Ð|Db|$|₫|Esc|€|ƒ|t|FBu|FCFA|Fr|FRw|CFA|G|gr|₲|h|₴|₭|Kč|kr|kn|MK|ZK|Kz|K|L|Le|E|lp|Ł|M|KM|MT|₥|Nfk|₦|Nu\\.|UM|MOP$|₱|Pt\\.|£|ج.م.|LL|LS|P|Q|q|R|R$|ر.ع.|ر.ق|ر.س|៛|RM|p|Rf\\.|₨|₨(PKR)|SRe|Rp|₪|Ksh|Sh\\.So\\.|USh|S/\\.|SDR|лв|сом|৳|WS$|₮|VT|₩|¥|zł)\\d").search(word) != null
					   )
					   ||
					   (
							    x.Regex("\\d([\u20a0-\u20cf]|\\p{Sc}|¤|؋|Ar|฿|B/\\.|Br|Bs\\.|Bs\\.F\\.|GH₵|¢|c|Ch\\.|₡|D|ден|دج|.د.ب|د.ع|JD|د.ك|ل.د|дин|د.ت|د.م.|د.إ|Ð|Db|$|₫|Esc|€|ƒ|t|FBu|FCFA|Fr|FRw|CFA|G|gr|₲|h|₴|₭|Kč|kr|kn|MK|ZK|Kz|K|L|Le|E|lp|Ł|M|KM|MT|₥|Nfk|₦|Nu\\.|UM|MOP$|₱|Pt\\.|£|ج.م.|LL|LS|P|Q|q|R|R$|ر.ع.|ر.ق|ر.س|៛|RM|p|Rf\\.|₨|₨(PKR)|SRe|Rp|₪|Ksh|Sh\\.So\\.|USh|S/\\.|SDR|лв|сом|৳|WS$|₮|VT|₩|¥|zł)$").search(word) != null
					   )
					   ;
		this.isCurrency = x.Regex("^([\u20a0-\u20cf]|\\p{Sc}|¤|؋|Ar|฿|B/\\.|Br|Bs\\.|Bs\\.F\\.|GH₵|¢|c|Ch\\.|₡|D|ден|دج|.د.ب|د.ع|JD|د.ك|ل.د|дин|د.ت|د.م.|د.إ|Ð|Db|$|₫|Esc|€|ƒ|t|FBu|FCFA|Fr|FRw|CFA|G|gr|₲|h|₴|₭|Kč|kr|kn|MK|ZK|Kz|K|L|Le|E|lp|Ł|M|KM|MT|₥|Nfk|₦|Nu\\.|UM|MOP$|₱|Pt\\.|£|ج.م.|LL|LS|P|Q|q|R|R$|ر.ع.|ر.ق|ر.س|៛|RM|p|Rf\\.|₨|₨(PKR)|SRe|Rp|₪|Ksh|Sh\\.So\\.|USh|S/\\.|SDR|лв|сом|৳|WS$|₮|VT|₩|¥|zł)$").search(word) != null;

		this.isCamel = (
								x.Regex("^\\p{javaLowerCase}+\\p{javaUpperCase}").search(word) != null
					   )
					   ||
					   (
							    x.Regex("^\\p{javaUpperCase}+\\p{javaLowerCase}+\\p{javaUpperCase}+").search(word) != null
					   )
					   ;
		this.hasAccent = !x.String.stripAccents(word).equals(word);
		
		this.isLatinAlpha = x.RegexNoCase("^[a-z]+$").search(word) != null;
		this.hasLatinAlpha = x.RegexNoCase("[a-z]").search(word) != null;
		
		this.isGreek = x.RegexNoCase("^[\0370-\u03ff\u1f00-\u1fff]+$").search(word) != null;
		this.hasGreek = x.RegexNoCase("[\0370-\u03ff\u1f00-\u1fff]").search(word) != null;
		
		this.isArabic = x.RegexNoCase("^[\u0600-\u06ff\ufe70-\ufefc]+$").search(word) != null;
		this.hasArabic = x.RegexNoCase("[\u0600-\u06ff\ufe70-\ufefc]").search(word) != null;
		
		this.hasRussian = x.RegexNoCase("[\u0451\u0410-\u044f]").search(word) != null;
		
		this.isCyrillic = x.RegexNoCase("^[\u0400-\u04ff\u2de0-\u2dff\ua640-\ua69d]+$").search(word) != null;
		this.hasCyrillic = x.RegexNoCase("[\u0400-\u04ff\u2de0-\u2dff\ua640-\ua69d]").search(word) != null;
		
		this.isHangul = x.RegexNoCase("^[\u1100-\u11ff]+$").search(word) != null;
		this.hasHangul = x.RegexNoCase("[\u1100-\u11ff]").search(word) != null;
		
		this.isHiragana = x.RegexNoCase("^[\u3041-\u3096\3099-\309f]+$").search(word) != null;
		this.hasHiragana = x.RegexNoCase("[\u3041-\u3096\3099-\309f]").search(word) != null;
		
		this.isKatakana = x.RegexNoCase("^[\u30a0-\u30ff\u31f0-\u31ff]+$").search(word) != null;
		this.hasKatakana = x.RegexNoCase("[\u30a0-\u30ff\u31f0-\u31ff]").search(word) != null;
		
		this.isHebrew = x.RegexNoCase("^[\u0590-\u05c7\u05D0-\u05ea\u05f0-\u05f4]+$").search(word) != null;
		this.hasHebrew = x.RegexNoCase("[\u0590-\u05c7\u05D0-\u05ea\u05f0-\u05f4]").search(word) != null;
		
		this.isMath = x.RegexNoCase("^[\u2200-\u22ff]$").search(word) != null;
		this.hasMath = x.RegexNoCase("[\u2200-\u22ff]").search(word) != null;
		
		this.hasTurkish = x.RegexNoCase("[İÖÜŞğüĞ]").search(word) != null;
		
		this.firstLetter = token.substring(0, 1);
		this.lastLetter = token.substring(token.length()-1);
		
		this.firstBigram = (token + " ").substring(0, 2).trim();
		this.lastBigram = (" " +  token).substring((" " +  token).length()-2).trim();
		
		this.firstTrigram = (token + "  ").substring(0, 3).trim();
		this.lastTrigram = ("  " +  token).substring((" " +  token).length()-3).trim();
		
		this.length = token.length();
		str fullShapeStr = x.str();
		for (String character : x.str(token)) {
			if (x.isTrue(x.Regex("[0-9]").search(character))){
				fullShapeStr.append("D");
			} else if (!character.equals("_") && x.isTrue(x.Regex("\\w").search(character)) && character.toLowerCase().equals(character)) {
				fullShapeStr.append("L");	
			} else if (!character.equals("_") && x.isTrue(x.Regex("\\w").search(character)) && character.toUpperCase().equals(character)) {
				fullShapeStr.append("U");
			} else {
				fullShapeStr.append("S");
			}
		}
		this.shape = fullShapeStr.toString();
		this.compressedShape = x.String(shape).compress();
		
		this.isComma = token.equals(",");
		this.isDot = token.equals(".");
		this.isPunct = x.isTrue(x.Regex("[.,;!?-:()]").search(token));
	}
	
	boolean in(Iterable<String> iterable){
		return x.String(token).in(iterable);
	}
	
	public String translit(){
		return x.String.translit(token);
	}
	
	public String stem() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		return x.Token.stem(token);
	}
	
	public String stem(String stemmerName) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		return x.Token.stem(token, stemmerName);
	}

	@Override
	public String toString(){
		return token;
	}
}
