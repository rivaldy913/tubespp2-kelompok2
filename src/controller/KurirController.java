package controller;

import mapper.UserMapper;
import model.MyBatisUtil;
import model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import util.PasswordHasher;

public class KurirController {

    // Register Kurir
    public boolean registerKurir(String nama, String email, String password, String kk, String ktp) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Hash password sebelum ke database
            String hashedPassword = PasswordHasher.hashPassword(password);
            // System.out.println("Register - Hashed Password: " + hashedPassword); // Debug

            User user = new User(0, email, hashedPassword, kk, ktp);

            // Insert user ke database
            userMapper.insertUser(user);
            session.commit();
            return true;
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

                // // Debug
                // System.out.println("Login - Input Hashed Password: " + hashedPassword);
                // System.out.println("Login - Stored Hashed Password: " + user.getPassword());
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

}
