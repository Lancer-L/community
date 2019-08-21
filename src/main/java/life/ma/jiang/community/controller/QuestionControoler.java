package life.ma.jiang.community.controller;

import life.ma.jiang.community.dto.CommentDTO;
import life.ma.jiang.community.dto.QuestionDTO;
import life.ma.jiang.community.enums.CommentTypeEnum;
import life.ma.jiang.community.service.CommentService;
import life.ma.jiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionControoler {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public  String question(@PathVariable(name = "id") Long id,
                            Model model){
      //增加阅读数
        questionService.incView(id);
        QuestionDTO questionDTO =  questionService.findQuestionById(id);
        List<CommentDTO> list = commentService.listByTargetId(id,CommentTypeEnum.Question);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",list);
        return "question";
    }
}
