# Task 8 — B3: Statement vs PreparedStatement vs Batch

## SQL setup

Run `schema.sql` in the assignment5 database. Configure the database environment variables and add MySQL Connector/J to the classpath.

## Run

```bash
javac -cp ".;mysql-connector-j-<version>.jar" task8.java
java -cp ".;mysql-connector-j-<version>.jar" task8
```

The program generates sample passes, performs batch inserts in one transaction and reports elapsed time and inserted row count. Extend the experiment to compare Statement and single-row PreparedStatement at 1,000, 10,000 and 100,000 records, recording actual measurements. Do not treat one run as a reliable benchmark; repeat and compare medians.

## Transaction test

The loader rolls back all rows when an invalid amount causes a batch failure. Verify the table count is unchanged after the forced failure.
