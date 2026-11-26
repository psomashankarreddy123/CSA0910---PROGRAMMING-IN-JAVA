# Task 3 – Campus Library Search Screen

**Source:** Assignment 4, Task 3 (page 4).

## Files
- `task3.java` – Swing layout with a fixed search row, expandable results region and bottom controls.

## Layout decisions
- The frame uses `BorderLayout`.
- The search row and bottom action row use `FlowLayout`.
- The results panel uses `BorderLayout` and a `JScrollPane`; it receives the remaining frame space and grows on resize.
- Nested panels keep controls grouped and maintain their preferred sizes.

## Compile and run
Requires JDK 8 or later.

```bash
javac task3.java
java task3
```

## Result
The search button adds demonstration results. Resize the window to check that the central results area expands while the search and action rows remain compact.

## Evidence
Add screenshots at three window sizes and explain the observed resizing behaviour. The result data is sample data, not a live library database.