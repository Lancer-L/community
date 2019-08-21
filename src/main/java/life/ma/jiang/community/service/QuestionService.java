package life.ma.jiang.community.service;

import life.ma.jiang.community.dto.PaginationDTO;
import life.ma.jiang.community.dto.QuestionDTO;
import life.ma.jiang.community.exception.CustomizeErrorCode;
import life.ma.jiang.community.exception.CustomizeException;
import life.ma.jiang.community.exception.ICustomizeErrorCode;
import life.ma.jiang.community.mapper.QuestionExtMapper;
import life.ma.jiang.community.mapper.QuestionMapper;
import life.ma.jiang.community.mapper.UserMapper;
import life.ma.jiang.community.model.Question;
import life.ma.jiang.community.model.QuestionExample;
import life.ma.jiang.community.model.User;
import life.ma.jiang.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Value("${page.size}")
    private Integer size ;

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    UserMapper userMapper;

    public Integer operationBefore(PaginationDTO<QuestionDTO> paginationDTO,Integer page){
        if (page<=0) page = 1;
        Integer total = (int) questionMapper.countByExample(new QuestionExample());
        paginationDTO.setTotalCount(total);
        if(total % size != 0 )
            paginationDTO.setTotalPage(total / size + 1);
        else
            paginationDTO.setTotalPage(total/size);
        if(page > paginationDTO.getTotalPage())
            page = paginationDTO.getTotalPage();
        paginationDTO.setCurrentPage(page);
        return  page;
    }
    public  void operationAfter(List<Question> questions,PaginationDTO<QuestionDTO> paginationDTO){
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            if (user == null)
                  throw new CustomizeException(CustomizeErrorCode.USER_NOT_EXIST);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setData(questionDTOS);
    }

    public PaginationDTO<QuestionDTO> list(Integer page) {
        if(page<=0) page = 1;
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        page = operationBefore(paginationDTO,page);
        page = (page -1) * size;
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(page,size)); // RowBounds 第一个参数是 offset 第二个参数是 size
        operationAfter(questions,paginationDTO);
        return  paginationDTO;
    }

    public PaginationDTO<QuestionDTO> listMyQuestion(Integer page, User user) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        page = operationBefore(paginationDTO,page);
        page = (page -1) * size;
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(user.getId());
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(page,size));
        operationAfter(questions,paginationDTO);
        return  paginationDTO;
    }

    public QuestionDTO findQuestionById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null)
            throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void create(Question question) {
        questionMapper.insert(question);
    }
    @Transactional
    public void createOrUpdate(Question question,User user) {
        if (question.getId() == null || question.getId() == 0) {
            question.setId(null);
            question.setViewCount(0L);
            question.setCommentCount(0L);
            question.setLikeCount(0L);
            questionMapper.insert(question);
        } else {
            Question dbQuestion = questionMapper.selectByPrimaryKey(question.getId());
            if (user.getId() != question.getCreator())
                throw new CustomizeException(CustomizeErrorCode.PUBLISH_NOT_EDIT);
            question.setId(dbQuestion.getId());
            question.setGmtCreate(dbQuestion.getGmtCreate());
            question.setLikeCount(dbQuestion.getLikeCount());
            question.setCommentCount(dbQuestion.getCommentCount());
            question.setViewCount(dbQuestion.getViewCount());
            int update = questionMapper.updateByPrimaryKey(question);
            if(update!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1L);
        questionExtMapper.incView(question);
    }
}
