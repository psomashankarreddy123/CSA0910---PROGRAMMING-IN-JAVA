# Task 6 – Campus Event Registration Desktop Application

**Source:** Assignment 4, Task 6 (pages 8–9).

## Files
- `task6.java` – Swing UI, service layer and JDBC access.
- `schema.sql` – MySQL database and table definition.

## Requirements
- JDK 8 or later.
- MySQL Server.
- MySQL Connector/J on the compile and runtime classpath.

Set environment variables before running:
- `DB_URL` (default: `jdbc:mysql://localhost:3306/campus_events`)
- `DB_USER` (default: `root`)
- `DB_PASSWORD` (default: empty)

## Setup, compile and run

```sql
SOURCE schema.sql;
```

```bash
javac -cp .;mysql-connector-j.jar task6.java
java -cp .;mysql-connector-j.jar task6
```

On macOS/Linux, replace the classpath separator `; ` with `:` (without the space).

## Design
- The Swing form and table form the presentation layer.
- `RegistrationService` validates inputs and performs JDBC operations.
- `PreparedStatement` prevents SQL injection.
- `SwingWorker` keeps registration and loading off the event-dispatch thread.
- The database unique key on student ID and event prevents duplicate registrations.
- The table loads a page of up to 100 records; extend the UI with page controls to request later offsets.

## Result
Register a student, filter the table and export the currently displayed rows as CSV. Duplicate student/event pairs are rejected by the database constraint.

## Evidence
Add screenshots for successful registration, invalid input, duplicate registration, filtered results and CSV export. Verify the application against your local MySQL instance before submission.