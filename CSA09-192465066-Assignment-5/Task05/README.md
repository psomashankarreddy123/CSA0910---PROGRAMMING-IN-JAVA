# Task 5 — A5: Correctness and Safety

## A5.1 JDBC transaction pseudocode

```text
SET autoCommit = false
TRY
  UPDATE hostel_wallet SET balance = balance - 500 WHERE wallet_id = ...
  VERIFY exactly one row updated and sufficient balance
  UPDATE canteen_wallet SET balance = balance + 500 WHERE wallet_id = ...
  VERIFY exactly one row updated
  COMMIT
CATCH error
  ROLLBACK
  REPORT failure
FINALLY
  RESTORE previous autoCommit value
```

Both updates must use the same connection and transaction. Add a database constraint preventing negative balances and lock/check the source wallet safely to prevent concurrent overspending.

## A5.2 SQL injection

Concatenating untrusted text into SQL lets quote characters and SQL operators change the query structure. For example, an input such as `' OR '1'='1' -- ` can make a condition true in a vulnerable query.

Safe design:

```java
String sql = "SELECT 1 FROM users WHERE id = ? AND pwd_hash = ?";
try (PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setString(1, id);
    ps.setString(2, passwordHash);
    try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
    }
}
```

Use a password hashing scheme such as Argon2id, bcrypt or PBKDF2 in a real system; do not compare plaintext passwords.

## Alternative: escaping or regex validation

Escaping and allow-list validation are useful additional controls but are not substitutes for parameter binding. They are easy to get wrong for encodings, SQL dialects and edge cases. Validation is usually O(length of input); PreparedStatement also has parameter-binding overhead, but keeps values separate from SQL syntax and allows statement reuse.

## Result

Design task. Execute the demonstration in Task07 against a disposable local database only.

## Java program

Java implementation: `task5.java` transfers funds using one JDBC transaction with commit/rollback and demonstrates a parameterized login query. Configure the MySQL connection before running.
