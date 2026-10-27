/** Task 9: abstract class + interfaces (default/static) + multiple interfaces. */
public class AbstractionDemo {
    interface Chargeable {
        double chargingFee(int kWh);
        default double chargingFee(int kWh, boolean peak) { return peak ? chargingFee(kWh) * 1.5 : chargingFee(kWh); }
        static boolean isValidKwh(int kWh) { return kWh >= 0; }
    }
    interface Payable { double amountDue(); }
    static abstract class Vehicle {
        private final String regNo;
        protected Vehicle(String r) { regNo = r; }
        public String getRegNo() { return regNo; }
        protected abstract double ratePerHour();
        public double calculateFee(int m) { return ((m + 59) / 60) * ratePerHour(); }
    }
    static class ElectricCar extends Vehicle implements Chargeable, Payable {        // 1 class + 2 interfaces
        private double due;
        ElectricCar(String r) { super(r); }
        protected double ratePerHour() { return 30; }
        public double chargingFee(int kWh) { return kWh * 12; }
        public double amountDue() { return due; }
        void bill(int minutes, int kWh) { due = calculateFee(minutes) + chargingFee(kWh); }
    }
    public static void main(String[] args) {
        // Vehicle v = new Vehicle("X");   // compile error: abstract class cannot be instantiated
        ElectricCar e = new ElectricCar("TN03C3"); e.bill(130, 10);
        System.out.println("Amount due        : " + e.amountDue());
        System.out.println("Peak charging 10kWh: " + e.chargingFee(10, true) + " (default method)");
        System.out.println("isValidKwh(-5)     : " + Chargeable.isValidKwh(-5) + " (static interface method)");
        Payable p = e; Chargeable c = e;
        System.out.println("One object, many roles: " + (p instanceof Payable) + " " + (c instanceof Chargeable) + " " + (e instanceof Vehicle));
    }
}
