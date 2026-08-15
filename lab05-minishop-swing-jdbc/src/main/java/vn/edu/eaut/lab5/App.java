package vn.edu.eaut.lab5;
import vn.edu.eaut.lab5.config.DBHelper;import vn.edu.eaut.lab5.ui.LoginFrame;import javax.swing.*;
public class App {public static void main(String[] args){if(!DBHelper.testConnection()){JOptionPane.showMessageDialog(null,"Không thể kết nối MySQL. Vui lòng kiểm tra MySQL Server và biến MINISHOP_DB_PASSWORD.","Lỗi kết nối",JOptionPane.ERROR_MESSAGE);return;}System.out.println("Ket noi MySQL thanh cong!");SwingUtilities.invokeLater(()->new LoginFrame().setVisible(true));}}
