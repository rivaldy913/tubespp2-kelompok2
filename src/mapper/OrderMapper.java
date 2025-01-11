package mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import model.Order;

public interface OrderMapper {

    // Mendapatkan semua data order
    @Select("SELECT * FROM orders")
    List<Order> getAllOrders();

    // Menambahkan order baru
    @Insert("INSERT INTO orders (nama, alamat_penjemputan, alamat_pengantaran, berat_barang, jenis_barang) " +
            "VALUES (#{nama}, #{alamatPenjemputan}, #{alamatPengantaran}, #{beratBarang}, #{jenisBarang})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // Mendapatkan ID secara otomatis setelah
                                                                            // insert
    void insertOrder(Order order);

    // Memperbarui order berdasarkan ID
    @Update("UPDATE orders SET nama = #{nama}, alamat_penjemputan = #{alamatPenjemputan}, alamat_pengantaran = #{alamatPengantaran}, "
            +
            "berat_barang = #{beratBarang}, jenis_barang = #{jenisBarang} WHERE id = #{id}")
    void updateOrder(Order order);

    // Menghapus order berdasarkan ID
    @Delete("DELETE FROM orders WHERE id = #{id}")
    void deleteOrder(int id);
}
