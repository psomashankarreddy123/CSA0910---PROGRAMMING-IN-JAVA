import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Task7 {
    static class ResultTask implements Callable<String> {
        private final Path file;

        ResultTask(Path file) { this.file = file; }

        public String call() throws Exception {
            if (!Files.exists(file)) throw new IOException("File not found");
            String value = Files.readString(file).trim();
            int mark = Integer.parseInt(value);
            if (mark < 0 || mark > 100) throw new IllegalArgumentException("Invalid mark");
            return file.getFileName() + " : " + (mark >= 40 ? "PASS" : "FAIL");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Path folder = Path.of("scores");
        try {
            Files.createDirectories(folder);
            for (int i = 1; i <= 10; i++) {
                Path file = folder.resolve("student" + i + ".txt");
                if (!Files.exists(file)) Files.writeString(file, String.valueOf(35 + i * 6));
            }
        } catch (IOException e) {
            System.out.println("Could not prepare sample files: " + e.getMessage());
            return;
        }

        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Path file = folder.resolve("student" + i + ".txt");
            futures.add(pool.submit(new ResultTask(file)));
        }

        int success = 0, failure = 0;
        for (Future<String> future : futures) {
            try {
                System.out.println(future.get());
                success++;
            } catch (ExecutionException e) {
                System.out.println("Processing failed: " + e.getCause().getMessage());
                failure++;
            }
        }
        pool.shutdown();
        System.out.println("Summary: " + success + " successful, " + failure + " failed.");
    }
}
