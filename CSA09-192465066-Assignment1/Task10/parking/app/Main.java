package parking.app;

import parking.model.*;
import parking.service.ParkingFacility;

public class Main {
    public static void main(String[] args) {
        ParkingFacility f = new ParkingFacility(new int[]{2, 1});
        Car c = new Car("TN02B2");
        System.out.println("allocate -> " + java.util.Arrays.toString(f.allocate(c)));
        c.setMinutes(130);
        System.out.println("fee -> " + c.calculateFee());
        try { c.setMinutes(-5); } catch (IllegalArgumentException e) { System.out.println("Rejected: " + e.getMessage()); }
        System.out.println("release invalid slot -> " + f.release(9, 9));
        // c.ratePerHour();   // compile error: protected, different package
        Integer a = 1000, b = 1000, x = 100, y = 100;
        System.out.println("Integer 1000 == : " + (a == b) + "   equals: " + a.equals(b));
        System.out.println("Integer 100  == : " + (x == y) + "    (cache -128..127)");
    }
}
