import java.time.*;

/** Task 8: compile-time overloading, upcasting, dynamic dispatch trace, instanceof alternative. */
public class OverloadDemo {
    static abstract class Vehicle {
        protected abstract double ratePerHour();
        public double calculateFee(int minutes) { return ((minutes + 59) / 60) * ratePerHour(); }
        public double calculateFee(LocalTime in, LocalTime out) { return calculateFee((int) Duration.between(in, out).toMinutes()); }
    }
    static class TwoWheeler extends Vehicle { protected double ratePerHour() { return 10; } }
    static class Car extends Vehicle { protected double ratePerHour() { return 30; } }
    static class ElectricCar extends Car { }
    public static void main(String[] args) {
        Vehicle[] parked = { new TwoWheeler(), new Car(), new ElectricCar() };        // upcasting
        for (Vehicle v : parked)
            System.out.println("declared=Vehicle actual=" + v.getClass().getSimpleName() + " fee(130)=" + v.calculateFee(130));
        System.out.println("Overload (int)            : " + parked[1].calculateFee(130));
        System.out.println("Overload (LocalTime,Local): " + parked[1].calculateFee(LocalTime.of(9, 0), LocalTime.of(11, 10)));
        // Alternative: instanceof chain - order matters (ElectricCar before Car) and must be repeated everywhere
        for (Vehicle v : parked) {
            double r = (v instanceof TwoWheeler) ? 10 : (v instanceof ElectricCar || v instanceof Car) ? 30 : -1;
            System.out.println("instanceof chain rate for " + v.getClass().getSimpleName() + " = " + r);
        }
    }
}
