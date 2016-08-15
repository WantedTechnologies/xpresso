import java.util.ArrayList;
import java.util.List;


import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.experimental.concurrency.Channel;
import com.wantedtech.common.xpresso.experimental.concurrency.Mapper;
import com.wantedtech.common.xpresso.experimental.concurrency.Reducer;
import com.wantedtech.common.xpresso.experimental.generator.Generator;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.helpers.Slicer;
import com.wantedtech.common.xpresso.regex.Regex;
import com.wantedtech.common.xpresso.sentence.PosTagger;
import com.wantedtech.common.xpresso.sentence.Sentence;
import com.wantedtech.common.xpresso.sentence.pos.en.stanford.MaxentPosTagger;
import com.wantedtech.common.xpresso.strings.FuzzyWuzzy;
import com.wantedtech.common.xpresso.strings.HappyString;
import com.wantedtech.common.xpresso.token.Token;
import com.wantedtech.common.xpresso.types.*;
import com.wantedtech.common.xpresso.types.tuples.tuple2;
import com.wantedtech.common.xpresso.types.tuples.tuple3;
import com.wantedtech.common.xpresso.web.service.WebService;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Test {
	
	DefaultDict<DefaultDict<String>> ff = x.DefaultDict(x.DefaultDict(String.class));
	
	DefaultDict<DefaultDict<Integer>> stats = x.DefaultDict(x.DefaultDict(Integer.class));
	
	static Mapper<String,String> mpr = new Mapper<String,String>() {
		public void map(String input) {
			x.Time.sleep(5);
			if (x.String(input).startsWith("a")) {
				yield("upper", input.toUpperCase());				
			} else {
				yield("lower", input.toLowerCase());
			}
		}
	};
	
	static Reducer<String,String> rdr = new Reducer<String,String>() {
		public void reduce(tuple2<String,list<String>> input) {
			yield(input.key,x.String("~").join(input.value));
		}
	};
	
	public static Generator<Integer> gen5(final int max) {
		return new Generator<Integer>() {
			@Override
			public void generate() {
				int i = 0;
				while (i < max) {
					i++;
					yield(i);
				}
			}
		};
	};

	public enum Day {
	    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
	    THURSDAY, FRIDAY, SATURDAY 
	}
	
	public static void main(String[] args) throws Exception {
		try {
			
			x.print(x.Object(Day.SUNDAY).equals(Day.SUNDAY));
			
			x.print(x.listOf(x.listOf(x.String("hello"))).flatten(HappyString.class));
			
			String kor = "공식적인";
			x.print(x.String(kor).stripAccents());
			
			x.print(x.Regex("[\\w'.+&/-]").findAll("He;-*llo World"));
			
			Function<tuple2<String,String>,Integer> ratio = x.Function(FuzzyWuzzy.class, "ratio", String.class, String.class);
			
			x.print("aa",ratio.apply(x.tuple2("this is a test", "this is a test!")));
			
			Function<tuple,Integer> ratio2 = x.Function(FuzzyWuzzy.class, "ratio");
			
			x.print("bb",ratio2.apply(x.tuple2("this is a test", "this is a test!")));
			
			x.assertTrue(FuzzyWuzzy.ratio("this is a test", "this is a test!") == 97);
			x.assertTrue(FuzzyWuzzy.partial_ratio("this is a test", "this is a test!") == 100);
			x.assertTrue(FuzzyWuzzy.ratio("fuzzy wuzzy was a bear", "wuzzy fuzzy was a bear") == 91);
			x.assertTrue(FuzzyWuzzy.token_sort_ratio("fuzzy wuzzy was a bear", "wuzzy fuzzy was a bear", null) == 100);
			x.assertTrue(FuzzyWuzzy.token_sort_ratio("fuzzy was a bear", "fuzzy fuzzy was a bear", null) == 84);
			x.assertTrue(FuzzyWuzzy.token_set_ratio("fuzzy was a bear", "fuzzy fuzzy was a bear", null) == 100);
			
			list<String> choices = x.list("Atlanta Falcons", "New York Jets", "New York Giants", "Dallas Cowboys");
			x.assertTrue(FuzzyWuzzy.extract("new york jets", choices, null, null, 2).equals(x.list(x.tuple2("New York Jets", 100), x.tuple2("New York Giants", 79))));
					    
			x.assertTrue(FuzzyWuzzy.extractOne("cowboys", choices, null, null, null).equals(x.tuple2("Dallas Cowboys", 90)));
			
			WebService ws = x.WebService(new WebServiceTest(),8050).start();
			
			x.timer.start();
			x.print(x.<String,String,String>MapReduce(x.list("Map","aNd","ReDuce","arE","aWEsome")).map(mpr).reduce(rdr));
			x.print(x.timer.stop());
			
			Regex jjj = x.Regex("dgjdsjfl;kgjsdlgj;dlskfgj|dgjdsjfl;kgjsdlgj;dlskfgj|dgjdsjfl;kgjsdlgj;dlskfgj|dgjdsjfl;kgjsdlgj;dlskfgj|(?:[Tt]he\\s+|an?\\s+)?(?<g1430>%%X%%)\\.?(?: has been marketing|,? through our| is strong| has more than| advocates| represents| operates| manages| markets| supplies| designs| delivers| conducts| is a specialist| is open| differentiates itself| has transformed| (?:strict )?standards| builds|,? through its|,? which recorded| has been retained)\\b|ksfhalskjghlakgjhladfksghldfkjgh");
			
			x.print(jjj.find("ts related items in the %%X%%. represents %%X%% with a professional manner in a high-visibility role.").group(0));
			
			String tran = x.String.translit("Чичётка 北亰");
			x.print(x.String(tran).stripAccents());
			
			String input = "Hello world. How do you do?";
			
			PosTagger tagger = new MaxentPosTagger(MaxentPosTagger.Model.ENGLISH_LEFT_3_WORDS);
			
			for (Sentence s : x.String.EN.tokenize(input)) {
				s = tagger.tag(s);
				x.print(s);
				x.print(s.getAnnotations("pos"));
			}
			
	        //save the advertiser score in the advertiser_scores_dict:
			String advertiser = "aaa";
			double advertiser_score = 100.0;
			DefaultDict<Double> advertiser_scores_dict = x.DefaultDict(Double.class);
	        if (x.String(advertiser).in(advertiser_scores_dict)) {
	        	advertiser_scores_dict.setAt(advertiser).value(advertiser_scores_dict.get(advertiser) + advertiser_score);
	        }
	        else {
	            advertiser_scores_dict.setAt(advertiser).value(advertiser_score);
	        }
	        
	        x.print(advertiser_scores_dict);
									
			x.print(x.String.unidecode("Городская агломерация"));
			
			str title = x.str("Hello‹„ World");
			
			dict<String> analogs = x.dict(
		            x.tuple("«","\""),
		            x.tuple("»","\""),
		            x.tuple("‹","<"),
		            x.tuple("›",">"),
		            x.tuple("„","\""),
		            x.tuple("‚",","),
		            x.tuple("‘","\""),
		            x.tuple("“","\""),
		            x.tuple("”","\""),
		            x.tuple("’","'"),
		            x.tuple("–"," - "),
		            x.tuple("—"," - ")
		    );
			title = title.translate(analogs);
			x.assertTrue(title.toString().equals("Hello<\" World"));
			
			str sortedTitle = x.sort(title,true);
			x.assertTrue(sortedTitle.toString().equals("roollledWH<\" "));
			
			for (tuple item : x.enumerate(title)){
				x.assertTrue(item.get("index").equals(item.get("index")));
				x.assertTrue(item.get("index").equals(item.get(0)));
				x.assertTrue(item.get("char").equals(item.get("char")));
				x.assertTrue(item.get("char").equals(item.get(1)));
				x.assertTrue(!item.get("char").equals(item.get("index")));
			}
			
			title = x.str(x.<String>yield().apply(x.<String>asKey(analogs)).when(x.in(analogs)).applyOtherwise(x.upper).forEach(title).unless(x.in(x.list("o","r"))));
			
			x.print(title);
			
			list<list<String>> lists_lst = x.list(x.list("aaa","bbb","ccc"),x.list("ddd","eee","fff")); 
			
			list<tuple> lists_lst2 = x.list(x.yield("a","b").where("a","b","c").in(lists_lst));
			
			x.print(lists_lst2);
			
			x.print(x.String("a").in("a","["));
			
			list<dict<String>> lst2 = x.list(x.<String>dict(x.tuple("aaa", "bbb"),x.tuple("ccc", "ddd")),x.<String>dict(x.tuple("eee", "fff"),x.tuple("ggg", "hhh")));
			
			x.print(lst2);
			
			list<String> lst3 = x.listOf("aaa");
			
			x.print(lst3);
			
			x.print(x.dict(x.tuple("aaaa",1)));
			
			str new_str = x.str("hello");
			new_str = x.str(x.<String>yield().apply(x.upper).forEach(new_str));
			x.print(new_str); 
			
			list<tuple> lst4 = x.list(x.tuple("aaa", "bbb"),x.tuple("xxxx", "yy Y yy"));
			list<tuple> lst5 = x.list(x.yield("a","b").apply(x.len,x.toUpperCase).where("a","b").in(lst4));
			x.print("uuu",lst5);
			list<tuple> lst55 = x.list(x.yield("a","b").apply(x.len).replace("HI!").where("a","b").in(lst4));
			x.print("jjj",lst55);
			
			x.print(x.Json(lst2));
			x.print(lst2);
			list<dict<String>> loaded_lst2 = x.String("[{\"aaa\":\"bbb\",\"ccc\":\"ddd\"},{\"eee\":\"fff\",\"ggg\":\"hhh\"}]").parseJson();
			x.print(loaded_lst2);
			
			dict<String> dct = x.first(loaded_lst2);
			
			dict<String> dct2 = x.<String>dict(x.tuple("aaa", "bbb"),x.tuple("ccc", "ddd"));
			
			dict<Character> def = x.DefaultDict(Character.class);
		
			x.print("ff",def.get("llll"),"jj");
			
			//x.print(x.str("dddddaaaaassssooooxxxxxiiiiiii").translate(lst4));
			
			tuple t = x.tuple((Object)1, (Object)"ddd");
			tuple2<Integer,String> t2 = (tuple2<Integer,String>)t;
			x.print(t2.value0+4,t2.value1+"yyy");
			
			list<list<String>> lst6 = x.list(x.list("aaa", "bbb"),x.list("xxxx", "yyYyy"));
			
			list<String> lst7 = x.list(x.<String>yield().apply(x.lambdaF("x : f0(x[0])",x.upper)).forEach(lst6).when(x.lambdaP("x : f0(x[0]) && f1(x[0]) && (x[1] != \"bbb\")",x.TRUE,x.TRUE)));
			
			x.print(lst7);
			
			list<Integer> lst8 = x.list(1,2,3,4,5);
			x.print(lst8.sliceTo(-100,-1));
			
			x.print(x.String("hello").sliceTo(4),"0");
			
			x.print(x.Time.time());
			
			dict<String> translator = x.dict(x.tuple("a", "A"),x.tuple("b", "B"));
			
			x.print(x.Regex("\\w").sub(translator, "Mama papa bonjour!"));
			
			x.print(x.Regex("(?<name1>a)|(?<name2>b)").find("mama").group("name1"));
			
			x.print(x.Regex("(?<name1>a)|(?<name2>b)").find("mama").group(1));
			
			list<String> flatten = lst6.flatten(String.class);
			
			x.print("flatten",flatten); 
			
			list<Integer> ints = x.list(1,2,3,4,5);
			ints.setAt(1,3).values(x.list(7,8,9));
			
			x.print(ints); 
			
			ints = x.list(1,2,3,4,5);
			x.print(ints.slice(Slicer.EVERY_THIRD));
			
			List<Integer> list_int = new ArrayList<Integer>();
			list_int.add(1);
			list_int.add(2);
			list_int.add(3);
			
			dict<String> replacer = x.dict(x.tuple("\\bhaha\\b","ohoh"),x.tuple("\\bhjehe\\b","wow"));
			x.print(x.Regex(replacer).translate("lala haha bebeb hehe ogogo"));
			
			x.print(x.largestN(x.set(1,2,3),2));
			
			x.print(x.list(1,2,3,4,5).ngrams(6));
			
			x.print(x.String("aab").in("xaaabx"));
			
			x.print(x.String("aab").in(x.str("xaaabx")));
			
			Bag<String> bag = x.Bag(x.dict(x.tuple("aa", 10),x.tuple("bb", 10)));
			
			for(String element : bag.elements()){
				x.print(element);
			}
			
			list<String> lst10= x.list(" aaa "," кккк "," ccc ");
			
			lst10 = x.list(x.<String>yield().apply(x.lambdaF("x : f1(f0(x)) + '''rrr'''",x.upper,x.strip)).forEach(lst10).when(x.lambdaP("x : (f0(x) > 5) && (f1(x) != '''жжж''')",x.len,x.strip)));
			
			x.print(lst10);
			
			Function<Object,Integer> increment = x.<Integer>lambdaF("x : x + 1");
			
			Function<Integer,Integer> incrementAndMultiplyBy5 = x.chain(increment,x.<Integer>lambdaF("x : x * 5"));
			
			Function<Integer, String> strCopy = new Function<Integer, String>() {
				public String apply(Integer count) {
					return x.String("hello").times(count);
				}
			};
			
			
			Function<Integer,String> cachedFunction = x.memo(strCopy);
			
			x.timer.start();
			String copies = cachedFunction.apply(500000);
			x.print(x.timer.stop());
			
			x.timer.start();
			copies = cachedFunction.apply(500000);
			x.print(x.timer.stop());
			
			x.print(x.tuple(0,1).equals(x.tuple(0,2)));
			
			x.print(x.tuple(0,1).equals(x.tuple(0,1)));
			
			x.print(x.tuple(0,1).compareTo(x.tuple(0,2)));
			
			x.print(x.tuple(1,1).hashCode());
			x.print(x.tuple(0,1).hashCode());
			
			list<String> someList = x.list("good","bad","good");
			list<Boolean> evals = x.list(x.<Boolean>yield().replace(true).when(x.lambdaP("x : x == '''good'''")).replaceOtherwise(false).forEach(someList));
			x.print(evals);
			
			list<String> trips = x.list("Dubai","New York","London","Paris","Moscow","London","Saint-Petersburg","New York");
			
			Function<Object, String> toUpper = new Function<Object, String>() {
				public String apply(Object value) {
					return value.toString().toUpperCase();
				}
			};

			list<String> tripsUp = x.list(x.map(toUpper, trips));
			x.print(tripsUp);

			Predicate<Object> containsO = new Predicate<Object>() {
				public Boolean apply(Object value) {
					return value.toString().contains("o") ? true : false;
				}
			};

			list<String> tripsO = x.list(x.filter(containsO, trips));
			x.print(tripsO);
			
			Function<Object,Integer> incrementFun = x.lambdaF("x : x + 1");
			Function<Object,Integer> squareFun = x.lambdaF("x : x * x");

			Function<Object,Integer> chainFun = x.chain(incrementFun,squareFun);
			
			dict<Integer> rank = x.dict(x.tuple("Moscow",30),x.tuple("Saint-Petersburg",15),x.tuple("New York",20),x.tuple("London",10),x.tuple("Paris",5),x.tuple("Dubai",32));
			
			x.print(x.Json(rank));
			
			dict<Integer> rankCopy = x.String("{\"New York\":20,\"London\":10,\"Saint-Petersburg\":15,\"Moscow\":30,\"Dubai\":32,\"Paris\":5}").parseJson();
			
			x.print(x.Object(rank).equals(rankCopy));
			
			dict<Integer> coolCities = x.dict(x.yield("city","_").apply(x.chain(x.upper,x.strip,x.String.stripAccents)).replace(true).where("city","_").in(rank.items()).when(x.lambdaP("city, score : score > 20")));
			
			x.print(coolCities);
			
			list<Integer> ranks = x.list(x.<Integer>yield().apply(x.invoke("toLowerCase")).forEach(coolCities));
			
			list<tuple2<String,Integer>> sr = x.list(x.sort(rank.items(), x.<Integer>invoke("get", 1)));
			
			x.print(sr);
			
			x.print(x.avg(x.list(1,2,3)));
			
			list<Integer> list = x.listOf(1);
			x.print(list.toScalar());
			
			list<tuple> items = x.list(x.tuple("name1",1d,100),x.tuple("name2",3d,105),x.tuple("name1",4d,210));
			
			tuple3<list<String>,list<Double>,list<Integer>> unzipped = x.unzip(items, String.class, Double.class, Integer.class);
			
			x.print(x.tuple(x.last(unzipped.value0), x.avg(unzipped.value1), x.max(unzipped.value2)));
			
			list<String> ids = x.list("name1","name2");
			
			x.print(x.Object("name1").in(ids));
			
			list<tuple> filtered = x.list(x.<tuple>yield().forEach(items).when(x.lambdaP("x : f1(f0(x))",x.invoke("get",0),x.in(ids))));
			
			x.print(filtered);
			
			class PlannedTrip {
			    int year;
			    String city;

			    public PlannedTrip(int year, String city){
			        this.year = year;
			        this.city = city;
			    }

			    public int getYear() { return year; }
			    public String getCity() { return city; }
			}

			list<PlannedTrip> plans = x.list(new PlannedTrip(2015, "Moscow"), new PlannedTrip(2016, "Paris"));

			list<tuple> plansData = x.list(x.yield("year", "city").where("year", "city").in(plans).when(x.lambdaP("year, city : year > 2016")));

			x.print(plansData);
			
			/*
			try(HappySQL conn = x.mysql("10.0.5.32:3306", "wd", "", "jobs")){
				String query =
						"SELECT ID, Name FROM " +
						"tbl_Skill s " +
						"WHERE s.Name LIKE ?";
				
				conn.execute(query, "%Java%");
				
				try(HappySQL conn2 = x.mysql("10.0.5.32:3306", "wd", "", "workspace")){
					for (tuple row : conn.execute(query, "%Java%")) {
						query =
								"INSERT IGNORE INTO tttt " +
								"VALUES (?, ?)";
						conn2.execute(query, row.get("ID"), row.get("Name"));
					}
				}
			}*/
			
			String stem = x.Token.stem("Working");
			x.print(stem);
			 
			String stem2 = x.Token.stem("Marcher", "french");
			x.print(stem2);
			
			 
			x.print(x.String("Чичётка").similarity("Чичeтка"));
			
			x.print(x.String("abcd").similarity("bcde"));
			 
			x.print(x.String("You are cooding in Java.").search("coding"));
			 
			x.print(x.Token("Hello1").features());
			 
			x.print(x.String("Чичётка 北亰").unidecode());
						
			x.print("uuuu",x.csv(lst6).toString());
			
			for (Integer i : gen5(50000000)) {
				x.print(i);
				if (i == 10) {
					break;
				}
			}
			list<String> emails = x.list(x.Regex.EMAIL.findAll("Contact me at john.smith@company.com or john@smith.com."));

			x.print(emails);
			
			Predicate<Channel<Integer>> counter1 = new Predicate<Channel<Integer>>() {
				public Boolean apply(Channel<Integer> channel) {
					for (int cnt : x.countTo(5)) {
						channel.send(cnt);
						x.print("1 put", cnt);
					}
					//channel.close();
					x.print("1 finished");
				    return true;
				}
			};
			
			Predicate<Channel<Integer>> counter2 = new Predicate<Channel<Integer>>() {
				public Boolean apply(Channel<Integer> channel) {
					for (int cnt : x.countTo(10)) {
						channel.send(cnt);
						x.print("2 put", cnt);
					}
					x.print("2 finished");
				    return true;
				}
			};
			
			Channel<Integer> channel = x.Channel(Integer.class,100);
			
			x.go(counter1, channel);
			x.go(counter2, channel.sendOnly());
			x.go(counter1, channel.sendOnly());
			x.go(counter2, channel.sendOnly());
			x.go(counter1, channel.sendOnly());
			x.go(counter2, channel.sendOnly());
			
			x.print("OK");
			
			for (int element : channel.receiveOnly()) {
				x.print("Read:", element);
			}
			
			x.print("OK2");
			
			String text2 = "Hello my name is Andrei. This is my test.";
			
			for (Sentence sent : x.String.EN.tokenize(text2)) {
				for (Token tok : sent) {
					x.print(tok);
				}
			}
			
			ws.stop();
			
		}catch(Exception e){
			throw e;
		}
	}
	
}