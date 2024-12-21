package controller;

import model.Profile;
import mapper.ProfileMapper;
import model.User;
import mapper.UserMapper;
import model.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import view.LoginView;

public class KurirController {

    // register
    public boolean registerKurir(String nama, String email, String password, String kk, String ktp) {

        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {

            UserMapper userMapper = session.getMapper(UserMapper.class);
            ProfileMapper profileMapper = session.getMapper(ProfileMapper.class);

            User user = new User(0, email, password, kk, ktp);

            userMapper.insertUser(user);
            session.commit();

            int userId = user.getId();

            if (userId <= 0) {
                session.rollback();
                System.out.println("User ID tidak valid: " + userId);
                return false;
            }

            Profile profile = new Profile(0, userId, null, null, null, null);

            profileMapper.insertProfile(profile);
            session.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Terjadi kesalahan saat registrasi: " + e.getMessage());
            return false;
        }
    }

    // Entry point to start the application
    public static void main(String[] args) {
        // Launch the login page
        javax.swing.SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
