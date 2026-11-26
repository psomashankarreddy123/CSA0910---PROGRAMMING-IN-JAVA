# CSA09 – Programming in Java | Assignment 3 (CO3)

**Student:** L Rishinathan  
**Register Number:** 1911260190  
**Java Version:** Java 17 or later

## Assignment overview

This repository contains seven Java programs covering exception handling, file handling and multithreading. Each task folder contains its Java source file and an individual README.

| Task | Java file | Main concept |
|---|---|---|
| 1 | `Task01/Task1.java` | Exception handling |
| 2 | `Task02/Task2.java` | Custom exceptions and exception chaining |
| 3 | `Task03/Task3.java` | File handling and try-with-resources |
| 4 | `Task04/Task4.java` | Thread life cycle |
| 5 | `Task05/Task5.java` | Synchronization |
| 6 | `Task06/Task6.java` | Producer-consumer, wait and notifyAll |
| 7 | `Task07/Task7.java` | ExecutorService, Callable and Future |

## Repository structure

```
Task01/
  Task1.java
  README.md
Task02/
  Task2.java
  README.md
Task03/
  Task3.java
  README.md
Task04/
  Task4.java
  README.md
Task05/
  Task5.java
  README.md
Task06/
  Task6.java
  README.md
Task07/
  Task7.java
  README.md
README.md
```

## Requirements

- JDK 17 or later
- Terminal or a Java IDE such as IntelliJ IDEA, Eclipse or VS Code

Check the installation:

```bash
java -version
javac -version
```

## Compile and run

Run each command from the repository root:

```bash
javac Task01/Task1.java && java -cp Task01 Task1
javac Task02/Task2.java && java -cp Task02 Task2
javac Task03/Task3.java && java -cp Task03 Task3
javac Task04/Task4.java && java -cp Task04 Task4
javac Task05/Task5.java && java -cp Task05 Task5
javac Task06/Task6.java && java -cp Task06 Task6
javac Task07/Task7.java && java -cp Task07 Task7
```

## Notes

- Task 3 creates `sales.txt` and `report.txt` in the current working directory.
- Task 7 creates sample score files in a `scores/` directory in the current working directory.
- Thread execution order may vary between runs.
- Add your own program output screenshots and test evidence to the corresponding task folders before final submission.
