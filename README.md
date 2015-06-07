# xpresso
The pythonic way to code in Java.

xpresso allows a line-into-line rewrite of a Python code into Java. It's also a great way to prototype your algorithms directly in Java instead of using less verbose languages, like Python or Ruby. It implements, in Java, familiar pythonic methods (e.g., len, enumerate, split/join, slicing) and coding paradigms (e.g., everything is iterable, list comprehensions, lambda expressions, filtering iterables using predicates and modifying them using functions).

Less boilerplate, more fun, more work done.

License: [MIT] (https://en.wikipedia.org/wiki/MIT_License).

## Usage

```
import com.wantedtech.common.xpresso.x;

x.print("Hello World!");
```

## Main features

#### Types similar to pythonic ones

* str
* set
* dict
* list
* tuple
* DefaultDict (in Python: collections.defaultdict)
* OrderedDict (in Python: collections.OrderedDict)
* Bag (in Python: collections.Counter)

#### Slicable and iterable **str** type
```
import com.wantedtech.common.xpresso.types.str.str;

str city = x.str("New York City");

x.print(city.slice(4,8));

Console: York
```

```
for(String character : city)
    x.print(character);

Console: N
e
w

Y
o
r
k
```

#### One-line file open

Python:
```
with open("name.txt","r","utf-8") as f:
	#do stuff
```

xpresso:
```
import com.wantedtech.common.xpresso.types.HappyFile;
 
try(HappyFile f = x.open("name.txt","r","utf-8")){
	//do stuff
}
```

Works for write/read/append in both text and binary mode.

#### Iterable file

Python:
```
for line in f: print line
```

xpresso:
```
for (String line : f) x.print(line);
```

#### Neat standard object creation

Python:
```
trips = ["Dubai","New York","London","Paris","Moscow","London","Saint-Petersburg","New York"]

russian_cities = set(["Moscow","Saint-Petersburg"])

rank = dict(("Moscow":30),("Saint-Petersburg":15),("New York":20),("London":10),("Paris":5),("Dubai":32))

```

xpresso:
```
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.set;
import com.wantedtech.common.xpresso.types.tuple.tuple;

list<String> trips = x.list("Dubai","New York","London","Paris","Moscow","London","Saint-Petersburg","New York");

set<String> russianCities = x.set("Moscow","Saint-Petersburg");

dict<Integer> rank = x.dict(x.tuple("Moscow",30),x.tuple("Saint-Petersburg",15),x.tuple("New York",20),x.tuple("London",10),x.tuple("Paris",5),x.tuple("Dubai",32));
```

#### List comprehensions

Python:
```
foreign_trips_lower = [element.lower() for element in trips if element not in russian_cities]
```

xpresso:
```
list<String> foreignTripsLower = x.list(x.element().transformWith(x.lower).forElementIn(trips).ifElementNot(x.in(russianCities)));
```

Python:
```
new_list = [element.upper() if element[0] == "a" else element.lower() for element in old_list if element[0] not in ["x", "y", "z"]]
```

xpresso:
```
list<String> newList = [x.element().transformWith(x.upper).ifElement(x.LambdaP("x:x[0] == "a")).elseTransfromWith(x.lower).forElementIn(oldList).ifNotElement(x.LabmdaP("x:f0(x[0])",x.in(x.list("x", "y", "z"))));
```

#### Pythonic iterable dict

Python:
```
for city in rank: print rank[city]
```

xpresso:
```
for (String city : rank) x.print(rank.get(city));
```

#### Pythonic tuples

Python:
```
my_car = ("Honda", "red", 2010, True)
```

xpresso:
```
tuple myCar = x.tuple("Honda", "red", 2010, true);
```

#### Slicing for list, String, and str

Python:
```
trips = ["Dubai","New York","London","Paris","Moscow","London","Saint-Petersburg","New York"]

print trips[2:4]

>>> ['London', 'Paris']
```

xpresso:
```
x.print(trips.slice(2,4));

Console: [London, Paris]
```

Python:
```
print trips[:5]

>>> ['Dubai','New York','London','Paris','Moscow']
```

xpresso:
```
x.print(trips.sliceTo(5));

Console: [Dubai, New York, London, Paris, Moscow]
```

Negative and non-unit steps are supported:

Python:
```
print trips[::-1]

>>> ['New York', 'Saint-Petersburg', 'London', 'Moscow', 'Paris', 'London', 'New York', 'Dubai']
```

xpresso:
```
x.print(trips.slice(-1));

Console: [New York, Saint-Petersburg, London, Moscow, Paris, London, New York, Dubai]
```

Python:
```
print trips[::2]

>>> ['Dubai','London','Moscow','Saint-Petersburg']
```

xpresso:
```
x.print(trips.slice(2));

Console: [Dubai, London, Moscow, Saint-Petersburg]
```

#### Slice object
```
Slice LAST_THREE = x.sliceFrom(-3);

x.print(x.String("tic tac toe").slice(LAST_THREE));

Console: toe
```

#### Iterable regex search results

Python:
```
for long_word_match in re.finditer("\b\w{10,}\b",text):
    print long_word_match.group(0)
```

xpresso:
```
for (Match longWordMatch : x.Regex("\\b\\w{10,}\\b").searchIter(text))
    x.print(longWordMatch.group(0));
```

Python:
```
for long_word in re.findall("\b\w{10,}\b",text):
    print long_word
```

xpresso:
```
for (String longWord : x.Regex("\\b\\w{10,}\\b").searchAll(text))
    x.print(longWord);
```

#### Replace with a Function

Python:
```
def toUpperCaseFun(value):
	return value.upper()

text = re.sub("\b\w{10,}\b",toUpperCaseFun,text)
```

xpresso:
```
Function<String,String> toUpperCaseFun = new Function<String,String>(){
	public String apply(String value) {
		return value.toUpperCase();
	}
}

text = x.Regex("\\b\\w{10,}\\b").sub(toUpperCaseFun,text);
```

Regex.searchIter and Regex.searchAll replace Python's re.findIter and re.findAll.

#### Replace with a dict

Python:
```
not available
```

xpresso:
```
dict<String> replacer = x.dict(x.tuple("bad","good"),x.tuple("small","big"),x.tuple("hard","easy"));

text = x.Regex(replacer).sub(text);
```

#### Lambda expressions

Python:
```
best_cities = reversed(sorted(item[0] for item in rank.items(),lambda x:x[0]))
```

xpresso:
```
bestCities = x.reversed(x.sorted(element(0).forElementIn(rank.items()),x.lambdaF("x:x[0]")));
```

More complex lambda expressions:
```
Predicate<Object> pr = x.LambdaP("x : f0(f1(x[1])) == '''new york'''",x.lower,x.strip);
```

```
list<Integer> ints = x.list(10, 50, 48);

Function<Object,Integer> squareFun = x.LambdaF("x : x * x");

Function<Object,Integer> fun = x.LambdaF("x : x[0] * 10 * (x[1] - f0(x[2])))",squareFun);
```

#### Extended String functions

Python:
```
if "e" in "Hello World":
    #do stuff
```

xpresso:
```
if(x.String("e").in("Hello World"))
    //do stuff
```

Python:
```
colorsPattern = "|".join(["black","green","red","white"]);

print(colorsPattern)

>>> black|green|red|white
```

xpresso:
```
String colorsPattern = x.String("|").join(x.list("black","green","red","white"));

x.print(colorsPattern);

Console: black|green|red|white
```

Python:
```
tokens = "Moscow;London;Paris".split(";")

print(tokens)

>>> ['Moscow', 'London', 'Paris']
```

xpresso:
```
list<String> tokens = x.String("Moscow;London;Paris").split(";");

x.print(tokens);

Console: [Moscow, London, Paris]
```

#### Easy caching of any object's method results

As a quick example, let *xerox* be a *Function* object whose method *apply* copies the string *"hello"* the given number *count* of times:
```
Function<Integer, String> xerox = new Function<Integer, String>() {
	public String apply(Integer count) {
		return x.String("hello").times(count);
	}
};
```
It's a long to execute function for large values of *count*.

In order to avoid the long computation for the same value of *count*, we first create a cached version of xerox using **x.memo**:
```
Function<Integer,String> cachedXerox = x.memo(xerox);
```			
The first call of the function. The computation takes a very long time:
```
Timer timer = x.Timer();
String copies = cachedXerox.apply(5000000);
x.print(timer.stop());

Console: 18.898s
```
The second call with the same value of *count*, the result is instantaneous:
```
timer.start();
String moreCopies = cachedXerox.apply(5000000);
x.print(timer.stop());

Console: 0.0s
```
*x.memo* can be used to cache methods of object of any Java type, not only Function.

#### Automatic building of o.hashCode() and o1.equals(Object o2)
When defining a class:
```
@Override
int hashCode(){
	return x.Object(this).hashCode();
}
```
The code above works by exploring the members of *this* and of each of its superclasses (via reflections) and by dynamically computing the hash code for *this* based on the values of those members.

```
@Override
boolean equals(Object obj){
	return x.Object(this).equals(obj);
}
```
The code above works by exploring the members of the two objects (*this* and *obj*), as well as of each of their respective superclasses, and by comparing the values of those members.

#### n-grams
```
str phrase = "If you want something done right, you have to do it yourself.";

list<str> tokens = phrase.split();

list<list<str>> ngrams = tokens.ngrams(3);

x.print(ngrams);

Console: [[If, you, want], [you, want, something], [something, done, right]..., [do, it, yourself.]]
```
 
#### Built-in iterators

* cycle
```
for (String letter : x.cycle(x.str("ABC")))
    x.print(letter);

Console: A
B
C
A
B
C
A
B
C
...
```

```
for (String letter : x.cycle(x.list("hello","world"),3))
    x.print(letter);

Console: hello
world
hello
world
hello
world
```

* repeat
```
for (String word : x.repeat("cool"))
    x.print(word);

Console: cool
cool
cool
cool
...
```

```
for (String word : x.repeat("cool"),3)
    x.print(word);

Console: cool
cool
cool
```

* count
```
for (Integer index : x.countTo(3))
    x.print(index);

Console: 0
1
2
```

```
for (Integer index : x.countFrom(10))
    x.print(index);

Console: 10
11
12
13
...
```

```
for (Integer index : x.count(3,10))
    x.print(index);

Console: 3
4
5
6
7
8
9
```

```
for (Integer index : x.count(3,10,3))
    x.print(index);

Console: 3
6
9
```

x.count(min, max) and x.count(min, max, step) replace Python's range(min, max) and range(min, max, step).

#### Print anything
```
x.print("Hello World", 1, true, x.list(1, 2, 3), null);

Console: Hello World 1 true [1, 2, 3] NullType
```

#### largestN and smallestN

```
list<String> cities = x.list("Moscow","San Francisco","Saint-Petersbourg","Rome");

x.print(cities.smallestN(2));

Console: [Rome, Moscow]
```

```
list<String> cities = x.list("Moscow","San Francisco","Saint-Petersbourg","Rome");

x.print(cities.largestN(2));

Console: [Saint-Petersbourg, San Francisco]
```

#### More
* invert dict: ```dict.inverted();```
* flatten list: ```list.flattened();```
* modify list using slicing: ```trips.setAt(3, 5).values(x.list(1, 2, 3, 4, 5));```
* for more see the [comments](https://github.com/WantedTechnologies/xpresso/blob/master/src/main/java/com/wantedtech/common/xpresso/x.java) for the main class [x](https://github.com/WantedTechnologies/xpresso/blob/master/src/main/java/com/wantedtech/common/xpresso/x.java).
* Case insensitive regex shortcut: ```x.RegexNoCase("\\bmama\\b")```
* Replace each match by an empty string: ```x.Regex("[,.;]").clear(inputString)```

#### Future
* Approximate string comparison: ```x.String("Hello World").similarity("Helo Wold!") --> .89```
* Get similar strings:

```
list<String> lookAlikes = x.String("apple").lookAlikes(x.list("ape", "apple", "peach", "puppy"),2,.8);

x.print(lookAlikes);

Console: [ape, apple]
```

* Find longest match (in the spirit of Python's [SequenceMatcher] (https://docs.python.org/3.4/library/difflib.html#difflib.SequenceMatcher)):

```
tuple match = x.String("I like apples.").longestMatch("My girlfriend likes apples too.");

x.print(match);

Console: ("apples", 7, 20)
```

* Mistyping detection: ```x.String("Random").isMistypingOf("Randon","qwerty") --> true```
* Wrap a string (similar to Python's [textwrap](https://docs.python.org/3.4/library/textwrap.html)):

```
x.print(x.String("If you want something done right you have to do it yourself.").wrap(25));

Console: [If you want something, done right you have to, do it yourself.]
```


* Shorten a string:
```
x.print(x.String("Hello World!").shorten(10, "..."));

Console: Hello...
```
