package view;

import controller.KurirController;
import java.awt.*;
import javax.swing.*;

public class RegisterView extends JFrame {

    private KurirController controller;

    public RegisterView() {
        controller = new KurirController();
        setTitle("Registrasi Kurir");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel headerLabel = new JLabel("Formulir Registrasi Kurir");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Input data
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelNama = new JLabel("Nama:");
        JTextField fieldNama = new JTextField(20);

        JLabel labelEmail = new JLabel("Email:");
        JTextField fieldEmail = new JTextField(20);

        JLabel labelPassword = new JLabel("Password:");
        JPasswordField fieldPassword = new JPasswordField(20);

        JLabel labelKTP = new JLabel("Nomor KTP:");
        JTextField fieldKTP = new JTextField(20);

        JLabel labelKK = new JLabel("Nomor KK:");
        JTextField fieldKK = new JTextField(20);

        JButton btnRegister = new JButton("Daftar");
        btnRegister.setBackground(new Color(70, 130, 180));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));

        // Action listener untuk tombol registrasi
        btnRegister.addActionListener(e -> {
            String nama = fieldNama.getText().trim();
            String email = fieldEmail.getText().trim();
            String password = new String(fieldPassword.getPassword()).trim();
            String ktp = fieldKTP.getText().trim();
            String kk = fieldKK.getText().trim();

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || ktp.isEmpty() || kk.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                boolean success = controller.registerKurir(nama, email, password, kk, ktp);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Registrasi Berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    new LoginView().setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Registrasi Gagal", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat proses registrasi: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(labelNama, gbc);

        gbc.gridx = 1;
        formPanel.add(fieldNama, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(labelEmail, gbc);

        gbc.gridx = 1;
        formPanel.add(fieldEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(labelPassword, gbc);

        gbc.gridx = 1;
        formPanel.add(fieldPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(labelKTP, gbc);

        gbc.gridx = 1;
        formPanel.add(fieldKTP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(labelKK, gbc);

        gbc.gridx = 1;
        formPanel.add(fieldKK, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(btnRegister, gbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterView().setVisible(true));
    }
}
