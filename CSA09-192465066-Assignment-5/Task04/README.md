# Task 4 — A4: Serialization Design

## A4.1 What to persist

Persist stable hostel domain state: room identifiers and resident names/register numbers, plus other fields genuinely required to restore the application. Mark the live JDBC `Connection` transient because it is an external, runtime resource that cannot meaningfully be restored from a serialized snapshot. Passwords should not be serialized in plaintext; preferably store a password hash in a protected credential store, not in the general object graph.

When a `Hostel` is serialized, Java follows references to serializable reachable objects: its rooms and each room's residents are included, subject to `transient` and custom serialization rules. Shared references are represented consistently within the stream.

## A4.2 Version changes

Declare an explicit `serialVersionUID`. If the class changes compatibly and the UID remains the same, an older stream can often be read and a newly added field receives its default value (for a reference, `null`; for a numeric primitive, zero). An incompatible change or mismatched UID can cause `InvalidClassException`. Validate and migrate default values after reading.

## Alternative: CSV or JSON

Text formats are inspectable and easier to migrate across languages. Serialization is often compact and convenient for Java object graphs but is Java-specific and more brittle across class changes. Saving/loading n residents is generally O(n) for either representation; file size and constants depend on encoding and metadata. CSV/JSON require explicit escaping and schema/version handling but can add optional fields more gracefully.

## Result

Design task. Do not store real passwords in test snapshots.

## Java program

Java implementation: `task4.java` serializes a Hostel → Room → Resident object graph. Password and live JDBC Connection are transient; the demonstration shows those fields are not restored after deserialization. Run `javac task4.java` then `java task4`.
