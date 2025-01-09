package controller;

import mapper.UserMapper;
import model.MyBatisUtil;
import model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import util.PasswordHasher;
import util.EmailUtil;
import java.util.Random;

public class KurirController {

    private static final int OTP_LENGTH = 6;

    public boolean resetPasswordByEmail(String email, String password) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Hash password baru
            String hashedPassword = PasswordHasher.hashPassword(password);

            // Update password di database berdasarkan email
            userMapper.updatePasswordByEmail(email, hashedPassword);
            session.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metode untuk menghasilkan OTP
    public String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    // Metode untuk mengirim OTP ke email untuk reset password
    public boolean sendOtpToEmail(String email, String otp) {
        String subject = "OTP Reset Password";
        String body = "Your OTP for resetting your password is: " + otp;
        try {
            EmailUtil.sendEmail(email, subject, body);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metode untuk mengirim OTP ke email untuk registrasi Kurir
    public boolean sendOtpForRegistration(String email, String otp) {
        String subject = "OTP Registration";
        String body = "Your OTP for registration is: " + otp;
        try {
            EmailUtil.sendEmail(email, subject, body);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Register Kurir dengan OTP
    public boolean registerKurir(String email, String password, String kk, String ktp, String otp) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            String hashedPassword = PasswordHasher.hashPassword(password);

            User user = new User(0, email, hashedPassword, kk, ktp);

            userMapper.insertUser(user);
            session.commit();

            boolean emailSent = sendOtpForRegistration(email, otp);
            return emailSent;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login Kurir
    public User loginKurir(String email, String password) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Cari user berdasarkan email
            User user = userMapper.findByEmail(email);

            if (user != null) {
                // Hash password yang dimasukkan
                String hashedPassword = PasswordHasher.hashPassword(password);

                // Perbandingan password hash
                if (hashedPassword.equals(user.getPassword())) {
                    return user;
                } else {
                    System.out.println("Login - Password mismatch!");
                }
            } else {
                System.out.println("Login - User not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Periksa apakah NIK KTP ada di database
    public boolean checkKTPExists(String ktp) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.checkKTPExists(ktp) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Reset password pengguna
    public boolean resetPassword(String ktp, String password) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Hash password baru
            String hashedPassword = PasswordHasher.hashPassword(password);

            // Update password di database
            userMapper.updatePasswordByKTP(ktp, hashedPassword);
            session.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return MyBatisUtil.getSqlSessionFactory();  // Pastikan ini sesuai dengan cara Anda mendapatkan SqlSessionFactory
    }

    // Metode untuk menghapus akun berdasarkan email
    public boolean deleteAccount(String email) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Periksa apakah pengguna ada sebelum menghapus
            User user = userMapper.findByEmail(email);
            if (user == null) {
                System.out.println("Akun tidak ditemukan untuk email: " + email);
                return false;
            }

            // Hapus akun
            userMapper.deleteAccount(email);
            session.commit();
            System.out.println("Akun berhasil dihapus untuk email: " + email);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
