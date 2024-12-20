package view;

import java.awt.*;
import javax.swing.*;

public class LoginView extends JFrame {

    public LoginView() {
        setTitle("Login");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel labelEmail = new JLabel("Email:");
        JTextField fieldEmail = new JTextField();

        JLabel labelPassword = new JLabel("Password:");
        JPasswordField fieldPassword = new JPasswordField();

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            String email = fieldEmail.getText();
            String password = new String(fieldPassword.getPassword());
        });

        panel.add(labelEmail);
        panel.add(fieldEmail);
        panel.add(labelPassword);
        panel.add(fieldPassword);
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
