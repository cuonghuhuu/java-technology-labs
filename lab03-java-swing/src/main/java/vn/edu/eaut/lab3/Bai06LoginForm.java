package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai06LoginForm extends JFrame {

    private final JTextField txtUsername = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();

    private final JComboBox<String> cboRole =
            new JComboBox<>(new String[]{"Admin", "User"});

    private final JCheckBox chkShowPassword =
            new JCheckBox("Hiển thị mật khẩu");

    private final char defaultEchoChar;

    public Bai06LoginForm() {

        setTitle("Bài 6 - Form đăng nhập");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // Lưu ký tự che mật khẩu mặc định
        defaultEchoChar = txtPassword.getEchoChar();

        // Khu vực nhập dữ liệu
        JPanel formPanel = new JPanel(
                new GridLayout(4, 2, 8, 8)
        );

        formPanel.add(new JLabel("Tài khoản:"));
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Mật khẩu:"));
        formPanel.add(txtPassword);

        formPanel.add(new JLabel("Vai trò:"));
        formPanel.add(cboRole);

        formPanel.add(new JLabel(""));
        formPanel.add(chkShowPassword);

        // Nút đăng nhập
        JButton btnLogin = new JButton("Đăng nhập");

        JButton btnClear = new JButton("Làm mới");

        JPanel buttonPanel = new JPanel(
                new FlowLayout()
        );

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnClear);

        // Sự kiện đăng nhập
        btnLogin.addActionListener(
                e -> dangNhap()
        );

        // Sự kiện làm mới
        btnClear.addActionListener(
                e -> lamMoi()
        );

        // Hiển thị / ẩn mật khẩu
        chkShowPassword.addActionListener(
                e -> hienThiMatKhau()
        );

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(420, 250);

        setLocationRelativeTo(null);
    }

    private void dangNhap() {

        String username =
                txtUsername.getText().trim();

        String password =
                new String(txtPassword.getPassword());

        String role =
                cboRole.getSelectedItem().toString();

        // Kiểm tra dữ liệu trống
        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ tài khoản và mật khẩu!"
            );

            return;
        }

        // Kiểm tra tài khoản Admin
        boolean adminLogin =
                username.equals("admin")
                && password.equals("123456")
                && role.equals("Admin");

        // Kiểm tra tài khoản User
        boolean userLogin =
                username.equals("user")
                && password.equals("123456")
                && role.equals("User");

        if (adminLogin || userLogin) {

            JOptionPane.showMessageDialog(
                    this,
                    "Đăng nhập thành công!\n"
                            + "Chào mừng " + username
                            + " - Vai trò: " + role
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Sai tài khoản, mật khẩu hoặc vai trò!",
                    "Đăng nhập thất bại",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void hienThiMatKhau() {

        if (chkShowPassword.isSelected()) {

            // Hiển thị mật khẩu
            txtPassword.setEchoChar((char) 0);

        } else {

            // Ẩn mật khẩu
            txtPassword.setEchoChar(
                    defaultEchoChar
            );
        }
    }

    private void lamMoi() {

        txtUsername.setText("");

        txtPassword.setText("");

        cboRole.setSelectedIndex(0);

        chkShowPassword.setSelected(false);

        txtPassword.setEchoChar(
                defaultEchoChar
        );

        txtUsername.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new Bai06LoginForm()
                        .setVisible(true)
        );
    }
}