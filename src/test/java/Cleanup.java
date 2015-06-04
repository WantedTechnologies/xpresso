import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.regex.Match;
import com.wantedtech.common.xpresso.regex.Regex;
import com.wantedtech.common.xpresso.types.HappyFile;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.set;
import com.wantedtech.common.xpresso.types.str.str;
import com.wantedtech.common.xpresso.types.tuple.tuple;
import com.wantedtech.common.xpresso.types.tuple.tuple2;

public class Cleanup {
	
	String ref_file_dir = "/Users/andriy.burkov/p/workspace/python/InternationalTitleCleanup/ref";
	
	dict<String> acceptedSpecialCharactersDict = x.dictOf(
            x.tupleOf("RU","йцукеёнгшщзфывапролдячсмитьъбюэжх«»€$"),
            x.tupleOf("FR","àâèéêëîïôœùûç«»€$"),
            x.tupleOf("DE","àâèéêéäöüß€$«»‹›„‚“‘”’–—"),
            x.tupleOf("NL","àèìòùáéíóúýäëïöüÿâêîôû«»€$„‚”’‹›“‘–—"),
            x.tupleOf("PT","àáâãéêíóôõúüçºª«»€$"),
            x.tupleOf("ES","áéíóúñü¿¡ºª«»€$"),
            x.tupleOf("SE","åäö«»€$„‚”’‹›“‘–—"),
            x.tupleOf("TR","çğİıöüş«»€$„‚”’‹›“‘–—")
	);

	list<tuple> analogsTupleList = x.listOf(
            x.tupleOf("«","\""),
            x.tupleOf("»","\""),
            x.tupleOf("‹","<"),
            x.tupleOf("›",">"),
            x.tupleOf("„","\""),
            x.tupleOf("‚",","),
            x.tupleOf("‘","\""),
            x.tupleOf("“","\""),
            x.tupleOf("”","\""),
            x.tupleOf("’","'"),
            x.tupleOf("–"," - "),
            x.tupleOf("—"," - ")
    );
	
	set<String> countriesSet = x.setOf();
	set<String> citiesSet = x.setOf();
	set<String> unsupportedCitiesSet = x.setOf();
	set<String> geoExceptionsSet = x.setOf();
	set<String> cityPartsSet = x.setOf();
	set<String> advertisersSet = x.setOf();
	set<String> monthsSet = x.setOf();
	set<String> stopWordsSet = x.setOf();
	set<String> beginningWordsSet = x.setOf();
	set<String> metroSet = x.setOf();
	set<String> statesSet = x.setOf();
	list<list<String>> periodExpressionsList = x.listOf();
	dict<String> replaceRulesDict = x.dictOf();
	Regex replaceRulesRegex;
	
	Regex beginningWordsRegex;
	Regex stopWordsRegex;
	Regex placesRegex;
	Regex placesInBeginningRegex;
	Regex advertisersRegex;
	Regex specialCharsRegex;
	Regex shortWordsRegex;
	Regex oneWordsRegex;
	Regex referenceNumberAndDatesRegex;
	Regex betweenParenthesesRegex;
	Regex fixRegex;
	Regex forRegex;
	
	String periodExpressionsRegexPattern;
	
	dict<set<String>> surroundingWordsDict = x.dictOf();
	dict<Regex> surroundingWordsRegexDict = x.dictOf();
	
	public static Function<Object, Object> fGeneralizeRegex = new Function<Object, Object>() {
		public String apply(Object string) {
			return generalizeRegex((String)string);
		}
	};
	private static String generalizeRegex(String pattern){
		Regex saintRegex = x.RegexNoCase("\\b(?:st|saint)e?\\b[\\\\\\.\\s-]{0,3}");
		Regex wordsWithDashRegex = x.RegexNoCase("(\\w)\\\\?-(\\w)");
		Regex wordsWithApostropheRegex = x.RegexNoCase("(\\w)\\\\?'(\\w)");
		pattern = saintRegex.sub("(?:st|saint)e?[.\\s-]{0,3}",pattern);
		pattern = wordsWithDashRegex.sub("$1[\\s.-]$2",pattern);
		pattern = wordsWithApostropheRegex.sub("$1[\\s']$2",pattern);
	    return pattern;
	}

