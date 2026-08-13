package vn.edu.eaut.lab4;

import vn.edu.eaut.lab4.model.Product;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductLoaderFrame extends JFrame {
    private final JButton loadButton = new JButton("Tải sản phẩm");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel statusLabel = new JLabel("Sẵn sàng");
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Mã SP", "Tên sản phẩm", "Đơn giá"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ProductLoaderFrame() {
        setTitle("Bài 9 - Tải danh sách sản phẩm");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 440);
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBorder(BorderFactory.createEmptyBorder(12, 15, 0, 15));
        JLabel title = new JLabel("MÔ PHỎNG TẢI DANH SÁCH SẢN PHẨM", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        top.add(title, BorderLayout.NORTH);
        top.add(loadButton, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(2, 1, 5, 5));
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 15, 12, 15));
        progressBar.setStringPainted(true);
        bottom.add(progressBar);
        bottom.add(statusLabel);
        add(bottom, BorderLayout.SOUTH);

        loadButton.addActionListener(event -> loadProducts());
        setLocationRelativeTo(null);
    }

    private void loadProducts() {
        loadButton.setEnabled(false);
        progressBar.setValue(0);
        statusLabel.setText("Đang tải danh sách sản phẩm...");
        tableModel.setRowCount(0);

        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws InterruptedException {
                List<Product> source = List.of(
                        new Product("SP01", "Bàn phím", 250000),
                        new Product("SP02", "Chuột", 150000),
                        new Product("SP03", "Màn hình", 2500000),
                        new Product("SP04", "Tai nghe", 450000),
                        new Product("SP05", "Webcam", 700000),
                        new Product("SP06", "Loa máy tính", 900000),
                        new Product("SP07", "Ổ cứng SSD", 1800000),
                        new Product("SP08", "USB 64GB", 220000)
                );
                List<Product> loaded = new ArrayList<>();
                for (int i = 0; i < source.size(); i++) {
                    Thread.sleep(500);
                    loaded.add(source.get(i));
                    setProgress((i + 1) * 100 / source.size());
                }
                return loaded;
            }

            @Override
            protected void done() {
                try {
                    for (Product product : get()) {
                        tableModel.addRow(new Object[]{
                                product.getId(), product.getName(), String.format("%.0f", product.getPrice())
                        });
                    }
                    statusLabel.setText("Đã tải danh sách sản phẩm");
                    progressBar.setValue(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    statusLabel.setText("Tác vụ đã bị gián đoạn.");
                } catch (ExecutionException ex) {
                    statusLabel.setText("Không thể tải sản phẩm.");
                    JOptionPane.showMessageDialog(ProductLoaderFrame.this,
                            "Lỗi tải dữ liệu: " + ex.getCause().getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    loadButton.setEnabled(true);
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
