# CSA09 Assignment 1 - Campus Smart-Parking System

- **Name:** P SOMA SHANKAR REDDY
- **Register Number:** 192465066
- **Java version:** OpenJDK 21 (26.0.1; Java 16+ required)

## How to compile and run
Open a terminal inside the task folder, then:

| Task | Folder | Compile | Run | Result |
|---|---|---|---|---|
| 1 | Task01 | `javac ParkingAnalysis.java` | `java ParkingAnalysis` | Parallel arrays O(n) vs HashMap O(1); duplicate regNo rejected |
| 2 | Task02 | `javac DataRepresentation.java` | `java DataRepresentation` | Integer-division bug gives 60.0, correct ceiling gives 90.0; jagged array |
| 3 | Task03 | `javac *.java` | `java ParkingFacility` / `java FastParkingFacility` | Matches expected output; stack version gives same result |
| 4 | Task04 | `javac TicketDemo.java` | `java TicketDemo` | Constructor chaining, copy constructor, factory |
| 5 | Task05 | `javac StaticDemo.java` | `java StaticDemo` | Static counter unique; instance counter duplicates numbers |
| 6 | Task06 | `javac InheritanceDemo.java` | `java InheritanceDemo` | Constructor order Vehicle -> Car -> ElectricCar |
| 7 | Task07 | `javac OverrideDemo.java` | `java OverrideDemo` | equals/hashCode: same regNo = equal; switch alternative |
| 8 | Task08 | `javac *.java` | `java FeeDemo` / `java OverloadDemo` | 30.0, 90.0, 210.0, true (matches expected) |
| 9 | Task09 | `javac AbstractionDemo.java` | `java AbstractionDemo` | Abstract class + 2 interfaces, default/static methods |
| 10 | Task10 | `javac -d out parking/model/*.java parking/service/*.java parking/app/*.java` | `java -cp out parking.app.Main` | Validation, packages, Integer cache pitfall |
| 11 | Task11 | `javac ParkingApp.java` | `java ParkingApp` | 20/20 tests pass; day total Rs 325 = hand calculation; benchmark |
| 12 | Task12 | `javac ParkingApp.java` | `java ParkingApp` | E-bike, delivery van, one-day pass, 5 uneven floors - all pass |

Each folder has `output*.txt` (saved console output) and a `screenshots/` folder for your own screenshots.
