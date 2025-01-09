package view;

import controller.KurirController;
import java.awt.*;
import javax.swing.*;

public class RegisterView extends JFrame {

    private KurirController controller;
    private String otp;

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

        // input data
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

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

        // action listener untuk tombol registrasi
        btnRegister.addActionListener(e -> {
            String email = fieldEmail.getText().trim();
            String password = new String(fieldPassword.getPassword()).trim();
            String ktp = fieldKTP.getText().trim();
            String kk = fieldKK.getText().trim();

            if (email.isEmpty() || password.isEmpty() || ktp.isEmpty() || kk.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                otp = controller.generateOTP();
                controller.sendOtpForRegistration(email, otp);

                new ROtpVerificationView(email, password, kk, ktp, otp).setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal mengirim OTP", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        });

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

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(btnRegister, gbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterView().setVisible(true));
    }
}
