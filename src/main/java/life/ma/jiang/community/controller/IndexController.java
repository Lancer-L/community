package life.ma.jiang.community.controller;

import life.ma.jiang.community.dto.QuestionDTO;
import life.ma.jiang.community.mapper.QuestionMapper;
import life.ma.jiang.community.mapper.UserMapper;
import life.ma.jiang.community.model.Question;
import life.ma.jiang.community.model.User;
import life.ma.jiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/") // 映射根目录
    public  String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        if(cookies != null){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    token =  cookie.getValue();
                    break;
                }
            }
            User user = userMapper.findByToken(token);
            if(user!=null)
                request.getSession().setAttribute("user",user);
        }else{
            System.out.println("禁用cookies");
        }
        List<QuestionDTO> questionList = questionService.findAllQuestion();
        model.addAttribute("qlist",questionList);
        return  "index";
    }
}
