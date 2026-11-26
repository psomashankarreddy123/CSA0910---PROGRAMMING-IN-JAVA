# Task 7 — B2: Adversarial Input and PreparedStatement

## Run

Create a disposable MySQL database named `assignment5`, create the `users` table, configure `DB_URL`, `DB_USER` and `DB_PASSWORD`, then compile/run with MySQL Connector/J.

The program creates a demonstration account with a hashed password, compares an intentionally unsafe concatenated query with a parameterized query, and prints the results. Use only the local test database.

## Interpretation

The crafted input attempts to alter the SQL expression. The concatenated query can treat the injected text as SQL syntax, while PreparedStatement binds it as a literal value and rejects the invalid login. A damaged pass file should be tested separately with malformed field counts, empty lines and invalid numeric values; the Task06 parser skips malformed records rather than trusting them.

## Evidence

Add screenshots of the console output and the test database schema after running locally.
