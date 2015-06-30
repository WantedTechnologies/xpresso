# xpresso
The pythonic way to code in Java.

xpresso is a Java library inspired by Python. It allows a (near) line-into-line rewrite of a Python code into Java. It's also a great way to prototype your algorithms directly in Java.

xpresso implements in Java familiar pythonic methods (e.g., len, enumerate, split/join, slicing) and coding paradigms (e.g., everything is iterable, list comprehensions, generators, lambda expressions, filtering iterables using predicates and modifying them using functions).

xpresso also offers multiple useful tools, usually one-liners, that save developer's time and make the code more readable: x.String, x.Object, x.memo, x.WebService, x.MapReduce, x.go, x.timer, x.Json, x.mysql, x.csv and others.

xpresso: less boilerplate, more fun, more work done.

License: [MIT] (https://en.wikipedia.org/wikei/MIT_License).

## Usage

```java
import com.wantedtech.common.xpresso.x;

x.print("Hello World!");
```

## Main features

#### Types similar to pythonic ones

```java
import com.wantedtech.common.xpresso.types.*;
```
Imports: set, dict, list, tuple, DefaultDict, OrderedDict, Bag, HappyFile, HappySQL


#### Slicable and iterable str type
```java
str city = x.str("New York City");

x.print(city.slice(4,8));

Console: York
```

```java
for (String character : city)
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
```python
with open("name.txt","r","utf-8") as f:
	#do stuff
```

xpresso:
```java
try (HappyFile f = x.open("name.txt","r","utf-8")) {
	//do stuff
}
```
Works for write/read/append in both text and binary mode.

As in Python, a file opened for reading in text mode is an Iterable of strings:

Python:
```python
for line in f:
	print line
```

xpresso:
```java
for (String line : f)
	x.print(line);
```

#### Tuples

Python:
```python
my_car = ("Honda", "red", 2010, True)
```

xpresso:
```java
tuple myCar = x.tuple("Honda", "red", 2010, true);
```

Dynamic name assignment to tuple elements:
```java
myCar.name("make","color","year","good");
x.print(myCar.get("good"),myCar.get("make"),myCar.get("year"));

Console: true Honda 2010
```
If *name* method has not yet been called, but *get(someName)* is called for the first time, then the returned value will be *get(i)*, where *i* is the smallest index of a remaining unnamed element in the tuple. All the subsequent calls for the same value *someName*, the same element *i* will be returned by *get(someName)*.

You can also define and use a typed version of tuple. For example:
```java
tuple3<String,String,Integer> myCar = x.tuple3("Honda", "red", 2010);

String myCarMake = myCar.left;
String myCarColor = myCar.middle;
Integer myCarYear = myCar.right;

tuple2<Integer,tuple3<String,String,Integer>> item = x.tuple2("car",myCar);

String type = item.key; //or, alternatively String type = item.left;
tuple3<String,String,Integer> car = item.value; //or, alternatively tuple3<String,String,Integer> car = item.right;
```

#### Neat standard object creation

Python:
```python
trips = ["Dubai","New York","London","Paris","Moscow","London","Saint-Petersburg","New York"]

russian_cities = set(["Moscow","Saint-Petersburg"])

rank = dict(("Moscow":30),("Saint-Petersburg":15),("New York":20),("London":10),("Paris":5),("Dubai":32))

```

xpresso:
```java
list<String> trips = x.list("Dubai","New York","London","Paris","Moscow","London","Saint-Petersburg","New York");

set<String> russianCities = x.set("Moscow","Saint-Petersburg");

dict<Integer> rank = x.dict(x.tuple("Moscow",30),x.tuple("Saint-Petersburg",15),x.tuple("New York",20),x.tuple("London",10),x.tuple("Paris",5),x.tuple("Dubai",32));
```

#### Functions and predicates
```java
import com.wantedtech.common.functional.*

Function<Object, String> toUpperCaseFun = new Function<Object, String>() {
	public String apply(Object value) {
		return value.toString().toUpperCase();
	}
};

list<String> tripsUp = x.map(toUpperCaseFun, trips);
x.print(tripsUp);

Console: [DUBAI, NEW YORK, LONDON, PARIS, MOSCOW, LONDON, SAINT-PETERSBURG, NEW YORK]
```

```java
Predicate<Object> containsO = new Predicate<Object>() {
	public Boolean apply(Object value) {
		return x.String("o").in(value.toString()) ? true : false;
	}
};

list<String> tripsO = x.filter(containsO, trips);
x.print(tripsO);

Console: [New York, London, Moscow, London, New York]
```

#### Lambda expressions
Python:
```python
best_cities = reversed(sorted(item for item in rank.items(),lambda x: x[0]))
```

xpresso:
```java
list<String> bestCities = x.reverse(x.sort(yield().forEach(rank.items()),x.lambdaF("x: x[0]")));
```

More complex lambda expressions:
```java
Predicate<Object> pr = x.lambdaP("x : f0(f1(x[1])) == '''new york'''",x.lower,x.strip);
```

```java
Function<Object,Integer> squareFun = x.lambdaF("x : x * x");

Function<Object,Integer> fun = x.lambdaF("x : x[0] * 10 * (x[1] - f0(x[2])))",squareFun);
```

Function chains:
```java
Function<Object,Integer> incrementFun = x.lambdaF("x : x + 1");
Function<Object,Integer> squareFun = x.lambdaF("x : x * x");

Function<Object,Integer> chainFun = x.chain(incrementFun,squareFun);
```
*chainFun* will first increment, then square its input. *x.chain(...)* can take more than two functions as argument. The last function in the chain has to return the value of the desired output type.

#### List comprehensions

Python:
```python
foreign_trips_lower = [city.lower() for city in trips if city not in russian_cities]
```

xpresso:
```java
list<String> foreignTripsLower = x.list(x.<String>yield().apply(x.lower).forEach(trips).unless(x.in(russianCities)));
```

Python:
```python
cool_cities = dict([(city.upper(),true) for (city, score) in rank.items() if score > 5])
```

xpresso:
```java
dict<Integer> coolCities = x.dict(x.yield("city","_").apply(x.upper).replace(true).where("city","score").in(rank.items()).when(x.lambdaP("city, score : score > 20")));
```

Python:
```python
evals = [True if value == "good" else False for value in some_list]
```

xpresso:
```java
list<Boolean> evals = x.list(x.<Boolean>yield().replace(true).when(x.lambdaP("x : x == '''good'''")).replaceOtherwise(false).forEach(someList));
```

You can use list comprehensions to extract properties from element objects:
```java
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

```java
list<tuple> plansData = x.list(x.yield("year", "city").where("year", "city").in(plans).when(x.lambdaP("year, city : year > 2015)));

x.print(plansData);

Console: [(2016, Paris)]
```

#### RESTful web services
Let's suppose we have an object of a class SomeMath which has two methods we would like to publish on the network as RESTful web services, getSum and getProduct:
```java
public class SomeMath() {
	public Double getSum(Double[] values) { //we want to publish this one
		return x.sum(values);
	}
	public Double getProduct(Double x, Double y) {//and this one
		return x * y;
	}
	public Double anotherMethod(Double somethingElse) {//but not this one
		return somethingElse;
	}
}
```

In order to convert our SomeMath class into a web service, we simply need to first annotate our two methods we want to call from the network with the @ExposeAs annotation, and then start our web service:
```java
public class SomeMath() {
	public Double getSum(@ExposeAs("values") Double[] values) {
		return x.sum(values);
	}
	public Double getProduct(@ExposeAs("x") Double x, @ExposeAs("y") Double y) {
		return x * y;
	}
	public Double anotherMethod(Double somethingElse) {
		return somethingElse;
	}
}

WebService ws = x.WebService(new SomeMath(), 8080).start();
```

That's all! Our web service is up and running. Let's test it. Open the following url in your browser:
```
http://localhost:8080/SomeMath/getSum?values=5&values=6&values=7
```

The output:
```
18.0
```
Now open the following url:
```
http://localhost:8080/SomeMath/getProduct?x=5&y=10
```

The output:
```
50.0
```

If a method returns an output type of more complex classes such as Java's standard Map and List, or xpresso's own list and dict, the output will be a corresponding JSON string.

#### Generators
Python:
```python
def firstn(n):
	num = 0
	while num < n:
		yield num
		num += 1

for i in firstn(500000):
	print i
```

xpresso:
```java
public Generator<Integer> firstn (final int n) {
	return new Generator<Integer>() {
		public void generate() {
			int num = 0;
			while (num < n) {
				yield(num);
				num++;
			}
		}
	};
}

for (int i : firstn(500000)) 
	x.print(i);
```

#### Memoization

As a quick example, let *xerox* be a *Function* object whose method *apply* copies the string *"hello"* the given number *count* of times:
```java
Function<Integer, String> xerox = new Function<Integer, String>() {
	public String apply(Integer count) {
		return x.String("hello").times(count);
	}
};
```
It's a long to execute function for large values of *count*.

In order to avoid the long computation for the same value of *count*, we first create a cached version of xerox using **x.memo**:
```java
Function<Integer,String> cachedXerox = x.memo(xerox);
```			
The first call of the function. The computation takes a very long time:
```java
x.timer.start();
String copies = cachedXerox.apply(5000000);
x.print(x.timer.stop());

Console: 18.898s
```
The second call with the same value of *count*, the result is instantaneous:
```java
x.timer.start();
String moreCopies = cachedXerox.apply(5000000);
x.print(x.timer.stop());

Console: 0.0s
```
*x.memo* can be used to cache methods of object of any Java type, not only Function. Notice the usage of the standard *x.timer*: no additional timer object needs to be created.

#### Concurrency (beta)
Concurrency in xpresso is inspired by [Go](https://en.wikipedia.org/wiki/Go_(programming_language)) and, as a consequence, is extremely simple. First, define a worker as an instance of Predicate:
```java
Predicate<Channel<Integer>> worker = new Predicate<Channel<Integer>>() {
	public Boolean apply(Channel<Integer> channel) {
		while (some_condition_true) {
			Integer value = computeValue();	//compute something in parallel
			channel.send(value);			//send the computed value to the channel
		}
		return true;						//everything went as expected
	}
};
```
Then, define the channel to where the workers should send the computed values as soon as those values are ready:
```java
Channel<Integer> channel = x.Channel(Integer.class);//this channel only accepts Integer values
```
Then, start as many concurrent workers as needed:
```java
x.go(worker, channel);
x.go(worker, channel);
x.go(worker, channel);
...
```
Finally, retrieve from the channel the values concurrently computed by the workers when those values are needed:
```java
for (Integer value : channel) {
	x.print(value);
}
```

#### MapReduce (beta)
Let's assume that we have a list of elements we want to process:
```java
list<String> elements = x.list("Map","aNd","ReDuce","arE","aWEsome");
```
The processing of each element takes a long time (10 seconds), so we want to parallelize the processing on our multicore machine. Let the processing be as follows: if the element starts with an "a", then put it in uppercase and join it with other uppercase elements using "~" as separator; if the element doesn't start with an "a", then put it to lowercase and join it with other lowercase words.

Let's define the Mapper and Reducer:
```java
import com.wantedtech.common.xpresso.experimental.concurrency.Mapper;
import com.wantedtech.common.xpresso.experimental.concurrency.Reducer;

static Mapper<String,String> mapper = new Mapper<String,String>() {
	public void map(String input) {
		x.Time.sleep(10); //the processing of each element takes a long time :-)
		if (x.String(input).startsWith("a")) {
			yield("upper", input.toUpperCase());				
		} else {
			yield("lower", input.toLowerCase());
		}
	}
};

static Reducer<String,String> reducer = new Reducer<String,String>() {
	public void reduce(tuple2<String,list<String>> input) {
		yield(input.key,x.String("~").join(input.value));
	}
};
```
Our mapper does the transformation of the string case as described above, and our reducer joins the resulting values with the "~".

Our MapReduce setup is now ready, so let's start crunching:
```java
x.timer.start();
x.print(x.<String,String,String>MapReduce(elements).map(mapper).reduce(reducer), x.timer.stop());

Console:
{upper:AND~AWESOME~ARE, lower:reduce~map}
10.013s
```
As you can see, the processing of all 5 elements took only about 10 seconds, while we have defined above that the processing of each single element takes 10 seconds.

#### JSON
Remember the *rank* dict:
```java
dict<Integer> rank = x.dict(x.tuple("Moscow",30),x.tuple("Saint-Petersburg",15),x.tuple("New York",20),x.tuple("London",10),x.tuple("Paris",5),x.tuple("Dubai",32));
```

Let's first dump it as a String:
```java
String rankAsString = x.Json(rank).toString();
x.print(rankAsString);

Console: {"New York":20,"London":10,"Saint-Petersburg":15,"Moscow":30,"Dubai":32,"Paris":5}
```
Now let's create a copy of the *rank* dict from its JSON string representation:
```java
dict<Integer> rankCopy = x.String(rankAsString).parseJson();
```
Compare the original *rank* dict to the copy:
```java
x.print(x.Object(rank).equals(rankCopy));

Console: true
```

#### CSV
Read from file:
```java
try (HappyFile f = x.open("filename.txt","r","utf-8")) {
	for (list<String> row : x.csv(f)) {
		//do stuff
	}
}
```
Or, simply:
```java
list<list<String>> data = x.list(x.csv("filename.txt","r","utf-8"));
```

Write to file:
```java
try (HappyFile f = x.open("filename.txt","w","utf-8")) {
	for (list<?> row : data){
		x.csv(f).writerow(row);
	}
}
```
Or, simply:
```java
try (HappyFile f = x.open("filename.txt","w","utf-8")) {
	f.write(x.csv(data).toString());
}
```

Write to a StringBuilder:
```java
StringBuilder builder = new StringBuilder();

for (list<?> row : data) {
	x.csv(builder).writerow(row);
}

String cs = c.toString();
```
Or, simply:
```java
String cs = x.csv(data).toString();
```

#### MySQL
```java
String host = "host:port";
String user = "user";
String password = "password";
String db = "db";

try (HappySQL sql = x.mysql(host, user, password, db)) {
	try (HappySQL sql2 = x.mysql(sql)){
		String query =	"SELECT ID FROM " +
						"tbl_Employees e " +
						"WHERE e.Name LIKE ?";
		for (tuple row : sql.execute(query, "John %")) {
			query =	"UPDATE tbl_Employees " +
					"SET Promoted = 1 " +
					"WHERE ID = ?";
			sql2.execute(query, row.get("ID"));
		}
	}
}
```

#### Extended String functions

Python:
```python
if "e" in "Hello World":
    #do stuff
```

xpresso:
```java
if(x.String("e").in("Hello World")) {
    //do stuff
}
```

Python:
```python
colorsPattern = "|".join(["black","green","red","white"]);

print colorsPattern

>>> black|green|red|white
```

xpresso:
```java
String colorsPattern = x.String("|").join(x.list("black","green","red","white"));

x.print(colorsPattern);

Console: black|green|red|white
```

Python:
```python
tokens = "Moscow;London;Paris".split(";")

print tokens

>>> ['Moscow', 'London', 'Paris']
```

xpresso:
```java
list<String> tokens = x.String("Moscow;London;Paris").split(";");

x.print(tokens);

Console: [Moscow, London, Paris]
```

Transliterate:
```java
String trans = x.String("Чичётка 北亰").translit();

x.print(trans);

Console: Čičëtka bei jing

x.print(trans.stripAccents());

Console: Cicetka bei jing
```

Convert unicode to ascii:
```java
String unidec = x.String("Чичётка 北亰").unidecode();

x.print(unidec);

Console: Chichiotka bei jing
```

Approximate string comparison:
```java
x.print(x.String("Hello World").similarity("Hello Wold!"))

Console: 91
```
The output is 100% compatible with [FuzzyWuzzy](https://github.com/seatgeek/fuzzywuzzy).

Approximate pattern matching:
```java
x.print(x.String("You are cooding in Java.").search("coding"));

Console: 8
```

Get similar strings:
```java
list<String> lookAlikes = x.String("apple").lookAlikes(x.list("ape", "apples", "peach", "puppy"),50);

x.print(lookAlikes);

Console: [ape, apples]
```

Tokenization:
```java
String text = "English is hard. It can be understood through tough thorough thought, though.";

for (Sentence s : x.String.EN.tokenize(text)) {
	for (Token t : s) {
		x.print(t);
	}
}

Console: English
is
hard
.
It
can
...
```

#### Part-of-speech tagging
With xpresso  you can easily POS tag any English text with the very fast and accurate (~97%) Stanford CoreNLP english-left3words model:

```java
import com.wantedtech.common.xpresso.sentence.Sentence;
import com.wantedtech.common.xpresso.sentence.PosTagger;
import com.wantedtech.common.xpresso.sentence.pos.en.stanford.MaxentPosTagger;

PosTagger posTagger = new MaxentPosTagger();
String text = "Some English text. Multiple sentences.";
for (Sentence sent : x.String.EN.tokenize(text)) {
    posTagger.tag(sent);
    x.print(sent.getAnnotations("pos"));
}

Console: [(Some, DT), (English, NNP), (text, NN), (., .)]
[(Multiple, JJ), (sentences, NNS), (., .)]
```


#### Slicing for list, String, and str

Python:
```python
trips = ["Dubai","New York","London","Paris","Moscow","London","Saint-Petersburg","New York"]

print trips[2:4]

>>> ['London', 'Paris']
```

xpresso:
```java
x.print(trips.slice(2,4));

Console: [London, Paris]
```

Python:
```python
print trips[:5]

>>> ['Dubai','New York','London','Paris','Moscow']
```

xpresso:
```java
x.print(trips.sliceTo(5));

Console: [Dubai, New York, London, Paris, Moscow]
```

Negative and non-unit steps are supported:

Python:
```python
print trips[::-1]

>>> ['New York', 'Saint-Petersburg', 'London', 'Moscow', 'Paris', 'London', 'New York', 'Dubai']
```

xpresso:
```java
x.print(trips.slice(-1));

Console: [New York, Saint-Petersburg, London, Moscow, Paris, London, New York, Dubai]
```

Python:
```python
print trips[::2]

>>> ['Dubai','London','Moscow','Saint-Petersburg']
```

xpresso:
```java
x.print(trips.slice(2));

Console: [Dubai, London, Moscow, Saint-Petersburg]
```

#### Slicer object
```java
Slicer LAST_THREE = x.sliceFrom(-3);

x.print(x.String("tic tac toe").slice(LAST_THREE));

Console: toe
```

#### Iterable regex search results

Python:
```python
for long_word_match in re.finditer("\b\w{10,}\b",text):
    print long_word_match.group(0)
```

xpresso:
```java
for (Match longWordMatch : x.Regex("\\b\\w{10,}\\b").findIter(text))
    x.print(longWordMatch.group(0));
```

Python:
```python
for long_word in re.findall("\b\w{10,}\b",text):
	print long_word
```

xpresso:
```java
for (String longWord : x.Regex("\\b\\w{10,}\\b").findAll(text))
	x.print(longWord);
```

#### Replace with a Function

Python:
```python
def toUpperCaseFun(value):
	return value.group(0).upper()

text = re.sub("\b\w{10,}\b",toUpperCaseFun,text)
```

xpresso:
```java
Function<Match,String> toUpperCaseFun = new Function<Match,String>(){
	public String apply(Match value) {
		return value.group(0).toUpperCase();
	}
}

text = x.Regex("\\b\\w{10,}\\b").sub(toUpperCaseFun,text);
```

#### Replace with a dict
```java
dict<String> replacer = x.dict(x.tuple("bad","good"),x.tuple("small","big"),x.tuple("hard","easy"));

text = x.Regex(replacer).sub(text);
```

#### Predefined regex patterns
```java
list<String> emails = x.list(x.Regex.EMAIL.findAll("Contact me at john.smith@company.com or john@smith.com."));
x.print(emails);

Console: [smith@company.com, john@smith.com]
```
Other patterns include x.Regex.LINK, x.Regex.EMAIL, x.Regex.IPV4, x.Regex IPV6, x.Regex.HEX_COLOR, x.Regex.ACRONYM, x.Regex.CREDIT_CARD, x.Regex.FLOAT, and so on, as well as a number of country-specific ones: x.Regex.US.DATE, x.Regex.US.TIME, x.Regex.US.PHONE, x.Regex.US.PRICE, x.Regex.US.STREET_ADDRESS, etc.

#### The Token type
```java
Token tok = x.Token("MySQL5");
x.print(tok.shape(), tok.isCamel(), tok.hasDigits(), tok.hasRussian());

Console: ULUUUD, true, true, false
```

```
tok = x.Token("Thinking");
x.print(tok.stem());

Console: Think
```

#### hashCode(), equals(...), and compareTo(...) builders
When defining a class:
```java
@Override
int hashCode() {
	return x.Object(this).hashCode();
}
```
In the above code, xpresso first finds the members of *this* (via reflections) and then dynamically computes the hash code for *this* based on the values of its members.

```java
@Override
boolean equals(Object obj) {
	return x.Object(this).equals(obj);
}
```
In the above code, xpresso first finds the members of the two objects (*this* and *obj*), and then compares the values of those members.

```java
@Override
public int compareTo(Object obj) {
	return x.Object(this).compareTo(obj, fieldName0, fieldName1, ...);
}
```
In the above code, xpresso first finds the members of the two objects (*this* and *obj*). It then compares the values of those members between the two objects if those members' names are listed among the input field names fieldName0, fieldName1, etc. The order of comparisons between the member's values is the same as the order of input field names.

#### Assertions

```java
x.assertTrue(condition);	// throws IllegalArgumentException

x.assertNotNull(parameter);	// throws NullPointerException

x.assertNotEmpty(iterable);	/* throws NullPointerException if iterable is null,
							   throws IllegalArgumentException if iterable is empty */
x.assertNotEmpty(string);

x.assertNotEmpty(array);
```

#### Built-in iterators

* cycle
```java
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

```java
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
```java
for (String word : x.repeat("cool"))
    x.print(word);

Console: cool
cool
cool
cool
...
```

```java
for (String word : x.repeat("cool",3))
    x.print(word);

Console: cool
cool
cool
```

* count
```java
for (Integer index : x.countTo(3))
    x.print(index);

Console: 0
1
2
```

```java
for (Integer index : x.countFrom(10))
    x.print(index);

Console: 10
11
12
13
...
```

```java
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

```java
for (Integer index : x.count(3,10,3))
    x.print(index);

Console: 3
6
9
```

x.count(min, max) and x.count(min, max, step) replace Python's range(min, max) and range(min, max, step).

#### Print anything
```java
x.print("Hello World", 1, true, x.list(1, 2, 3), null);

Console: Hello World 1 true [1, 2, 3] NullType
```

#### N-grams
```java
str phrase = "If you want something done right, you have to do it yourself.";

list<str> tokens = phrase.split();

list<list<str>> ngrams = tokens.ngrams(3);

x.print(ngrams);

Console: [[If, you, want], [you, want, something], [something, done, right]..., [do, it, yourself.]]
```

#### largestN and smallestN
```java
list<String> cities = x.list("Moscow","San Francisco","Saint-Petersbourg","Rome");

x.print(x.smallestN(cities,2));

Console: [Rome, Moscow]
```

```java
list<String> cities = x.list("Moscow","San Francisco","Saint-Petersbourg","Rome");

x.print(x.largestN(cities,2));

Console: [Saint-Petersbourg, San Francisco]
```

#### More
* Invert dict: ```dict.invert();```
* Flatten list: ```list.flatten();```
* Modify list using slicing: ```trips.setAt(3, 5).values(x.list(1, 2, 3, 4, 5));```
* Case insensitive regex shortcut: ```x.RegexNoCase("\\bmama\\b")```
* Replace each match by an empty string: ```x.Regex("[,.;]").clear(inputString)```
* For more see the [javadoc](http://wantedtechnologies.github.io/xpresso/) for the main class [x](http://wantedtechnologies.github.io/xpresso/com/wantedtech/common/xpresso/x.html).

#### Future
* HTTP client
* Find longest match (in the spirit of Python's [SequenceMatcher] (https://docs.python.org/3.4/library/difflib.html#difflib.SequenceMatcher)):

```java
tuple match = x.String("I like apples.").longestMatch("My girlfriend likes apples too.");

x.print(match);

Console: ("apples", 7, 20)
```

* Mistyping detection: ```x.String("Random").isMistypingOf("Randon","qwerty") --> true```
