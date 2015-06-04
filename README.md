# xpresso
The most pythonic way to code in Java.

Xpresso allows a line-into-line rewrite of a Python code into Java. It implements in Java familiar pythonic methods (e.g., len, enumerate, range, split/join) and coding paradigms (everything is iterable, list comprehensions, lambda expressions, filtering iterables using predicates and modifying them using functions).

Main features:

 * one-line file open for write/read/append in text and binary mode (HappyFile):
 
 ```
 HappyFile f0 = x.open("name.txt","r","utf-8");
 ```
 
 * list comprehensions: list\<String\> list1 = x.list(x.scalar().transformWith(x.upper).forScalarIn(list0).ifNotScalar(x.in(set0)));
 
 * iterable file: for(String line : f0)) {x.print(line);}
 
 * iterable dict: for(String key : dict0) {x.print(dict0.get(key));}

 * fast list, set and tuple definition by simply providing member values: tuple tuple0 = x.tupleOf("Hello",1,true);

 * fast dict definition from a list of tuples: dict\<String\> dict0 = x.dictOf(x.tupleOf("key0","value0"),x.tupleOf("key1","value1"),...);

 * slicing for list, strings (via x.String), and str (negative steps are supported)
 
 * Regex with function and dict as a replacement
 
 * Iterable Regex search results (via searchIter and searchAll)
  
 * lambda expressions
 
 * standard Python type clones: str, set, dict, list, tuple, DefaultDict, OrderedDict, Bag (similar to Python's Counter)
  
 * extended String functions (via x.String)
 
 * flattening list
 
 * list assignement via a slice
 
 * Slicer object
 
 * inverted dict
 
 * largest and smallest elements of an iterable (x.largestN, x.slmallestN)
 
 * n-grams
 
 * str in String, x.String in String, str in str, x.String in str
 
 * itertools clones: cycle, repeat, count (combining Python's itertools.count and range methods)
 
 * and more
