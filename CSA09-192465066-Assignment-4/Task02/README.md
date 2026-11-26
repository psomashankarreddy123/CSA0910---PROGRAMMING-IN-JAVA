# Task 2 – Hospital Outpatient Appointment Desk

**Source:** Assignment 4, Task 2 (pages 3–4).

## Files
- `task2.java` – Swing appointment form with dependent doctor selection, validation, confirmation and guarded window closing.

## Event design
| Source | Event | Listener | Action |
|---|---|---|---|
| Department combo | Item/action change | ActionListener | Refresh doctor list |
| Book button | ActionEvent | ActionListener | Validate and book |
| Window | WindowEvent | WindowAdapter | Ask before closing during booking |

The doctor lists are loaded into a `Map<String, List<String>>` once, then used when the department changes.

## Compile and run
Requires JDK 8 or later.

```bash
javac task2.java
java task2
```

## Result
Choose a department to refresh its doctors, enter a patient ID, select a slot and book. An empty patient ID is rejected.

## Evidence
Add screenshots of the form, changed doctor list, validation and booking confirmation after running.