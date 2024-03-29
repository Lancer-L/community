package life.ma.jiang.community.controller;


import life.ma.jiang.community.dto.PaginationDTO;
import life.ma.jiang.community.dto.QuestionDTO;
import life.ma.jiang.community.model.User;
import life.ma.jiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    QuestionService questionService;
    //动态切换路径
    @GetMapping("/profile/{action}")
    public  String profile(@PathVariable(name = "action") String action,
                           Model model,
                           @RequestParam(name = "page",defaultValue = "1") Integer page,
                           HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null)
            return "redirect:/login";
        if(action.equals("questions")){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        else if(action.equals("replies")){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","我的最新回复");
        }
        PaginationDTO<QuestionDTO> paginationDTO = questionService.listMyQuestion(page,user);
        model.addAttribute("pagination",paginationDTO);
        return  "profile";
    }
}
