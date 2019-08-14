package life.ma.jiang.community.dto;

import life.ma.jiang.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private int id;
    private String title;
    private  String description;
    private  Long gmtCreate;
    private Long gmtModified;
    private String tag;
    private  int creator;
    private int viewCount;
    private int commentCount;
    private int likeCount;
    private User user;
}
