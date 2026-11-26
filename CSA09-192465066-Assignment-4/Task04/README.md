# Task 4 – Event-Handling Forensics

**Source:** Assignment 4, Task 4 (pages 5–6).

## Files
- `task4.java` – completed AWT drawing pad based on the PDF's TODO requirements.
- `given_task4.txt` – original faulty example from the PDF, preserved for defect analysis.

## Defects repaired
- The listener method is correctly named `mouseClicked`; Java is case-sensitive.
- `@Override` makes the compiler check that the method really overrides the listener method.
- Each click is stored as a `Point`, the label is updated and `repaint()` is requested.
- `paint()` redraws all stored points, so they survive minimising and restoring the window.

## Compile and run
Requires JDK 8 or later.

```bash
javac task4.java
java task4
```

## Result
Click inside the drawing window. The click count increases and dots remain after minimising and restoring.

## Evidence
Add screenshots before and after minimising/restoring. For the assignment's compiler experiment, remove `@Override`, change `mouseClicked` to `mouseclicked`, compile, and record the compiler output.