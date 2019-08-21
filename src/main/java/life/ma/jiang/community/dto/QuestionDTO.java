package life.ma.jiang.community.dto;

import life.ma.jiang.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private  String description;
    private  Long gmtCreate;
    private Long gmtModified;
    private String tag;
    private  Long creator;
    private Long viewCount;
    private Long commentCount;
    private Long likeCount;
    private User user;
}
