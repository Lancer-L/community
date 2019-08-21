package life.ma.jiang.community.service;

import life.ma.jiang.community.dto.CommentDTO;
import life.ma.jiang.community.enums.CommentTypeEnum;
import life.ma.jiang.community.exception.CustomizeErrorCode;
import life.ma.jiang.community.exception.CustomizeException;
import life.ma.jiang.community.mapper.*;
import life.ma.jiang.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;

    @Transactional //事务注解
    public void insert(CommentDTO commentDTO,Long id) {
        Comment record = new Comment();
        record.setType(commentDTO.getType());
        record.setContent(commentDTO.getContent());
        record.setParentId(commentDTO.getParentId());
        record.setCommentator(id);
        record.setLikeCount(0L);
        record.setCommentCount(0L);
        record.setGmtCreate(System.currentTimeMillis());
        record.setGmtModified(record.getGmtCreate());
        if(record.getParentId() == null || record.getParentId() == 0){
            throw new CustomizeException((CustomizeErrorCode.TARGET_PARAM_NOT_FOUND));
        }else if(record.getType() == null || !CommentTypeEnum.isExist(record.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(record.getType() == CommentTypeEnum.Question.getType()){
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(record.getParentId());
            if(question ==null){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1L);//回复数加一
            questionExtMapper.incComment(question);
        }else{
            //回复评论
            Comment comment = commentMapper.selectByPrimaryKey(record.getParentId());
            comment.setCommentCount(1L);
            if(comment == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentExtMapper.incComment(comment);
        }
        int cnt = commentMapper.insert(record);
    }


    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        if(type == CommentTypeEnum.Question){
            Question dbQuestion = questionMapper.selectByPrimaryKey(id);
            if(dbQuestion==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);
        if(comments.size()==0)
            return  new ArrayList<>();
        Set<Long> commentors =  comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> users = new ArrayList<>(commentors);
        UserExample example1 = new UserExample();
        example.createCriteria().andIdIn(users);
        List<User> userList = userMapper.selectByExample(example1);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(commentDTO.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return  commentDTOS;
    }
}
