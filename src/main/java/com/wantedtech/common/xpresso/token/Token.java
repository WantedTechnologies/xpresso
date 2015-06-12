package com.wantedtech.common.xpresso.token;

import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.strings.HappyString;
import com.wantedtech.common.xpresso.types.OrderedDict;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.str;

public class Token extends HappyString {
	
	private list<String> featureOrder = x.list(
			"stem",
			"shape",
			"compressedShape",
			"isUpper",
			"isLower",
			"isCamel",
			"isTitle",
			"isDash",
			"hasDash",
			"hasAccent",
			"isLatinAlpha",
			"hasLatinAlpha",
			"isGreek",
			"hasGreek",
			"isArabic",
			"hasArabic",
			"hasRussian",
			"isCyrillic",
			"hasCyrillic",
			"isHangul",
			"hasHangul",
			"isHiragana",
			"hasHiragana",
			"isKatakana",
			"hasKatakana",
			"isHebrew",
			"hasHebrew",
			"hasTurkish",
			"startsWithWovel",
			"endsWithWovel",
			"firstLetter",
			"lastLetter",
			"firstBigram",
			"lastBigram",
			"firstTrigram",
			"lastTrigram",
			"isDigits",
			"hasDigits",
			"isCurrency",
			"isMoney",
			"isMath",
			"hasMath",
			"isComma",
			"isDot",
			"isPunct",
			"isSpecial",
			"hasSpecial"
	);
	
	private Boolean isUpper;
	private Boolean isLower;
	private Boolean hasDigits;
	private Boolean isDigits;
	private Boolean hasSpecial;
	private Boolean isSpecial;
	private Boolean hasDash;
	private Boolean isDash;
	private Boolean isMoney;
	private Boolean isCurrency;
	private Boolean isCamel;
	private Boolean isTitle;
	private Boolean hasAccent;
	private Boolean hasLatinAlpha;
	private Boolean isLatinAlpha;
	
	private Boolean isMath;
	private Boolean hasMath;
	
	private Boolean isComma;
	private Boolean isDot;
	private Boolean isPunct;
	
	private Boolean isGreek;
	private Boolean hasGreek;
	
	private Boolean isArabic;
	private Boolean hasArabic;
	
	private Boolean hasRussian;
	
	private Boolean isCyrillic;
	private Boolean hasCyrillic;
	
	private Boolean isHangul;
	private Boolean hasHangul;
	
	private Boolean isHiragana;
	private Boolean hasHiragana;
	
	private Boolean isKatakana;
	private Boolean hasKatakana;
	
	private Boolean isHebrew;
	private Boolean hasHebrew;
	
	private Boolean hasTurkish;
	
	private Boolean startsWithWovel;
	private Boolean endsWithWovel;
	
	private String firstLetter;
	private String lastLetter;
	
	private String firstBigram;
	private String lastBigram;
	
	private String firstTrigram;
	private String lastTrigram;
	
	private String shape;
	private String compressedShape;
	
	public Token(){
		super("");
		throw new UnsupportedOperationException("Token cannot be empty.");
	}
	
	public Token(String word) {
		super("");
		this.value = word;
	}
	
	public boolean isUpper() {
		if (this.isUpper == null) {
			this.isUpper = value.toUpperCase().equals(value);			
		}
		return this.isUpper; 
	}
		
	public boolean isLower() {
		if (this.isLower == null) {
			this.isLower = value.toLowerCase().equals(value);			
		}
		return this.isLower;
	}

	public boolean hasDigits() {
		if (this.hasDigits == null) {
			this.hasDigits = x.Regex("[0-9]").search(value) != null;
		}
		return this.hasDigits;
	}
	
	public boolean isDigits() {
		if (this.isDigits == null) {
			this.isDigits = x.Regex("^[0-9]+$").search(value) != null;			
		}
		return this.isDigits;
	}

	public boolean hasSpecial() {
		if (this.hasSpecial == null) {
			this.hasSpecial = x.Regex("\\W").search(value) != null;
		}
		return this.hasSpecial;
	}
		
	public boolean isSpecial() {
		if (this.isSpecial == null) {
			this.isSpecial = x.Regex("^\\W+$").search(value) != null;	
		}
		return this.isSpecial;
	}	
	
	public boolean hasDash() {
		if (this.hasDash == null) {
			this.hasDash = x.Regex("-").search(value) != null;	
		}
		return this.hasDash;
	}
	
