package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import model.User;
import org.apache.ibatis.session.SqlSessionFactory;

public class OrderView extends JFrame {
    private JTextField txtNamaKurir, txtAlamatPenjemputan, txtAlamatPengantaran, txtBeratBarang, txtJenisBarang;
    private JTable table;
    private DefaultTableModel tableModel;

    private Connection connection;
    private final User user;
    private final SqlSessionFactory sqlSessionFactory;

    public OrderView(User user, SqlSessionFactory sqlSessionFactory) {
        this.user = user;
        this.sqlSessionFactory = sqlSessionFactory;
        initialize();
        connectDatabase();
        loadData();
    }

    private void initialize() {
        setTitle("Halaman Order");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(245, 245, 245));

        JButton btnBack = new JButton("Kembali ke Dashboard");
        btnBack.setBackground(new Color(70, 130, 180));
        btnBack.setForeground(Color.WHITE);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> {
            new DashboardView(user, sqlSessionFactory).setVisible(true);
            dispose();
        });

        JLabel lblTitle = new JLabel("Order Management");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel.add(btnBack);
        topPanel.add(lblTitle);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(new Color(245, 245, 245));

        formPanel.add(new JLabel("Nama Kurir:"));
        txtNamaKurir = new JTextField();
        formPanel.add(txtNamaKurir);

        formPanel.add(new JLabel("Alamat Penjemputan:"));
        txtAlamatPenjemputan = new JTextField();
        formPanel.add(txtAlamatPenjemputan);

        formPanel.add(new JLabel("Alamat Pengantaran:"));
        txtAlamatPengantaran = new JTextField();
        formPanel.add(txtAlamatPengantaran);

        formPanel.add(new JLabel("Berat Barang (kg):"));
        txtBeratBarang = new JTextField();
        formPanel.add(txtBeratBarang);

        formPanel.add(new JLabel("Jenis Barang:"));
        txtJenisBarang = new JTextField();
        formPanel.add(txtJenisBarang);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton btnAdd = new JButton("Add Order");
        JButton btnUpdate = new JButton("Update Order");
        JButton btnDelete = new JButton("Delete Order");
        JButton btnRefresh = new JButton("Refresh");

        btnAdd.setBackground(new Color(34, 139, 34));
        btnAdd.setForeground(Color.WHITE);
        btnUpdate.setBackground(new Color(255, 140, 0));
        btnUpdate.setForeground(Color.WHITE);
        btnDelete.setBackground(new Color(220, 20, 60));
        btnDelete.setForeground(Color.WHITE);
        btnRefresh.setBackground(new Color(70, 130, 180));
        btnRefresh.setForeground(Color.WHITE);

        btnAdd.addActionListener(e -> addOrder());
        btnUpdate.addActionListener(e -> updateOrder());
        btnDelete.addActionListener(e -> deleteOrder());
        btnRefresh.addActionListener(e -> loadData());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Nama", "Penjemputan", "Pengantaran", "Berat Barang", "Jenis Barang" }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void connectDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tubespp2", "root", "");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM orders")) {

            while (resultSet.next()) {
                tableModel.addRow(new Object[] {
                        resultSet.getInt("id"),
                        resultSet.getString("nama"),
                        resultSet.getString("alamat_penjemputan"),
                        resultSet.getString("alamat_pengantaran"),
                        resultSet.getDouble("berat_barang"),
                        resultSet.getString("jenis_barang")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load data: " + e.getMessage());
        }
    }

    private void addOrder() {
        String nama = txtNamaKurir.getText();
        String penjemputan = txtAlamatPenjemputan.getText();
        String pengantaran = txtAlamatPengantaran.getText();
        String berat = txtBeratBarang.getText();
        String jenis = txtJenisBarang.getText();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO orders (nama, alamat_penjemputan, alamat_pengantaran, berat_barang, jenis_barang) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, nama);
            preparedStatement.setString(2, penjemputan);
            preparedStatement.setString(3, pengantaran);
            preparedStatement.setDouble(4, Double.parseDouble(berat));
            preparedStatement.setString(5, jenis);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Order added successfully!");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to add order: " + e.getMessage());
        }
    }

    private void updateOrder() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to update.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nama = txtNamaKurir.getText();
        String penjemputan = txtAlamatPenjemputan.getText();
        String pengantaran = txtAlamatPengantaran.getText();
        String berat = txtBeratBarang.getText();
        String jenis = txtJenisBarang.getText();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE orders SET nama = ?, alamat_penjemputan = ?, alamat_pengantaran = ?, berat_barang = ?, jenis_barang = ? WHERE id = ?")) {

            preparedStatement.setString(1, nama);
            preparedStatement.setString(2, penjemputan);
            preparedStatement.setString(3, pengantaran);
            preparedStatement.setDouble(4, Double.parseDouble(berat));
            preparedStatement.setString(5, jenis);
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Order updated successfully!");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to update order: " + e.getMessage());
        }
    }

    private void deleteOrder() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM orders WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Order deleted successfully!");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to delete order: " + e.getMessage());
        }
    }
}
