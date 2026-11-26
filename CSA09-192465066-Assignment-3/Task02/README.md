# Task 2 – Custom Exceptions and Exception Chaining

## Objective

Demonstrates user-defined checked exceptions, throw, throws and exception chaining.

## Program

`Task2.java`

## Description

The storage operation simulates a disk quota error and wraps the IOException inside a custom SubmissionStorageException, preserving the original cause.

## Requirements

- JDK 17 or later
- Java compiler (`javac`)

## Compile and run

Run these commands from the repository root:

```bash
javac Task2.java
java Task2
```

## Expected behavior

The first sample file is saved; the oversized second file triggers the custom exception and displays its cause.

## Result

The program demonstrates the concepts listed above. Run it and capture the actual console output for your assignment record.
