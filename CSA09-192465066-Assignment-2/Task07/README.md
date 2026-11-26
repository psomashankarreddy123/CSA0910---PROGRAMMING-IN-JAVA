# Q7 — Diagnose, Redesign and Validate

## Aim
Use Q1 evidence to find a performance limitation and test a redesigned solution.

## Java file
task7.java

## Diagnosis
Repeated + joins create new String objects and can copy the existing text again.

## Redesign
Create one StringBuilder and use append() for each field. Convert the result to String once.

## Prediction
As the number of fields increases, StringBuilder should usually need less time and create fewer intermediate text objects. Small tests may show little difference due to timing overhead and JVM warm-up.

## Compile and run
~~~bash
javac task7.java
java RedesignValidation
~~~

## Validation
The program tests 10, 50, 100 and 500 fields and averages 1,000 ticket builds per method. Repeat on your own computer under the same conditions.

## Trade-off
StringBuilder uses a mutable buffer but avoids repeatedly copying the growing ticket. String concatenation is still acceptable for a few simple joins.