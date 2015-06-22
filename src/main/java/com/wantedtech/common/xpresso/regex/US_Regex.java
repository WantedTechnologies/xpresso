package com.wantedtech.common.xpresso.regex;

import java.util.regex.Pattern;

public class US_Regex {	
	public Regex DATE = new Regex("\\b(?:(?<!\\:)(?<!\\:\\d)[0-3]?\\d(?:st|nd|rd|th)?\\s+(?:of\\s+)?(?:jan\\.?|january|feb\\.?|february|mar\\.?|march|apr\\.?|april|may|jun\\.?|june|jul\\.?|july|aug\\.?|august|sep\\.?|september|oct\\.?|october|nov\\.?|november|dec\\.?|december)|(?:jan\\.?|january|feb\\.?|february|mar\\.?|march|apr\\.?|april|may|jun\\.?|june|jul\\.?|july|aug\\.?|august|sep\\.?|september|oct\\.?|october|nov\\.?|november|dec\\.?|december)\\s+(?<!\\:)(?<!\\:\\d)[0-3]?\\d(?:st|nd|rd|th)?)(?:\\,)?\\s*(?:\\d{4})?|[0-3]?\\d[-\\./][0-3]?\\d[-\\./]\\d{2,4}\\b", Pattern.CASE_INSENSITIVE);
	public Regex TIME = new Regex("\\b\\d{1,2}:\\d{2} ?(?:[ap]\\.?m\\.?)?|\\d[ap]\\.?m\\.?\\b", Pattern.CASE_INSENSITIVE);
	public Regex PHONE = new Regex("\\b((?:(?<![\\d-])(?:\\+?\\d{1,3}[-.\\s*]?)?(?:\\(?\\d{3}\\)?[-.\\s*]?)?\\d{3}[-.\\s*]?\\d{4}(?![\\d-]))|(?:(?<![\\d-])(?:(?:\\(\\+?\\d{2}\\))|(?:\\+?\\d{2}))\\s*\\d{2}\\s*\\d{3}\\s*\\d{4}(?![\\d-])))\\b", Pattern.CASE_INSENSITIVE);
	public Regex PRICE = new Regex("\\b[$]\\s?[+-]?[0-9]{1,3}(?:(?:,?[0-9]{3}))*(?:\\.[0-9]{1,2})?\\b", Pattern.CASE_INSENSITIVE);
	public Regex STREET_ADDRESS = new Regex("\\b\\d{1,4} [\\w\\s]{1,20}(?:street|st|avenue|ave|road|rd|highway|hwy|square|sq|trail|trl|drive|dr|court|ct|parkway|pkwy|circle|cir|boulevard|blvd)\\W?(?=\\s|$|\\b)", Pattern.CASE_INSENSITIVE);	
	public Regex ZIP_CODE = new Regex("\\b[0-9]{5}\\b", Pattern.CASE_INSENSITIVE);
	public Regex ZIP9_CODE = new Regex("\\b[0-9]{5}-[0-9]{4}\\b", Pattern.CASE_INSENSITIVE);
	public Regex SSN = new Regex("[0-9]{3}[-]*[0-9]{2}[-]*[0-9]{4}", Pattern.CASE_INSENSITIVE);
	public Regex STATE = new Regex("A[KLRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]|N[CDEHJMVY]|O[HKR]|PA|RI|S[CD]|T[NX]|UT|V[AT]|W[AIVY]");	
}
