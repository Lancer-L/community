package life.ma.jiang.community.mapper;

import life.ma.jiang.community.model.Question;
import life.ma.jiang.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    int incView(@Param("record") Question record);
    int incComment(@Param("record") Question record);
}