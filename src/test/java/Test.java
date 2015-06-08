import java.util.ArrayList;
import java.util.List;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.regex.Regex;
import com.wantedtech.common.xpresso.time.Timer;
import com.wantedtech.common.xpresso.experimental.generator.Generator;
import com.wantedtech.common.xpresso.experimental.helpers.Slicer;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.lambda.LambdaFunction;
import com.wantedtech.common.xpresso.functional.lambda.LambdaPredicate;
import com.wantedtech.common.xpresso.json.Json;
import com.wantedtech.common.xpresso.json.JsonArray;
import com.wantedtech.common.xpresso.types.*;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public class Test {
	
	public static void main(String[] args) throws Exception {
		try{
			
			String ref_file_dir = "/Users/andriy.burkov/p/workspace/python/InternationalTitleCleanup/ref";
			
			try(HappyFile f = x.open(ref_file_dir+"/test.txt", "r")){
				for(String line : f){
					x.print(line);
				}	
			}
			
			String dd = x.String.valueOf(3);
			
			x.print(dd);
			
			Regex a = x.Regex(
					 "gdfgfdg"	//
					+"sdfssdf"	//
					+""			//
					+""			//
					+""			//
			);
			
			
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
			
			str title = x.str("Hello‹„ World");
			title = x.sorted(title,x.len,true);
			
			title = x.str(x.<String>element().transformWith(x.<String>asKeyOn(analogs)).ifElement(x.in(analogs)).elseTransformWith(x.upper).forElementIn(title).ifNotElement(x.in(x.list("o","r"))));
			
			x.print(title);
			
			list<list<String>> lists_lst = x.list(x.list("aaa","bbb","ccc"),x.list("ddd","eee","fff")); 
			
			list<tuple> lists_lst2 = x.list(x.element(0,1).forElementIn(lists_lst));
			
			x.print(lists_lst2);
			
			x.print(x.String("a").in("a","["));
			
			list<dict<String>> lst2 = x.list(x.<String>dict(x.tuple("aaa", "bbb"),x.tuple("bbb", "bbb")),x.<String>dict(x.tuple("aaa", "bbb"),x.tuple("bbb", "bbb")));
			
			x.print(lst2);
			
			list<String> lst3 = x.listOf("aaa");
			
			x.print(lst3);
			
			x.print(x.dict(x.tuple("aaaa",1)));
			
			str new_str = x.str("hello");
			new_str = x.str(x.<String>element().transformWith(x.upper).forElementIn(new_str));
			x.print(new_str); 
			
			list<tuple> lst4 = x.list(x.tuple("aaa", "bbb"),x.tuple("xxxx", "yy Y yy"));
			list<tuple> lst5 = x.list(x.element(0,1).transformWith(x.len,x.toUpperCase).forElementIn(lst4));
			x.print("uuu",lst5);
			
			x.print(Json.dump(lists_lst));
			JsonArray loaded_lst = Json.loads("[[\"aaa\",\"bbb\",\"ccc\"],[\"ddd\",\"eee\",\"fff\"]]");
			x.print(Json.dump(loaded_lst));
			
			dict<Character> def = x.DefaultDict(Character.class);
		
			x.print("ff",def.get("llll"),"jj");
			
			//x.print(x.str("dddddaaaaassssooooxxxxxiiiiiii").translate(lst4));
			
			tuple t = x.tuple((Object)1, (Object)"ddd");
			tuple2<Integer,String> t2 = (tuple2<Integer,String>)t;
			x.print(t2.value0+4,t2.value1+"yyy");
			
			list<list<String>> lst6 = x.list(x.list("aaa", "bbb"),x.list("xxxx", "yyYyy"));
			
			list<String> lst7 = x.list(x.<String>element().transformWith(new LambdaFunction<String>("x : f0(x[0])",x.upper)).forElementIn(lst6).ifElement(new LambdaPredicate("x : f0(x[0]) && f1(x[0]) && (x[1] != \"bbb\")",x.TRUE,x.TRUE)));
			
			x.print(lst7);
			
			list<Integer> lst8 = x.list(1,2,3,4,5);
			x.print(lst8.sliceTo(-100,-1));
			
			x.print(x.String("hello").sliceTo(4),"0");
			
			//x.print(Time.time());
			
			dict<String> translator = x.dict(x.tuple("a", "A"),x.tuple("b", "B"));
			
			x.print(x.Regex("\\w").sub(translator, "Mama papa bonjour!"));
			
			x.print(x.Regex("(?<name1>a)|(?<name2>b)").search("mama").group("name1"));
			
			x.print(x.Regex("(?<name1>a)|(?<name2>b)").search("mama").group(1));
			
			list<String> flatten = lst6.flattened(String.class);
			
			x.print(flatten); 
			
			list<Integer> ints = x.list(1,2,3,4,5);
			ints.setAt(1,3).values(x.list(7,8,9));
			
			x.print(ints); 
			
			ints = x.list(1,2,3,4,5);
			x.print(ints.slice(Slicer.EVERY_THIRD));
			
			List<Integer> list_int = new ArrayList<Integer>();
			list_int.add(1);
			list_int.add(2);
			list_int.add(3);
			
			Iterable<Integer> gen = new Generator<Integer>() {
				public void define() throws InterruptedException{
					int i = 0;
					while(i<5){
						yield(1);
						i++;
					}
				}
			};
			
			for(int val : gen){
				x.print(val);
			}
			
			dict<String> replacer = x.dict(x.tuple("\\bhaha\\b","ohoh"),x.tuple("\\bhjehe\\b","wow"));
			x.print(x.Regex(replacer).sub("lala haha bebeb hehe ogogo"));
			
			x.print(x.largestN(x.set(1,2,3),2));
			
			x.print(x.list(1,2,3,4,5).ngrams(6));
			
			x.print(x.String("aab").in("xaaabx"));
			
			x.print(x.String("aab").in(x.str("xaaabx")));
			
			Bag<String> bag = x.Bag(x.dict(x.tuple("aa", 10),x.tuple("bb", 10)));
			
			for(String element : bag.elements()){
				x.print(element);
			}
			
			list<String> lst10= x.list(" aaa "," кккк "," ccc ");
			
			lst10 = x.list(x.<String>element().transformWith(x.lambdaF("x : f1(f0(x)) + '''rrr'''",x.upper,x.strip)).forElementIn(lst10).ifElement(x.lambdaP("x : (f0(x) > 5) && (f1(x) != '''жжж''')",x.len,x.strip)));
			
			x.print(lst10);
			
			Function<Object,Integer> increment = x.<Integer>lambdaF("x : x + 1");
			
			Function<Integer,Integer> incrementAndMultiplyBy5 = x.chainOf(increment,x.<Integer>lambdaF("x : x * 5"));
			
			Function<Integer, String> strCopy = new Function<Integer, String>() {
				public String apply(Integer count) {
					return x.String("hello").times(count);
				}
			};
			
			
			Function<Integer,String> cachedFunction = x.memo(strCopy);
			
			Timer timer = x.Timer();
			String copies = cachedFunction.apply(500);
			x.print(timer.stop());
			
			timer.start();
			copies = cachedFunction.apply(500);
			x.print(timer.stop());
			
			x.print(x.tuple(0,1).equals(x.tuple(0,2)));
			
			x.print(x.tuple(0,1).equals(x.tuple(0,1)));
			
			x.print(x.tuple(0,1).compareTo(x.tuple(0,2)));
			
			x.print(x.tuple(1,1).hashCode());
			x.print(x.tuple(0,1).hashCode());
			
		}catch(Exception e){
			throw e;
		}
	}
	
}