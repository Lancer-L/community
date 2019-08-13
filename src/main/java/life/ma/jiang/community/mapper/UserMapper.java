package life.ma.jiang.community.mapper;

import life.ma.jiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insertUser(User user);
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token); // 如果参数是一个类的话 就直接写就行 如果是一个变量的话需要用Param注解
    @Select("select * from user where account_id = #{account_id}")
    User findUserByAccountId(@Param("account_id") String id);
}
