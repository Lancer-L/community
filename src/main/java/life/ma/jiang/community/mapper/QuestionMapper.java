package life.ma.jiang.community.mapper;

import life.ma.jiang.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{page} , #{size}")
    List<Question> listAllQuestion(@Param("page") Integer page, @Param("size") Integer size);
    @Select("select count(1) from question")
    Integer getSumCount();
    @Select("select count(1) from question where creator = #{creator}")
    Integer getMySumCount(@Param("creator") int id);
    @Select("select * from question where creator = #{creator}  limit #{page} , #{size}")
    List<Question> listMyQuestion(@Param("page") Integer page, @Param("size") Integer size,@Param("creator") Integer id);
    @Select("select * from question where id = #{id}")
    Question getQuestionById(@Param(value = "id") Integer id);
}
