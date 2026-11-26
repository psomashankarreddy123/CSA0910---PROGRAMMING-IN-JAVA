public class task7 {

    public static String oldMethod(int fields) {
        String ticket = "";
        for (int i = 1; i <= fields; i++) {
            ticket = ticket + "Field" + i + ": Value" + i + "\n";
        }
        return ticket;
    }

    public static String newMethod(int fields) {
        StringBuilder ticket = new StringBuilder();
        for (int i = 1; i <= fields; i++) {
            ticket.append("Field").append(i).append(": Value").append(i).append('\n');
        }
        return ticket.toString();
    }

    public static long testOldMethod(int fields, int repeats) {
        long start = System.nanoTime();
        int total = 0;
        for (int i = 0; i < repeats; i++) {
            total = total + oldMethod(fields).length();
        }
        if (total == 0) System.out.println();
        return (System.nanoTime() - start) / repeats;
    }

    public static long testNewMethod(int fields, int repeats) {
        long start = System.nanoTime();
        int total = 0;
        for (int i = 0; i < repeats; i++) {
            total = total + newMethod(fields).length();
        }
        if (total == 0) System.out.println();
        return (System.nanoTime() - start) / repeats;
    }

    public static void main(String[] args) {
        int[] sizes = {10, 50, 100, 500};
        int repeats = 1000;

        System.out.println("Fields,Original ns/ticket,StringBuilder ns/ticket");

        for (int fields : sizes) {
            long oldTime = testOldMethod(fields, repeats);
            long newTime = testNewMethod(fields, repeats);
            System.out.println(fields + "," + oldTime + "," + newTime);
        }

        System.out.println("The redesigned version uses StringBuilder to reduce repeated String creation.");
    }
}