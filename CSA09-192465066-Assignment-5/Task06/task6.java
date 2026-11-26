import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class task6 {
    static class PassStore {
        private final Path file;

        PassStore(Path file) {
            this.file = file;
        }

        public void add(String passId, String regNo, String name, int route, double amount)
                throws IOException {
            try (BufferedWriter w = Files.newBufferedWriter(file,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                w.write(String.join(",", passId, regNo, name,
                        String.valueOf(route), String.valueOf(amount)));
                w.newLine();
            }
        }

        public void listByRoute(int route) throws IOException {
            if (!Files.exists(file)) return;
            try (BufferedReader r = Files.newBufferedReader(file)) {
                String line;
                while ((line = r.readLine()) != null) {
                    String[] fields = line.split(",", -1);
                    if (fields.length != 5) continue;
                    try {
                        if (Integer.parseInt(fields[3].trim()) == route) {
                            System.out.println(Arrays.toString(fields));
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }

        public String[] find(String passId) throws IOException {
            if (!Files.exists(file)) return null;
            try (BufferedReader r = Files.newBufferedReader(file)) {
                String line;
                while ((line = r.readLine()) != null) {
                    String[] fields = line.split(",", -1);
                    if (fields.length != 5) continue;
                    try {
                        Integer.parseInt(fields[3].trim());
                        Double.parseDouble(fields[4].trim());
                    } catch (NumberFormatException ex) {
                        continue;
                    }
                    if (fields[0].equals(passId)) return fields;
                }
            }
            return null;
        }
    }

    private static long countLines(InputStream in) throws IOException {
        try (InputStream input = in) {
            byte[] buffer = new byte[8192];
            long lines = 0;
            int count;
            while ((count = input.read(buffer)) != -1) {
                for (int i = 0; i < count; i++) {
                    if (buffer[i] == '\n') lines++;
                }
            }
            return lines;
        }
    }

    private static long countLinesUnbuffered(Path file) throws IOException {
        long lines = 0;
        try (InputStream in = new FileInputStream(file.toFile())) {
            int value;
            while ((value = in.read()) != -1) {
                if (value == '\n') lines++;
            }
        }
        return lines;
    }

    private static long countLinesBuffered(Path file) throws IOException {
        return countLines(Files.newInputStream(file));
    }

    public static void main(String[] args) throws IOException {
        Path f = Files.createTempFile("passes", ".csv");
        PassStore store = new PassStore(f);
        store.add("P1001", "192311001", "Arun", 12, 4500);
        Files.writeString(f, "P1002,DAMAGED LINE\n", StandardOpenOption.APPEND);
        store.add("P1003", "192311003", "Divya", 7, 3800);

        System.out.println("Find P1003: " + Arrays.toString(store.find("P1003")));
        System.out.println("Missing/damaged P1002: " + store.find("P1002"));
        System.out.println("Route 12 passes:");
        store.listByRoute(12);

        for (int size : new int[]{1000, 10000, 100000}) {
            Files.writeString(f, "", StandardOpenOption.TRUNCATE_EXISTING);
            for (int i = 1; i <= size; i++) {
                store.add("P" + i, "REG" + i, "Student" + i, i % 20 + 1, 4500);
            }
            long searchStart = System.nanoTime();
            store.find("P" + size);
            long searchTime = System.nanoTime() - searchStart;
            System.out.printf("Find in %,d records: %.3f ms%n", size, searchTime / 1_000_000.0);
        }

        Path sample = Files.createTempFile("line-count", ".txt");
        Files.writeString(sample, "one\ntwo\nthree\n");
        long start = System.nanoTime();
        long unbuffered = countLinesUnbuffered(sample);
        long unbufferedMs = (System.nanoTime() - start) / 1_000_000;
        start = System.nanoTime();
        long buffered = countLinesBuffered(sample);
        long bufferedMs = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Unbuffered lines=" + unbuffered + ", time=" + unbufferedMs + " ms");
        System.out.println("Buffered lines=" + buffered + ", time=" + bufferedMs + " ms");

        Files.deleteIfExists(sample);
        Files.deleteIfExists(f);
    }
}
