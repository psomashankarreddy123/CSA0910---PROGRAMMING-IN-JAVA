import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class task9 {
    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }

    public static void main(String[] args) throws Exception {
        Path source = Path.of(args.length == 0 ? "passes.csv" : args[0]);
        String url = env("DB_URL", "jdbc:mysql://localhost:3306/assignment5");
        String user = env("DB_USER", "root");
        String password = env("DB_PASSWORD", "");

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            boolean oldAutoCommit = con.getAutoCommit();
            con.setAutoCommit(false);
            String sql = "INSERT INTO bus_pass(pass_id, reg_no, name, route_no, amount) VALUES (?, ?, ?, ?, ?)";
            List<Integer> rejected = new ArrayList<>();
            int loaded = 0;
            try (BufferedReader reader = Files.newBufferedReader(source);
                 PreparedStatement ps = con.prepareStatement(sql)) {
                String line;
                int lineNo = 0;
                while ((line = reader.readLine()) != null) {
                    lineNo++;
                    String[] f = line.split(",", -1);
                    if (f.length != 5 || f[0].isBlank() || f[1].isBlank() || f[2].isBlank()) {
                        rejected.add(lineNo);
                        continue;
                    }
                    try {
                        int route = Integer.parseInt(f[3].trim());
                        double amount = Double.parseDouble(f[4].trim());
                        if (!Double.isFinite(amount) || amount < 0) throw new NumberFormatException();
                        ps.setString(1, f[0].trim());
                        ps.setString(2, f[1].trim());
                        ps.setString(3, f[2].trim());
                        ps.setInt(4, route);
                        ps.setDouble(5, amount);
                        ps.addBatch();
                        loaded++;
                        if (loaded % 1000 == 0) ps.executeBatch();
                    } catch (NumberFormatException ex) {
                        rejected.add(lineNo);
                    }
                }
                ps.executeBatch();
                con.commit();
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(oldAutoCommit);
            }

            System.out.println("Rows migrated: " + loaded);
            System.out.println("Rejected line numbers: " + rejected);
            DatabaseMetaData md = con.getMetaData();
            System.out.println("Database: " + md.getDatabaseProductName() + " " + md.getDatabaseProductVersion());
            System.out.println("JDBC driver: " + md.getDriverName() + " " + md.getDriverVersion());
            try (ResultSet tables = md.getTables(con.getCatalog(), null, "%", new String[]{"TABLE"})) {
                System.out.println("Tables:");
                while (tables.next()) System.out.println(tables.getString("TABLE_NAME"));
            }
        }
    }
}
