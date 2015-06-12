package com.wantedtech.common.xpresso.token.stem;

import com.wantedtech.common.xpresso.token.stem.snowball.SnowballStemmer;

public class Stemmer {
	
	public String stem(String string, String stemmerName) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class<?> stemClass = Class.forName("com.wantedtech.common.xpresso.token.stem.snowball.ext." + stemmerName.toLowerCase() + "Stemmer");
		SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
		stemmer.setCurrent(string);
		stemmer.stem();
		return stemmer.getCurrent();
	}
	
	public String stem(String string) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		return stem(string, "porter");
	}
	
}
