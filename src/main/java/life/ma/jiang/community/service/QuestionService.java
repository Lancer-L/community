package life.ma.jiang.community.service;

import life.ma.jiang.community.dto.QuestionDTO;
import life.ma.jiang.community.mapper.QuestionMapper;
import life.ma.jiang.community.mapper.UserMapper;
import life.ma.jiang.community.model.Question;
import life.ma.jiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public List<QuestionDTO> findAllQuestion() {
        List<Question> questions = questionMapper.findAllQuestion();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions){
             User user =  userMapper.findById(question.getCreator());
             QuestionDTO questionDTO = new QuestionDTO();
             BeanUtils.copyProperties(question,questionDTO);//将question所有属性拷贝到questionDTO里面
             questionDTO.setUser(user);
             questionDTOS.add(questionDTO);
        }
        return  questionDTOS;
    }
}
