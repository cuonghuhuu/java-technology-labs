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

public class ProgressDemoFrame extends JFrame {
    private final JButton loadButton = new JButton("Tải dữ liệu");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel statusLabel = new JLabel("Sẵn sàng", SwingConstants.CENTER);

    public ProgressDemoFrame() {
        setTitle("Bài 2 - Mô phỏng tải dữ liệu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 240);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("MÔ PHỎNG TẢI DỮ LIỆU", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(10, 35, 20, 35));
        progressBar.setStringPainted(true);
        center.add(loadButton);
        center.add(progressBar);
        center.add(statusLabel);
        add(center, BorderLayout.CENTER);

        loadButton.addActionListener(event -> loadData());
        setLocationRelativeTo(null);
    }

    private void loadData() {
        loadButton.setEnabled(false);
        progressBar.setValue(0);
        statusLabel.setText("Đang tải dữ liệu...");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i += 10) {
                    setProgress(i);
                    if (i < 100) {
                        Thread.sleep(1000);
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                progressBar.setValue(100);
                statusLabel.setText("Tải dữ liệu hoàn tất");
                loadButton.setEnabled(true);
            }
        };
        worker.addPropertyChangeListener(event -> {
            if ("progress".equals(event.getPropertyName())) {
                progressBar.setValue((Integer) event.getNewValue());
            }
        });
        worker.execute();
    }
}
