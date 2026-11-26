# Q5 — Generics, Comparator and Leaderboard

## Aim
Rank sports teams by points, goal difference and team name.

## Java file
task5.java

## Simple explanation
The Leaderboard<T> class stores different object types using a generic type T. A Comparator tells Java how to compare two teams: points high-to-low, goal difference high-to-low, and name A-to-Z.

## Methods and concepts
Generics (<T>), Comparator, compare(), Collections.sort(), ArrayList, compareTo(), and the wildcard Comparator<? super T>.

## Compile and run
~~~bash
javac task5.java
java LeaderboardDemo
~~~

## Expected output
~~~text
[Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]
~~~

Bisons is above Panthers because points and goal difference are tied, so alphabetical order decides.

## Complexity and design decision
Sorting n teams takes O(n log n). The copied list uses O(n) extra space. Comparator is reusable when different ranking orders are needed. Comparable<Team> would define one natural ordering inside Team; a separate Comparator is better when the same team data needs multiple ranking rules. A parameter such as List<? extends T> is useful when a method should accept a list containing T objects or objects of a subclass of T.