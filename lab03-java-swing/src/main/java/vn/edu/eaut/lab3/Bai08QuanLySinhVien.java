package vn.edu.eaut.lab3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Bai08QuanLySinhVien extends JFrame {

    private final JTextField txtMaSV = new JTextField();
    private final JTextField txtHoTen = new JTextField();
    private final JTextField txtDiemTB = new JTextField();

    private final ArrayList<Student> danhSachSinhVien =
            new ArrayList<>();

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{
                            "Mã sinh viên",
                            "Họ tên",
                            "Điểm trung bình",
                            "Xếp loại"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable table = new JTable(tableModel);

    public Bai08QuanLySinhVien() {

        setTitle("Bài 8 - Quản lý sinh viên");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // ==========================
        // FORM NHẬP SINH VIÊN
        // ==========================

        JPanel formPanel =
                new JPanel(new GridLayout(3, 2, 8, 8));

        formPanel.add(new JLabel("Mã sinh viên:"));
        formPanel.add(txtMaSV);

        formPanel.add(new JLabel("Họ tên:"));
        formPanel.add(txtHoTen);

        formPanel.add(new JLabel("Điểm trung bình:"));
        formPanel.add(txtDiemTB);

        // ==========================
        // CÁC NÚT CHỨC NĂNG
        // ==========================

        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        JPanel buttonPanel =
                new JPanel(new FlowLayout());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLamMoi);

        // Gộp form + button
        JPanel topPanel =
                new JPanel(new BorderLayout(10, 10));

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ==========================
        // BẢNG SINH VIÊN
        // ==========================

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ==========================
        // SỰ KIỆN
        // ==========================

        btnThem.addActionListener(
                e -> themSinhVien()
        );

        btnSua.addActionListener(
                e -> suaSinhVien()
        );

        btnXoa.addActionListener(
                e -> xoaSinhVien()
        );

        btnLamMoi.addActionListener(
                e -> lamMoi()
        );

        // Khi chọn một dòng trong JTable
        table.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        hienThiSinhVienDangChon();
                    }
                });

        setSize(700, 450);

        setLocationRelativeTo(null);
    }

    // ==========================
    // THÊM SINH VIÊN
    // ==========================

    private void themSinhVien() {

        try {

            String maSV =
                    txtMaSV.getText().trim();

            String hoTen =
                    txtHoTen.getText().trim();

            String diemText =
                    txtDiemTB.getText().trim();

            // Kiểm tra trống
            if (maSV.isEmpty()
                    || hoTen.isEmpty()
                    || diemText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng nhập đầy đủ thông tin!"
                );

                return;
            }

            // Kiểm tra mã sinh viên trùng
            for (Student sv : danhSachSinhVien) {

                if (sv.getMaSinhVien()
                        .equalsIgnoreCase(maSV)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Mã sinh viên đã tồn tại!"
                    );

                    return;
                }
            }

            double diem =
                    Double.parseDouble(diemText);

            // Kiểm tra điểm
            if (diem < 0 || diem > 10) {

                JOptionPane.showMessageDialog(
                        this,
                        "Điểm trung bình phải từ 0 đến 10!"
                );

                return;
            }

            Student sinhVien =
                    new Student(
                            maSV,
                            hoTen,
                            diem
                    );

            danhSachSinhVien.add(sinhVien);

            capNhatBang();

            lamMoi();

            JOptionPane.showMessageDialog(
                    this,
                    "Thêm sinh viên thành công!"
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Điểm trung bình phải là số hợp lệ!"
            );
        }
    }

    // ==========================
    // SỬA SINH VIÊN
    // ==========================

    private void suaSinhVien() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sinh viên cần sửa!"
            );

            return;
        }

        try {

            String maSV =
                    txtMaSV.getText().trim();

            String hoTen =
                    txtHoTen.getText().trim();

            String diemText =
                    txtDiemTB.getText().trim();

            if (maSV.isEmpty()
                    || hoTen.isEmpty()
                    || diemText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng nhập đầy đủ thông tin!"
                );

                return;
            }

            double diem =
                    Double.parseDouble(diemText);

            if (diem < 0 || diem > 10) {

                JOptionPane.showMessageDialog(
                        this,
                        "Điểm trung bình phải từ 0 đến 10!"
                );

                return;
            }

            Student sinhVien =
                    danhSachSinhVien.get(row);

            sinhVien.setMaSinhVien(maSV);
            sinhVien.setHoTen(hoTen);
            sinhVien.setDiemTrungBinh(diem);

            capNhatBang();

            lamMoi();

            JOptionPane.showMessageDialog(
                    this,
                    "Sửa sinh viên thành công!"
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Điểm trung bình phải là số hợp lệ!"
            );
        }
    }

    // ==========================
    // XÓA SINH VIÊN
    // ==========================

    private void xoaSinhVien() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sinh viên cần xóa!"
            );

            return;
        }

        int luaChon =
                JOptionPane.showConfirmDialog(
                        this,
                        "Bạn có chắc muốn xóa sinh viên này?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION
                );

        if (luaChon == JOptionPane.YES_OPTION) {

            danhSachSinhVien.remove(row);

            capNhatBang();

            lamMoi();

            JOptionPane.showMessageDialog(
                    this,
                    "Xóa sinh viên thành công!"
            );
        }
    }

    // ==========================
    // HIỂN THỊ DÒNG ĐANG CHỌN
    // ==========================

    private void hienThiSinhVienDangChon() {

        int row =
                table.getSelectedRow();

        if (row == -1) {
            return;
        }

        Student sinhVien =
                danhSachSinhVien.get(row);

        txtMaSV.setText(
                sinhVien.getMaSinhVien()
        );

        txtHoTen.setText(
                sinhVien.getHoTen()
        );

        txtDiemTB.setText(
                String.valueOf(
                        sinhVien.getDiemTrungBinh()
                )
        );
    }

    // ==========================
    // CẬP NHẬT JTable
    // ==========================

    private void capNhatBang() {

        // Xóa các dòng cũ
        tableModel.setRowCount(0);

        // Thêm lại dữ liệu
        for (Student sinhVien : danhSachSinhVien) {

            tableModel.addRow(
                    new Object[]{
                            sinhVien.getMaSinhVien(),
                            sinhVien.getHoTen(),
                            sinhVien.getDiemTrungBinh(),
                            sinhVien.getXepLoai()
                    }
            );
        }
    }

    // ==========================
    // LÀM MỚI FORM
    // ==========================

    private void lamMoi() {

        txtMaSV.setText("");
        txtHoTen.setText("");
        txtDiemTB.setText("");

        table.clearSelection();

        txtMaSV.requestFocus();
    }

    // ==========================
    // MAIN
    // ==========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new Bai08QuanLySinhVien()
                        .setVisible(true)
        );
    }
}