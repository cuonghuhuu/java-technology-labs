package vn.edu.eaut.lab4;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FibonacciFrame extends JFrame {
    private final JTextField numberField = new JTextField();
    private final JButton findButton = new JButton("Tìm");
    private final JLabel resultLabel = new JLabel("Kết quả: --", SwingConstants.CENTER);
    private final JProgressBar progressBar = new JProgressBar(0, 100);

    public FibonacciFrame() {
        setTitle("Bài 4 - Fibonacci");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(540, 260);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("FIBONACCI THỨ N BẰNG MEMOIZATION", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(10, 30, 25, 30));
        JPanel input = new JPanel(new GridLayout(1, 3, 8, 8));
        input.add(new JLabel("Nhập N:"));
        input.add(numberField);
        input.add(findButton);
        progressBar.setStringPainted(true);
        center.add(input);
        center.add(progressBar);
        center.add(resultLabel);
        add(center, BorderLayout.CENTER);

        findButton.addActionListener(event -> findFibonacci());
        setLocationRelativeTo(null);
    }

    private void findFibonacci() {
        final int n;
        try {
            n = Integer.parseInt(numberField.getText().trim());
            if (n < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "N phải là số nguyên lớn hơn hoặc bằng 0.",
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            return;
        }

        findButton.setEnabled(false);
        progressBar.setValue(0);
        progressBar.setIndeterminate(true);
        resultLabel.setText("Đang tính...");

        SwingWorker<BigInteger, Void> worker = new SwingWorker<>() {
            @Override
            protected BigInteger doInBackground() {
                Map<Integer, BigInteger> memo = new HashMap<>();
                memo.put(0, BigInteger.ZERO);
                memo.put(1, BigInteger.ONE);
                return fibonacci(n, memo);
            }

            @Override
            protected void done() {
                try {
                    resultLabel.setText("Fibonacci(" + n + ") = " + get());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    resultLabel.setText("Tác vụ đã bị gián đoạn.");
                } catch (ExecutionException ex) {
                    resultLabel.setText("Không thể tính kết quả.");
                } finally {
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(100);
                    findButton.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private BigInteger fibonacci(int n, Map<Integer, BigInteger> memo) {
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        BigInteger value = fibonacci(n - 1, memo).add(fibonacci(n - 2, memo));
        memo.put(n, value);
        return value;
    }
}
