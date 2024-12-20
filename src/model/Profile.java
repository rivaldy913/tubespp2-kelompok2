package model;

public class Profile {

    private int id;
    private int userId;
    private String foto;
    private String alamat;
    private String tanggalLahir;
    private String noRekening;

    public Profile(int id, int userId, String foto, String alamat, String tanggalLahir, String noRekening) {
        this.id = id;
        this.userId = userId;
        this.foto = foto;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
        this.noRekening = noRekening;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }
}