	list<String> annotate(String strng,list<tuple> regexesTypesList, list<String> annotations){
	    for(tuple regex__regexType : regexesTypesList){
	    	Regex regex = (Regex)regex__regexType.get(0);
	    	String regexType = (String)regex__regexType.get(1);
	        for(Match match : regex.searchIter(x.stripAccents(strng))){
	            if(x.String(regexType).notEquals("sho")){
	                for(int i : x.count(match.start(0),match.end(0))){
	                    annotations.setAt(i).value(regexType);	
	                }	
	            }else{
	                if(
	                	x.String("pla").in(annotations.slice(match.start(0)-4,match.start(0)))
	            		||
	            		x.String("pla").in(annotations.slice(match.end(0),match.end(0)+4))
	            		||
	            		x.String("sur").in(annotations.slice(match.start(0)-4,match.start(0)))
	            		||
	            		x.String("sur").in(annotations.slice(match.end(0),match.end(0)+4))
	            		||
	            		x.String("one").in(annotations.slice(match.start(0)-4,match.start(0)))
	            		||
	            		x.String("one").in(annotations.slice(match.end(0),match.end(0)+4))
	            		||
	            		x.String("ref").in(annotations.slice(match.start(0)-4,match.start(0)))
	            		||
	            		x.String("ref").in(annotations.slice(match.end(0),match.end(0)+4))
	            		||
	            		x.String("sto").in(annotations.slice(match.start(0)-4,match.start(0)))
	            		||
	            		x.String("sto").in(annotations.slice(match.end(0),match.end(0)+4))
	            		||
	            		x.String("adv").in(annotations.slice(match.start(0)-4,match.start(0)))
	            		||
	            		x.String("adv").in(annotations.slice(match.end(0),match.end(0)+4))
	                	){
	                    for(int i : x.count(match.start(0),match.end(0))){
	                        annotations.setAt(i).value(regexType);
	                    }
	                }
	            }
	        }
	    }
	    return annotations;
	}
	list<String> annotate(String strng,list<tuple> regexesTypesList){
		list<String> annotations = x.listOf("not").times(x.len(strng));
		return annotate(strng,regexesTypesList,annotations);
	}
	list<String> annotate(str strng,list<tuple> regexesTypesList, list<String> annotations){
		return annotate(strng.toString(),regexesTypesList,annotations);
	}
	list<String> annotate(str strng,list<tuple> regexesTypesList){
		list<String> annotations = x.listOf("not").times(x.len(strng));
		return annotate(strng,regexesTypesList,annotations);
	}
	
	public float get_annotation_score(list<String> annotation){
	    return (float)(annotation.count("not") + annotation.count("sho") + annotation.count("sto"))/(float)x.len(annotation);
	}
	
    //mark as 'pla' every word that sticks to a 'pla' (with a '-', or '/')
    public void contaminate(list<String> annotationsList,int idx, String strng){
    	contaminate(annotationsList,idx, strng,"pla",x.<String>listOf(),x.str(" "));
    }
    public void contaminate(list<String> annotations_lst,int idx, String strng,list<String> nonContaminableLabelsList){
    	contaminate(annotations_lst,idx, strng,"pla",nonContaminableLabelsList,x.str(" "));
    }
    public void contaminate(list<String> annotationsLst,int idx,String strng,String contaminationLabel,list<String> nonContaminableLabelsList,str boundaryCharactersList){
    	annotationsLst.setAt(idx).value(contaminationLabel);
        if(
        		(
        				idx-1 >= 0 && x.String(annotationsLst.get(idx-1)).notEquals(contaminationLabel)
        				&&
        				x.String(x.String(strng).get(idx-1)).notIn(boundaryCharactersList)
        				&& x.String(annotationsLst.get(idx-1)).notIn(nonContaminableLabelsList)
        		)
        ){
            contaminate(annotationsLst,idx-1,strng,contaminationLabel,nonContaminableLabelsList,boundaryCharactersList);	
        }
        if(idx+1 < x.len(annotationsLst) && x.String(annotationsLst.get(idx+1)).notEquals(contaminationLabel) && x.String(x.String(strng).get(idx+1)).notIn(boundaryCharactersList) && x.String(annotationsLst.get(idx+1)).notIn(nonContaminableLabelsList)){
            contaminate(annotationsLst,idx+1,strng,contaminationLabel,nonContaminableLabelsList,boundaryCharactersList);
        }
    }
	
	public String cleanTitle(String title) throws Exception{
		return cleanTitle(title,"FR");
	}
	
