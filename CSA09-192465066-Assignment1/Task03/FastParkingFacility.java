import java.util.*;

/** Task 3 alternative: free-slot count per floor + stack of free slots. */
public class FastParkingFacility {
    private final String[][] slots; private final int[] freeCount; private final Deque<Integer>[] freeStack;
    @SuppressWarnings("unchecked")
    public FastParkingFacility(int[] perFloor) {
        slots = new String[perFloor.length][]; freeCount = new int[perFloor.length]; freeStack = new Deque[perFloor.length];
        for (int f = 0; f < perFloor.length; f++) {
            slots[f] = new String[perFloor[f]]; freeCount[f] = perFloor[f]; freeStack[f] = new ArrayDeque<>();
            for (int s = perFloor[f] - 1; s >= 0; s--) freeStack[f].push(s);   // slot 0 on top
        }
    }
    public int[] allocate(String v) {                         // O(F)
        for (int f = 0; f < slots.length; f++) if (freeCount[f] > 0) {
            int s = freeStack[f].pop(); freeCount[f]--; slots[f][s] = v; return new int[]{f, s}; }
        return null;
    }
    public boolean release(int f, int s) {                    // O(1)
        if (f < 0 || f >= slots.length || s < 0 || s >= slots[f].length || slots[f][s] == null) return false;
        slots[f][s] = null; freeStack[f].push(s); freeCount[f]++; return true;
    }
    public static void main(String[] a) {
        FastParkingFacility p = new FastParkingFacility(new int[]{2, 1});
        System.out.println(Arrays.toString(p.allocate("A"))); System.out.println(Arrays.toString(p.allocate("B")));
        System.out.println(Arrays.toString(p.allocate("C"))); System.out.println(Arrays.toString(p.allocate("D")));
        System.out.println(p.release(0, 1) + " " + p.release(0, 1) + " " + p.release(5, 0));
        System.out.println(Arrays.toString(p.allocate("D")));
    }
}
