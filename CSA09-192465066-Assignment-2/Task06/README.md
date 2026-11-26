# Q6 — Queue, PriorityQueue and Emergency Triage

## Aim
Treat the most critical patient first. For equal severity, treat the earliest arrival first.

## Java file
- task6.java — uses a priority queue and arrival-order tie-break.

## Simple explanation
A normal FIFO queue only follows arrival order. PriorityQueue uses a Comparator to place the smallest severity number first. An arrival counter keeps equal-severity patients in arrival order.

## Methods and concepts
PriorityQueue, Comparator, add(), poll(), StringBuilder.append(), and toString().

## Compile and run
~~~bash
javac task6.java
java task6
~~~

## Expected output
~~~text
Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5)
~~~

## Complexity and fairness
Adding and removing takes O(log n); checking the next patient takes O(1). The queue uses O(n) space. A minor patient may wait indefinitely if critical cases keep arriving. Priority ageing can raise priority after a waiting-time limit. The example explains this rule but does not implement time-based ageing. A real hospital system needs clinical review, persistence and concurrency controls.
