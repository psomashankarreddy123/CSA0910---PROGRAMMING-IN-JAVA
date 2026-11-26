# Task 9 — B4: Migration and Production Decision

## Run

Create the `bus_pass` table using Task08/schema.sql. Configure `DB_URL`, `DB_USER` and `DB_PASSWORD`. Place a comma-separated source file at the path supplied as the first command-line argument (default: `passes.csv`).

```bash
javac -cp ".;mysql-connector-j-<version>.jar" task9.java
java -cp ".;mysql-connector-j-<version>.jar" task9 passes.csv
```

The migration validates each record, reports invalid line numbers, inserts valid rows in batches within a transaction, and prints database/driver metadata and table names.

## Production decision

For 12 campuses, concurrent counters, shared search and transaction safety, use a centralized relational database with indexes, transactions, backups and appropriate access control. Text files are useful for interchange/import/export but are weak as the primary concurrent store. Java serialization is Java-specific and does not provide multi-user transaction coordination. Campus-local serialized files would require synchronization, duplicate resolution and cross-campus aggregation; searching all campuses becomes a fan-out scan and consistency is difficult.

## Evidence

Add your migration output, invalid-row report, metadata output and measured performance here. Measurements must come from your environment.
