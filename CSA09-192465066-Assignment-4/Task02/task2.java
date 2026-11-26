import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class task2 extends JFrame {
    private final JTextField patientId = new JTextField(12);
    private final JComboBox<String> department = new JComboBox<>(
            new String[]{"Cardiology", "Neurology", "Orthopaedics"});
    private final JComboBox<String> doctor = new JComboBox<>();
    private final JComboBox<String> slot = new JComboBox<>(
            new String[]{"09:00", "10:00", "11:00", "14:00", "15:00"});
    private final JLabel status = new JLabel("Enter patient details.");
    private boolean bookingInProgress;
    private final Map<String, java.util.List<String>> doctors = new LinkedHashMap<>();

    public task2() {
        super("Hospital Outpatient Appointment Desk");
        doctors.put("Cardiology", Arrays.asList("Dr. Kumar", "Dr. Meena"));
        doctors.put("Neurology", Arrays.asList("Dr. Arun", "Dr. Priya"));
        doctors.put("Orthopaedics", Arrays.asList("Dr. Ravi", "Dr. Nisha"));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        form.add(new JLabel("Patient ID")); form.add(patientId);
        form.add(new JLabel("Department")); form.add(department);
        form.add(new JLabel("Doctor")); form.add(doctor);
        form.add(new JLabel("Time slot")); form.add(slot);
        JButton book = new JButton("Book appointment");
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(book); bottom.add(status);
        add(form, BorderLayout.CENTER); add(bottom, BorderLayout.SOUTH);

        department.addActionListener(e -> refreshDoctors());
        book.addActionListener(e -> bookAppointment());
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                if (bookingInProgress) {
                    int answer = JOptionPane.showConfirmDialog(task2.this,
                            "A booking is in progress. Close anyway?",
                            "Confirm close", JOptionPane.YES_NO_OPTION);
                    if (answer != JOptionPane.YES_OPTION) return;
                }
                dispose();
            }
        });
        refreshDoctors();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(520, 260);
        setLocationRelativeTo(null);
    }

    private void refreshDoctors() {
        String selected = (String) department.getSelectedItem();
        doctor.removeAllItems();
        if (selected != null)
            for (String name : doctors.getOrDefault(selected, Collections.emptyList()))
                doctor.addItem(name);
    }

    private void bookAppointment() {
        if (patientId.getText().isBlank()) {
            status.setText("Patient ID is required."); return;
        }
        if (doctor.getSelectedItem() == null || slot.getSelectedItem() == null) {
            status.setText("Choose a doctor and time slot."); return;
        }
        bookingInProgress = true;
        try {
            status.setText("Appointment booked.");
            JOptionPane.showMessageDialog(this, "Appointment confirmed for "
                    + patientId.getText().trim() + " with " + doctor.getSelectedItem()
                    + " at " + slot.getSelectedItem() + ".");
        } finally {
            bookingInProgress = false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new task2().setVisible(true));
    }
}