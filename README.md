# xpresso
The most pythonic way to code in Java.

**x**presso allows a line-into-line rewrite of a Python code into Java. It implements in Java familiar pythonic methods (e.g., len, enumerate, range, split/join) and coding paradigms (everything is iterable, list comprehensions, lambda expressions, filtering iterables using predicates and modifying them using functions).

## Main features

#### One-line file open
 
Python:

 ```
 f = open("name.txt","r","utf-8");
 ```

**x**presso:

 ```
 HappyFile f = x.open("name.txt","r","utf-8");
 ```

Works for write/read/append in both text and binary mode.

#### Iterable file

Python:

 ```
 for line in f: print line
 ```

**x**presso:

 ```
 for (String line : f) x.print(line);
 ```

#### Neat standard object creation

Python:

```
trips = ["New York","London","Paris","Moscow","London","Saint-Petersburg","New York"]

russian_cities = set(["Moscow","Saint-Petersburg"])

rank = dict(("Moscow":"30"),("Saint-Petersburg":15),("New York":20),("London":10),("Paris":5)])

```

**x**presso

```
list<String> trips = x.list("New York","London","Paris","Moscow","London","Saint-Petersburg","New York");

set<String> russianCities = x.set("Moscow","Saint-Petersburg")

dict<Integer> rank = x.dict(x.tuple("Moscow":"30"),x.tuple("Saint-Petersburg":15),x.tuple("New York":20),x.tuple("London":10),x.tuple("Paris":5)])
```

#### List comprehensions

Python:

```
foreign_trips_lower = [element.lower() for element in trips if element not in russian_cities];
```

**x**presso:

```
list<String> foreignTripsLower = x.list(x.element().transformWith(x.lower).forElementIn(trips).ifElementNot(x.in(russianCities)));
```
 
#### Pythonic iterable dict

Python:

```
for city in rank: print rank[city]
```

**x**presso:

```
for (String city : rank) x.print(rank.get(city));
```

#### Pythonic tuples

Python:

```
my_car = ("Honda", "red", 2010, True)
```

**x**presso:

```
tuple myCar = x.tuple("Honda", "red", 2010, true);
```

#### Slicing for list, String, and str
 
Negative steps are supported.
 
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
