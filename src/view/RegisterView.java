package view;

import controller.KurirController;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import javax.swing.*;

public class RegisterView extends JFrame {

    private KurirController controller;

    public RegisterView() {
        controller = new KurirController();
        setTitle("Registrasi Kurir");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel labelNama = new JLabel("Nama:");
        JTextField fieldNama = new JTextField(20);

        JLabel labelEmail = new JLabel("Email:");
        JTextField fieldEmail = new JTextField(20);

        JLabel labelPassword = new JLabel("Password:");
        JPasswordField fieldPassword = new JPasswordField(20);

        JLabel labelKTP = new JLabel("KTP (file):");
        JButton buttonKTP = new JButton("Pilih KTP");
        JTextField fieldKTP = new JTextField(20);
        fieldKTP.setEditable(false);

        JLabel labelKK = new JLabel("KK (file):");
        JButton buttonKK = new JButton("Pilih KK");
        JTextField fieldKK = new JTextField(20);
        fieldKK.setEditable(false);

        buttonKTP.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                String uploadPath = "uploads/" + file.getName();
                File targetFile = new File(uploadPath);
                try {
                    Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    fieldKTP.setText(uploadPath);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan file KTP: " + ex.getMessage());
                }
            }
        });

        buttonKK.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                String uploadPath = "uploads/" + file.getName();
                File targetFile = new File(uploadPath);
                try {
                    Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    fieldKK.setText(uploadPath);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan file KK: " + ex.getMessage());
                }
            }
        });

        JButton btnRegister = new JButton("Daftar");
        btnRegister.addActionListener(e -> {
            String nama = fieldNama.getText();
            String email = fieldEmail.getText();
            String password = new String(fieldPassword.getPassword());
            String ktp = fieldKTP.getText();
            String kk = fieldKK.getText();

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || ktp.isEmpty() || kk.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            try {
                boolean success = controller.registerKurir(nama, email, password, kk, ktp);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Registrasi Berhasil!");
                    new LoginView().setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Registrasi Gagal! Pastikan email atau data yang dimasukkan benar.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat proses registrasi: " + ex.getMessage());
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelNama, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(fieldNama, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelEmail, gbc);

        gbc.gridx = 1;
        panel.add(fieldEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelPassword, gbc);

        gbc.gridx = 1;
        panel.add(fieldPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelKTP, gbc);

        gbc.gridx = 1;
        panel.add(buttonKTP, gbc);

        gbc.gridx = 2;
        panel.add(fieldKTP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(labelKK, gbc);

        gbc.gridx = 1;
        panel.add(buttonKK, gbc);

        gbc.gridx = 2;
        panel.add(fieldKK, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(btnRegister, gbc);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterView().setVisible(true));
    }
}
