# Task 3 — A3: Data-Access Design Pattern

## A3.1 Repeated pattern

The three classes repeat: obtain a connection, prepare SQL, bind parameters, execute, map ResultSet rows to objects, handle exceptions and close resources. Table names, columns, key types, SQL statements and row-to-object mapping differ.

## A3.2 Generic DAO

```java
interface Dao<T, K> {
    void insert(T value) throws SQLException;
    Optional<T> findById(K id) throws SQLException;
    List<T> findAll() throws SQLException;
    void update(T value) throws SQLException;
    void delete(K id) throws SQLException;
}
```

`StudentDao implements Dao<Student, Integer>` and owns the SQL and mapping for the student table. A service calls the interface rather than embedding SQL in a GUI.

## A3.3 Object-table mapping

| Student field | SQL column | Key |
|---|---|---|
| id | student_id | Primary key |
| registerNumber | register_no | Unique |
| name | student_name | — |
| email | email | — |

## Alternative: SQL in service/GUI

A renamed column must be corrected in every SQL string that refers to it. With a DAO, the change is localized to the data-access layer. The asymptotic database-operation time and returned-data space are normally the same; DAO mainly improves maintainability, testability and consistency rather than changing query complexity.

## Result

Design task. Add a class diagram if required.

## Java program

Java implementation: `task3.java` defines `Dao<T,K>` and a JDBC-backed `StudentDao` with insert, findById, findAll, update and delete. Configure MySQL Connector/J and DB_URL, DB_USER and DB_PASSWORD before running.
