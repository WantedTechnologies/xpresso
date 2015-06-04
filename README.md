# xpresso
The most pythonic way to code in Java.

xpresso allows a line-into-line rewrite of a Python code into Java. It implements, in Java, familiar pythonic methods (e.g., len, enumerate, split/join, slicing) and coding paradigms (everything is iterable, list comprehensions, lambda expressions, filtering iterables using predicates and modifying them using functions).

## Main features

#### Types similar to pythonic ones

* str
* set
* dict
* list
* tuple
* DefaultDict
* OrderedDict
* Bag

#### Slicable and iterable **str** type

```
str city = x.str("New York");

x.print(city.sliceTo(3));

Console: New
```

```
for(String character : city)
    x.print(character);

Console:
N
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
 f = open("name.txt","r","utf-8");
 ```

xpresso:

 ```
 HappyFile f = x.open("name.txt","r","utf-8");
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

xpresso

```
list<String> trips = x.list("Dubai","New York","London","Paris","Moscow","London","Saint-Petersburg","New York");

set<String> russianCities = x.set("Moscow","Saint-Petersburg")

dict<Integer> rank = x.dict(x.tuple("Moscow",30),x.tuple("Saint-Petersburg",15),x.tuple("New York",20),x.tuple("London",10),x.tuple("Paris",5),x.tuple("Dubai",5))
```

#### List comprehensions

Python:

```
foreign_trips_lower = [element.lower() for element in trips if element not in russian_cities];
```

xpresso:

```
list<String> foreignTripsLower = x.list(x.element().transformWith(x.lower).forElementIn(trips).ifElementNot(x.in(russianCities)));
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

```
xprint(trips.slice(2,4));

Console: [London, Paris]
```

Python:
```
print trips[:5]
>>> ['Dubai','New York','London','Paris','Moscow']
```

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

#### Iterable Regex search results

Python:
```
for long_word_match re.finditer("\b\w{10,}\b",text):
    print long_word_match.group(0)
```

xpresso:
```
for (Match longWordMatch re.searchIter("\\b\\w{10,}\\b",text))
    x.print(longWordMatch)
```

Python:
```
for long_word re.findall("\b\w{10,}\b",text):
    print long_word
```

xpresso:
```
for (String longWord re.findAll("\\b\\w{10,}\\b",text))
    x.print(longWord)
```

#### Replace with a function

Python:
```
text = re.sub("\b\w{10,}\b",toUpperCaseFun,text)
```

xpresso:
```
text = Regex("\\b\\w{10,}\\b").sub(toUpperCaseFun,text);
```

#### Replace with a dict

Python:
```
not available
```

xpresso:
```
dict<String> replacer = x.dict(x.tuple("bad","good"),x.tuple("small","big"),x.tuple("hard","easy"));

text = Regex(replacer).sub(text);
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
 
#### largest and smallest

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
 
#### n-grams

```
str phrase = "If you want something done right, you have to do it yourself."
list<str> tokens = phrase.split();
list<list<str>> ngrams = tokens.ngrams(3);
x.print(ngrams);

Console: [[If, you, want], [you, want, something], [something, done, right]..., [do, it, yourself.]]
```
 
#### Built-in iterators

* cycle

```
for (String letter : x.cycle(x.str("ABC"))){
    x.print(letter);
}

Console:
A
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
for (String letter : x.cycle(x.list("hello","world"),3)){
    x.print(letter);
}

Console:
hello
world
hello
world
hello
world
```
* repeat

```
for (String word : x.repeat("cool")){
    x.print(word);
}

Console:
cool
cool
cool
cool
...
```

```
for (String word : x.repeat("cool"),3){
    x.print(word);
}

Console:
cool
cool
cool
```
* count
```
for (Integer index : x.countTo(3)){
    x.print(index);
}

Console:
0
1
2
```

```
for (Integer index : x.countFrom(10)){
    x.print(index);
}

Console:
10
11
12
13
...
```

```
for (Integer index : x.count(3,10)){
    x.print(index);
}

Console:
3
4
5
6
7
8
9
```

```
for (Integer index : x.count(3,10,3)){
    x.print(index);
}

Console:
3
6
9
```

#### More:
* invert dict: ```dict.inverted();```
* flatten list: ```list.flattened();```
* modify list using slicing: ```trips.setAt(3,5).values(x.listOf(1,2,3,4,5));```
* and more (see the javadoc comments in ![Mockup for feature A](https://github.com/WantedTechnologies/xpresso/blob/master/src/main/java/com/wantedtech/common/xpresso/x.java)
