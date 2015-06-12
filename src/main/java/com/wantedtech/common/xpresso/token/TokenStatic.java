package com.wantedtech.common.xpresso.token;

import com.ibm.icu.text.Transliterator;
import com.wantedtech.common.xpresso.token.stem.Stemmer;


public class TokenStatic {

	Transliterator anyLatin = Transliterator.getInstance("Any-Latin", Transliterator.FORWARD);
	
	public String stem(String string) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		return (new Stemmer()).stem(string);
	}
	
	public String stem(String string, String stemmerName) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		return (new Stemmer()).stem(string, stemmerName);
	}

}
