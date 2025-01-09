package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.User;
import controller.KurirController;
import controller.UserDocumentController;
import org.apache.ibatis.session.SqlSessionFactory;

public class DashboardView extends JFrame {

    private final SqlSessionFactory sqlSessionFactory;
    private final User user;

    public DashboardView(User user, SqlSessionFactory sqlSessionFactory) {
        this.user = user;
        this.sqlSessionFactory = sqlSessionFactory;
        setTitle("Dashboard Kurir");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Create the welcome label
        JLabel welcomeLabel = new JLabel("Selamat datang, " + user.getEmail() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        String[] columnNames = {" ", " "};
        String[][] data = {
            {"Email", user.getEmail()},
            {"KK", user.getKk()},
            {"KTP", user.getKtp()}
        };
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable userTable = new JTable(tableModel);
        userTable.setEnabled(false);
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel passwordPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel oldPasswordLabel = new JLabel("Password Lama:");
        JPasswordField oldPasswordField = new JPasswordField();

        JLabel newPasswordLabel = new JLabel("Password Baru:");
        JPasswordField newPasswordField = new JPasswordField();

        JButton changePasswordButton = new JButton("Ganti Password");
        changePasswordButton.setBackground(new Color(70, 130, 180));
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        passwordPanel.add(oldPasswordLabel);
        passwordPanel.add(oldPasswordField);
        passwordPanel.add(newPasswordLabel);
        passwordPanel.add(newPasswordField);
        passwordPanel.add(new JLabel());
        passwordPanel.add(changePasswordButton);

        changePasswordButton.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());

            KurirController controller = new KurirController();
            if (controller.loginKurir(user.getEmail(), oldPassword) != null) {
                if (controller.resetPasswordByEmail(user.getEmail(), newPassword)) {
                    JOptionPane.showMessageDialog(this, "Password berhasil diganti.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengganti password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Password lama tidak sesuai.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnDocument = new JButton("Dokumen User");
        btnDocument.setFont(new Font("Arial", Font.BOLD, 16));
        btnDocument.setBackground(new Color(70, 130, 180));
        btnDocument.setForeground(Color.WHITE);
        btnDocument.setPreferredSize(new Dimension(200, 40));
        btnDocument.setFocusPainted(false);
        btnDocument.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnDocument.addActionListener(e -> {
            UserDocumentController controller = new UserDocumentController(sqlSessionFactory, user);
            this.dispose();
        });

        JButton btnOrder = new JButton("Halaman Order");
        btnOrder.setFont(new Font("Arial", Font.BOLD, 16));
        btnOrder.setBackground(new Color(70, 130, 180));
        btnOrder.setForeground(Color.WHITE);
        btnOrder.setPreferredSize(new Dimension(200, 40));
        btnOrder.setFocusPainted(false);
        btnOrder.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnOrder.addActionListener(e -> {
            new OrderView(user, sqlSessionFactory).setVisible(true);
            this.dispose();
        });

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogout.setBackground(new Color(220, 20, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setPreferredSize(new Dimension(200, 40));
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLogout.addActionListener(e -> {
            new LoginView().setVisible(true);
            this.dispose();
        });

        JButton btnDeleteAccount = new JButton("Hapus Akun");
        btnDeleteAccount.setFont(new Font("Arial", Font.BOLD, 16));
        btnDeleteAccount.setBackground(new Color(139, 0, 0));
        btnDeleteAccount.setForeground(Color.WHITE);
        btnDeleteAccount.setPreferredSize(new Dimension(200, 40));
        btnDeleteAccount.setFocusPainted(false);
        btnDeleteAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnDeleteAccount.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus akun ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                KurirController controller = new KurirController();
                if (controller.deleteAccount(user.getEmail())) {
                    JOptionPane.showMessageDialog(this, "Akun berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    new LoginView().setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus akun.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.add(btnOrder);
        topPanel.add(btnDocument);  // Both buttons in one panel

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(245, 245, 245));
        bottomPanel.add(btnLogout);
        bottomPanel.add(btnDeleteAccount);

        JPanel bottomScrollablePanel = new JPanel();
        bottomScrollablePanel.setLayout(new BoxLayout(bottomScrollablePanel, BoxLayout.Y_AXIS));
        bottomScrollablePanel.add(passwordPanel);
        bottomScrollablePanel.add(bottomPanel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(welcomeLabel);
        centerPanel.add(tableScrollPane);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomScrollablePanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
