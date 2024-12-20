package mapper;

import model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users (email, password, kk, ktp) VALUES (#{email}, #{password}, #{kk}, #{ktp})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Select("SELECT * FROM users WHERE email = #{email} AND password = #{password}")
    User loginUser(@Param("email") String email, @Param("password") String password);
}
