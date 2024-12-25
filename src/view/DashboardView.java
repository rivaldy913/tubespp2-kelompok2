package view;

import java.awt.*;
import javax.swing.*;
import model.User;

public class DashboardView extends JFrame {

    public DashboardView(User user) {
        setTitle("Dashboard Kurir");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // label selamat datang
        JLabel welcomeLabel = new JLabel("Selamat datang, " + user.getEmail() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        // tombol ke halaman order
        JButton btnOrder = new JButton("Halaman Order");
        btnOrder.setFont(new Font("Arial", Font.BOLD, 16));
        btnOrder.setBackground(new Color(70, 130, 180));
        btnOrder.setForeground(Color.WHITE);
        btnOrder.setPreferredSize(new Dimension(200, 40));
        btnOrder.setFocusPainted(false);
        btnOrder.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //action listener tombol order
        btnOrder.addActionListener(e -> {
            new OrderView(user).setVisible(true);
            this.dispose();
        });

        // tombol logout
        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogout.setBackground(new Color(220, 20, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setPreferredSize(new Dimension(200, 40));
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //action listener tombol logout
        btnLogout.addActionListener(e -> {
            System.out.println("Logout berhasil!");
            new LoginView().setVisible(true);
            this.dispose();
        });

        buttonPanel.add(btnOrder);
        buttonPanel.add(btnLogout);

        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}
