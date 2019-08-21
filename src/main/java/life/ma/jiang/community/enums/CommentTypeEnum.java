package life.ma.jiang.community.enums;

public enum CommentTypeEnum {
    Question(1),
    Comment(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        if(type == Question.type || type == Comment.type)
            return  true;
        return  false;
    }

    public Integer getType() {
        return type;
    }
}
