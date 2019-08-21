package life.ma.jiang.community.mapper;

import life.ma.jiang.community.model.Comment;
import life.ma.jiang.community.model.Question;
import org.apache.ibatis.annotations.Param;

public interface CommentExtMapper {
    int incComment(@Param("record") Comment comment);
}
