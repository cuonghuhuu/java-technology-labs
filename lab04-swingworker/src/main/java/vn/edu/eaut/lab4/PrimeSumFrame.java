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
import java.util.concurrent.ExecutionException;

public class PrimeSumFrame extends JFrame {
    private final JTextField numberField = new JTextField();
    private final JButton calculateButton = new JButton("Tính");
    private final JLabel resultLabel = new JLabel("Kết quả: --", SwingConstants.CENTER);
    private final JProgressBar progressBar = new JProgressBar(0, 100);

    public PrimeSumFrame() {
        setTitle("Bài 3 - Tổng các số nguyên tố");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(530, 280);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("TỔNG CÁC SỐ NGUYÊN TỐ NHỎ HƠN N", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4, 1, 8, 8));
        center.setBorder(BorderFactory.createEmptyBorder(8, 30, 20, 30));
        JPanel input = new JPanel(new GridLayout(1, 3, 8, 8));
        input.add(new JLabel("Nhập N:"));
        input.add(numberField);
        input.add(calculateButton);
        progressBar.setStringPainted(true);
        center.add(input);
        center.add(progressBar);
        center.add(resultLabel);
        center.add(new JLabel("Ví dụ: N = 10 thì tổng bằng 17", SwingConstants.CENTER));
        add(center, BorderLayout.CENTER);

        calculateButton.addActionListener(event -> calculatePrimeSum());
        setLocationRelativeTo(null);
    }

    private void calculatePrimeSum() {
        final int limit;
        try {
            limit = Integer.parseInt(numberField.getText().trim());
            if (limit <= 2) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "N phải là số nguyên lớn hơn 2.",
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            return;
        }

        calculateButton.setEnabled(false);
        progressBar.setValue(0);
        resultLabel.setText("Đang tính...");

        SwingWorker<Long, Void> worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() {
                long sum = 0;
                for (int number = 2; number < limit; number++) {
                    if (isPrime(number)) {
                        sum += number;
                    }
                    setProgress((int) ((long) (number - 1) * 100 / (limit - 2)));
                }
                setProgress(100);
                return sum;
            }

            @Override
            protected void done() {
                try {
                    long sum = get();
                    resultLabel.setText("Tổng các số nguyên tố nhỏ hơn " + limit + " = " + sum);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    resultLabel.setText("Tác vụ đã bị gián đoạn.");
                } catch (ExecutionException ex) {
                    resultLabel.setText("Không thể tính kết quả.");
                } finally {
                    calculateButton.setEnabled(true);
                }
            }
        };
        worker.addPropertyChangeListener(event -> {
            if ("progress".equals(event.getPropertyName())) {
                progressBar.setValue((Integer) event.getNewValue());
            }
        });
        worker.execute();
    }

    private boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int divisor = 3; divisor <= n / divisor; divisor += 2) {
            if (n % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
