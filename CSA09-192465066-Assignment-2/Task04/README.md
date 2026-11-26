# Q4 — Choosing a Map for Pharmacy Inventory

## Aim
Store medicine stock by code and produce code-sorted and insertion-order reports.

## Java file
task4.java

## Simple explanation
- HashMap provides quick key lookup but no promised iteration order.
- TreeMap keeps keys sorted.
- LinkedHashMap remembers insertion order.

## Methods used
put(), get(), keySet(), enhanced for loop.

## Test cases
The program tests 100, 1,000 and 10,000 medicines and performs 100,000 lookups for each map. It also measures report iteration.

## Compile and run
~~~bash
javac task4.java
java PharmacyInventory
~~~

## Result and decision
Use TreeMap for code-sorted reports and LinkedHashMap for insertion-order reports. Hashtable is a legacy synchronized map and is not the default choice for new code.

## Big-O summary
| Operation | HashMap | TreeMap | LinkedHashMap |
|---|---:|---:|---:|
| put/get | O(1) average | O(log n) | O(1) average |
| Iteration | O(n) | O(n), sorted | O(n), insertion order |
| Space | O(n) | O(n) | O(n) |

A sorted array with binary search gives O(log n) lookup but O(n) insertion.