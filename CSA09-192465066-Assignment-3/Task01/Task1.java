import java.util.InputMismatchException;
import java.util.Scanner;

public class Task1 {
    private static final double BALANCE = 5000.00;

    static void withdraw(double amount) {
        try {
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");
            if (amount > BALANCE) throw new ArithmeticException("Insufficient balance.");
            System.out.printf("Withdrawal successful. Remaining balance: %.2f%n", BALANCE - amount);
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        } finally {
            System.out.println("ATM session completed.");
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter withdrawal amount: ");
            withdraw(sc.nextDouble());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Enter a numeric amount.");
            System.out.println("ATM session completed.");
        }
    }
}
