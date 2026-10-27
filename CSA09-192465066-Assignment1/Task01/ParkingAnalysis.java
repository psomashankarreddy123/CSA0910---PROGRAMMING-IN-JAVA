import java.util.*;

/** Task 1: candidate classes and parallel-array vs object comparison. */
public class ParkingAnalysis {
    static class Vehicle {
        final String regNo; final String type; final long entryTime;
        Vehicle(String r, String t, long e) { regNo = r; type = t; entryTime = e; }
    }
    // Design A: parallel arrays
    static String[] regNo = new String[5]; static String[] type = new String[5]; static long[] entry = new long[5];
    static int count = 0;
    static int findParallel(String r) { for (int i = 0; i < count; i++) if (regNo[i].equals(r)) return i; return -1; }

    public static void main(String[] args) {
        String[][] data = {{"TN01A1","2W"},{"TN02B2","CAR"},{"TN03C3","EV"}};
        List<Vehicle> objs = new ArrayList<>(); Map<String,Vehicle> index = new HashMap<>();
        for (String[] d : data) {
            regNo[count] = d[0]; type[count] = d[1]; entry[count] = 600; count++;       // parallel arrays
            Vehicle v = new Vehicle(d[0], d[1], 600); objs.add(v); index.put(d[0], v);   // objects
        }
        System.out.println("Parallel array find TN03C3 -> index " + findParallel("TN03C3") + " (O(n) scan)");
        System.out.println("Object HashMap find TN03C3 -> " + index.get("TN03C3").type + " (O(1) average)");
        System.out.println("Business rule: duplicate regNo allowed? " + (index.put("TN01A1", objs.get(0)) == null ? "yes" : "no - key already present"));
    }
}
