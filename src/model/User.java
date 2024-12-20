package model;

public class User {

    private int id;
    private String email;
    private String password;
    private String kk;
    private String ktp;

    public User(int id, String email, String password, String kk, String ktp) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.kk = kk;
        this.ktp = ktp;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKk() {
        return kk;
    }

    public void setKk(String kk) {
        this.kk = kk;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }
}
