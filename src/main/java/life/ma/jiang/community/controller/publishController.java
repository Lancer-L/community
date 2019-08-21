package life.ma.jiang.community.controller;

import life.ma.jiang.community.dto.QuestionDTO;
import life.ma.jiang.community.mapper.QuestionMapper;
import life.ma.jiang.community.model.Question;
import life.ma.jiang.community.model.User;
import life.ma.jiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {
    @Autowired
    private QuestionService questionService;
    /*
    * ctrl + alt + o 是删掉不用的import
    *  */
    @GetMapping("/publish")
    public String publish(){
        return  "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "qid",required = false) Long id,
            HttpServletRequest request,
            Model model
            ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //校验
        if(title==null || title.length()==0){
            model.addAttribute("error","标题必须填写");
            return "/publish";
        }
        if(description==null || description.length()==0){
            model.addAttribute("error","内容描述必须填写");
            return "/publish";
        }
        if(tag==null || tag.length()==0){
            model.addAttribute("error","标签必须填写");
            return "/publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "/publish";
        }else{
            Question question = new Question();
            question.setTitle(title);
            question.setId(id);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionService.createOrUpdate(question,user);
            return "redirect:/";
        }
    }
    @GetMapping("/publish/{id}")
    public  String edit(@PathVariable("id") Long id,
                        Model model,
                        HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/login";
        }
        QuestionDTO questionDTO = questionService.findQuestionById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("qid",id);
        return "/publish";
    }
}
