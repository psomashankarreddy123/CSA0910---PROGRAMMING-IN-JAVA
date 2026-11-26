import java.sql.*;

public class task5 {
    static String env(String key,String fallback) {
        String v=System.getenv(key); return v==null||v.isBlank()?fallback:v;
    }
    static void transfer(Connection con,int fromId,int toId,double amount) throws SQLException {
        boolean old=con.getAutoCommit();
        con.setAutoCommit(false);
        try {
            try(PreparedStatement debit=con.prepareStatement("UPDATE wallet SET balance=balance-? WHERE wallet_id=? AND balance>=?");
                PreparedStatement credit=con.prepareStatement("UPDATE wallet SET balance=balance+? WHERE wallet_id=?")) {
                debit.setDouble(1,amount); debit.setInt(2,fromId); debit.setDouble(3,amount);
                if(debit.executeUpdate()!=1) throw new SQLException("Insufficient funds or source wallet missing");
                credit.setDouble(1,amount); credit.setInt(2,toId);
                if(credit.executeUpdate()!=1) throw new SQLException("Destination wallet missing");
            }
            con.commit();
        } catch(SQLException | RuntimeException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(old);
        }
    }
    static boolean safeLogin(Connection con,String id,String password) throws SQLException {
        String sql="SELECT 1 FROM users WHERE id=? AND pwd=?";
        try(PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1,id); ps.setString(2,password);
            try(ResultSet rs=ps.executeQuery()) { return rs.next(); }
        }
    }
    public static void main(String[] args) throws Exception {
        String url=env("DB_URL","jdbc:mysql://localhost:3306/assignment5");
        try(Connection con=DriverManager.getConnection(url,env("DB_USER","root"),env("DB_PASSWORD",""));
            Statement st=con.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS wallet(wallet_id INT PRIMARY KEY,balance DECIMAL(12,2) NOT NULL)");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS users(id VARCHAR(80) PRIMARY KEY,pwd VARCHAR(100) NOT NULL)");
            st.executeUpdate("INSERT INTO wallet(wallet_id,balance) VALUES(1,2000),(2,1000) ON DUPLICATE KEY UPDATE balance=VALUES(balance)");
            transfer(con,1,2,500.00);
            try(ResultSet rs=st.executeQuery("SELECT wallet_id,balance FROM wallet ORDER BY wallet_id")) {
                while(rs.next()) System.out.println("Wallet "+rs.getInt(1)+": "+rs.getBigDecimal(2));
            }
            System.out.println("PreparedStatement login for injected ID: "+safeLogin(con,"' OR '1'='1","anything"));
        }
    }
}
