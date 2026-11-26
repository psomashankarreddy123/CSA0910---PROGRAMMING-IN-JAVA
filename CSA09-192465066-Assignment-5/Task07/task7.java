import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class task7 {
    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }

    private static String hash(String value) throws Exception {
        byte[] bytes = MessageDigest.getInstance("SHA-256")
                .digest(value.getBytes(StandardCharsets.UTF_8));
        StringBuilder out = new StringBuilder();
        for (byte b : bytes) out.append(String.format("%02x", b));
        return out.toString();
    }

    private static void inspectDamagedFile() throws Exception {
        Path file = Files.createTempFile("damaged-passes", ".csv");
        Files.writeString(file, "P1,REG1,Arun,12,4500.0\n"
                + "P2,REG2,Divya,seven,3800.0\n"
                + "P3,REG3\n"
                + "\n"
                + "P4,REG4,Kumar,7,3200.0\n");
        try (var reader = Files.newBufferedReader(file)) {
            String line;
            int number = 0;
            while ((line = reader.readLine()) != null) {
                number++;
                String[] fields = line.split(",", -1);
                boolean valid = fields.length == 5;
                if (valid) {
                    try {
                        Integer.parseInt(fields[3]);
                        double amount = Double.parseDouble(fields[4]);
                        valid = Double.isFinite(amount) && amount >= 0;
                    } catch (NumberFormatException ex) {
                        valid = false;
                    }
                }
                System.out.println("Line " + number + ": " + (valid ? "ACCEPTED" : "REJECTED"));
            }
        }
        Files.deleteIfExists(file);
    }

    public static void main(String[] args) throws Exception {
        inspectDamagedFile();
        String url = env("DB_URL", "jdbc:mysql://localhost:3306/assignment5");
        String user = env("DB_USER", "root");
        String password = env("DB_PASSWORD", "");
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS users (id VARCHAR(80) PRIMARY KEY, pwd_hash CHAR(64) NOT NULL)");
            st.executeUpdate("DELETE FROM users WHERE id='demo'");
            try (PreparedStatement insert = con.prepareStatement(
                    "INSERT INTO users(id, pwd_hash) VALUES (?, ?)")) {
                insert.setString(1, "demo");
                insert.setString(2, hash("correct-password"));
                insert.executeUpdate();
            }

            String injectedId = "demo' OR '1'='1' -- ";
            String invalidPassword = "wrong";
            String unsafeSql = "SELECT * FROM users WHERE id='" + injectedId
                    + "' AND pwd_hash='" + hash(invalidPassword) + "'";
            try (Statement unsafe = con.createStatement();
                 ResultSet rs = unsafe.executeQuery(unsafeSql)) {
                System.out.println("Unsafe login accepted: " + rs.next());
            }

            String safeSql = "SELECT 1 FROM users WHERE id=? AND pwd_hash=?";
            try (PreparedStatement safe = con.prepareStatement(safeSql)) {
                safe.setString(1, injectedId);
                safe.setString(2, hash(invalidPassword));
                try (ResultSet rs = safe.executeQuery()) {
                    System.out.println("PreparedStatement login accepted: " + rs.next());
                }
            }
        }
    }
}
