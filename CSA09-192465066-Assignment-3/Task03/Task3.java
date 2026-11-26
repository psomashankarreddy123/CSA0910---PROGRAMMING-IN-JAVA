import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Task3 {
    public static void main(String[] args) {
        Path input = Path.of("sales.txt");
        Path output = Path.of("report.txt");

        try {
            if (!Files.exists(input)) {
                Files.write(input, List.of("Store A,1200", "Store B,950", "Store C,1500"));
            }
            int total = 0;
            try (BufferedReader reader = Files.newBufferedReader(input);
                 BufferedWriter writer = Files.newBufferedWriter(output)) {
                writer.write("DAILY SALES REPORT");
                writer.newLine();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length != 2) throw new IOException("Invalid record: " + line);
                    int amount = Integer.parseInt(parts[1].trim());
                    total += amount;
                    writer.write(parts[0] + " : " + amount);
                    writer.newLine();
                }
                writer.write("TOTAL : " + total);
                writer.newLine();
            }
            System.out.println("Report created successfully. Total sales: " + total);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Report generation failed: " + e.getMessage());
        }
    }
}
