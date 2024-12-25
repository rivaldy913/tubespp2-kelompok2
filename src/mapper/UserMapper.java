package mapper;

import model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // Insert User
    @Insert("INSERT INTO users (email, password, kk, ktp) VALUES (#{email}, #{password}, #{kk}, #{ktp})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    // Cari User berdasarkan email
    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

}
