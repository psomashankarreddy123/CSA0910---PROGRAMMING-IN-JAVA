package parking.service;

import parking.model.Vehicle;

public class ParkingFacility {
    private final String[][] slots;                                // private: only allocate/release change it
    public ParkingFacility(int[] perFloor) {
        slots = new String[perFloor.length][];
        for (int f = 0; f < perFloor.length; f++) slots[f] = new String[perFloor[f]];
    }
    public int[] allocate(Vehicle v) {
        for (int f = 0; f < slots.length; f++) for (int s = 0; s < slots[f].length; s++)
            if (slots[f][s] == null) { slots[f][s] = v.getRegNo(); return new int[]{f, s}; }
        return null;
    }
    public boolean release(int f, int s) {
        if (f < 0 || f >= slots.length || s < 0 || s >= slots[f].length || slots[f][s] == null) return false;
        slots[f][s] = null; return true;
    }
}
