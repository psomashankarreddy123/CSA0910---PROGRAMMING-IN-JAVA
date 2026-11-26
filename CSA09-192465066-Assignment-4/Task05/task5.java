import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class task5 extends JFrame implements ActionListener {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target =
            new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");

    private static double rate(String currency) {
        return currency.equals("USD") ? 0.012 : 0.011;
    }

    public task5(int style) {
        super("Currency Converter");
        setLayout(new FlowLayout());
        JButton convertButton = new JButton("Convert");
        add(new JLabel("INR"));
        add(amount);
        add(target);
        add(convertButton);
        add(result);

        switch (style) {
            case 1:
                convertButton.addActionListener(this);
                amount.addActionListener(this);
                break;
            case 2:
                ConvertHandler handler = new ConvertHandler();
                convertButton.addActionListener(handler);
                amount.addActionListener(handler);
                break;
            case 3:
                ActionListener anonymous = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        convert();
                    }
                };
                convertButton.addActionListener(anonymous);
                amount.addActionListener(anonymous);
                break;
            case 4:
                ActionListener lambda = e -> convert();
                convertButton.addActionListener(lambda);
                amount.addActionListener(lambda);
                break;
            default:
                throw new IllegalArgumentException("Choose style 1 to 4");
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        convert();
    }

    private void convert() {
        try {
            double value = Double.parseDouble(amount.getText().trim());
            if (!Double.isFinite(value) || value < 0) {
                throw new NumberFormatException();
            }
            String currency = (String) target.getSelectedItem();
            double converted = value * rate(currency);
            String message = String.format("%.2f %s", converted, currency);
            result.setText(message);
            JOptionPane.showMessageDialog(this, message,
                    "Conversion Result", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            result.setText("Invalid amount");
            JOptionPane.showMessageDialog(this, "Invalid amount",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ConvertHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            convert();
        }
    }

    public static void main(String[] args) {
        int style = args.length == 0 ? 4 : Integer.parseInt(args[0]);
        SwingUtilities.invokeLater(() -> new task5(style).setVisible(true));
    }
}