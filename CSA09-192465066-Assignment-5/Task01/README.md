# Task 1 — A1: Design a File Format

## A1.1 Record layouts

**Delimited text:** `passId|regNo|name|routeNo|boardingStop|validityDate|amountPaid`. Use UTF-8. Escape backslash as `\\` and the delimiter as `\|` in text fields; unescape while reading. Validate the exact field count and numeric/date fields.

**Binary:** write fields in the same order with `DataOutputStream`: each string as UTF-8 bytes preceded by a 32-bit byte length, route as `writeInt`, validity date as epoch-day `writeLong`, amount as `writeDouble`. Length-prefix strings avoid ambiguity and allow variable-size names. A fixed schema/version header should precede records.

## A1.2 Pseudocode

```text
ADD_PASS(pass)
  OPEN BufferedWriter(FileWriter(file, append=true))
  VALIDATE pass fields
  WRITE escaped fields joined by delimiter
  WRITE newline
  CLOSE writer

FIND_PASS(passId)
  IF file does not exist THEN RETURN NOT_FOUND
  OPEN BufferedReader(FileReader(file))
  FOR each line
    SPLIT line using delimiter, preserving empty fields
    IF field count or field validation is invalid THEN CONTINUE
    IF field[0] equals passId THEN RETURN parsed record
  RETURN NOT_FOUND

LIST_ROUTE(route)
  OPEN BufferedReader(FileReader(file))
  FOR each line
    PARSE and validate line
    IF valid and route field equals route THEN DISPLAY record
  CLOSE reader
```

Use buffered character streams for delimited UTF-8 text because the data is textual and line-oriented. For binary data use buffered byte streams around FileInputStream/FileOutputStream.

## A1.3 Damaged data and recovery

- Damaged line: validate field count, required fields, numeric values and date format; log the line number and skip it rather than terminating the scan.
- Missing file: distinguish a missing file from an empty file; create it for an add operation, but report no records for a read operation.
- Incomplete write: write a complete replacement to a temporary file, flush/close it, then atomically move it over the original where supported. Keep a backup or checksum for recovery.

## Alternative: RandomAccessFile

A fixed-size record permits direct seek to record index `i` at `i × recordSize`, which is O(1) once the index is known. Finding an arbitrary pass ID still requires O(n) scanning unless a separate ID-to-offset index is maintained. Fixed-width name/stop fields waste padding space; text records are variable length but are easier to inspect and evolve.

## Result

Design-only task. Add a diagram or execution evidence if required by the faculty.

## Java program

Java implementation: `task1.java` demonstrates the seven-field pass record, escaped pipe-delimited text storage, DataOutputStream binary storage, add/find/list operations and a binary read-back. Run `javac task1.java` then `java task1`.
