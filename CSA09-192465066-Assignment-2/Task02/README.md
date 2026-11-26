# Q2 — ArrayList, LinkedList and ArrayDeque

## Aim
Compare list structures for a music playlist and an Up Next queue.

## Java file
task2.java

## Simple explanation
- ArrayList stores elements in an array. get(index) is fast; front and middle insertions shift elements.
- LinkedList stores linked nodes. End operations are fast, but indexed access takes time.
- ArrayDeque is useful when items are added and removed at both ends. It has no indexed access.

## Methods used
add(), addFirst(), addLast(), remove(), removeFirst(), removeLast(), get(), enhanced for loop, System.nanoTime().

## Test cases
1,000, 10,000 and 50,000 elements. The program measures end insertion, front and middle operations, indexed access, iteration and ArrayDeque operations at both ends with removal from the front.

## Compile and run
~~~bash
javac task2.java
java ListStructureBenchmark
~~~

## Result and decision
ArrayList suits playlists with frequent index access. ArrayDeque suits the separate Up Next queue.

## Big-O summary
| Operation | ArrayList | LinkedList | ArrayDeque |
|---|---:|---:|---:|
| Add at end | O(1) amortized | O(1) | O(1) amortized |
| Add/remove at front | O(n) | O(1) | O(1) amortized |
| Middle insert/remove | O(n) | O(n) to find position | Not designed for it |
| Get by index | O(1) | O(n) | Not supported |
| Iterate | O(n) | O(n) | O(n) |