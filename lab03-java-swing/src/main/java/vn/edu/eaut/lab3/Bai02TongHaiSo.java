package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai02TongHaiSo extends JFrame {

    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();

    private final JLabel lblResult = new JLabel("Kết quả: ");

    public Bai02TongHaiSo() {

        // Tiêu đề cửa sổ
        setTitle("Bài 2 - Tính tổng hai số");

        // Đóng cửa sổ thì kết thúc chương trình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Chia giao diện thành 4 hàng, 2 cột
        setLayout(new GridLayout(4, 2, 8, 8));

        // Số thứ nhất
        add(new JLabel("Số thứ nhất:"));
        add(txtA);

        // Số thứ hai
        add(new JLabel("Số thứ hai:"));
        add(txtB);

        // Hai nút chức năng
        JButton btnSum = new JButton("Tính tổng");
        JButton btnClear = new JButton("Làm mới");

        add(btnSum);
        add(btnClear);

        // Kết quả
        add(new JLabel(""));
        add(lblResult);

        // Sự kiện tính tổng
        btnSum.addActionListener(e -> tinhTong());

        // Sự kiện làm mới
        btnClear.addActionListener(e -> lamMoi());

        // Kích thước cửa sổ
        setSize(380, 190);

        // Hiển thị giữa màn hình
        setLocationRelativeTo(null);
    }

    private void tinhTong() {

        try {

            double a = Double.parseDouble(txtA.getText().trim());
            double b = Double.parseDouble(txtB.getText().trim());

            double tong = a + b;

            lblResult.setText("Kết quả: " + tong);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Dữ liệu nhập phải là số hợp lệ!"
            );
        }
    }

    private void lamMoi() {

        txtA.setText("");
        txtB.setText("");

        lblResult.setText("Kết quả: ");

        txtA.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new Bai02TongHaiSo().setVisible(true)
        );
    }
}