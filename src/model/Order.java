package model;

// Order.java
public class Order {
    private int id;
    private String nama;
    private String alamatPenjemputan;
    private String alamatPengantaran;
    private double beratBarang;
    private String jenisBarang;

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamatPenjemputan() {
        return alamatPenjemputan;
    }

    public void setAlamatPenjemputan(String alamatPenjemputan) {
        this.alamatPenjemputan = alamatPenjemputan;
    }

    public String getAlamatPengantaran() {
        return alamatPengantaran;
    }

    public void setAlamatPengantaran(String alamatPengantaran) {
        this.alamatPengantaran = alamatPengantaran;
    }

    public double getBeratBarang() {
        return beratBarang;
    }

    public void setBeratBarang(double beratBarang) {
        this.beratBarang = beratBarang;
    }

    public String getJenisBarang() {
        return jenisBarang;
    }

    public void setJenisBarang(String jenisBarang) {
        this.jenisBarang = jenisBarang;
    }
}
