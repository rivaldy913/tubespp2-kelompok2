package mapper;

import model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // insert User
    @Insert("INSERT INTO users (email, password, kk, ktp) VALUES (#{email}, #{password}, #{kk}, #{ktp})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    // cari User berdasarkan email
    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

    // periksa apakah NIK KTP ada di database
    @Select("SELECT COUNT(*) FROM users WHERE ktp = #{ktp}")
    int checkKTPExists(@Param("ktp") String ktp);

    // update password berdasarkan NIK KTP
    @Update("UPDATE users SET password = #{password} WHERE ktp = #{ktp}")
    void updatePasswordByKTP(@Param("ktp") String ktp, @Param("password") String password);

    // update password berdasarkan email
    @Update("UPDATE users SET password = #{password} WHERE email = #{email}")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    // hapus user berdasarkan email
    @Delete("DELETE FROM users WHERE email = #{email}")
    void deleteAccount(@Param("email") String email);
}
