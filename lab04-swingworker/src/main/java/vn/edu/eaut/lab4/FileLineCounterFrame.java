package vn.edu.eaut.lab4;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;

public class FileLineCounterFrame extends JFrame {
    private final JButton chooseButton = new JButton("Chọn file");
    private final JButton countButton = new JButton("Đếm dòng");
    private final JLabel pathLabel = new JLabel("Chưa chọn file", SwingConstants.CENTER);
    private final JLabel resultLabel = new JLabel("Số dòng: --", SwingConstants.CENTER);
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private File selectedFile;

    public FileLineCounterFrame() {
        setTitle("Bài 5 - Đếm dòng file");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 300);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("ĐỌC FILE LỚN VÀ ĐẾM SỐ DÒNG", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4, 1, 8, 8));
        center.setBorder(BorderFactory.createEmptyBorder(8, 25, 25, 25));
        JPanel buttons = new JPanel(new GridLayout(1, 2, 10, 10));
        buttons.add(chooseButton);
        buttons.add(countButton);
        progressBar.setStringPainted(true);
        center.add(buttons);
        center.add(pathLabel);
        center.add(progressBar);
        center.add(resultLabel);
        add(center, BorderLayout.CENTER);

        chooseButton.addActionListener(event -> chooseFile());
        countButton.addActionListener(event -> countLines());
        setLocationRelativeTo(null);
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn file văn bản");
        chooser.setFileFilter(new FileNameExtensionFilter("File văn bản (*.txt, *.csv)", "txt", "csv"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            pathLabel.setText(selectedFile.getAbsolutePath());
            resultLabel.setText("Số dòng: --");
        }
    }

    private void countLines() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file trước khi đếm.",
                    "Chưa chọn file", JOptionPane.WARNING_MESSAGE);
            return;
        }

        chooseButton.setEnabled(false);
        countButton.setEnabled(false);
        progressBar.setValue(0);
        resultLabel.setText("Đang đếm dòng...");
        File fileToRead = selectedFile;

        SwingWorker<Long, Void> worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() throws IOException {
                long fileSize = Math.max(1, Files.size(fileToRead.toPath()));
                long estimatedBytesRead = 0;
                long lines = 0;
                try (BufferedReader reader = Files.newBufferedReader(
                        fileToRead.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines++;
                        estimatedBytesRead += line.getBytes(StandardCharsets.UTF_8).length + 1L;
                        setProgress((int) Math.min(99, estimatedBytesRead * 100 / fileSize));
                    }
                }
                setProgress(100);
                return lines;
            }

            @Override
            protected void done() {
                try {
                    resultLabel.setText("Số dòng: " + get());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    resultLabel.setText("Tác vụ đã bị gián đoạn.");
                } catch (ExecutionException ex) {
                    resultLabel.setText("Không thể đọc file.");
                    JOptionPane.showMessageDialog(FileLineCounterFrame.this,
                            "Lỗi đọc file: " + ex.getCause().getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    chooseButton.setEnabled(true);
                    countButton.setEnabled(true);
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
}
