class ScannerStation implements Runnable {
    private final String station;
    private final int scripts;

    ScannerStation(String station, int scripts) {
        this.station = station;
        this.scripts = scripts;
    }

    public void run() {
        try {
            System.out.println(station + " started");
            Thread.sleep(500);
            System.out.println(station + " scanned " + scripts + " scripts");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(station + " interrupted");
        }
    }
}

public class Task4 {
    public static void main(String[] args) throws InterruptedException {
        Thread[] stations = new Thread[4];
        for (int i = 0; i < stations.length; i++) {
            stations[i] = new Thread(new ScannerStation("Station " + (i + 1), 25));
            stations[i].start();
        }

        for (Thread station : stations) station.join();

        System.out.println("All stations completed. Total scripts scanned: 100");
    }
}
