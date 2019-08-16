package life.ma.jiang.community.mapper;

import life.ma.jiang.community.model.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,avatar_url,pwd) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{pwd})")
    void insertUser(User user);
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token); // 如果参数是一个类的话 就直接写就行 如果是一个变量的话需要用Param注解
    @Select("select * from user where account_id = #{account_id}")
    User findUserByAccountId(@Param("account_id") String id);
    @Select("select * from user where id = #{creator}")
    User findUserById(@Param("creator") int creator);

    @Update("update  user set bio = #{bio} , name = #{name},token = #{token},gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} where id = #{id}")
    void updateUserById(User user);
    @Select("select * from user where name = #{name} and pwd = #{pwd}")
    User findUserByNameAndPwd(@Param("name") String name, @Param("pwd") String pwd);
}
