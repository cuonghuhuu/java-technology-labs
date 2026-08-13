package vn.edu.eaut.lab4;

import vn.edu.eaut.lab4.model.Product;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductManagerFrame extends JFrame {
    private final JTextField idField = new JTextField(20);
    private final JTextField nameField = new JTextField(20);
    private final JTextField priceField = new JTextField(20);
    private final JButton addButton = new JButton("Thêm");
    private final JButton updateButton = new JButton("Cập nhật");
    private final JButton deleteButton = new JButton("Xóa");
    private final JButton readButton = new JButton("Đọc CSV");
    private final JButton saveButton = new JButton("Lưu CSV");
    private final JLabel statusLabel = new JLabel("Sẵn sàng");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final List<Product> products = new ArrayList<>();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Mã SP", "Tên sản phẩm", "Đơn giá"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable productTable = new JTable(tableModel);

    public ProductManagerFrame() {
        setTitle("Bài 10 - Quản lý sản phẩm CSV");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 590);
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBorder(BorderFactory.createEmptyBorder(12, 15, 0, 15));
        JLabel title = new JLabel("MINI PROJECT QUẢN LÝ SẢN PHẨM CSV", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        top.add(title, BorderLayout.NORTH);
        top.add(createInputPanel(), BorderLayout.CENTER);
        top.add(createButtonPanel(), BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        productTable.setFillsViewportHeight(true);
        productTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(2, 1, 5, 5));
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 15, 12, 15));
        progressBar.setStringPainted(true);
        bottom.add(progressBar);
        bottom.add(statusLabel);
        add(bottom, BorderLayout.SOUTH);

        addButton.addActionListener(event -> addProduct());
        updateButton.addActionListener(event -> updateProduct());
        deleteButton.addActionListener(event -> deleteProduct());
        readButton.addActionListener(event -> chooseAndReadCsv());
        saveButton.addActionListener(event -> chooseAndSaveCsv());
        productTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                copySelectedRowToForm();
            }
        });

        setLocationRelativeTo(null);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 8, 5, 8);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        panel.add(new JLabel("Mã sản phẩm:"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        panel.add(idField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;
        panel.add(new JLabel("Tên sản phẩm:"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        panel.add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0;
        panel.add(new JLabel("Đơn giá:"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        panel.add(priceField, constraints);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 8, 8));
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(readButton);
        panel.add(saveButton);
        return panel;
    }

    private void addProduct() {
        Product product = readProductFromForm();
        if (product == null) {
            return;
        }
        if (findProductIndex(product.getId(), -1) >= 0) {
            showValidationError("Mã sản phẩm đã tồn tại.");
            return;
        }
        products.add(product);
        refreshTable();
        clearForm();
        statusLabel.setText("Đã thêm sản phẩm " + product.getId());
    }

    private void updateProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            showValidationError("Vui lòng chọn một dòng trước khi cập nhật.");
            return;
        }
        Product newValue = readProductFromForm();
        if (newValue == null) {
            return;
        }
        if (findProductIndex(newValue.getId(), selectedRow) >= 0) {
            showValidationError("Mã sản phẩm đã tồn tại.");
            return;
        }

        Product product = products.get(selectedRow);
        product.setId(newValue.getId());
        product.setName(newValue.getName());
        product.setPrice(newValue.getPrice());
        refreshTable();
        clearForm();
        statusLabel.setText("Đã cập nhật sản phẩm " + product.getId());
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            showValidationError("Vui lòng chọn một dòng trước khi xóa.");
            return;
        }
        Product product = products.get(selectedRow);
        int choice = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa sản phẩm " + product.getId() + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            products.remove(selectedRow);
            refreshTable();
            clearForm();
            statusLabel.setText("Đã xóa sản phẩm " + product.getId());
        }
    }

    private Product readProductFromForm() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        if (id.isEmpty()) {
            showValidationError("Mã sản phẩm không được để trống.");
            return null;
        }
        if (name.isEmpty()) {
            showValidationError("Tên sản phẩm không được để trống.");
            return null;
        }
        if (id.contains(",") || name.contains(",")) {
            showValidationError("Mã và tên sản phẩm không được chứa dấu phẩy.");
            return null;
        }

        try {
            double price = Double.parseDouble(priceField.getText().trim());
            if (!Double.isFinite(price) || price < 0) {
                throw new NumberFormatException();
            }
            return new Product(id, name, price);
        } catch (NumberFormatException ex) {
            showValidationError("Đơn giá phải là số lớn hơn hoặc bằng 0.");
            return null;
        }
    }

    private int findProductIndex(String id, int ignoredIndex) {
        for (int i = 0; i < products.size(); i++) {
            if (i != ignoredIndex && products.get(i).getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    private void copySelectedRowToForm() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < products.size()) {
            Product product = products.get(selectedRow);
            idField.setText(product.getId());
            nameField.setText(product.getName());
            priceField.setText(formatPrice(product.getPrice()));
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getId(), product.getName(), formatPrice(product.getPrice())
            });
        }
    }

    private String formatPrice(double price) {
        if (price == Math.rint(price)) {
            return String.format("%.0f", price);
        }
        return Double.toString(price);
    }

    private void clearForm() {
        productTable.clearSelection();
        idField.setText("");
        nameField.setText("");
        priceField.setText("");
        idField.requestFocusInWindow();
    }

    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Dữ liệu không hợp lệ", JOptionPane.WARNING_MESSAGE);
    }

    private void chooseAndReadCsv() {
        JFileChooser chooser = createCsvChooser("Chọn file CSV sản phẩm");
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        readCsv(chooser.getSelectedFile());
    }

    private void readCsv(File file) {
        setFileButtonsEnabled(false);
        progressBar.setValue(0);
        statusLabel.setText("Đang đọc file " + file.getName() + "...");

        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws IOException {
                List<Product> loaded = new ArrayList<>();
                long fileSize = Math.max(1, Files.size(file.toPath()));
                long estimatedBytesRead = 0;
                try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
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
                        double price;
                        try {
                            price = Double.parseDouble(parts[2].trim());
                            if (!Double.isFinite(price) || price < 0) {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException ex) {
                            throw new IOException("Đơn giá ở dòng " + lineNumber + " không hợp lệ.", ex);
                        }
                        String id = parts[0].trim();
                        String name = parts[1].trim();
                        if (id.isEmpty() || name.isEmpty()) {
                            throw new IOException("Dòng " + lineNumber + " thiếu mã hoặc tên sản phẩm.");
                        }
                        boolean duplicate = loaded.stream()
                                .anyMatch(product -> product.getId().equalsIgnoreCase(id));
                        if (duplicate) {
                            throw new IOException("Mã sản phẩm " + id + " bị trùng trong file.");
                        }
                        loaded.add(new Product(id, name, price));
                        estimatedBytesRead += line.getBytes(StandardCharsets.UTF_8).length + 1L;
                        setProgress((int) Math.min(99, estimatedBytesRead * 100 / fileSize));
                    }
                }
                setProgress(100);
                return loaded;
            }

            @Override
            protected void done() {
                try {
                    List<Product> loaded = get();
                    products.clear();
                    products.addAll(loaded);
                    refreshTable();
                    clearForm();
                    progressBar.setValue(100);
                    statusLabel.setText("Đã đọc " + loaded.size() + " sản phẩm từ " + file.getName());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    statusLabel.setText("Tác vụ đã bị gián đoạn.");
                } catch (ExecutionException ex) {
                    statusLabel.setText("Không thể đọc file CSV.");
                    JOptionPane.showMessageDialog(ProductManagerFrame.this,
                            "Lỗi CSV: " + ex.getCause().getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    setFileButtonsEnabled(true);
                }
            }
        };
        addProgressListener(worker);
        worker.execute();
    }

    private void chooseAndSaveCsv() {
        JFileChooser chooser = createCsvChooser("Chọn nơi lưu file CSV");
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getParentFile(), file.getName() + ".csv");
        }
        if (file.exists()) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "File đã tồn tại. Bạn có muốn ghi đè không?",
                    "Xác nhận ghi đè", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
        }

        List<Product> snapshot = products.stream()
                .map(product -> new Product(product.getId(), product.getName(), product.getPrice()))
                .toList();
        saveCsv(file, snapshot);
    }

    private void saveCsv(File file, List<Product> snapshot) {
        setFileButtonsEnabled(false);
        progressBar.setValue(0);
        statusLabel.setText("Đang lưu file " + file.getName() + "...");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws IOException {
                try (BufferedWriter writer = Files.newBufferedWriter(
                        file.toPath(), StandardCharsets.UTF_8)) {
                    writer.write("MaSP,TenSP,DonGia");
                    writer.newLine();
                    int total = Math.max(1, snapshot.size());
                    for (int i = 0; i < snapshot.size(); i++) {
                        Product product = snapshot.get(i);
                        writer.write(product.getId() + "," + product.getName() + "," + product.getPrice());
                        writer.newLine();
                        setProgress((i + 1) * 100 / total);
                    }
                }
                setProgress(100);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    progressBar.setValue(100);
                    statusLabel.setText("Đã lưu " + snapshot.size() + " sản phẩm vào " + file.getName());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    statusLabel.setText("Tác vụ đã bị gián đoạn.");
                } catch (ExecutionException ex) {
                    statusLabel.setText("Không thể lưu file CSV.");
                    JOptionPane.showMessageDialog(ProductManagerFrame.this,
                            "Lỗi ghi file: " + ex.getCause().getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    setFileButtonsEnabled(true);
                }
            }
        };
        addProgressListener(worker);
        worker.execute();
    }

    private JFileChooser createCsvChooser(String title) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        chooser.setFileFilter(new FileNameExtensionFilter("File CSV (*.csv)", "csv"));
        return chooser;
    }

    private void addProgressListener(SwingWorker<?, ?> worker) {
        worker.addPropertyChangeListener(event -> {
            if ("progress".equals(event.getPropertyName())) {
                progressBar.setValue((Integer) event.getNewValue());
            }
        });
    }

    private void setFileButtonsEnabled(boolean enabled) {
        readButton.setEnabled(enabled);
        saveButton.setEnabled(enabled);
        addButton.setEnabled(enabled);
        updateButton.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
    }
}
