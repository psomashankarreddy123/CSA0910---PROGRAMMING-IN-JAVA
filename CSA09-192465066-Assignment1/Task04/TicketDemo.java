/** Task 4: constructors, this(...), copy constructor. */
public class TicketDemo {
    static class ParkingTicket {
        private static int counter = 1000;
        private final int ticketNo; private final String regNo; private final int floor, slot, entryMinute;
        ParkingTicket(String regNo, int floor, int slot, int entryMinute) {
            if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("regNo required");
            this.ticketNo = ++counter; this.regNo = regNo; this.floor = floor; this.slot = slot; this.entryMinute = entryMinute;
        }
        ParkingTicket(String regNo, int floor, int slot) { this(regNo, floor, slot, 540); }   // chaining: default entry 9:00
        ParkingTicket(ParkingTicket t) {                                                      // copy: re-print, same number
            this.ticketNo = t.ticketNo; this.regNo = t.regNo; this.floor = t.floor; this.slot = t.slot; this.entryMinute = t.entryMinute;
        }
        @Override public String toString() { return "Ticket#" + ticketNo + " " + regNo + " [" + floor + "," + slot + "] @" + entryMinute; }
    }
    // Alternative: static factory
    static ParkingTicket issue(String r, int f, int s) { return new ParkingTicket(r, f, s); }

    public static void main(String[] args) {
        ParkingTicket a = new ParkingTicket("TN01A1", 0, 0, 600);
        ParkingTicket b = new ParkingTicket("TN02B2", 0, 1);
        ParkingTicket c = new ParkingTicket(a);
        System.out.println(a); System.out.println(b); System.out.println(c + "  (copy, same ticket number)");
        System.out.println("a == c ? " + (a == c) + "   (different heap objects)");
        System.out.println(issue("TN03C3", 1, 0));
        try { new ParkingTicket("", 0, 0); } catch (IllegalArgumentException e) { System.out.println("Rejected: " + e.getMessage()); }
    }
}
