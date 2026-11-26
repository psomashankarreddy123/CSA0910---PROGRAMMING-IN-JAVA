public class task1 {

    public static long testString(int fields, int tickets) {
        long start = System.nanoTime();
        long totalLength = 0;
        int rounds = 5;

        for (int round = 0; round < rounds; round++) {
            for (int i = 0; i < tickets; i++) {
                String ticket = "";
                for (int j = 1; j <= fields; j++) {
                    ticket = ticket + "Field" + j + ": Value" + j + "\n";
                }
                totalLength = totalLength + ticket.length();
            }
        }

        if (totalLength == 0) System.out.println();
        return (System.nanoTime() - start) / (tickets * rounds);
    }

    public static long testStringBuilder(int fields, int tickets) {
        long start = System.nanoTime();
        long totalLength = 0;
        int rounds = 5;

        for (int round = 0; round < rounds; round++) {
            for (int i = 0; i < tickets; i++) {
                StringBuilder ticket = new StringBuilder();
                for (int j = 1; j <= fields; j++) {
                    ticket.append("Field").append(j).append(": Value").append(j).append('\n');
                }
                totalLength = totalLength + ticket.length();
            }
        }

        if (totalLength == 0) System.out.println();
        return (System.nanoTime() - start) / (tickets * rounds);
    }

    public static long testStringBuffer(int fields, int tickets) {
        long start = System.nanoTime();
        long totalLength = 0;
        int rounds = 5;

        for (int round = 0; round < rounds; round++) {
            for (int i = 0; i < tickets; i++) {
                StringBuffer ticket = new StringBuffer();
                for (int j = 1; j <= fields; j++) {
                    ticket.append("Field").append(j).append(": Value").append(j).append('\n');
                }
                totalLength = totalLength + ticket.length();
            }
        }

        if (totalLength == 0) System.out.println();
        return (System.nanoTime() - start) / (tickets * rounds);
    }

    public static void main(String[] args) {
        int[] fields = {6, 50, 500};
        int[] tickets = {1, 10000, 1000};

        System.out.println("Fields,Tickets,String ns/ticket,StringBuilder ns/ticket,StringBuffer ns/ticket");

        for (int i = 0; i < fields.length; i++) {
            long stringTime = testString(fields[i], tickets[i]);
            long builderTime = testStringBuilder(fields[i], tickets[i]);
            long bufferTime = testStringBuffer(fields[i], tickets[i]);

            System.out.println(fields[i] + "," + tickets[i] + "," + stringTime + "," + builderTime + "," + bufferTime);
        }

        System.out.println("Use StringBuilder for a ticket and a thread-safe logger for shared logs.");
    }
}