package life.ma.jiang.community.dto;

import lombok.Data;

@Data
public class GithubUserDTO {
    private long id;
    private  String name;
    private  String bio;
    private String avatarUrl;
}
