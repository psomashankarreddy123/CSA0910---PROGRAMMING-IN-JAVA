import java.util.*;

/** Task 7: overriding, @Override, final, equals/hashCode/toString, and the switch alternative. */
public class OverrideDemo {
    static abstract class Vehicle {
        private final String regNo;                       // final: set once
        Vehicle(String r) { regNo = r; }
        protected abstract double ratePerHour();
        public final String describe() { return toString(); }   // final method: cannot be overridden
        public double calculateFee(int m) { return ((m + 59) / 60) * ratePerHour(); }
        @Override public boolean equals(Object o) { return o instanceof Vehicle v && regNo.equals(v.regNo); }
        @Override public int hashCode() { return regNo.hashCode(); }
        @Override public String toString() { return getClass().getSimpleName() + "[" + regNo + "]"; }
    }
    static class TwoWheeler extends Vehicle { TwoWheeler(String r) { super(r); } @Override protected double ratePerHour() { return 10; } }
    static class Car extends Vehicle { Car(String r) { super(r); } @Override protected double ratePerHour() { return 30; } }

    // Alternative: switch on a type field (adding a type means editing this method)
    static double feeBySwitch(String type, int m) {
        int h = (m + 59) / 60;
        return switch (type) { case "2W" -> h * 10; case "CAR" -> h * 30; default -> throw new IllegalStateException("unknown " + type); };
    }
    public static void main(String[] args) {
        Vehicle a = new Car("TN02B2"), b = new Car("TN02B2");
        System.out.println(a + " equals " + b + " -> " + a.equals(b) + ", same hashCode -> " + (a.hashCode() == b.hashCode()));
        Set<Vehicle> set = new HashSet<>(List.of(a)); System.out.println("HashSet contains duplicate reg? " + set.contains(b));
        System.out.println("Overriding : TwoWheeler 130min = " + new TwoWheeler("X").calculateFee(130) + ", Car = " + a.calculateFee(130));
        System.out.println("Switch alt : 2W = " + feeBySwitch("2W", 130) + ", CAR = " + feeBySwitch("CAR", 130));
        System.out.println(a.describe());
    }
}
