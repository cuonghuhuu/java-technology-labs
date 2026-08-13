package vn.edu.eaut.lab4;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class CancelTaskFrame extends JFrame {
    private final JButton startButton = new JButton("Bắt đầu");
    private final JButton cancelButton = new JButton("Hủy");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel statusLabel = new JLabel("Sẵn sàng", SwingConstants.CENTER);
    private SwingWorker<Void, Void> worker;

    public CancelTaskFrame() {
        setTitle("Bài 6 - Hủy tác vụ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(470, 260);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("BỔ SUNG CHỨC NĂNG HỦY TÁC VỤ", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(10, 35, 25, 35));
        JPanel buttons = new JPanel(new GridLayout(1, 2, 10, 10));
        cancelButton.setEnabled(false);
        buttons.add(startButton);
        buttons.add(cancelButton);
        progressBar.setStringPainted(true);
        center.add(buttons);
        center.add(progressBar);
        center.add(statusLabel);
        add(center, BorderLayout.CENTER);

        startButton.addActionListener(event -> startTask());
        cancelButton.addActionListener(event -> cancelTask());
        setLocationRelativeTo(null);
    }

    private void startTask() {
        startButton.setEnabled(false);
        cancelButton.setEnabled(true);
        progressBar.setValue(0);
        statusLabel.setText("Đang thực hiện tác vụ...");

        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                for (int i = 0; i <= 100; i++) {
                    if (isCancelled()) {
                        return null;
                    }
                    setProgress(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        if (isCancelled()) {
                            return null;
                        }
                        Thread.currentThread().interrupt();
                        return null;
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                if (isCancelled()) {
                    statusLabel.setText("Đã hủy tác vụ");
                } else {
                    progressBar.setValue(100);
                    statusLabel.setText("Tác vụ đã hoàn thành");
                }
                startButton.setEnabled(true);
                cancelButton.setEnabled(false);
            }
        };
        worker.addPropertyChangeListener(event -> {
            if ("progress".equals(event.getPropertyName())) {
                progressBar.setValue((Integer) event.getNewValue());
            }
        });
        worker.execute();
    }

    private void cancelTask() {
        if (worker != null && !worker.isDone()) {
            worker.cancel(true);
            statusLabel.setText("Đang hủy tác vụ...");
        }
    }
}
