package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Dùng giao diện mặc định nếu hệ thống không hỗ trợ look and feel này.
            }
            new Lab4MenuFrame().setVisible(true);
        });
    }
}
