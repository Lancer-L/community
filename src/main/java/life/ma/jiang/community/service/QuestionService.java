package life.ma.jiang.community.service;

import life.ma.jiang.community.dto.PaginationDTO;
import life.ma.jiang.community.dto.QuestionDTO;
import life.ma.jiang.community.mapper.QuestionMapper;
import life.ma.jiang.community.mapper.UserMapper;
import life.ma.jiang.community.model.Question;
import life.ma.jiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Value("${page.size}")
    private Integer size ;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public void operationbefore(Integer total,PaginationDTO<QuestionDTO> paginationDTO,Integer page){
        if(total % size != 0 )
            paginationDTO.setTotalPage(total / size + 1);
        else
            paginationDTO.setTotalPage(total/size);
        if(page> paginationDTO.getTotalPage())
            page = paginationDTO.getTotalPage();
        paginationDTO.setCurrentPage(page);
    }
    public  void operationafter(List<Question> questions,PaginationDTO<QuestionDTO> paginationDTO){
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions){
            User user =  userMapper.findUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//将question所有属性拷贝到questionDTO里面
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setData(questionDTOS);
    }

    public PaginationDTO<QuestionDTO> list(Integer page) {
        if(page<=0) page = 1;
        Integer total = questionMapper.getSumCount();
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        paginationDTO.setTotalCount(total);
        operationbefore(total,paginationDTO,page);
        page = (page -1) * size;
        List<Question> questions = questionMapper.listAllQuestion(page,size);
        operationafter(questions,paginationDTO);
        return  paginationDTO;
    }

    public PaginationDTO<QuestionDTO> listMyQuestion(Integer page, User user) {
        if(page<=0) page = 1;
        Integer total = questionMapper.getMySumCount(user.getId());
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        paginationDTO.setTotalCount(total);
        operationbefore(total,paginationDTO,page);
        page = (page -1) * size;
        List<Question> questions = questionMapper.listMyQuestion(page,size,user.getId());
        operationafter(questions,paginationDTO);
        return  paginationDTO;
    }

    public QuestionDTO findQuestionById(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        User user = null;
        if(question != null){
            BeanUtils.copyProperties(question,questionDTO);
            user = userMapper.findUserById(question.getCreator());
        }
        questionDTO.setUser(user);
        return questionDTO;
    }
}
