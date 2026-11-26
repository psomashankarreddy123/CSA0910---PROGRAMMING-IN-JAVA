# Q3 — HashSet and the equals/hashCode Contract

## Aim
Prevent the same student from being counted more than once at different event gates.

## Java file
- task3.java — uses HashSet to store unique students and TreeSet to display students in registration-number order.

## Simple explanation
HashSet uses hashCode() to find a bucket and equals() to check whether an equal item is present. Equal objects must return the same hash code. The Student class uses the registration number in both methods.

## Methods used
equals(), hashCode(), toString(), add(), size().

## Compile and run
~~~bash
javac task3.java
java task3
~~~

## Expected output
~~~text
Unique students: 2
TreeSet students: [192311001 Arun, 192311002 Divya]
~~~

## Complexity
HashSet has expected O(1) add and lookup with well-distributed hashes. TreeSet with a registration-number comparator has O(log n) operations and sorted order. Both use O(n) space.
