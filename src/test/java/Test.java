import java.util.ArrayList;
import java.util.List;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.Slicer;
import com.wantedtech.common.xpresso.Time;
import com.wantedtech.common.xpresso.experimental.generator.Generator;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.json.Json;
import com.wantedtech.common.xpresso.json.JsonArray;
import com.wantedtech.common.xpresso.lambda.LambdaFunction;
import com.wantedtech.common.xpresso.lambda.LambdaPredicate;
import com.wantedtech.common.xpresso.regex.Regex;
import com.wantedtech.common.xpresso.types.Bag;
import com.wantedtech.common.xpresso.types.str.str;
import com.wantedtech.common.xpresso.types.tuple.tuple;
import com.wantedtech.common.xpresso.types.tuple.tuple2;

public class Test {
	
	public static void main(String[] args) throws Exception {
		try{
			
			String ref_file_dir = "/Users/andriy.burkov/p/workspace/python/InternationalTitleCleanup/ref";
			
			for(String line : x.open(ref_file_dir+"/test.txt", "r")){
				x.print(line);
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
			
			
			dict<String> analogs = x.dictOf(
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
			
			str title = x.str("Hello‹„ World");
			title = x.sorted(title,x.len,true);
			
			title = x.str(x.<String>element().transformWith(x.<String>asKeyOn(analogs)).ifElement(x.in(analogs)).elseTransformWith(x.upper).forElementIn(title).ifNotElement(x.in(x.listOf("o","r"))));
			
			x.print(title);
			
			list<list<String>> lists_lst = x.listOf(x.listOf("aaa","bbb","ccc"),x.listOf("ddd","eee","fff")); 
			
			list<tuple> lists_lst2 = x.list(x.element(0,1).forElementIn(lists_lst));
			
			x.print(lists_lst2);
			
			x.print(x.String("a").in("a","["));
			
			list<dict<String>> lst2 = x.listOf(x.<String>dictOf(x.tupleOf("aaa", "bbb"),x.tupleOf("bbb", "bbb")));
			
			x.print(lst2);
			
			list<String> lst3 = x.listOf("aaa");
			
			x.print(lst3);
			
			x.print(x.dictOf(x.tupleOf("aaaa",1)));
			
			str new_str = x.str("hello");
			new_str = x.str(x.<String>element().transformWith(x.upper).forElementIn(new_str));
			x.print(new_str); 
			
			list<tuple> lst4 = x.listOf(x.tupleOf("aaa", "bbb"),x.tupleOf("xxxx", "yy Y yy"));
			list<tuple> lst5 = x.list(x.element(0,1).transformWith(x.len,x.toUpperCase).forElementIn(lst4));
			x.print("uuu",lst5);
			
			x.print(Json.dump(lists_lst));
			JsonArray loaded_lst = Json.loads("[[\"aaa\",\"bbb\",\"ccc\"],[\"ddd\",\"eee\",\"fff\"]]");
			x.print(Json.dump(loaded_lst));
			
			dict<Character> def = x.DefaultDict(Character.class);
		
			x.print("ff",def.get("llll"),"jj");
			
			//x.print(x.str("dddddaaaaassssooooxxxxxiiiiiii").translate(lst4));
			
			tuple t = x.tupleOf((Object)1, (Object)"ddd");
			tuple2<Integer,String> t2 = (tuple2<Integer,String>)t;
			x.print(t2.value0+4,t2.value1+"yyy");
			
			list<list<String>> lst6 = x.listOf(x.listOf("aaa", "bbb"),x.listOf("xxxx", "yyYyy"));
			
			list<String> lst7 = x.list(x.<String>element().transformWith(new LambdaFunction<String>("x : f0(x[0])",x.upper)).forElementIn(lst6).ifElement(new LambdaPredicate("x : f0(x[0]) && f1(x[0]) && (x[1] != \"bbb\")",x.TRUE,x.TRUE)));
			
			x.print(lst7);
			
			list<Integer> lst8 = x.listOf(1,2,3,4,5);
			x.print(lst8.sliceTo(-100,-1));
			
			x.print(x.String("hello").sliceTo(4),"0");
			
			x.print(Time.time());
			
			dict<String> translator = x.dictOf(x.tupleOf("a", "A"),x.tupleOf("b", "B"));
			
			x.print(x.Regex("\\w").sub(translator, "Mama papa bonjour!"));
			
			x.print(x.Regex("(?<name1>a)|(?<name2>b)").search("mama").group("name1"));
			
			x.print(x.Regex("(?<name1>a)|(?<name2>b)").search("mama").group(1));
			
			list<String> flatten = lst6.flattened(String.class);
			
			x.print(flatten); 
			
			list<Integer> ints = x.listOf(1,2,3,4,5);
			ints.setAt(1,3).values(x.listOf(7,8,9));
			
			x.print(ints); 
			
			ints = x.listOf(1,2,3,4,5);
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
			
			dict<String> replacer = x.dictOf(x.tupleOf("\\bhaha\\b","ohoh"),x.tupleOf("\\bhjehe\\b","wow"));
			x.print(x.Regex(replacer).sub("lala haha bebeb hehe ogogo"));
			
			x.print(x.largestN(x.setOf(1,2,3),2));
			
			x.print(x.listOf(1,2,3,4,5).ngrams(6));
			
			x.print(x.String("aab").in("xaaabx"));
			
			x.print(x.String("aab").in(x.str("xaaabx")));
			
			Bag<String> bag = x.Bag(x.dictOf(x.tupleOf("aa", 10),x.tupleOf("bb", 10)));
			
			for(String element : bag.elements()){
				x.print(element);
			}
			
			list<String> lst10= x.listOf(" aaa "," кккк "," ccc ");
			
			lst10 = x.list(x.<String>element().transformWith(x.lambdaF("x : f1(f0(x)) + '''rrr'''",x.upper,x.strip)).forElementIn(lst10).ifElement(x.lambdaP("x : (f0(x) > 5) && (f1(x) != '''жжж''')",x.len,x.strip)));
			
			x.print(lst10);
			
			Function<Object,Integer> increment = x.<Integer>lambdaF("x : x + 1");
			
			Function<Integer,Integer> incrementAndMultiplyBy5 = x.chainOf(increment,x.<Integer>lambdaF("x : x * 5"));
			
		}catch(Exception e){
			throw e;
		}
	}
	
}