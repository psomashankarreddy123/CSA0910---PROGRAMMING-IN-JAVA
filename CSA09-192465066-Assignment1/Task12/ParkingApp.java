import java.util.*;

public class ParkingApp {

    static int hours(int minutes) { return (minutes + 59) / 60; }

    // ---------- model ----------
    interface Chargeable { double chargingFee(int kWh); }

    static abstract class Vehicle {
        private final String regNo;
        protected Vehicle(String regNo) {
            if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("regNo required");
            this.regNo = regNo;
        }
        public String getRegNo() { return regNo; }
        protected abstract double ratePerHour();
        public double calculateFee(int minutes, int entryHour) {
            if (minutes < 0) throw new IllegalArgumentException("negative duration");
            return hours(minutes) * ratePerHour();
        }
        @Override public boolean equals(Object o) { return o instanceof Vehicle v && regNo.equals(v.regNo); }
        @Override public int hashCode() { return regNo.hashCode(); }
        @Override public String toString() { return getClass().getSimpleName() + "[" + regNo + "]"; }
    }
    static class TwoWheeler extends Vehicle { TwoWheeler(String r){super(r);} protected double ratePerHour(){return 10;} }
    static class Car extends Vehicle { Car(String r){super(r);} protected double ratePerHour(){return 30;} }
    static class ElectricCar extends Car implements Chargeable {
        ElectricCar(String r){super(r);} public double chargingFee(int kWh){return kWh*12;} }
    // --- new in Task 12: only NEW classes, no existing class edited ---
    static class EBike extends Vehicle {
        EBike(String r){super(r);} protected double ratePerHour(){return 0;}
        @Override public double calculateFee(int m, int h){ if(m<0) throw new IllegalArgumentException("negative duration"); return 15; } }
    static class DeliveryVan extends Vehicle {
        DeliveryVan(String r){super(r);} protected double ratePerHour(){return 60;}
        @Override public double calculateFee(int m, int h){
            if(m<0) throw new IllegalArgumentException("negative duration");
            double rate = (h>=9 && h<17) ? 2*30 : 30;   // double the car rate, 9AM-5PM
            return hours(m)*rate; } }

    enum PassType { NONE, MONTHLY, ONE_DAY }

    static class Ticket {
        private static int counter = 1000;               // static: shared across all tickets
        final int no; final Vehicle vehicle; final int floor, slot, entryHour; final PassType pass;
        Ticket(Vehicle v, int f, int s, int h, PassType p){ no=++counter; vehicle=v; floor=f; slot=s; entryHour=h; pass=p; }
    }

    static class Facility {
        private final String[][] slots;
        private final Map<String,Ticket> active = new HashMap<>();
        private double collection = 0;
        Facility(int[] perFloor){ slots=new String[perFloor.length][]; for(int f=0;f<perFloor.length;f++) slots[f]=new String[perFloor[f]]; }
        Ticket enter(Vehicle v, int hour, PassType p){
            if (active.containsKey(v.getRegNo())) throw new IllegalStateException("duplicate registration " + v.getRegNo());
            for(int f=0;f<slots.length;f++) for(int s=0;s<slots[f].length;s++) if(slots[f][s]==null){
                slots[f][s]=v.getRegNo(); Ticket t=new Ticket(v,f,s,hour,p); active.put(v.getRegNo(),t); return t; }
            throw new IllegalStateException("facility full");
        }
        double exit(String regNo, int minutes, int kWh){
            Ticket t = active.remove(regNo);
            if (t==null) throw new NoSuchElementException("no active ticket for " + regNo);
            slots[t.floor][t.slot]=null;
            double fee = (t.pass==PassType.NONE) ? t.vehicle.calculateFee(minutes, t.entryHour) : 0;
            if (t.vehicle instanceof Chargeable c && kWh>0) fee += c.chargingFee(kWh);
            collection += fee; return fee;
        }
        double getCollection(){ return collection; }
    }

    static Vehicle make(String type, String reg){
        return switch (type) {
            case "2W" -> new TwoWheeler(reg); case "CAR" -> new Car(reg); case "EV" -> new ElectricCar(reg);
            case "EBIKE" -> new EBike(reg); case "VAN" -> new DeliveryVan(reg);
            default -> throw new IllegalArgumentException("unknown vehicle type " + type); };
    }

    static int pass=0, fail=0;
    static void check(String name, Object expected, Object actual){
        boolean ok = expected.equals(actual); if(ok) pass++; else fail++;
        System.out.printf("%-48s expected=%-10s actual=%-10s %s%n", name, expected, actual, ok?"PASS":"FAIL"); }
    static String tryIt(Runnable r){ try { r.run(); return "no error"; } catch(RuntimeException e){ return e.getClass().getSimpleName(); } }

