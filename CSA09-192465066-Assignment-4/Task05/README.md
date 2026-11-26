# Task 5 – Same Screen, Different Event-Handling Styles

**Source:** Assignment 4, Task 5 (pages 6–7).

## Files
- `task5.java` – completed converter based on the PDF's lambda-version starter; also demonstrates all four required listener styles.
- `given_task5.txt` – original TODO starter code copied from the PDF for reference.

## Four styles
1. Frame itself implements `ActionListener`.
2. Named inner class.
3. Anonymous inner class.
4. Lambda expression.

Each selected listener is shared by the Convert button and Enter key in the amount field. A `WindowAdapter` handles window closing. The conversion uses the assignment's fixed rates: USD = 0.012 and EUR = 0.011 per INR. The result is displayed to two decimal places in the label and a dialog.

## Compile and run
Requires JDK 8 or later.

```bash
javac task5.java
java task5 1
java task5 2
java task5 3
java task5 4
```

The default is style 4 (lambda).

## Result
Enter a valid non-negative amount, choose USD or EUR and convert. Invalid text displays **Invalid amount**. The rates are the fixed example values printed in the assignment, not live exchange rates.

## Evidence
Add screenshots of a valid conversion and an invalid amount. Record how much code changes when the Enter-key trigger and dialog output are added to each listener style.