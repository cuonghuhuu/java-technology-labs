package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai01HelloSwing extends JFrame {

    private final JTextField txtName = new JTextField(20);

    public Bai01HelloSwing() {

        // Tiêu đề cửa sổ
        setTitle("Bài 1 - Chào người dùng");

        // Đóng cửa sổ thì kết thúc chương trình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Bố trí các thành phần theo hàng
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        // Nhãn nhập tên
        add(new JLabel("Nhập tên:"));

        // Ô nhập tên
        add(txtName);

        // Nút hiển thị lời chào
        JButton btnHello = new JButton("Hiển thị lời chào");
        add(btnHello);

        // Xử lý sự kiện khi nhấn nút
        btnHello.addActionListener(e -> hienThiLoiChao());

        // Tự điều chỉnh kích thước cửa sổ
        pack();

        // Hiển thị cửa sổ ở giữa màn hình
        setLocationRelativeTo(null);
    }

    private void hienThiLoiChao() {

        String name = txtName.getText().trim();

        // Kiểm tra người dùng đã nhập tên chưa
        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập tên!"
            );

            txtName.requestFocus();
            return;
        }

        // Hiển thị lời chào
        JOptionPane.showMessageDialog(
                this,
                "Xin chào, " + name + "!"
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new Bai01HelloSwing().setVisible(true)
        );
    }
}