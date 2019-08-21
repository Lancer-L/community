package life.ma.jiang.community.exception;


public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND (2001,"你访问的问题已被删除或不存在,换一个试试吧"),
    TARGET_PARAM_NOT_FOUND (2002,"未选中任何问题或者评论进行回复"),
    PUBLISH_NOT_EDIT(2003,"大兄弟你走错房间了吧"),
    NO_LOGIN(2004,"当前操作需要登录，请先登录以后再来操作"),
    SYSTEM_ERROR(2005,"服务器跑小差了，稍后在试试"),
    TYPE_PARAM_WRONG(2006,"评论类型有误,或不存在"),
    COMMENT_NOT_FOUND(2007,"你访问的评论不存在"),
    USER_NOT_EXIST(2008,"用户不存在"),
   CONTENT_IS_EMPTY(2009,"回复内容不能为空");
    private String message;
    private  Integer code;
    @Override
    public String getMessage() { //重写接口的getMessage()方法
        return message;
    }
    @Override
    public  Integer getCode(){
        return  code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
