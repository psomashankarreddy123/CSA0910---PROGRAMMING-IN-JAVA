import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class task6 extends JFrame {
    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://localhost:3306/campus_events");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");

    private final JTextField studentId = new JTextField(10);
    private final JTextField studentName = new JTextField(14);
    private final JComboBox<String> event = new JComboBox<>(
            new String[]{"Tech Symposium", "Cultural Fest", "Sports Meet"});
    private final JComboBox<String> filter = new JComboBox<>(
            new String[]{"All", "Tech Symposium", "Cultural Fest", "Sports Meet"});
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Student ID", "Student Name", "Event"}, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JLabel status = new JLabel("Ready");

    interface SqlWork<T> { T run(Connection connection) throws SQLException; }

    static class RegistrationService {
        <T> T withConnection(SqlWork<T> work) throws SQLException {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                return work.run(connection);
            }
        }

        void register(String id, String name, String eventName) throws SQLException {
            if (id.isBlank() || name.isBlank())
                throw new IllegalArgumentException("Student ID and name are required.");
            withConnection(c -> {
                String sql = "INSERT INTO registrations(student_id, student_name, event_name) VALUES (?, ?, ?)";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, id.trim());
                    ps.setString(2, name.trim());
                    ps.setString(3, eventName);
                    ps.executeUpdate();
                }
                return null;
            });
        }

        ResultSetData load(String selectedEvent, int limit, int offset) throws SQLException {
            return withConnection(c -> {
                String sql = "SELECT id, student_id, student_name, event_name FROM registrations "
                        + (selectedEvent.equals("All") ? "" : "WHERE event_name = ? ")
                        + "ORDER BY id LIMIT ? OFFSET ?";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    int p = 1;
                    if (!selectedEvent.equals("All")) ps.setString(p++, selectedEvent);
                    ps.setInt(p++, limit); ps.setInt(p, offset);
                    try (ResultSet rs = ps.executeQuery()) {
                        ResultSetData data = new ResultSetData();
                        while (rs.next()) data.rows.add(new Object[]{
                                rs.getInt("id"), rs.getString("student_id"),
                                rs.getString("student_name"), rs.getString("event_name")});
                        return data;
                    }
                }
            });
        }
    }

    static class ResultSetData {
        java.util.List<Object[]> rows = new java.util.ArrayList<>();
    }

    private final RegistrationService service = new RegistrationService();

    public task6() {
        super("Campus Event Registration");
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Student ID")); form.add(studentId);
        form.add(new JLabel("Name")); form.add(studentName);
        form.add(event);
        JButton register = new JButton("Register");
        form.add(register);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem refresh = new JMenuItem("Refresh");
        JMenuItem export = new JMenuItem("Export CSV");
        menu.add(refresh); menu.add(export); menuBar.add(menu);
        setJMenuBar(menuBar);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filter event")); filterPanel.add(filter);
        JButton apply = new JButton("Apply filter"); filterPanel.add(apply);

        JPanel north = new JPanel(new BorderLayout());
        north.add(form, BorderLayout.NORTH); north.add(filterPanel, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        register.addActionListener(e -> registerAsync());
        apply.addActionListener(e -> loadAsync());
        refresh.addActionListener(e -> loadAsync());
        export.addActionListener(e -> exportCsv());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 480); setLocationRelativeTo(null);
        loadAsync();
    }

    private void registerAsync() {
        String id = studentId.getText();
        String name = studentName.getText();
        String eventName = (String) event.getSelectedItem();
        setBusy(true, "Registering...");
        new SwingWorker<Void, Void>() {
            @Override protected Void doInBackground() throws Exception {
                service.register(id, name, eventName); return null;
            }
            @Override protected void done() {
                setBusy(false, "Registration completed.");
                try { get(); loadAsync(); }
                catch (Exception ex) { showError(ex); }
            }
        }.execute();
    }

    private void loadAsync() {
        String selected = (String) filter.getSelectedItem();
        setBusy(true, "Loading registrations...");
        new SwingWorker<ResultSetData, Void>() {
            @Override protected ResultSetData doInBackground() throws Exception {
                return service.load(selected, 100, 0);
            }
            @Override protected void done() {
                try {
                    model.setRowCount(0);
                    for (Object[] row : get().rows) model.addRow(row);
                    setBusy(false, "Loaded " + model.getRowCount() + " registration(s).");
                } catch (Exception ex) { setBusy(false, "Load failed."); showError(ex); }
            }
        }.execute();
    }

    private void exportCsv() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try (java.io.PrintWriter out = new java.io.PrintWriter(chooser.getSelectedFile())) {
            for (int c = 0; c < model.getColumnCount(); c++) {
                if (c > 0) out.print(",");
                out.print(model.getColumnName(c));
            }
            out.println();
            for (int r = 0; r < model.getRowCount(); r++) {
                for (int c = 0; c < model.getColumnCount(); c++) {
                    if (c > 0) out.print(",");
                    String value = String.valueOf(model.getValueAt(r, c)).replace("\"", "\"\"");
                    out.print("\"" + value + "\"");
                }
                out.println();
            }
            status.setText("CSV exported.");
        } catch (Exception ex) { showError(ex); }
    }

    private void setBusy(boolean busy, String message) {
        status.setText(message);
    }

    private void showError(Exception ex) {
        Throwable cause = ex;
        while (cause.getCause() != null) cause = cause.getCause();
        String message = cause instanceof SQLIntegrityConstraintViolationException
                ? "This student is already registered for that event."
                : cause.getMessage();
        status.setText(message);
        JOptionPane.showMessageDialog(this, message, "Registration error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "MySQL Connector/J is missing from the classpath.");
            return;
        }
        SwingUtilities.invokeLater(() -> new task6().setVisible(true));
    }
}