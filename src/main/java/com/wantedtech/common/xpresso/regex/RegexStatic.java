package com.wantedtech.common.xpresso.regex;

import java.util.regex.Pattern;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;

public class RegexStatic implements Function<Object, Regex> {

	public US_Regex US = new US_Regex();
	public UK_Regex UK = new UK_Regex();
	public AU_Regex AU = new AU_Regex();
	public CA_Regex CA = new CA_Regex();
	
	public int CASE_INSENSITIVE = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
	public int I = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
	
	public Regex TIME_24H = new Regex("([01]?[0-9]|2[0-3]):[0-5][0-9]");
	public Regex HTML_TAG = new Regex("<([a-z]+)([^<]+)*(?:>(.*)<\\/\\1>|\\s+\\/>)$");
	public Regex LINK = new Regex("(?:(?:https?|ftp)://|www\\.)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x00a1-\\xffff0-9]+-?)*[a-z\\x00a1-\\xffff0-9]+)(?:\\.(?:[a-z\\x00a1-\\xffff0-9]+-?)*[a-z\\x00a1-\\xffff0-9]+)*(?:\\.(?:[a-z\\x00a1-\\xffff]{2,})))(?::\\d{2,5})?(?:/[^\\s]*)?", Pattern.CASE_INSENSITIVE);
	public Regex EMAIL = new Regex("([a-z0-9!#$%&'*+\\/=?^_`{|}~-]+@([a-z0-9]+\\.)+([a-z0-9]+))",Pattern.CASE_INSENSITIVE);
	public Regex IPV4 = new Regex("\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b",Pattern.CASE_INSENSITIVE);
	public Regex IPV6 = new Regex("((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))\\b",Pattern.CASE_INSENSITIVE);
	public Regex HEX_COLOR = new Regex("#(?:[0-9a-fA-F]{3}){1,2}\\b",Pattern.CASE_INSENSITIVE);
	public Regex ACRONYM = new Regex("\\b(([A-Z]\\.)+|([A-Z]){2,})");
	public Regex FLOAT = new Regex("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
	
	public Regex CREDIT_CARD = new Regex(
			 "\\b(?:4[0-9]{3}[- ]?[0-9]{4}[- ]?[0-9]{4}[0-9](?:[0-9]{3})?" +	// Visa
			 "|5[1-5][0-9]{2}[- ]?[0-9]{4}[- ]?[0-9]{4}[- ]?[0-9]{4}" +			// MasterCard
			 "|3[47][0-9]{2}[- ]?[0-9]{6}[- ]?[0-9]{5}" +                   	// American Express
			 "|3(?:0[0-5]|[68][0-9])[0-9]{2}[- ]?[0-9]{6}[- ]?[0-9]{4}" +   	// Diners Club
			 "|6(?:011|5[0-9]{2})[- ]?[0-9]{4}[- ]?[0-9]{4}[- ]?[0-9]{4}" + 	// Discover
			 "|35(?:2[89]|[3-8]\\d)[- ]?[0-9]{4}[- ]?[0-9]{4}[- ]?[0-9]{4}" +	// JCB
			 ")\\b",Pattern.CASE_INSENSITIVE);
	
	@Override
	public Regex apply(Object input) {
		return x.Regex(input.toString());
	}
}
