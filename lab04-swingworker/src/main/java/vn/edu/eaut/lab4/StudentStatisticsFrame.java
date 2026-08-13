package vn.edu.eaut.lab4;

import vn.edu.eaut.lab4.model.Student;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StudentStatisticsFrame extends JFrame {
    private final JButton chooseButton = new JButton("Chọn CSV");
    private final JButton readButton = new JButton("Đọc dữ liệu");
    private final JLabel fileLabel = new JLabel("Chưa chọn file");
    private final JLabel averageLabel = new JLabel("Điểm trung bình: --");
    private final JLabel highestLabel = new JLabel("Sinh viên điểm cao nhất: --");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Mã sinh viên", "Họ tên", "Điểm"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private File selectedFile;

    public StudentStatisticsFrame() {
        setTitle("Bài 8 - Thống kê điểm sinh viên");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(720, 480);
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(12, 15, 0, 15));
        JLabel title = new JLabel("ĐỌC CSV ĐIỂM SINH VIÊN VÀ THỐNG KÊ", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        top.add(title, BorderLayout.NORTH);
        JPanel controls = new JPanel(new BorderLayout(8, 8));
        JPanel buttons = new JPanel(new GridLayout(1, 2, 8, 8));
        buttons.add(chooseButton);
        buttons.add(readButton);
        controls.add(buttons, BorderLayout.WEST);
        controls.add(fileLabel, BorderLayout.CENTER);
        top.add(controls, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách sinh viên"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(3, 1, 5, 5));
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 15, 12, 15));
        progressBar.setStringPainted(true);
        bottom.add(progressBar);
        bottom.add(averageLabel);
        bottom.add(highestLabel);
        add(bottom, BorderLayout.SOUTH);

        chooseButton.addActionListener(event -> chooseCsv());
        readButton.addActionListener(event -> readStudents());
        setLocationRelativeTo(null);
    }

    private void chooseCsv() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn file CSV sinh viên");
        chooser.setFileFilter(new FileNameExtensionFilter("File CSV (*.csv)", "csv"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            fileLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    private void readStudents() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file CSV trước.",
                    "Chưa chọn file", JOptionPane.WARNING_MESSAGE);
            return;
        }

        chooseButton.setEnabled(false);
        readButton.setEnabled(false);
        progressBar.setValue(0);
        progressBar.setIndeterminate(true);
        averageLabel.setText("Đang đọc dữ liệu...");
        highestLabel.setText("Sinh viên điểm cao nhất: --");
        File fileToRead = selectedFile;

        SwingWorker<List<Student>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Student> doInBackground() throws IOException {
                List<Student> students = new ArrayList<>();
                try (BufferedReader reader = Files.newBufferedReader(
                        fileToRead.toPath(), StandardCharsets.UTF_8)) {
                    String line = reader.readLine(); // Bỏ qua header.
                    int lineNumber = 1;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        if (line.isBlank()) {
                            continue;
                        }
                        String[] parts = line.split(",", 3);
                        if (parts.length != 3) {
                            throw new IOException("Dòng " + lineNumber + " không đúng định dạng CSV.");
                        }
                        try {
                            students.add(new Student(parts[0].trim(), parts[1].trim(),
                                    Double.parseDouble(parts[2].trim())));
                        } catch (NumberFormatException ex) {
                            throw new IOException("Điểm ở dòng " + lineNumber + " không hợp lệ.", ex);
                        }
                    }
                }
                return students;
            }

            @Override
            protected void done() {
                try {
                    List<Student> students = get();
                    showStatistics(students);
                    progressBar.setValue(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    averageLabel.setText("Tác vụ đã bị gián đoạn.");
                } catch (ExecutionException ex) {
                    averageLabel.setText("Không thể đọc dữ liệu.");
                    JOptionPane.showMessageDialog(StudentStatisticsFrame.this,
                            "Lỗi CSV: " + ex.getCause().getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    progressBar.setIndeterminate(false);
                    chooseButton.setEnabled(true);
                    readButton.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private void showStatistics(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student student : students) {
            tableModel.addRow(new Object[]{student.getId(), student.getFullName(), student.getScore()});
        }

        if (students.isEmpty()) {
            averageLabel.setText("Điểm trung bình: Không có dữ liệu");
            highestLabel.setText("Sinh viên điểm cao nhất: Không có dữ liệu");
            return;
        }

        double total = 0;
        Student highest = students.get(0);
        for (Student student : students) {
            total += student.getScore();
            if (student.getScore() > highest.getScore()) {
                highest = student;
            }
        }
        averageLabel.setText(String.format("Điểm trung bình: %.2f", total / students.size()));
        highestLabel.setText(String.format("Sinh viên điểm cao nhất: %s - %s (%.2f)",
                highest.getId(), highest.getFullName(), highest.getScore()));
    }
}
