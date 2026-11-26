# Task 2 — A2: Buffered I/O

## A2.1 Predict the cost

A byte-at-a-time loop over a 50 MiB file performs approximately 52,428,800 calls to `read()` at the Java API level. Depending on the stream implementation and operating-system buffering, this does not necessarily mean the same number of physical disk operations, but it creates substantial method-call and buffering overhead.

## A2.2 Buffered redesign

Wrap `FileInputStream` in `BufferedInputStream`, or read blocks into a byte array. Count newline bytes in each block. A typical 8 KiB buffer needs about 6,400 buffer fills for 50 MiB rather than one Java read call per byte. The exact count depends on buffer size and final partial block.

## A2.3 When buffering does not help

Buffering may add little benefit when the source already delivers large blocks, when the data is already in memory, or when processing time dominates I/O.

## Alternative: Files.readAllLines()

It is convenient for small text files but stores all decoded lines and strings in memory. Both approaches take O(file size) time. A streaming buffered scan uses O(buffer size) additional memory; readAllLines uses O(file size) memory and has substantial per-line object overhead. Prefer streaming for large logs.

## Result

No timing values are fabricated. Record measured results for 1 MB, 10 MB and 50 MB in the table below after running Task06.

| File size | Unbuffered time (ms) | Buffered time (ms) |
|---|---:|---:|
| 1 MB | — | — |
| 10 MB | — | — |
| 50 MB | — | — |

## Java program

Java implementation: `task2.java` compares byte-at-a-time and buffered line counting. With no argument it creates and measures 1 MB, 10 MB and 50 MB files; with a file path argument it measures that file. Run `javac task2.java` then `java task2`.
