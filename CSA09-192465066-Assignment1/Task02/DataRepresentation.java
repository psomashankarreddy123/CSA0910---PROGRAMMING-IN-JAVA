import java.util.*;

/** Task 2: data types, jagged array, casting risk, Scanner input. */
public class DataRepresentation {
    public static void main(String[] args) {
        Scanner sc = new Scanner("TN01AB1111 130");            // replace with new Scanner(System.in) for live input
        String regNo = sc.next(); int minutes = sc.nextInt();
        double rate = 30.0;
        double wrong  = (minutes / 60) * rate;                  // integer division: 130/60 = 2
        double wrong2 = (double) (minutes / 60) * rate;         // cast too late
        int hours = (minutes + 59) / 60;                        // integer ceiling
        double right = hours * rate;
        System.out.println(regNo + " minutes=" + minutes);
        System.out.println("WRONG  (minutes/60)*rate           = " + wrong);
        System.out.println("WRONG  (double)(minutes/60)*rate   = " + wrong2);
        System.out.println("RIGHT  ((minutes+59)/60)*rate      = " + right);
        int[] perFloor = {20, 35, 50};                          // jagged array: different slots per floor
        String[][] slots = new String[perFloor.length][];
        int total = 0;
        for (int f = 0; f < perFloor.length; f++) { slots[f] = new String[perFloor[f]]; total += perFloor[f]; }
        System.out.println("Floors=" + slots.length + " total slots=" + total + " slots[1].length=" + slots[1].length);
        // Alternative: 1-D array needs offsets when floors differ
        int[] offset = new int[perFloor.length + 1];
        for (int f = 0; f < perFloor.length; f++) offset[f + 1] = offset[f] + perFloor[f];
        System.out.println("1-D index of floor 1 slot 5 = " + (offset[1] + 5));
    }
}
