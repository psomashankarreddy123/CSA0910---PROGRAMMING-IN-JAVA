import java.sql.*;
import java.util.*;

public class task8 {
    static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }

    static void clear(Connection con) throws SQLException {
        try (Statement st = con.createStatement()) { st.executeUpdate("DELETE FROM bus_pass"); }
    }

    static List<String[]> generate(int size) {
        List<String[]> rows = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            rows.add(new String[]{"P" + i, "REG" + i, "Student" + i,
                    String.valueOf(i % 20 + 1), "4500.00"});
        }
        return rows;
    }

    static int insertWithStatement(Connection con, List<String[]> rows) throws SQLException {
        int count = 0;
        try (Statement st = con.createStatement()) {
            for (String[] p : rows) {
                String sql = "INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES ('"
                        + p[0] + "','" + p[1] + "','" + p[2] + "'," + p[3] + "," + p[4] + ")";
                st.executeUpdate(sql);
                count++;
            }
        }
        return count;
    }

    static int insertWithPreparedStatement(Connection con, List<String[]> rows) throws SQLException {
        String sql = "INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (String[] p : rows) {
                ps.setString(1, p[0]); ps.setString(2, p[1]); ps.setString(3, p[2]);
                ps.setInt(4, Integer.parseInt(p[3])); ps.setDouble(5, Double.parseDouble(p[4]));
                ps.executeUpdate();
            }
        }
        return rows.size();
    }

    static int loadAll(Connection con, List<String[]> passes) throws SQLException {
        String sql = "INSERT INTO bus_pass(pass_id, reg_no, name, route_no, amount) VALUES (?, ?, ?, ?, ?)";
        boolean oldAutoCommit = con.getAutoCommit();
        con.setAutoCommit(false);
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            int n = 0;
            for (String[] p : passes) {
                ps.setString(1, p[0]);
                ps.setString(2, p[1]);
                ps.setString(3, p[2]);
                ps.setInt(4, Integer.parseInt(p[3]));
                ps.setDouble(5, Double.parseDouble(p[4]));
                ps.addBatch();
                n++;
                if (n % 1000 == 0) ps.executeBatch();
            }
            ps.executeBatch();
            con.commit();
            return n;
        } catch (SQLException | RuntimeException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(oldAutoCommit);
        }
    }

    static long rowCount(Connection con) throws SQLException {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM bus_pass")) {
            rs.next();
            return rs.getLong(1);
        }
    }

    static void benchmark(Connection con, int size) throws SQLException {
        List<String[]> rows = generate(size);
        clear(con);
        long start = System.nanoTime();
        insertWithStatement(con, rows);
        long statementTime = System.nanoTime() - start;
        clear(con);
        start = System.nanoTime();
        insertWithPreparedStatement(con, rows);
        long preparedTime = System.nanoTime() - start;
        clear(con);
        start = System.nanoTime();
        loadAll(con, rows);
        long batchTime = System.nanoTime() - start;
        System.out.printf("%,d rows | Statement %.2f ms | PreparedStatement %.2f ms | Batch %.2f ms | verified %,d%n",
                size, statementTime / 1e6, preparedTime / 1e6, batchTime / 1e6, rowCount(con));
    }

    static void transactionFailureDemo(Connection con) throws SQLException {
        List<String[]> rows = generate(10);
        rows.set(5, new String[]{"P6", "REG6", "Invalid", "6", "not-a-number"});
        clear(con);
        try {
            for (String[] p : rows) {
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES(?,?,?,?,?)")) {
                    ps.setString(1, p[0]); ps.setString(2, p[1]); ps.setString(3, p[2]);
                    ps.setInt(4, Integer.parseInt(p[3]));
                    ps.setDouble(5, Double.parseDouble(p[4]));
                    ps.executeUpdate();
                }
            }
        } catch (RuntimeException ex) {
            System.out.println("Auto-commit failure leaves rows: " + rowCount(con));
        } finally {
            clear(con);
        }

        boolean old = con.getAutoCommit();
        con.setAutoCommit(false);
        try {
            for (String[] p : rows) {
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES(?,?,?,?,?)")) {
                    ps.setString(1, p[0]); ps.setString(2, p[1]); ps.setString(3, p[2]);
                    ps.setInt(4, Integer.parseInt(p[3]));
                    ps.setDouble(5, Double.parseDouble(p[4]));
                    ps.executeUpdate();
                }
            }
            con.commit();
        } catch (RuntimeException | SQLException ex) {
            con.rollback();
            System.out.println("Transaction rolled back; rows remaining: " + rowCount(con));
        } finally {
            con.setAutoCommit(old);
            clear(con);
        }
    }

    public static void main(String[] args) throws Exception {
        String url = env("DB_URL", "jdbc:mysql://localhost:3306/assignment5");
        String user = env("DB_USER", "root");
        String password = env("DB_PASSWORD", "");
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS bus_pass (pass_id VARCHAR(30) PRIMARY KEY, reg_no VARCHAR(30) NOT NULL, name VARCHAR(100) NOT NULL, route_no INT NOT NULL, amount DECIMAL(10,2) NOT NULL)");
            for (int size : new int[]{10, 1000, 10000, 100000}) benchmark(con, size);
            transactionFailureDemo(con);
        }
    }
}