    public static void main(String[] a){
        System.out.println("=== Normal operation ===");
        Facility f = new Facility(new int[]{2,1});
        Ticket t1=f.enter(make("2W","TN01A1"),10,PassType.NONE);
        Ticket t2=f.enter(make("CAR","TN02B2"),10,PassType.NONE);
        check("ticket numbers unique/increasing", true, t2.no>t1.no);
        check("2W 130 min", 30.0, f.exit("TN01A1",130,0));
        check("Car 130 min", 90.0, f.exit("TN02B2",130,0));
        f.enter(make("EV","TN03C3"),10,PassType.NONE);
        check("EV 130 min + 10 kWh", 210.0, f.exit("TN03C3",130,10));
        System.out.println("=== Boundary ===");
        Facility g = new Facility(new int[]{1});
        g.enter(make("CAR","LAST1"),10,PassType.NONE);
        check("last slot used -> next entry rejected", "IllegalStateException", tryIt(() -> g.enter(make("CAR","X2"),10,PassType.NONE)));
        check("0 minutes", 0.0, g.exit("LAST1",0,0));
        Car c = new Car("B1");
        check("60 min (slab boundary)", 30.0, c.calculateFee(60,10));
        check("61 min (just over)", 60.0, c.calculateFee(61,10));
        check("integer-division trap 61/60*30 would give", 30.0, (61/60)*30.0);
        System.out.println("=== Invalid ===");
        Facility h = new Facility(new int[]{3});
        check("unknown type", "IllegalArgumentException", tryIt(() -> make("BUS","B1")));
        h.enter(make("CAR","DUP1"),10,PassType.NONE);
        check("duplicate registration", "IllegalStateException", tryIt(() -> h.enter(make("CAR","DUP1"),10,PassType.NONE)));
        check("exit without ticket", "NoSuchElementException", tryIt(() -> h.exit("NOPE",10,0)));
        check("negative duration", "IllegalArgumentException", tryIt(() -> new Car("N1").calculateFee(-5,10)));
        ParkingFacilityCheck.run();
        System.out.println("=== Task 12: new categories ===");
        Facility n = new Facility(new int[]{4,6,3,8,5});
        n.enter(make("EBIKE","EB1"),10,PassType.NONE);
        check("E-bike flat fee (any duration)", 15.0, n.exit("EB1",300,0));
        n.enter(make("VAN","V1"),10,PassType.NONE);
        check("Van 130 min at 10AM (double car rate)", 180.0, n.exit("V1",130,0));
        n.enter(make("VAN","V2"),20,PassType.NONE);
        check("Van 130 min at 8PM (normal car rate)", 90.0, n.exit("V2",130,0));
        n.enter(make("CAR","V3"),10,PassType.ONE_DAY);
        check("Visitor one-day pass car", 0.0, n.exit("V3",600,0));
        n.enter(make("EV","V4"),10,PassType.MONTHLY);
        check("Monthly pass EV, charging still billed", 120.0, n.exit("V4",600,10));
        System.out.println("=== Hand-calculated day scenario ===");
        Facility d = new Facility(new int[]{4,6,3,8,5});
        d.enter(make("2W","D1"),9,PassType.NONE);  d.exit("D1",45,0);       // 1h*10 = 10
        d.enter(make("CAR","D2"),9,PassType.NONE); d.exit("D2",125,0);      // 3h*30 = 90
        d.enter(make("EV","D3"),9,PassType.NONE);  d.exit("D3",60,5);       // 30+60 = 90
        d.enter(make("EBIKE","D4"),9,PassType.NONE); d.exit("D4",30,0);     // 15
        d.enter(make("VAN","D5"),10,PassType.NONE); d.exit("D5",90,0);      // 2h*60 = 120
        d.enter(make("CAR","D6"),9,PassType.MONTHLY); d.exit("D6",500,0);   // 0
        check("Day total (hand: 10+90+90+15+120+0)", 325.0, d.getCollection());
        System.out.println("TOTAL PASS=" + pass + " FAIL=" + fail);
        System.out.println("=== Benchmark: find ticket on exit (n parked) ===");
        for (int n2 : new int[]{1000, 10000, 100000}) bench(n2);
    }

    static void bench(int n){
        String[] arr = new String[n]; ArrayList<String> al = new ArrayList<>(n); HashMap<String,String> hm = new HashMap<>();
        for(int i=0;i<n;i++){ String r="TN"+i; arr[i]=r; al.add(r); hm.put(r,r); }
        String key = "TN"+(n-1); int reps = 2000; long sink=0;
        long t0=System.nanoTime(); for(int k=0;k<reps;k++){ for(int i=0;i<n;i++) if(arr[i].equals(key)){sink+=i;break;} }
        long tArr=(System.nanoTime()-t0)/reps;
        t0=System.nanoTime(); for(int k=0;k<reps;k++) sink+=al.indexOf(key); long tAl=(System.nanoTime()-t0)/reps;
        t0=System.nanoTime(); for(int k=0;k<reps;k++) sink+=hm.get(key).length(); long tHm=(System.nanoTime()-t0)/reps;
        System.out.printf("n=%-7d array scan=%-8d ns  ArrayList.indexOf=%-8d ns  HashMap.get=%-5d ns  (sink %d)%n", n,tArr,tAl,tHm,sink>0?1:0);
    }

    static class ParkingFacilityCheck { static void run(){
        Facility x = new Facility(new int[]{1});
        check("slot freed after exit can be reused", "ok", tryIt2(x)); }
        static String tryIt2(Facility x){ x.enter(new Car("R1"),9,PassType.NONE); x.exit("R1",10,0); x.enter(new Car("R2"),9,PassType.NONE); return "ok"; } }
}
