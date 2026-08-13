package vn.edu.eaut.lab4;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class KeywordSearchFrame extends JFrame {
    private final JButton chooseButton = new JButton("Chọn file");
    private final JButton searchButton = new JButton("Tìm kiếm");
    private final JTextField keywordField = new JTextField();
    private final JTextArea resultArea = new JTextArea();
    private final JLabel fileLabel = new JLabel("Chưa chọn file");
    private final JLabel countLabel = new JLabel("Tìm thấy 0 dòng");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private File selectedFile;

    public KeywordSearchFrame() {
        setTitle("Bài 7 - Tìm từ khóa trong file");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(720, 500);
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(12, 15, 0, 15));
        JLabel title = new JLabel("TÌM KIẾM TỪ KHÓA TRONG FILE VĂN BẢN");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        top.add(title, BorderLayout.NORTH);

        JPanel controls = new JPanel(new GridLayout(2, 1, 8, 8));
        JPanel fileRow = new JPanel(new BorderLayout(8, 8));
        fileRow.add(chooseButton, BorderLayout.WEST);
        fileRow.add(fileLabel, BorderLayout.CENTER);
        JPanel searchRow = new JPanel(new BorderLayout(8, 8));
        searchRow.add(new JLabel("Từ khóa:"), BorderLayout.WEST);
        searchRow.add(keywordField, BorderLayout.CENTER);
        searchRow.add(searchButton, BorderLayout.EAST);
        controls.add(fileRow);
        controls.add(searchRow);
        top.add(controls, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Các dòng chứa từ khóa"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(2, 1, 5, 5));
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 15, 12, 15));
        progressBar.setStringPainted(true);
        bottom.add(progressBar);
        bottom.add(countLabel);
        add(bottom, BorderLayout.SOUTH);

        chooseButton.addActionListener(event -> chooseFile());
        searchButton.addActionListener(event -> searchKeyword());
        setLocationRelativeTo(null);
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn file văn bản");
        chooser.setFileFilter(new FileNameExtensionFilter("File văn bản (*.txt)", "txt"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            fileLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    private void searchKeyword() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file văn bản trước.",
                    "Chưa chọn file", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String keyword = keywordField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Từ khóa không được để trống.",
                    "Thiếu từ khóa", JOptionPane.WARNING_MESSAGE);
            return;
        }

        chooseButton.setEnabled(false);
        searchButton.setEnabled(false);
        progressBar.setValue(0);
        resultArea.setText("");
        countLabel.setText("Đang tìm kiếm...");
        File fileToRead = selectedFile;
        String lowerKeyword = keyword.toLowerCase(Locale.ROOT);

        SwingWorker<Integer, String> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() throws IOException {
                long fileSize = Math.max(1, Files.size(fileToRead.toPath()));
                long estimatedBytesRead = 0;
                int lineNumber = 0;
                int found = 0;
                try (BufferedReader reader = Files.newBufferedReader(
                        fileToRead.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        if (line.toLowerCase(Locale.ROOT).contains(lowerKeyword)) {
                            found++;
                            publish("Dòng " + lineNumber + ": " + line + System.lineSeparator());
                        }
                        estimatedBytesRead += line.getBytes(StandardCharsets.UTF_8).length + 1L;
                        setProgress((int) Math.min(99, estimatedBytesRead * 100 / fileSize));
                    }
                }
                setProgress(100);
                return found;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String result : chunks) {
                    resultArea.append(result);
                }
            }

            @Override
            protected void done() {
                try {
                    countLabel.setText("Tìm thấy " + get() + " dòng");
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    countLabel.setText("Tác vụ đã bị gián đoạn.");
                } catch (ExecutionException ex) {
                    countLabel.setText("Không thể tìm kiếm trong file.");
                    JOptionPane.showMessageDialog(KeywordSearchFrame.this,
                            "Lỗi đọc file: " + ex.getCause().getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    chooseButton.setEnabled(true);
                    searchButton.setEnabled(true);
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
