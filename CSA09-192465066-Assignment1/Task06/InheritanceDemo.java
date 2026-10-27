/** Task 6: hierarchy and constructor execution order for ElectricCar. */
public class InheritanceDemo {
    static abstract class Vehicle {
        private final String regNo;
        protected Vehicle(String r) { regNo = r; System.out.println("3. Vehicle constructor (regNo=" + r + ")"); }
        String getRegNo() { return regNo; }
        protected abstract double ratePerHour();
        double fee(int minutes) { return ((minutes + 59) / 60) * ratePerHour(); }
    }
    static class TwoWheeler extends Vehicle { TwoWheeler(String r) { super(r); } protected double ratePerHour() { return 10; } }
    static class Car extends Vehicle {
        Car(String r) { super(r); System.out.println("4. Car constructor"); }
        protected double ratePerHour() { return 30; }
    }
    interface Chargeable { double chargingFee(int kWh); }
    static class ElectricCar extends Car implements Chargeable {
        ElectricCar(String r) { super(r); System.out.println("5. ElectricCar constructor"); }
        public double chargingFee(int kWh) { return kWh * 12; }
        @Override double fee(int m) { return super.fee(m) + 0; }                     // super.method() usage
    }
    public static void main(String[] args) {
        System.out.println("Creating new ElectricCar(\"TN03C3\") ...");
        ElectricCar e = new ElectricCar("TN03C3");
        System.out.println("fee(130)=" + e.fee(130) + " charging(10)=" + e.chargingFee(10));
        System.out.println("e instanceof Car: " + (e instanceof Car) + ", Vehicle: " + (e instanceof Vehicle) + ", Chargeable: " + (e instanceof Chargeable));
    }
}
