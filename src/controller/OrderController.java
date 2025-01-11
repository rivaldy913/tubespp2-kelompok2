package controller;

import java.util.List;
import model.Order;
import mapper.OrderMapper;

public class OrderController {

    private final OrderMapper orderMapper;

    public OrderController(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    // Mendapatkan semua data order
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    // Menambahkan order baru
    public void addOrder(String nama, String alamatPenjemputan, String alamatPengantaran, double beratBarang,
            String jenisBarang) {
        Order order = new Order();
        order.setNama(nama);
        order.setAlamatPenjemputan(alamatPenjemputan);
        order.setAlamatPengantaran(alamatPengantaran);
        order.setBeratBarang(beratBarang);
        order.setJenisBarang(jenisBarang);
        orderMapper.insertOrder(order);
    }

    // Memperbarui data order berdasarkan ID
    public void updateOrder(int id, String nama, String alamatPenjemputan, String alamatPengantaran, double beratBarang,
            String jenisBarang) {
        Order order = new Order();
        order.setId(id);
        order.setNama(nama);
        order.setAlamatPenjemputan(alamatPenjemputan);
        order.setAlamatPengantaran(alamatPengantaran);
        order.setBeratBarang(beratBarang);
        order.setJenisBarang(jenisBarang);
        orderMapper.updateOrder(order);
    }

    // Menghapus data order berdasarkan ID
    public void deleteOrder(int id) {
        orderMapper.deleteOrder(id);
    }
}
