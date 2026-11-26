import java.sql.*;
import java.util.*;

public class task3 {
    interface Dao<T,K> {
        void insert(T value) throws SQLException;
        Optional<T> findById(K id) throws SQLException;
        List<T> findAll() throws SQLException;
        void update(T value) throws SQLException;
        void delete(K id) throws SQLException;
    }
    static class Student {
        int id; String name, department;
        Student(int id,String name,String department) { this.id=id; this.name=name; this.department=department; }
        public String toString() { return id+" "+name+" "+department; }
    }
    static class StudentDao implements Dao<Student,Integer> {
        private final Connection con;
        StudentDao(Connection con) { this.con=con; }
        public void insert(Student s) throws SQLException {
            String sql="INSERT INTO student(id,name,department) VALUES(?,?,?)";
            try(PreparedStatement ps=con.prepareStatement(sql)) {
                ps.setInt(1,s.id); ps.setString(2,s.name); ps.setString(3,s.department); ps.executeUpdate();
            }
        }
        public Optional<Student> findById(Integer id) throws SQLException {
            try(PreparedStatement ps=con.prepareStatement("SELECT id,name,department FROM student WHERE id=?")) {
                ps.setInt(1,id);
                try(ResultSet rs=ps.executeQuery()) {
                    return rs.next()?Optional.of(new Student(rs.getInt("id"),rs.getString("name"),rs.getString("department"))):Optional.empty();
                }
            }
        }
        public List<Student> findAll() throws SQLException {
            List<Student> result=new ArrayList<>();
            try(Statement st=con.createStatement(); ResultSet rs=st.executeQuery("SELECT id,name,department FROM student")) {
                while(rs.next()) result.add(new Student(rs.getInt(1),rs.getString(2),rs.getString(3)));
            }
            return result;
        }
        public void update(Student s) throws SQLException {
            try(PreparedStatement ps=con.prepareStatement("UPDATE student SET name=?,department=? WHERE id=?")) {
                ps.setString(1,s.name); ps.setString(2,s.department); ps.setInt(3,s.id); ps.executeUpdate();
            }
        }
        public void delete(Integer id) throws SQLException {
            try(PreparedStatement ps=con.prepareStatement("DELETE FROM student WHERE id=?")) {
                ps.setInt(1,id); ps.executeUpdate();
            }
        }
    }
    static String env(String key,String fallback) {
        String v=System.getenv(key); return v==null||v.isBlank()?fallback:v;
    }
    public static void main(String[] args) throws Exception {
        String url=env("DB_URL","jdbc:mysql://localhost:3306/assignment5");
        try(Connection con=DriverManager.getConnection(url,env("DB_USER","root"),env("DB_PASSWORD",""))) {
            try(Statement st=con.createStatement()) {
                st.executeUpdate("CREATE TABLE IF NOT EXISTS student(id INT PRIMARY KEY,name VARCHAR(80),department VARCHAR(80))");
                st.executeUpdate("DELETE FROM student WHERE id=1001");
            }
            Dao<Student,Integer> dao=new StudentDao(con);
            dao.insert(new Student(1001,"Arun","CSE"));
            System.out.println("Find: "+dao.findById(1001).orElse(null));
            System.out.println("All: "+dao.findAll());
            dao.update(new Student(1001,"Arun Kumar","CSE"));
            System.out.println("Updated: "+dao.findById(1001).orElse(null));
            dao.delete(1001);
            System.out.println("After delete: "+dao.findById(1001).orElse(null));
        }
    }
}
