package view;

import controller.KurirController;
import javax.swing.*;
import java.awt.*;

public class ROtpVerificationView extends JFrame {

    private JTextField fieldOtp;
    private KurirController controller; // Inisialisasi controller
    private String email, password, kk, ktp, otp;

    public ROtpVerificationView(String email, String password, String kk, String ktp, String otp) {

        this.email = email;
        this.password = password;
        this.kk = kk;
        this.ktp = ktp;
        this.otp = otp;

        // Menginisialisasi controller di konstruktor
        this.controller = new KurirController();

        setTitle("Verifikasi OTP");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel labelOtp = new JLabel("Masukkan OTP:");
        fieldOtp = new JTextField();
        JButton btnVerify = new JButton("Verifikasi");

        btnVerify.addActionListener(e -> verifyOtp());

        panel.add(labelOtp);
        panel.add(fieldOtp);
        panel.add(btnVerify);

        add(panel);
    }

    private void verifyOtp() {
        String otpInput = fieldOtp.getText().trim();
        if (otpInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "OTP harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!otpInput.equals(otp)) {
            JOptionPane.showMessageDialog(this, "OTP tidak valid", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // OTP valid, lanjutkan ke registrasi kurir
        boolean success = controller.registerKurir(email, password, kk, ktp, otpInput);
        if (success) {
            JOptionPane.showMessageDialog(this, "Registrasi Berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            new LoginView().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registrasi gagal", "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }
}
