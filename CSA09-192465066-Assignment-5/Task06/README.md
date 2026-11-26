# Task 6 — B1: File Store and Buffered Line Counter

## Files

- `task6.java`: completed PassStore implementation based on the assignment's supplied code, plus buffered/unbuffered line-count timing helpers.

## Run

```bash
javac task6.java
java task6
```

Expected PassStore output includes the P1003 record and `null` for P1002. The damaged P1002 line has only two fields, so find() skips it and continues.

## Validation checklist

| Test | Expected behaviour |
|---|---|
| Existing pass ID | Returns all five fields |
| Missing pass ID | Returns null |
| Damaged line | Skips line and continues |
| Empty line | Skips line |
| Non-numeric amount | Rejects/skips malformed record |

Run your own 1 MB, 10 MB and 50 MB measurements and append the actual values below. Results vary by hardware, filesystem and JVM warm-up.

| Size | Unbuffered (ms) | Buffered (ms) |
|---|---:|---:|
| 1 MB | — | — |
| 10 MB | — | — |
| 50 MB | — | — |

For pass lookup growth, generate 1,000 to 100,000 records and record the measured scan times.