	public boolean isDash() {
		if (this.isDash == null) {
			this.isDash = x.Regex("^(-|–|—)$").search(value) != null;	
		}
		return this.isDash;
	}

	public boolean isMoney() {
		if (this.isMoney == null) {
			this.isMoney = (
				    x.Regex("^([\u20a0-\u20cf]|\\p{Sc}|¤|؋|Ar|฿|B/\\.|Br|Bs\\.|Bs\\.F\\.|GH₵|¢|c|Ch\\.|₡|D|ден|دج|.د.ب|د.ع|JD|د.ك|ل.د|дин|د.ت|د.م.|د.إ|Ð|Db|$|₫|Esc|€|ƒ|t|FBu|FCFA|Fr|FRw|CFA|G|gr|₲|h|₴|₭|Kč|kr|kn|MK|ZK|Kz|K|L|Le|E|lp|Ł|M|KM|MT|₥|Nfk|₦|Nu\\.|UM|MOP$|₱|Pt\\.|£|ج.م.|LL|LS|P|Q|q|R|R$|ر.ع.|ر.ق|ر.س|៛|RM|p|Rf\\.|₨|₨(PKR)|SRe|Rp|₪|Ksh|Sh\\.So\\.|USh|S/\\.|SDR|лв|сом|৳|WS$|₮|VT|₩|¥|zł)\\d").search(value) != null
		    )
		    ||
		    (
				    x.Regex("\\d([\u20a0-\u20cf]|\\p{Sc}|¤|؋|Ar|฿|B/\\.|Br|Bs\\.|Bs\\.F\\.|GH₵|¢|c|Ch\\.|₡|D|ден|دج|.د.ب|د.ع|JD|د.ك|ل.د|дин|د.ت|د.م.|د.إ|Ð|Db|$|₫|Esc|€|ƒ|t|FBu|FCFA|Fr|FRw|CFA|G|gr|₲|h|₴|₭|Kč|kr|kn|MK|ZK|Kz|K|L|Le|E|lp|Ł|M|KM|MT|₥|Nfk|₦|Nu\\.|UM|MOP$|₱|Pt\\.|£|ج.م.|LL|LS|P|Q|q|R|R$|ر.ع.|ر.ق|ر.س|៛|RM|p|Rf\\.|₨|₨(PKR)|SRe|Rp|₪|Ksh|Sh\\.So\\.|USh|S/\\.|SDR|лв|сом|৳|WS$|₮|VT|₩|¥|zł)$").search(value) != null
		    )
		    ;	
		}
		return this.isMoney;
	}
	
	public boolean isCurrency() {
		if (this.isCurrency == null) {
			this.isCurrency = x.Regex("^([\u20a0-\u20cf]|\\p{Sc}|¤|؋|Ar|฿|B/\\.|Br|Bs\\.|Bs\\.F\\.|GH₵|¢|c|Ch\\.|₡|D|ден|دج|.د.ب|د.ع|JD|د.ك|ل.د|дин|د.ت|د.م.|د.إ|Ð|Db|$|₫|Esc|€|ƒ|t|FBu|FCFA|Fr|FRw|CFA|G|gr|₲|h|₴|₭|Kč|kr|kn|MK|ZK|Kz|K|L|Le|E|lp|Ł|M|KM|MT|₥|Nfk|₦|Nu\\.|UM|MOP$|₱|Pt\\.|£|ج.م.|LL|LS|P|Q|q|R|R$|ر.ع.|ر.ق|ر.س|៛|RM|p|Rf\\.|₨|₨(PKR)|SRe|Rp|₪|Ksh|Sh\\.So\\.|USh|S/\\.|SDR|лв|сом|৳|WS$|₮|VT|₩|¥|zł)$").search(value) != null;	
		}
		return this.isCurrency;
	}

	public boolean isCamel() {
		if (this.isCamel == null) {
			this.isCamel = (
					x.Regex("^\\p{javaLowerCase}+\\p{javaUpperCase}").search(value) != null
		   )
		   ||
		   (
				    x.Regex("^\\p{javaUpperCase}+\\p{javaLowerCase}+\\p{javaUpperCase}+").search(value) != null
		   )
		   ;	
		}
		return this.isCamel;
	}
	
	public boolean isTitle() {
		if (this.isTitle == null) {
			this.isTitle = x.Regex("^\\p{javaUpperCase}\\p{javaLowerCase}+").search(value) != null;
		}
		return this.isTitle;
	}

