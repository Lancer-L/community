package life.ma.jiang.community.dto;

import lombok.Data;

/*
* 数据传输 实体类 dto包下所有类都是数据传输的类
* */
@Data
public class AccessTokenDTO {
    private String client_id;
    private  String client_secret;
    private  String code;
    private  String redirect_uri;
    private  String state;
 /*
 * alt + insert 进行添加getter 和 setter方法
 * */

}
