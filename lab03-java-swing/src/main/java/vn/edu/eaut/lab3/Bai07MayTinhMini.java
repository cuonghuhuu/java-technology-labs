package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai07MayTinhMini extends JFrame {

    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();

    private final JTextField txtResult = new JTextField();

    private final JTextArea txtHistory = new JTextArea(8, 35);

    public Bai07MayTinhMini() {

        setTitle("Bài 7 - Máy tính mini");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // =========================
        // KHU VỰC NHẬP DỮ LIỆU
        // =========================
        JPanel inputPanel = new JPanel(
                new GridLayout(3, 2, 8, 8)
        );

        inputPanel.add(new JLabel("Số thứ nhất:"));
        inputPanel.add(txtA);

        inputPanel.add(new JLabel("Số thứ hai:"));
        inputPanel.add(txtB);

        inputPanel.add(new JLabel("Kết quả:"));

        // Không cho sửa ô kết quả
        txtResult.setEditable(false);

        inputPanel.add(txtResult);

        // =========================
        // CÁC NÚT PHÉP TÍNH
        // =========================
        JPanel buttonPanel = new JPanel(
                new FlowLayout()
        );

        JButton btnCong = new JButton("Cộng");
        JButton btnTru = new JButton("Trừ");
        JButton btnNhan = new JButton("Nhân");
        JButton btnChia = new JButton("Chia");
        JButton btnClear = new JButton("Clear");

        buttonPanel.add(btnCong);
        buttonPanel.add(btnTru);
        buttonPanel.add(btnNhan);
        buttonPanel.add(btnChia);
        buttonPanel.add(btnClear);

        // =========================
        // LỊCH SỬ
        // =========================
        txtHistory.setEditable(false);

        txtHistory.setLineWrap(true);
        txtHistory.setWrapStyleWord(true);

        JScrollPane scrollPane =
                new JScrollPane(txtHistory);

        JPanel historyPanel =
                new JPanel(new BorderLayout());

        historyPanel.add(
                new JLabel("Lịch sử phép tính:"),
                BorderLayout.NORTH
        );

        historyPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // GỘP PHẦN TRÊN
        // =========================
        JPanel topPanel =
                new JPanel(new BorderLayout(10, 10));

        topPanel.add(
                inputPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // Đưa vào JFrame
        add(topPanel, BorderLayout.NORTH);
        add(historyPanel, BorderLayout.CENTER);

        // =========================
        // XỬ LÝ SỰ KIỆN
        // =========================

        btnCong.addActionListener(
                e -> tinhToan("+")
        );

        btnTru.addActionListener(
                e -> tinhToan("-")
        );

        btnNhan.addActionListener(
                e -> tinhToan("*")
        );

        btnChia.addActionListener(
                e -> tinhToan("/")
        );

        btnClear.addActionListener(
                e -> lamMoi()
        );

        setSize(520, 400);

        setLocationRelativeTo(null);
    }

    private void tinhToan(String phepTinh) {

        try {

            double a = Double.parseDouble(
                    txtA.getText().trim()
            );

            double b = Double.parseDouble(
                    txtB.getText().trim()
            );

            double ketQua;

            switch (phepTinh) {

                case "+":
                    ketQua = a + b;
                    break;

                case "-":
                    ketQua = a - b;
                    break;

                case "*":
                    ketQua = a * b;
                    break;

                case "/":

                    // Kiểm tra chia cho 0
                    if (Math.abs(b) < 1e-9) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Không thể chia cho 0!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );

                        return;
                    }

                    ketQua = a / b;
                    break;

                default:
                    return;
            }

            // Hiển thị kết quả
            txtResult.setText(
                    String.valueOf(ketQua)
            );

            // Thêm vào lịch sử
            String lichSu =
                    a + " "
                    + phepTinh + " "
                    + b + " = "
                    + ketQua;

            txtHistory.append(
                    lichSu + "\n"
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập hai số hợp lệ!",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void lamMoi() {

        txtA.setText("");
        txtB.setText("");
        txtResult.setText("");

        // Xóa cả lịch sử
        txtHistory.setText("");

        txtA.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new Bai07MayTinhMini()
                        .setVisible(true)
        );
    }
}