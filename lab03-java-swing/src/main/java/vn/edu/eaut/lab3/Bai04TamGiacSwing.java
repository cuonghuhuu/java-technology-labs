package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Bai04TamGiacSwing extends JFrame {

    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();
    private final JTextField txtC = new JTextField();

    private final JLabel lblResult = new JLabel("Kết quả: ");

    public Bai04TamGiacSwing() {

        setTitle("Bài 4 - Kiểm tra tam giác");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // Khu vực nhập 3 cạnh
        JPanel panel = new JPanel(
                new GridLayout(3, 2, 8, 8)
        );

        panel.add(new JLabel("Cạnh a:"));
        panel.add(txtA);

        panel.add(new JLabel("Cạnh b:"));
        panel.add(txtB);

        panel.add(new JLabel("Cạnh c:"));
        panel.add(txtC);

        // Nút kiểm tra
        JButton btnCheck = new JButton("Kiểm tra");

        btnCheck.addActionListener(
                e -> kiemTraTamGiac()
        );

        // Đưa các thành phần vào JFrame
        add(lblResult, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(btnCheck, BorderLayout.SOUTH);

        setSize(420, 230);

        setLocationRelativeTo(null);
    }

    private void kiemTraTamGiac() {

        try {

            double a = Double.parseDouble(
                    txtA.getText().trim()
            );

            double b = Double.parseDouble(
                    txtB.getText().trim()
            );

            double c = Double.parseDouble(
                    txtC.getText().trim()
            );

            lblResult.setText(
                    "Kết quả: " + loaiTamGiac(a, b, c)
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ba cạnh phải là số hợp lệ!"
            );
        }
    }

    private String loaiTamGiac(double a, double b, double c) {

        final double EPS = 1e-9;

        // Kiểm tra điều kiện tạo thành tam giác
        if (a <= 0 || b <= 0 || c <= 0
                || a + b <= c
                || a + c <= b
                || b + c <= a) {

            return "Không phải tam giác";
        }

        // Kiểm tra tam giác đều
        boolean deu =
                Math.abs(a - b) < EPS
                && Math.abs(b - c) < EPS;

        // Kiểm tra tam giác cân
        boolean can =
                Math.abs(a - b) < EPS
                || Math.abs(a - c) < EPS
                || Math.abs(b - c) < EPS;

        // Sắp xếp 3 cạnh để kiểm tra định lý Pythagore
        double[] d = {a, b, c};

        Arrays.sort(d);

        boolean vuong =
                Math.abs(
                        d[0] * d[0]
                        + d[1] * d[1]
                        - d[2] * d[2]
                ) < EPS;

        // Phân loại
        if (deu) {
            return "Tam giác đều";
        }

        if (vuong && can) {
            return "Tam giác vuông cân";
        }

        if (vuong) {
            return "Tam giác vuông";
        }

        if (can) {
            return "Tam giác cân";
        }

        return "Tam giác thường";
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new Bai04TamGiacSwing()
                        .setVisible(true)
        );
    }
}