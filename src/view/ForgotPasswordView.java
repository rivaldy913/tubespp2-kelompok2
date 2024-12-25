package view;

import controller.KurirController;
import java.awt.*;
import javax.swing.*;
import model.User;

public class ForgotPasswordView extends JFrame {

    private KurirController controller;

    public ForgotPasswordView() {
        controller = new KurirController();
        setTitle("Lupa Password");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelKTP = new JLabel("Nomor KTP:");
        JTextField fieldKTP = new JTextField(20);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBackground(new Color(70, 130, 180));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 14));

        btnSubmit.addActionListener(e -> {
            String ktp = fieldKTP.getText().trim();
            if (ktp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nomor KTP harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                boolean ktpExists = controller.checkKTPExists(ktp);
                if (ktpExists) {
                    // Buka form untuk reset password
                    new ResetPasswordView(ktp).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "NIK KTP tidak ada di database", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                    new LoginView().setVisible(true);
                    this.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                new LoginView().setVisible(true);
                this.dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(labelKTP, gbc);

        gbc.gridx = 1;
        formPanel.add(fieldKTP, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(btnSubmit, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ForgotPasswordView().setVisible(true));
    }
}
