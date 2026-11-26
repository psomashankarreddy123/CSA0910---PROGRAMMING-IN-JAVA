# Programming in Java — Assignment 5 (CO5)

**Student name:** L Rishinathan  
**Register number:** 1911260190  
**Java version:** Record the output of `java -version`  
**Repository:** CSA0909-1911260190-ASSIGNMENT5  
**Topics:** Java I/O, files, serialization, JDBC, transactions and database metadata.

## Java source code for every task

| Assignment task | Java file | Main coverage |
|---|---|---|
| A1 | [Task01/task1.java](Task01/task1.java) | Delimited text file, binary DataOutputStream, add/find/list |
| A2 | [Task02/task2.java](Task02/task2.java) | Unbuffered and buffered line counting, file-size timing |
| A3 | [Task03/task3.java](Task03/task3.java) | Generic DAO interface and JDBC StudentDao |
| A4 | [Task04/task4.java](Task04/task4.java) | Object serialization and transient fields |
| A5 | [Task05/task5.java](Task05/task5.java) | JDBC wallet transaction and PreparedStatement login |
| B1 | [Task06/task6.java](Task06/task6.java) | PassStore add/find/list, damaged records, line count and search growth |
| B2 | [Task07/task7.java](Task07/task7.java) | Unsafe SQL login, PreparedStatement and damaged input tests |
| B3 | [Task08/task8.java](Task08/task8.java) | Statement vs PreparedStatement vs batch, transaction rollback |
| B4 | [Task09/task9.java](Task09/task9.java) | CSV migration, validation report and DatabaseMetaData |

Each task folder also contains a README with task-specific notes. The PDF requires Part A pseudocode/design answers in addition to code; Java programs are supporting demonstrations and do not replace those written answers.

## Requirements

- JDK 17 or later
- MySQL Server 8.x
- MySQL Connector/J for JDBC tasks

Set database credentials as environment variables:
- `DB_URL` (example: `jdbc:mysql://localhost:3306/assignment5`)
- `DB_USER`
- `DB_PASSWORD`

Do not commit real passwords.

## Compile and run

For file/serialization programs, open a terminal in the relevant task folder:

```powershell
javac taskN.java
java taskN
```

For JDBC programs, add the MySQL Connector/J JAR to the classpath:

```powershell
javac -cp ".;mysql-connector-j-<version>.jar" taskN.java
java -cp ".;mysql-connector-j-<version>.jar" taskN
```

Run `Task08/schema.sql` in MySQL Workbench or the MySQL command line before the database tasks if the table is not already present. Task03 creates its student table; Task05 creates its wallet and users tables; Task07 creates its users table; Task08 creates its bus_pass table. Task09 imports the CSV into bus_pass.

