import java.awt.*;
import java.awt.event.*;

public class task1 extends Frame {
    private final TextField registerNumber = new TextField(18);
    private final TextField amount = new TextField(18);
    private final Label feedback = new Label("Enter payment details.");
    private final FeeService feeService = new FeeService();

    static class FeeService {
        String pay(String registerNumber, String amountText) {
            if (registerNumber == null || registerNumber.isBlank())
                return "Enter a register number.";
            final double value;
            try { value = Double.parseDouble(amountText); }
            catch (NumberFormatException ex) { return "Enter a valid amount."; }
            if (!Double.isFinite(value) || value <= 0)
                return "Amount must be greater than zero.";
            return String.format("Payment of %.2f recorded for %s.", value, registerNumber);
        }
    }

    public task1() {
        super("Student Fee Payment");
        setLayout(new BorderLayout(8, 8));
        Panel fields = new Panel(new GridLayout(2, 2, 8, 8));
        fields.add(new Label("Register number")); fields.add(registerNumber);
        fields.add(new Label("Amount")); fields.add(amount);
        Panel actions = new Panel(new FlowLayout());
        Button pay = new Button("Pay");
        Button clear = new Button("Clear");
        Button receipt = new Button("Receipt");
        actions.add(pay); actions.add(clear); actions.add(receipt);
        add(fields, BorderLayout.CENTER);
        add(actions, BorderLayout.NORTH);
        add(feedback, BorderLayout.SOUTH);

        pay.addActionListener(e -> feedback.setText(
                feeService.pay(registerNumber.getText().trim(), amount.getText().trim())));
        clear.addActionListener(e -> {
            registerNumber.setText(""); amount.setText(""); feedback.setText("Fields cleared.");
        });
        receipt.addActionListener(e -> feedback.setText(
                "Receipt is available after a confirmed payment."));
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { dispose(); }
        });
        setSize(440, 190);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new task1().setVisible(true));
    }
}