	public String cleanTitle(String title,String language) throws Exception{

			str originalTitle = x.str(title);
			str currentTitle = x.str(title);
		
		    list<tuple> annotationRegexList;
				annotationRegexList = x.listOf(
					x.tupleOf(advertisersRegex,"adv"),
					x.tupleOf(placesRegex,"pla"),
					x.tupleOf(x.dict(surroundingWordsRegexDict).get(language),"sur"),
					x.tupleOf(oneWordsRegex,"one"),
					x.tupleOf(referenceNumberAndDatesRegex,"ref"),
					x.tupleOf(stopWordsRegex,"sto"),
					x.tupleOf(specialCharsRegex,"spe"),
					x.tupleOf(shortWordsRegex,"sho")
				);
		    
		    //replace analog chars
			currentTitle = currentTitle.translated(analogsTupleList);
		    
		    String accepted_special_characters = "";
    	    if(x.String(language).in(acceptedSpecialCharactersDict)){
				accepted_special_characters = acceptedSpecialCharactersDict.get(language);
    	    }
    	       
    	    
    	    if(x.String(accepted_special_characters).notEquals("")){
    	        //remove all non-accepted characters
    	    	Regex nonAcceptedCharsRegex = x.RegexNoCase("[^a-zA-Z0-9"+accepted_special_characters+",.;!|~?\\s&/()':+\\#-]");
    	    	currentTitle = x.str(nonAcceptedCharsRegex.sub(" ",currentTitle));
    	    }
    	    
    	    boolean all_caps = false;
    	    if(currentTitle.equals(currentTitle.upper())){
    	    	all_caps = true;
    	    }
    	    
    	    currentTitle = replaceRulesRegex.sub(currentTitle);
    	    
    	    Regex multipleSlashesRegex = x.Regex("/+");
    	    currentTitle = multipleSlashesRegex.sub("/",currentTitle);
    	    
    	    //things like this ")(blahblah" becomes like this ") (blahblah":
    	    Regex twoParenthesesThatStick = x.Regex("([\\w\\)）])([\\(（][^)）]{6,}[\\)）])"); 
    	    currentTitle = x.str(twoParenthesesThatStick.sub("$1 $2",currentTitle));
		    
    	    //remove (h/f)
    	    Regex hfRegex = x.RegexNoCase("\\(\\s?\\b\\w\\s?[\\\\/-]\\s?\\w\\b\\s?\\)|\\b\\w\\s?[\\\\/]\\s?\\w\\b[\\s-]|\\b\\w\\s?[\\\\/-]\\s?\\w\\b\\s|\\b\\w\\s?[\\\\/-]\\s?\\w\\b$");
    	    currentTitle = hfRegex.sub(" ",currentTitle);
    	    
    	    currentTitle = currentTitle.strip().strip(",").strip().strip(",");
    	    
    	    currentTitle = betweenParenthesesRegex.sub(" ",currentTitle);
    	    
    	    currentTitle = currentTitle.strip();
    	    
    	    currentTitle = placesInBeginningRegex.sub(" ",currentTitle);
    	    
    	    //things like this "      -      " become like this " - "
    	    Regex longDashRegex = x.RegexNoCase("[\\s-]*\\s-\\s[\\s-]*");
    	    currentTitle = longDashRegex.sub(" - ",currentTitle);
    	    
    	    currentTitle = currentTitle.strip();
    	    
    	    //split by the " - ", "~" and "|" and keep the left part only (if the remaining title is still long enough)
    	    Regex specialCharacterSplitter = x.Regex(" - |~|\\|");
    	    Regex wordChars = x.Regex("[\\w'-]+");
    	    list<str> long_dash_split = specialCharacterSplitter.split(currentTitle);
    	    while(x.len(long_dash_split) > 1){
    	        //the remaining part (has more dashes, or it's longer than 15 chars, or it contains 2 or more words) and it's at least a half of the title and contains more than 65% of non-special chars 
    	        if (
    	        		(
	    	        		x.len(long_dash_split.sliceTo(-1)) > 1
	    	        		||
	    	        		x.len(x.str(" - ").join(long_dash_split.sliceTo(-1))) > 15
	    	        		||
	    	        		x.len(x.list(wordChars.searchAll(x.str(" - ").join(long_dash_split.sliceTo(-1))))) > 1
    	        		) 
    	        		&&
    	        		x.len(long_dash_split.get(-1)) <= x.len(currentTitle)/2
    	        		&&
    	        		get_annotation_score(annotate(x.str(" - ").join(long_dash_split.sliceTo(-1)),annotationRegexList.append(x.tupleOf(fixRegex,"not")))) > 0.65
    	        	){
    	            currentTitle = x.str(x.str(" - ").join(long_dash_split.sliceTo(-1)));
    	            long_dash_split = x.str(currentTitle).split(" - ");
    	        }else{
    	        	break;
    	        }	
    	    }
    	    
    	    //if there's more than two "/" in the title then split by the third "/" and keep the left part only (if the remaining title is still long enough)
    	    //an exception is made if the first word right part looks like a part of an acronym, so we try to split by "/" after it
    	    list<str> slash_split = currentTitle.split("/");
    	    Regex wordInTheBeginningRegex = x.Regex("\\s*[A-Z0-9]{1,5}\\b|^\\s*\\w{1,2}\\b");
    	    Regex periodExpressionsRegex = x.RegexNoCase("^\\s*("+periodExpressionsRegexPattern+")");
    	    if(
    	    		x.len(slash_split) > 2 
    	    		&&
    	    		(
    	    				all_caps
    	    				&&
    	    				(
    	    						wordInTheBeginningRegex.search(slash_split.get(2)) != null
    	    						&&
    	    						periodExpressionsRegex.search(slash_split.get(2)) != null
    	    				)
    	    		)
    	    ){
    	        currentTitle = x.str("/").join(slash_split.sliceTo(2));
    	    }else if(
    	    		x.len(slash_split) > 3
    	    		&&
    	    		wordInTheBeginningRegex.search(slash_split.get(3)) != null
    	    		&&
    	    		periodExpressionsRegex.search(slash_split.get(3)) != null
    	    		){
    	    	        currentTitle = x.str("/").join(slash_split.sliceTo(3));
    	    }
    	    if(currentTitle.equals("")){
    	        return "";
    	    }
    	    
    	    list<String> annotations = annotate(currentTitle,annotationRegexList);
    	    
    	    //fix annotations for some special cases
    	    Regex regex = fixRegex;
    	    String regex_type = "not";
	        for(Match match : regex.searchIter(currentTitle)){
	            for(int i : x.count(match.start(0),match.end(0))){
	                annotations.setAt(i).value(regex_type);
	            }    
	        }
    	    
	        //mark as 'pla' everything between two 'pla' or between a 'sur' and a 'pla'
	        int idx1 = 0;
	        while(idx1 < x.len(annotations)){
	            if(annotations.get(idx1).equals("sur") || annotations.get(idx1).equals("pla")){
	                String contaminationTag = annotations.get(idx1);
	                int idx2 = idx1+1;
	                while(idx2 < x.len(annotations)){
	                    if(annotations.get(idx2).equals("pla") || (annotations.get(idx2).equals("sur") && contaminationTag.equals("pla"))){
	                    	break;
	                    }
	                    idx2 += 1;	
	                }
	                if(idx2 != x.len(annotations)){
	                    for(int contamination_idx : x.count(idx1,idx2)){
	                        annotations.setAt(contamination_idx).value(contaminationTag);	
	                    }	
	                }
	                idx1 = idx2;
	            }
	            idx1 += 1;
	        }
	        
	        //mark as 'pla' everything to the right of a surrounding word or a 'pla', if at least half of the title is remaining and it's of a descent quality
	        for(tuple2<Integer,String> idx__annotation : x.reversed(x.list(x.enumerate(annotations)))){
	        	int idx = idx__annotation.value0;
	        	String annotation = idx__annotation.value1;
	            if(
	            		x.String(annotation).in(x.listOf("sur","pla"))
	            		&&
	            		(
	            				idx+1 >= x.len(annotations)
	            				||
	            				x.String(annotations.get(idx+1)).notIn(x.listOf("pla","sur"))
	            				&&
	            				(
	            						(idx > x.len(annotations)/2 && get_annotation_score(annotations.sliceTo(idx)) > 0.5)
	            						||
	            						(idx > x.len(annotations)/3 && get_annotation_score(annotations.sliceTo(idx)) > 0.7)
	            				)
	            		)
	            	){
	            		annotations = annotations.sliceTo(idx+1).append("pla").times(x.len(annotations)-1-idx);
	            }
	        }
	        
	        //contamainate with place label
	        list<String> old_annotations = annotations.copy();
	        for(tuple2<Integer,String> idx__annotation : x.enumerate(annotations)){
	        	int idx = idx__annotation.value0;
	        	String annotation = idx__annotation.value1;
	            if(annotation.equals("pla") && x.String(currentTitle.get(idx)).notEquals(" ")){
	                contaminate(annotations,idx,title);	
	            }	
	        }
	        if(get_annotation_score(annotations) < .25){
	            annotations = old_annotations.copy();	
	        }
	        
	        //contaminate with ref_num label
	        for(tuple2<Integer,String> idx__annotation : x.enumerate(annotations)){
	        	int idx = idx__annotation.value0;
	        	String annotation = idx__annotation.value1;
	            if(annotation.equals("ref") && x.String(currentTitle.get(idx)).notEquals(" ")){
	                contaminate(annotations,idx,title,"ref",x.listOf("not"),x.str());	
	            }	
	        }
	                    
	        //fix for the left-most char annotation when it's a parenthesis but the interior of parentheses is "not"
	        if(x.String(currentTitle.get(0)).in(x.listOf("(","（")) && annotations.get(1).equals("not")){
	            annotations.setAt(0).value("not");
	        }
	        
	        //remove places and reference numbers from anywhere
	        str cleanTitle = x.str("");
	        list<String> cleanAnnotations = x.listOf();
	        for(tuple2<Integer,String> idx__label : x.enumerate(annotations)){
	        	int idx = idx__label.value0;
	        	String label = idx__label.value1;
	            if(x.String(label).notIn(x.listOf("pla","ref")) || currentTitle.get(idx).equals(" ")){
	            	cleanAnnotations.append(label);
	                cleanTitle=cleanTitle.plus(x.str(currentTitle.get(idx)));
	            }	
	        }
	        annotations = cleanAnnotations.copy();
	        currentTitle = cleanTitle.copy();
	        
	        //remove non-'not' from the beginning and from the end
	        int start = 0;
	        while(x.String(annotations.get(start)).notEquals("not")){
	            start += 1;
	            if(x.len(annotations) <= start){
		            break;	
	            }	
	        }

	        int end = x.len(annotations)-1;

	        //if 'pla' in annotations and annotations.index('pla') > len(annotations)/3:
	            
            while(x.String(annotations.get(end)).notEquals("not")){
                end -= 1;
                if(end < 0){
                    end = 0;
                    break;
                }	
            }
			currentTitle = currentTitle.slice(start,end+1);
			Regex multipleSpacesRegex = x.Regex("\\s+");
			currentTitle = multipleSpacesRegex.sub(" ",currentTitle);
			Regex spaceCommaRegex = x.Regex("\\s*,+");
			currentTitle = spaceCommaRegex.sub(",",currentTitle);
			Regex multiDotsRegex = x.Regex("\\.+");
			currentTitle = multiDotsRegex.sub(".",currentTitle);
			Regex wordEndsByDashRegex = x.Regex("(\\w)-\\s");
			currentTitle = wordEndsByDashRegex.sub("$1 ",currentTitle);
			Regex wordStartsByDashRegex = x.Regex("(?:\\s|^)-(\\w)");
			currentTitle = wordStartsByDashRegex.sub(" $1",currentTitle);
			currentTitle = currentTitle.strip().strip(",").strip().strip(",");
			Regex stringEndsByDotsRegex = x.Regex("\\.*$");
			currentTitle = stringEndsByDotsRegex.sub("",currentTitle);
            
            //if we cleaned the title, and it's so clean that nothing remains, we return the original title
            if(x.len(currentTitle) == 0){
            	currentTitle = originalTitle.strip();
            }
            
            Regex slashRegex = x.Regex("\\s*/\\s*");
            currentTitle = slashRegex.sub(" / ",currentTitle);
	        
		    return currentTitle.toString();

	}
	
