# Task 7 – Concurrent Result Processing

## Objective

Demonstrates ExecutorService, Callable, Future and exception handling in concurrent tasks.

## Program

`Task7.java`

## Description

Uses a fixed thread pool to process student mark files. It creates sample score files and also submits requests for two missing files to demonstrate failure handling.

## Requirements

- JDK 17 or later
- Java compiler (`javac`)

## Compile and run

Run these commands from the repository root:

```bash
javac Task07/Task7.java
java -cp Task07 Task7
```

## Expected behavior

The program prints PASS/FAIL results and a summary of successful and failed file-processing tasks. The exact output depends on the score files in the `scores` folder.

## Result

The program demonstrates concurrent file processing using a thread pool and handles failed tasks without stopping the remaining work. Capture the actual console output for your assignment record.
