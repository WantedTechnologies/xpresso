# xpresso
The pythonic way to code in Java.

xpresso is a Java library inspired by Python. It allows a (near) line-into-line rewrite of a Python code into Java. It's also a great way to prototype your algorithms directly in Java.

xpresso implements in Java familiar pythonic methods (e.g., len, enumerate, split/join, slicing) and coding paradigms (e.g., everything is iterable, list comprehensions, generators, lambda expressions, filtering iterables using predicates and modifying them using functions).

xpresso also offers multiple useful tools, usually one-liners, that save developer's time and make the code more readable: x.memo (a universal memoizer), x.Object (a universal *hashCode*, *compareTo*, and *equals* method builder), x.timer (a static timer), x.Json (a fast JSON I/O), x.mysql and others.

Less boilerplate, more fun, more work done.

License: [MIT] (https://en.wikipedia.org/wiki/MIT_License).

## Usage

```
import com.wantedtech.common.xpresso.x;

x.print("Hello World!");
```

## Main features

#### Types similar to pythonic ones

set, dict, list, tuple, DefaultDict, OrderedDict, Bag (similar to Python's Counter)

#### Slicable and iterable **str** type
```
import com.wantedtech.common.xpresso.types.str;

str city = x.str("New York City");

x.print(city.slice(4,8));

Console: York
```

```
for (String character : city) x.print(character);

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
 
try (HappyFile f = x.open("name.txt","r","utf-8")) {
	//do stuff
}
```
Works for write/read/append in both text and binary mode.

As in Python, a file opened for reading in text mode is an Iterable of strings:

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
import com.wantedtech.common.xpresso.types.*; //imports list, set, dict, tuple,...

list<String> trips = x.list("Dubai","New York","London","Paris","Moscow","London","Saint-Petersburg","New York");

set<String> russianCities = x.set("Moscow","Saint-Petersburg");

dict<Integer> rank = x.dict(x.tuple("Moscow",30),x.tuple("Saint-Petersburg",15),x.tuple("New York",20),x.tuple("London",10),x.tuple("Paris",5),x.tuple("Dubai",32));
```

#### Functions and predicates
```
Function<Object, String> toUpperCaseFun = new Function<Object, String>() {
	public String apply(Object value) {
		return value.toString().toUpperCase();
	}
};

list<String> tripsUp = trips.mapped(toUpperCaseFun);
x.print(tripsUp);

Console: [DUBAI, NEW YORK, LONDON, PARIS, MOSCOW, LONDON, SAINT-PETERSBURG, NEW YORK]
```

```
Predicate<Object> containsO = new Predicate<Object>() {
	public Boolean apply(Object value) {
		return value.toString().contains("o") ? true : false;
	}
};

list<String> tripsO = trips.filtered(containsO);
x.print(tripsO);

Console: [New York, London, Moscow, London, New York]
```

#### Lambda expressions
Python:
```
best_cities = reversed(sorted(item for item in rank.items(),lambda x: x[0]))
```

xpresso:
```
list<String> bestCities = x.reversed(x.sorted(yield().forEach(rank.items()),x.lambdaF("x: x[0]")));
```

More complex lambda expressions:
```
Predicate<Object> pr = x.lambdaP("x : f0(f1(x[1])) == '''new york'''",x.lower,x.strip);
```

```
Function<Object,Integer> squareFun = x.lambdaF("x : x * x");

Function<Object,Integer> fun = x.lambdaF("x : x[0] * 10 * (x[1] - f0(x[2])))",squareFun);
```

Function chains:
```
Function<Object,Integer> incrementFun = x.lambdaF("x : x + 1");
Function<Object,Integer> squareFun = x.lambdaF("x : x * x");

Function<Object,Integer> chainFun = x.chain(incrementFun,squareFun);
```
*chainFun* will first increment, then square its input. *x.chain(...)* can take more than two functions as argument. The last function in the chain has to return the value of the desired output type.

#### List comprehensions

Python:
```
foreign_trips_lower = [city.lower() for city in trips if city not in russian_cities]
```

xpresso:
```
list<String> foreignTripsLower = x.list(x.<String>yield().apply(x.lower).forEach(trips).unless(x.in(russianCities)));
```

Python:
```
cool_cities = dict([(city.upper(),true) for (city, score) in rank.items() if score > 5])
```

xpresso:
```
dict<Integer> coolCities = x.dict(x.yield("city","_").apply(x.upper).replace(true).where("city","score").in(rank.items()).when(x.lambdaP("city, score : score > 20")));
```

Python:
```
evals = [True if value == "good" else False for value in some_list]
```

xpresso:
```
list<Boolean> evals = x.list(x.<Boolean>yield().replace(true).when(x.lambdaP("x : x == '''good'''")).replaceOtherwise(false).forEach(someList));
```

You can use list comprehensions to extract properties from element objects:
```
class PlannedTrip {
    int year;
    String city;
    
    public PlannedTrip(int year, String city) {
        this.year = year;
        this.city = city;
    }
 
    public int getYear() { return year; }
    public String getCity() { return city; }
}

list<PlannedTrip> plans = x.list(new PlannedTrip(2015, "Moscow"), new PlannedTrip(2016, "Paris"));

list<tuple> plansData = x.list(x.yield("year", "city").where("year", "city").in(plans));

x.print(plansData);

Console: [(2015, Moscow), (2016, Paris)]
```
You can also filter the extracted values in the same expression:

```
list<tuple> plansData = x.list(x.yield("year", "city").where("year", "city").in(plans).when(x.lambdaP("year, city : year > 2015)));

x.print(plansData);

Console: [(2016, Paris)]
```

#### Generators
Python:
```
def firstn(n):
	num = 0
	while num < n:
		yield num
		num += 1

for i in firstn(500000):
	print i
```

xpresso:
```
class FirstN extends Generator<Integer> {
	public void generator(int n) {
		int num = 0;
		while (num < n) {
			yield(num);
			num++;
		}
	}
}

try (Generator<Integer> iter = x.generate(FirstN.class, 500000) {
	for (int i : iter) 
		x.print(i);
}
```

#### Memoization

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
x.timer.start();
String copies = cachedXerox.apply(5000000);
x.print(x.timer.stop());

Console: 18.898s
```
The second call with the same value of *count*, the result is instantaneous:
```
x.timer.start();
String moreCopies = cachedXerox.apply(5000000);
x.print(x.timer.stop());

Console: 0.0s
```
*x.memo* can be used to cache methods of object of any Java type, not only Function. Notice the usage of the standard *x.timer*: no additional timer object needs to be created.

#### JSON
Remember the *rank* dict:
```
dict<Integer> rank = x.dict(x.tuple("Moscow",30),x.tuple("Saint-Petersburg",15),x.tuple("New York",20),x.tuple("London",10),x.tuple("Paris",5),x.tuple("Dubai",32));
```

Let's first dump it as a String:
```
String rankAsString = x.Json(rank).toString();
x.print(rankAsString);

Console: {"New York":20,"London":10,"Saint-Petersburg":15,"Moscow":30,"Dubai":32,"Paris":5}
```
Now let's create a copy of the *rank* dict from its JSON string representation:
```
dict<Integer> rankCopy = x.String(rankAsString).parseJson();
```
Compare the original *rank* dict to the copy:
```
x.print(x.Object(rank).equals(rankCopy));

Console: true
```

#### CSV
```
try (csv f = x.csv("filename.txt","r","utf-8") {
	for (list<String> line : f) {
		//do stuff
	}
}
```

```
try (csv f = x.csv("filename.txt","w","utf-8") {
	for (list<?> line : iterable){
		csv.writerow(line);
	}
}
```

```
StringBuilder builder = new StringBuilder();
csv c = x.csv(builder);

for (list<?> line : iterable) {
	c.writerow(line);
}

String cs = c.toString();
```

Because the constructor of *csv* class can take iterables as input, the previous block of code can be written in a much shorter way:
```
String cs = x.csv(iterable).toString();
```

#### MySQL
```
String host = "host:port";
String user = "user";
String password = "password";
String db = "db";

try (HappySQL sql = x.mysql(host, user, password, db)) {
	try (HappySQL sql2 = x.mysql(sql)){
		String query =
		"SELECT ID FROM " +
		"tbl_Employees e " +
		"WHERE e.Name LIKE ?";
		
		for (tuple row : sql.execute(query, "John %")) {
			query =
			"UPDATE tbl_Employees " +
			"SET Promoted = 1 " +
			"WHERE ID = ?";
			sql2.execute(query, row.get("ID"));
		}
	}
}
```

#### Extended String functions

Python:
```
if "e" in "Hello World":
    #do stuff
```

xpresso:
```
if(x.String("e").in("Hello World")) {
    //do stuff
}
```

Python:
```
colorsPattern = "|".join(["black","green","red","white"]);

print colorsPattern

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

print tokens

>>> ['Moscow', 'London', 'Paris']
```

xpresso:
```
list<String> tokens = x.String("Moscow;London;Paris").split(";");

x.print(tokens);

Console: [Moscow, London, Paris]
```

Transliterate:
```
String trans = x.String("Чичётка 北亰").translit();

x.print(trans);

Console: Čičëtka bei jing

x.print(trans.stripAccents());

Console: Cicetka bei jing
```

Convert unicode to ascii:
```
String unidec = x.String("Чичётка 北亰").unidecode();

x.print(unidec);

Console: Chichiotka bei jing
```

Approximate string comparison:
```
x.print(x.String("Hello World").similarity("Hello Wold!"))

Console:  0.82
```

Approximate pattern matching:
```
x.print(x.String("You are cooding in Java.").search("coding"));

Console: 8
```

Get similar strings:
```
list<String> lookAlikes = x.String("apple").lookAlikes(x.list("ape", "apples", "peach", "puppy"),.5);

x.print(lookAlikes);

Console: [ape, apples]
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

#### Slicer object
```
Slicer LAST_THREE = x.sliceFrom(-3);

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

#### The Token type
```
Token tok = x.Token("MySQL5");
x.print(tok.shape, tok.isCamel, tok.hasDigits, tok.hasRussian);

Console: ULUUUD, true, true, false
```

```
tok = x.Token("Thinking");
x.print(tok.stem());

Console: Think
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

Dynamic name assignment to tuple elements:
```
myCar.name("make","color","year","good");
x.print(myCar.get("good"),myCar.get("make"),myCar.get("year"));

Console: true Honda 2010
```
If *name* method has not yet been called, but *get(someName)* is called for the first time, then the returned value will be *get(i)*, where *i* is the smallest index of a remaining unnamed element in the tuple. All the subsequent calls for the same value *someName*, the same element *i* will be returned by *get(someName)*.


#### hashCode(), equals(...), and compareTo(...) builders
When defining a class:
```
@Override
int hashCode() {
	return x.Object(this).hashCode();
}
```
In the above code, xpresso first finds the members of *this* (via reflections) and then dynamically computes the hash code for *this* based on the values of its members.

```
@Override
boolean equals(Object obj) {
	return x.Object(this).equals(obj);
}
```
In the above code, xpresso first finds the members of the two objects (*this* and *obj*), and then compares the values of those members.

```
@Override
public int compareTo(Object obj) {
	return x.Object(this).compareTo(obj, fieldName0, fieldName1, ...);
}
```
In the above code, xpresso first finds the members of the two objects (*this* and *obj*). It then compares the values of those members between the two objects if those members' names are listed among the input field names fieldName0, fieldName1, etc. The order of comparisons between the member's values is the same as the order of input field names.

#### Assertions

```
x.assertTrue(condition);	// throws IllegalArgumentException

x.assertNotNull(parameter);	// throws NullPointerException

x.assertNotEmpty(iterable);	/* throws NullPointerException if iterable is null,
							   throws IllegalArgumentException if iterable is empty */
x.assertNotEmpty(string);

x.assertNotEmpty(array);
```
Throws

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

#### N-grams
```
str phrase = "If you want something done right, you have to do it yourself.";

list<str> tokens = phrase.split();

list<list<str>> ngrams = tokens.ngrams(3);

x.print(ngrams);

Console: [[If, you, want], [you, want, something], [something, done, right]..., [do, it, yourself.]]
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
* Invert dict: ```dict.inverted();```
* Flatten list: ```list.flattened();```
* Modify list using slicing: ```trips.setAt(3, 5).values(x.list(1, 2, 3, 4, 5));```
* Case insensitive regex shortcut: ```x.RegexNoCase("\\bmama\\b")```
* Replace each match by an empty string: ```x.Regex("[,.;]").clear(inputString)```
* For more see the [comments](https://github.com/WantedTechnologies/xpresso/blob/master/src/main/java/com/wantedtech/common/xpresso/x.java) for the main class [x](https://github.com/WantedTechnologies/xpresso/blob/master/src/main/java/com/wantedtech/common/xpresso/x.java).

#### Future
* Find longest match (in the spirit of Python's [SequenceMatcher] (https://docs.python.org/3.4/library/difflib.html#difflib.SequenceMatcher)):

```
tuple match = x.String("I like apples.").longestMatch("My girlfriend likes apples too.");

x.print(match);

Console: ("apples", 7, 20)
```

* Mistyping detection: ```x.String("Random").isMistypingOf("Randon","qwerty") --> true```
