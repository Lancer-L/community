package life.ma.jiang.community.controller;

import life.ma.jiang.community.dto.QuestionDTO;
import life.ma.jiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionControoler {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public  String question(@PathVariable(name = "id") Integer id,
                            Model model){
      QuestionDTO questionDTO =  questionService.findQuestionById(id);
      if(questionDTO.getUser() == null)
          return "redirect:/";
      model.addAttribute("question",questionDTO);
      return "question";
    }
}
