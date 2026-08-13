package vn.edu.eaut.lab4;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class Lab4MenuFrame extends JFrame {
    public Lab4MenuFrame() {
        setTitle("Lab 4 - SwingWorker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 600);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("LAB 4 - SWINGWORKER", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 22f));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(18, 10, 8, 10));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(10, 1, 8, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 25, 30));

        addButton(buttonPanel, "Bài 1 - Đồng hồ đếm ngược", () -> new CountdownFrame().setVisible(true));
        addButton(buttonPanel, "Bài 2 - Mô phỏng tải dữ liệu", () -> new ProgressDemoFrame().setVisible(true));
        addButton(buttonPanel, "Bài 3 - Tổng các số nguyên tố", () -> new PrimeSumFrame().setVisible(true));
        addButton(buttonPanel, "Bài 4 - Fibonacci", () -> new FibonacciFrame().setVisible(true));
        addButton(buttonPanel, "Bài 5 - Đếm dòng file", () -> new FileLineCounterFrame().setVisible(true));
        addButton(buttonPanel, "Bài 6 - Hủy tác vụ", () -> new CancelTaskFrame().setVisible(true));
        addButton(buttonPanel, "Bài 7 - Tìm từ khóa trong file", () -> new KeywordSearchFrame().setVisible(true));
        addButton(buttonPanel, "Bài 8 - Thống kê điểm sinh viên", () -> new StudentStatisticsFrame().setVisible(true));
        addButton(buttonPanel, "Bài 9 - Tải danh sách sản phẩm", () -> new ProductLoaderFrame().setVisible(true));
        addButton(buttonPanel, "Bài 10 - Quản lý sản phẩm CSV", () -> new ProductManagerFrame().setVisible(true));

        add(buttonPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private void addButton(JPanel panel, String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(event -> action.run());
        panel.add(button);
    }
}
