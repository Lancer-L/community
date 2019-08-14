package life.ma.jiang.community.model;

import lombok.Data;

@Data
public class Question {
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

}