	public boolean hasAccent() {
		if (this.hasAccent == null) {
			this.hasAccent = !x.String.stripAccents(value).equals(value);
		}
		return this.hasAccent;
	}
	
	public boolean isLatinAlpha() {
		if (this.isLatinAlpha == null) {
			this.isLatinAlpha = x.RegexNoCase("^[a-z]+$").search(value) != null;
		}
		return this.isLatinAlpha;
	}
	
	public boolean hasLatinAlpha() {
		if (this.hasLatinAlpha == null) {
			this.hasLatinAlpha = x.RegexNoCase("[a-z]").search(value) != null;
		}
		return this.hasLatinAlpha;
	}
	
	public boolean isGreek() {
		if (this.isGreek == null) {
			this.isGreek = x.RegexNoCase("^[\0370-\u03ff\u1f00-\u1fff]+$").search(value) != null;
		}
		return this.isGreek;
	}
		
	public boolean hasGreek() {
		if (this.hasGreek == null) {
			this.hasGreek = x.RegexNoCase("[\0370-\u03ff\u1f00-\u1fff]").search(value) != null;
		}
		return this.hasGreek;
	}
		
	public boolean isArabic() {
		if (this.isArabic == null) {
			this.isArabic = x.RegexNoCase("^[\u0600-\u06ff\ufe70-\ufefc]+$").search(value) != null;
		}
		return this.isArabic;
	}	

	public boolean hasArabic() {
		if (this.hasArabic == null) {
			this.hasArabic = x.RegexNoCase("[\u0600-\u06ff\ufe70-\ufefc]").search(value) != null;
		}
		return this.hasArabic;
	}	
		
	public boolean hasRussian() {
		if (this.hasRussian == null) {
			this.hasRussian = x.RegexNoCase("[\u0451\u0410-\u044f]").search(value) != null;
		}
		return this.hasRussian;
	}	
		
	public boolean isCyrillic() {
		if (this.isCyrillic == null) {
			this.isCyrillic = x.RegexNoCase("^[\u0400-\u04ff\u2de0-\u2dff\ua640-\ua69d]+$").search(value) != null;
		}
		return this.isCyrillic;
	}	

	public boolean hasCyrillic() {
		if (this.hasCyrillic == null) {
			this.hasCyrillic = x.RegexNoCase("[\u0400-\u04ff\u2de0-\u2dff\ua640-\ua69d]").search(value) != null;
		}
		return this.hasCyrillic;
	}	
		
	public boolean isHangul() {
		if (this.isHangul == null) {
			this.isHangul = x.RegexNoCase("^[\u1100-\u11ff]+$").search(value) != null;
		}
		return this.isHangul;
	}		
	
	public boolean hasHangul() {
		if (this.hasHangul == null) {
			this.hasHangul = x.RegexNoCase("[\u1100-\u11ff]").search(value) != null;
		}
		return this.hasHangul;
	}

	public boolean isHiragana() {
		if (this.isHiragana == null) {
			this.isHiragana = x.RegexNoCase("^[\u3041-\u3096\u3099-\u309f]+$").search(value) != null;
		}
		return this.isHiragana;
	}
	
	public boolean hasHiragana() {
		if (this.hasHiragana == null) {
			this.hasHiragana = x.RegexNoCase("[\u3041-\u3096\u3099-\u309f]").search(value) != null;
		}
		return this.hasHiragana;
	}
	
	public boolean isKatakana() {
		if (this.isKatakana == null) {
			this.isKatakana = x.RegexNoCase("^[\u30a0-\u30ff\u31f0-\u31ff]+$").search(value) != null;
		}
		return this.isKatakana;
	}
	
	public boolean hasKatakana() {
		if (this.hasKatakana == null) {
			this.hasKatakana = x.RegexNoCase("[\u30a0-\u30ff\u31f0-\u31ff]").search(value) != null;
		}
		return this.hasKatakana;
	}
	
	public boolean isHebrew() {
		if (this.isHebrew == null) {
			this.isHebrew = x.RegexNoCase("^[\u0590-\u05c7\u05D0-\u05ea\u05f0-\u05f4]+$").search(value) != null;
		}
		return this.isHebrew;
	}
	
