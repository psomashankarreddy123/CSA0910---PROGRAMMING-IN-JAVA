/** Task 5: static counter vs instance counter, static block, object passing. */
public class StaticDemo {
    static class Ticket { static int counter = 0; final int no; Ticket() { no = ++counter; } }
    static class Facility { int counter = 0; int next() { return ++counter; } }        // the WRONG design for 2 facilities
    static final java.util.Map<String, Double> RATE = new java.util.HashMap<>();
    static { RATE.put("2W", 10.0); RATE.put("CAR", 30.0); }                             // runs once at class load
    static void modify(int[] slot) { slot[1] = 9; slot = new int[]{7, 7}; }             // pass-by-value of reference

    public static void main(String[] args) {
        System.out.println("Static counter: " + new Ticket().no + ", " + new Ticket().no + ", " + new Ticket().no);
        Facility north = new Facility(), south = new Facility();
        System.out.println("Instance counter north=" + north.next() + " south=" + south.next() + "  <- duplicate ticket number 1!");
        System.out.println("Static block loaded rates: " + RATE);
        int[] s = {0, 0}; modify(s);
        System.out.println("After modify(): " + java.util.Arrays.toString(s) + " (state changed, reference not reassigned)");
        Ticket t = new Ticket(); t = null;   // object now unreachable -> eligible for garbage collection
        System.out.println("Ticket object unreachable after t=null (GC eligible)");
    }
}
