package vn.edu.eaut.lab4;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

public class CountdownFrame extends JFrame {
    private final JTextField secondsField = new JTextField();
    private final JButton startButton = new JButton("Bắt đầu");
    private final JLabel timeLabel = new JLabel("Thời gian còn lại: --", SwingConstants.CENTER);

    public CountdownFrame() {
        setTitle("Bài 1 - Đồng hồ đếm ngược");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(430, 220);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("ĐỒNG HỒ ĐẾM NGƯỢC", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 8, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        inputPanel.add(new JLabel("Số giây:"));
        inputPanel.add(secondsField);
        inputPanel.add(startButton);
        add(inputPanel, BorderLayout.CENTER);

        timeLabel.setFont(timeLabel.getFont().deriveFont(Font.BOLD, 16f));
        timeLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 20, 10));
        add(timeLabel, BorderLayout.SOUTH);

        startButton.addActionListener(event -> startCountdown());
        setLocationRelativeTo(null);
    }

    private void startCountdown() {
        final int seconds;
        try {
            seconds = Integer.parseInt(secondsField.getText().trim());
            if (seconds <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số giây phải là số nguyên lớn hơn 0.",
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            return;
        }

        startButton.setEnabled(false);
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = seconds; i >= 0; i--) {
                    publish(i);
                    if (i > 0) {
                        Thread.sleep(1000);
                    }
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int latest = chunks.get(chunks.size() - 1);
                timeLabel.setText("Thời gian còn lại: " + latest + " giây");
            }

            @Override
            protected void done() {
                startButton.setEnabled(true);
                JOptionPane.showMessageDialog(CountdownFrame.this, "Hoàn thành!");
            }
        };
        worker.execute();
    }
}
