package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai03PhuongTrinhBacNhat extends JFrame {

    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();

    private final JLabel lblResult = new JLabel("Nghiệm: ");

    public Bai03PhuongTrinhBacNhat() {

        setTitle("Bài 3 - Giải phương trình ax + b = 0");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // Khu vực nhập a và b
        JPanel inputPanel = new JPanel(
                new GridLayout(2, 2, 8, 8)
        );

        inputPanel.add(new JLabel("Hệ số a:"));
        inputPanel.add(txtA);

        inputPanel.add(new JLabel("Hệ số b:"));
        inputPanel.add(txtB);

        // Nút giải phương trình
        JButton btnSolve = new JButton("Giải phương trình");

        btnSolve.addActionListener(
                e -> giaiPhuongTrinh()
        );

        // Đưa các thành phần vào cửa sổ
        add(lblResult, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(btnSolve, BorderLayout.SOUTH);

        setSize(420, 190);

        setLocationRelativeTo(null);
    }

    private void giaiPhuongTrinh() {

        try {

            double a = Double.parseDouble(
                    txtA.getText().trim()
            );

            double b = Double.parseDouble(
                    txtB.getText().trim()
            );

            final double EPS = 1e-9;

            // a = 0 và b = 0
            if (Math.abs(a) < EPS &&
                    Math.abs(b) < EPS) {

                lblResult.setText(
                        "Nghiệm: phương trình có vô số nghiệm"
                );
            }

            // a = 0 nhưng b khác 0
            else if (Math.abs(a) < EPS) {

                lblResult.setText(
                        "Nghiệm: phương trình vô nghiệm"
                );
            }

            // a khác 0
            else {

                double x = -b / a;

                lblResult.setText(
                        String.format(
                                "Nghiệm: x = %.4f",
                                x
                        )
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập a, b là số hợp lệ!"
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new Bai03PhuongTrinhBacNhat()
                        .setVisible(true)
        );
    }
}