parking.model   : Vehicle, Car (private regNo, validating setter, protected ratePerHour)
parking.service : ParkingFacility (private slots)
parking.app     : Main
Compile: javac -d out parking/model/*.java parking/service/*.java parking/app/*.java
Run    : java -cp out parking.app.Main
