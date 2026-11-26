import java.awt.*;
import javax.swing.*;

public class task3 extends JFrame {
    private final JTextField searchField = new JTextField(22);
    private final JComboBox<String> category = new JComboBox<>(
            new String[]{"All", "Science", "Technology", "Literature"});
    private final DefaultListModel<String> results = new DefaultListModel<>();
    private final JList<String> resultList = new JList<>(results);
    private final JLabel status = new JLabel("Ready");

    public task3() {
        super("Campus Library Search");
        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchRow.add(new JLabel("Search")); searchRow.add(searchField);
        searchRow.add(category);
        JButton search = new JButton("Search");
        searchRow.add(search);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Results"));
        resultPanel.add(new JScrollPane(resultList), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prev = new JButton("Prev");
        JButton next = new JButton("Next");
        JButton reserve = new JButton("Reserve");
        actions.add(prev); actions.add(next); actions.add(reserve);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(actions, BorderLayout.CENTER);
        bottom.add(status, BorderLayout.SOUTH);

        JPanel header = new JPanel(new BorderLayout());
        header.add(new JLabel("Campus Library", SwingConstants.CENTER), BorderLayout.NORTH);
        header.add(searchRow, BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        search.addActionListener(e -> {
            results.clear();
            String term = searchField.getText().trim();
            if (term.isEmpty()) status.setText("Enter a search term.");
            else {
                results.addElement(term + " – Sample library result 1");
                results.addElement(term + " – Sample library result 2");
                status.setText("2 sample results for " + category.getSelectedItem());
            }
        });
        reserve.addActionListener(e -> status.setText(
                resultList.getSelectedValue() == null ? "Select a result first." : "Reservation requested."));
        prev.addActionListener(e -> status.setText("Previous page"));
        next.addActionListener(e -> status.setText("Next page"));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720, 460);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new task3().setVisible(true));
    }
}