	public Cleanup() throws Exception{
		
		unsupportedCitiesSet = x.set(x.<String>element().transformWith(x.chainOf(x.stripAccents,x.strip,x.lower)).forElementIn("zürich","bern","schwyz","wien","vienna","aarga"));
		
		countriesSet = x.set(x.<String>element().transformWith(x.chainOf(x.stripAccents,x.strip,x.lower)).forElementIn("switzerland","schweiz","suisse","britain","british","uk","united kingdom","ireland","scotland","scottish","irish","us","united states","america","american","australia","australian","canada","canadian","china","中国","chinese","germany","deutschlandweit","deutschland","dtl","dtl. weit.","bundesrepublik deutschland","deutschland","japan","日本","india","indian","france","italy","italia","mexico","mexican","netherland","holland","belgium","belgië","belgique","belgien","portugal","república portuguesa","argentina","república argentina","sweden","sverige","russia","russian federation","rf","turkey","arab emirates","united arab emirates","emirates","uae","brazil","brazilian","saudi arabia","benelux","netherlands","nederland","рф","снг","россия","российская федерация","türkiye","السعودية","الإمارات العربية المتحدة"));

		//these locations cant be removed as other cities even if such cities exist:
		geoExceptionsSet = x.set(x.<String>element().transformWith(x.chainOf(x.stripAccents,x.strip,x.lower)).forElementIn("sta","it","cns","lot","centrale","marché","glacière","plaisance","art","la","tri","écoles","nationale","poissonniers","poissonnière","car","sur","se","foreman","mason","secretary","mobile","lead","cv","home","house","java","driver","low","field","branch","english","golf","campus","portage","trainer","teller","commerce"));

		HappyFile f;

		for (String place : x.open(ref_file_dir+"/cities_en_and_original.txt","r","utf-8")){
			citiesSet.absorb(x.set(x.<String>element().transformWith(x.chainOf(x.lower,x.stripAccents)).forElementIn(x.String(place).split(",")).ifNotElement(x.empty)));
		}
		citiesSet = citiesSet.difference(geoExceptionsSet);
	    
		f = x.open(ref_file_dir+"/common_city_parts.txt","r","utf-8");
		cityPartsSet = x.set(x.<String>element().transformWith(x.strip).forElementIn(f));

		set<String> addToSet;
	    for (String place : x.open(ref_file_dir+"/states_en_and_original.txt","r","utf-8")){
	    	addToSet = x.set(x.<String>element().transformWith(x.chainOf(x.stripAccents,x.lower)).forElementIn(x.String(place).split(",")).ifNotElement(x.empty));
	    	statesSet.absorb(addToSet);
	    }
	    statesSet = statesSet.difference(geoExceptionsSet);

		for(String place : x.open(ref_file_dir+"/metro_en_and_original.txt","r","utf-8")){
			addToSet = x.set(x.<String>element().transformWith(x.chainOf(x.stripAccents,x.lower)).forElementIn(x.String(place).split(",")).ifNotElement(x.empty));
			metroSet.absorb(addToSet);
		}
		metroSet.reject(geoExceptionsSet);
		
		list<String> langList = x.listOf();
		for(tuple2<Integer,String> idx__words : x.enumerate(x.open(ref_file_dir+"/surrounding_words.txt","r","utf-8"))){
			int idx = idx__words.value0;
			String words = idx__words.value1;
	        if(idx == 0){
	        	langList = x.list(x.<String>element().transformWith(x.upper).forElementIn(x.String(words).split("\t")));
	            for (String lang : langList){
	            	surroundingWordsDict.setAt(lang.toUpperCase()).value(x.<String>setOf());
	            }
	        }else{
	            for (tuple2<Integer,String> idx_word : x.enumerate(x.String(words).split("\t"))){
	                idx = idx_word.value0;
	                String word = idx_word.value1;
	                if (x.String(word.trim()).notEquals("")){
	                	String old_key = x.list(langList).get(idx);
	                	set<String> old_value = surroundingWordsDict.get(langList.get(idx));
	                	set<String> new_value = x.set(x.<String>element().transformWith(x.chainOf(x.stripAccents,x.lower)).forElementIn(x.String(word).split(",")));
	                	surroundingWordsDict.setAt(old_key).value(old_value.union(new_value));
	                }
	            }
	        }
		}

	    for (String word : x.open(ref_file_dir+"/drop_in_the_beginning_words.txt","r","utf-8")){
	    	addToSet = x.set(x.<String>element().transformWith(x.chainOf(x.stripAccents,x.lower)).forElementIn(x.String(word).split(",")).ifNotElement(x.empty));
	    	beginningWordsSet.absorb(addToSet);
	    }
		
	    for (String word : x.open(ref_file_dir+"/stop_words.txt","r","utf-8")){
	    	addToSet = x.set(x.<String>element().transformWith(x.chainOf(x.stripAccents,x.lower)).forElementIn(x.String(word).split(",")).ifNotElement(x.empty));
	    	stopWordsSet.absorb(addToSet);
	    }
		
	    for (String word : x.open(ref_file_dir+"/months_of_the_year.txt","r","utf-8")){
	    	addToSet = x.set(x.<String>element().transformWith(x.chainOf(x.stripAccents,x.lower)).forElementIn(x.String(word).split(",")).ifNotElement(x.empty));
	    	monthsSet.absorb(addToSet);
	    }

		f = x.open(ref_file_dir+"/top_advertisers.txt","r","utf-8");
		advertisersSet = x.set(x.<String>element().transformWith(x.chainOf(x.lower,x.stripAccents)).forElementIn(f));
		
		periodExpressionsList = x.listOf(x.listOf("AR","شهر"),x.listOf("ZH","月"),x.listOf("NL","maand"),x.listOf("FR","mois"),x.listOf("DE","monat"),x.listOf("DE","mon"),x.listOf("DE","mtl"),x.listOf("IT","mese"),x.listOf("JA","月"),x.listOf("PT","mês"),x.listOf("RU","месяц"),x.listOf("RU","мес"),x.listOf("ES","mes"),x.listOf("SE","månad"),x.listOf("TR","ay"));
		periodExpressionsRegexPattern = x.String("|").join(x.set(x.element(1).forElementIn(periodExpressionsList)));

		for(String lang : surroundingWordsDict){
			surroundingWordsRegexDict.setAt(lang).value(x.RegexNoCase("(?:\\W|_)?\\b(?:"+x.str("|").join(x.sorted(surroundingWordsDict.get(lang), x.len, true))+")\\b(?:\\W|_)?"));
		}

		beginningWordsRegex = x.RegexNoCase("^.*\\b(?:"+x.str("|").join(x.sorted(beginningWordsSet,x.len,true))+")");

		stopWordsRegex = x.RegexNoCase("\\d+\\sans(?: ou plus)?\\b|\\d+\\sye?a?rs?(?: old)?"+"|"+
		                              "\\d+\\s?(h|ч)(?:ours?|eures?|асо?в?а?я?)?(?:\\swe?e?k|\\ssem(?:aine)?)?\\b"+"|"+
		                              "(?:\\W|_)?\\b(?:"+x.str("|").join(x.sorted(stopWordsSet,x.len,true))+")\\b(?:\\W|_)?|\\[[^\\]]+\\]|\\(\\w[/\\\\]\\w\\)");
		
		placesRegex = x.RegexNoCase("(?:\\W|_)?\\b(?:" + x.String("|").join(x.list(x.<String>element().transformWith(x.chainOf(x.escape,fGeneralizeRegex)).forElementIn(x.sorted(x.union(citiesSet,statesSet,metroSet,countriesSet,unsupportedCitiesSet),x.len,true)))) + "|"+ x.str("|").join(cityPartsSet) + ")\\b(?:\\W|_)?");
		
		placesInBeginningRegex = x.RegexNoCase("^(?:\\W|_)*\\b(?:" + x.str("|").join(x.list(x.<String>element().transformWith(x.chainOf(x.escape,fGeneralizeRegex)).forElementIn(x.sorted(x.union(citiesSet,statesSet,metroSet,countriesSet,unsupportedCitiesSet),x.len,true)))) + "|" + x.str("|").join(cityPartsSet) + ")(-\\w+)?\\b(?:\\W|_)?");
		
		advertisersRegex = x.RegexNoCase("(?:\\W|_)?\\b(?:"+x.str("|").join(x.sorted(advertisersSet,x.len,true))+")\\b(?:\\W|_)?(?:\\s?(?:group|groupe|express|bank|holdings?|company|corp|inc|corporation|incorporated|limited|& ?co)\\b){0,2}");

		specialCharsRegex = x.Regex("(?:\\W|_)");

		shortWordsRegex = x.RegexNoCase("(?<![/\\()])\\b[a-zA-Zа-яА-Я0-9']{1,2}\\b");

		oneWordsRegex = x.RegexNoCase("\\b[a-zA-Zа-яА-Я6-9']{1,1}\\b(?!-)");

		referenceNumberAndDatesRegex = x.RegexNoCase("(?<=[\\s)])[\\#№][^\\s]+\\b|"+"\\b(?:20\\d\\d\\b)?\\W*(?:\\b\\d{1,2}\\b)?\\W*(?:\\b20\\d\\d\\b)?\\W*(?:\\b\\d{1,2})?\\b("+x.str("|").join(monthsSet)+")\\b\\W*(?:\\d{1,2}\\b)?\\W*(?:20\\d\\d\\b)?\\W*(?:\\d{1,2}\\b)?\\W*(?:20\\d\\d\\b)?|"+"\\b\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}\\b|\\b\\d{1,2}[\\\\/]\\d{1,2}(?:[\\\\/]\\d{2,4})?\\b|\\b([a-z]+[0-9]|[0-9]+[a-z])(?:[a-z0-9]{2,}|-[0-9][a-z0-9-]{2,})\\b");

		betweenParenthesesRegex = x.RegexNoCase("\\s[(（][^)）]+[\\)）]|^[(（][^)）]+[)）][\\s,]|\\s-\\w+-\\s|\\s-\\w+-$|[(（][^)）]+\\s[^)）]+[\\)）]");

		fixRegex = x.RegexNoCase("\\bjr\\b|\\bTI\\b|\\bSOLNA 164\\b|\\b\\d{1,3}\\s?%|\\bnationale?\\b|\\bblanche\\b|\\b[123](?:st|rd|nd)\\b|\\b[123][eè][rm]e\\b|\\bbras de\\b|\\b(?:dry|mini)[\\s-]van\\b|\\b[\\d.,-]+\\s?T?(?:EUR|CHF)\\b|\\bsharepoint\\b|\\bax\\b|\\bit\\b|\\.net\\b|\\bрп\\b|\\bофис-|\\b(?:d'|de\\sl'|des\\s)usines?\\b|\\brh\\b|\\bde secteur\\b|\\bd'[ée]tat\\b|\\bit\\b|\\bzone d'attraction\\b|\\bd'établissements?\\b|\\b(?:à la|de) direction\\b|\\w{3,}\\(-?\\w{1,4}\\)|\\b\\w&\\w\\b|(?<!\\()\\b(a\\b|b\\b|c(?:(?:\\+\\+|\\#)(?=$|[^+])|\\b)|d\\b|e\\b|r\\b|(?<=гории |ласса |гория |класс |.кат\\. |. кат |. кат\\.|..\\bкл\\. |...\\bкл |...\\bкл\\.)а\\b|(?<=гории |ласса |гория |класс |.кат\\. |. кат |. кат\\.|..\\bкл\\. |...\\bкл |...\\bкл\\.)в\\b|с(?:\\+\\+|\\#)(?=$|[^\\+])|(?<=гории |ласса |гория |класс |.кат\\. |. кат |. кат\\.|..\\bкл\\. |...\\bкл |...\\bкл\\.)с\\b|(?<=гории |ласса |гория |класс |.кат\\. |. кат |. кат\\.|..\\bкл\\. |...\\bкл |...\\bкл\\.)д\\b|(?<=гории |ласса |гория |класс |.кат\\. |. кат |. кат\\.|..\\bкл\\. |...\\bкл |...\\bкл\\.)е\\b)|[\\\\/]in\\b|\\boffice 365\\b|\\b(?:en|du|de|des|\\w+rice|\\w+eure?|\\w+euse|\\w+ien|\\w+ienne|\\w+ste|\\w+tre) b.timents?\\b|\\b(?:en|du|de|des|sur) (centres?|directions?|station)\\b|\\bstation[\\s-]service\\b|\\b(?:en|de) bureau\\b|\\bbureau des\\b|\\bbureau d'\\w+s\\b|\\b(?:des?(?: la)?|en) recherches?\\b|\\bcentre(?: d'?e?s?)?\\s?(?:appels?|loisirs?|vacances?|sports?|services?)\\b|\\bbureau (?:\\w+(?:que|lle|al))\\b|\\b(i|ii|iii|iv|v|vi)\\b|\\bd. d.partement\\b|\\bthé\\b|\\bde ville\\b|\\bj2ee\\b|\\bétat des\\b");

		forRegex = x.RegexNoCase("\\b(voor|pour|für|per|per(\\sil|\\sla)?|ための|para|для|в|para|para(\\sla|\\slos)?|för|için)\\b");
		
		replaceRulesDict = x.dictOf(
				x.tupleOf("(?<=\\w{5,5})a\\s?\\(o\\)","o"),
				x.tupleOf("kauffrau\\s?/\\s?-?kaufmann\\b","kaufmann"),
				x.tupleOf("frau\\s?/\\s?-?mann\\b","mann"),
				x.tupleOf("\\bzur/zum|zum/zur\\b","zur"),
				x.tupleOf("\\band/or|or/and\\b","and"),
				x.tupleOf("\\bet/ou|ou/et\\b","et"),
				x.tupleOf("\\be/ou|ou/e\\b","e")
		);
	    replaceRulesRegex = x.RegexNoCase(replaceRulesDict);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		try{
			Cleanup cleaner = new Cleanup();
		    String title = "‹„La Défense - Chef de Projet H/F";
		    x.print(cleaner.cleanTitle(title));
		}catch(Exception e){
			throw e;
		}
	}
}
