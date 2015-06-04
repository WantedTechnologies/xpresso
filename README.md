# xpresso
The most pythonic way to code in Java.

xpresso allows a line-into-line rewrite of a Python code into Java. It implements in Java familiar pythonic methods (e.g., len, enumerate, range, split/join) and coding paradigms (everything is iterable, list comprehensions, lambda expressions, filtering iterables using predicates and modifying them using functions).

## Main features

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
print trips[2:4]
>>> ['London','Paris']
```

```
xprint(trips.slice(2,4));
Console: ["London","Paris"]
```

Python:
```
print trips[:5]
>>> ['Dubai','New York','London','Paris','Moscow']
```

```
x.print(trips.sliceTo(4));
Console: ["Dubai","New York","London","Paris","Moscow"]
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
Console: ["New York", "Saint-Petersburg", "London", "Moscow", "Paris", "London", "New York", "Dubai"]
```

Python:
```
print trips[::2]
>>> ['Dubai','London','Moscow','Saint-Petersburg']
```

xpresso:
```
x.print(trips.slice(2));
Console: ["Dubai","London","Moscow","Saint-Petersburg"]
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