	public boolean hasHebrew() {
		if (this.hasHebrew == null) {
			this.hasHebrew = x.RegexNoCase("[\u0590-\u05c7\u05D0-\u05ea\u05f0-\u05f4]").search(value) != null;
		}
		return this.hasHebrew;
	}
	
	public boolean isMath() {
		if (this.isMath == null) {
			this.isMath = x.RegexNoCase("^[+-=*/\u2200-\u22ff]$").search(value) != null;
		}
		return this.isMath;
	}
	
	public boolean hasMath() {
		if (this.hasMath == null) {
			this.hasMath = x.RegexNoCase("[+-=*/\u2200-\u22ff]").search(value) != null;
		}
		return this.hasMath;
	}
		
	public boolean hasTurkish() {
		if (this.hasTurkish == null) {
			this.hasTurkish = x.RegexNoCase("[İÖÜŞğüĞ]").search(value) != null;
		}
		return this.hasTurkish;
	}	

	public boolean startsWithWovel() {
		if (this.startsWithWovel == null) {
			this.startsWithWovel = x.RegexNoCase("^[eyuioa]").search(x.String(value).unidecode()) != null;
		}
		return this.startsWithWovel;
	}
	
	public boolean endsWithWovel() {
		if (this.endsWithWovel == null) {
			this.endsWithWovel = x.RegexNoCase("[eyuioa]$").search(x.String(value).unidecode()) != null;
		}
		return this.endsWithWovel;
	}	
	
	public String firstLetter() {
		if (this.firstLetter == null) {
			this.firstLetter = value.substring(0, 1);
		}
		return this.firstLetter;
	}	
	
	public String lastLetter() {
		if (this.lastLetter == null) {
			this.lastLetter = value.substring(value.length()-1);
		}
		return this.lastLetter;
	}	
	
	public String firstBigram() {
		if (this.firstBigram == null) {
			this.firstBigram = (value + " ").substring(0, 2).trim();
		}
		return this.firstBigram;
	}	
	
	public String lastBigram() {
		if (this.lastBigram == null) {
			this.lastBigram = (" " +  value).substring((" " +  value).length()-2).trim();
		}
		return this.lastBigram;
	}	
	
	public String firstTrigram() {
		if (this.firstTrigram == null) {
			this.firstTrigram = (value + "  ").substring(0, 3).trim();
		}
		return this.firstTrigram;
	}	
	
	public String lastTrigram() {
		if (this.lastTrigram == null) {
			this.lastTrigram = ("  " +  value).substring((" " +  value).length()-3).trim();
		}
		return this.lastTrigram;
	}
	
	public String shape() {
		if (this.shape == null) {
			str fullShapeStr = x.str();
			for (String character : x.str(value)) {
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
		}
		return this.shape;
	}
	
	public String compressedShape() {
		if (this.compressedShape == null) {
			this.compressedShape = x.String(shape).compress();
		}
		return this.compressedShape;
	}
	
	public boolean isComma() {
		if (this.isComma == null) {
			this.isComma = value.equals(",");
		}
		return this.isComma;
	}
	
	public boolean isDot() {
		if (this.isDot == null) {
			this.isDot = value.equals(".");
		}
		return this.isDot;
	}
	
	public boolean isPunct() {
		if (this.isPunct == null) {
			this.isPunct = x.isTrue(x.Regex("[.,;!?:()-]").search(value));
		}
		return this.isPunct;
	}
	
	public String stem() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		return x.Token.stem(value);
	}
	
	public String stem(String stemmerName) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		return x.Token.stem(value, stemmerName);
	}

	public OrderedDict<Object> features() throws NoSuchFieldException, AccessDeniedException{
		OrderedDict<Object> features = x.OrderedDict();
		dict<Object> featureValues = x.dict();
		for (Method m : this.getClass().getDeclaredMethods()) {
			if (m.getParameterCount() == 0 && !m.getName().equals("features")) {
				try {
					featureValues.setAt(m.getName()).value(m.invoke(this));
				} catch (Exception e) {
					//Can't happen
					e.printStackTrace();
				}	
			}
		}
		for (String featureName : featureOrder) {
			features.setAt(featureName).value(featureValues.get(featureName));
		}
		if (x.len(x.set(featureValues.keys()).difference(x.set(featureOrder))) != 0){
			throw new AccessDeniedException("The list of features in featureOrder is incomplete. It shoudld include " + x.set(featureValues.keys()).difference(x.set(featureOrder)).toString());
		}
		return features;
	}
}
