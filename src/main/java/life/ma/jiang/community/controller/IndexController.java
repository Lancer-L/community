package life.ma.jiang.community.controller;

import life.ma.jiang.community.dto.PaginationDTO;
import life.ma.jiang.community.dto.QuestionDTO;
import life.ma.jiang.community.mapper.UserMapper;
import life.ma.jiang.community.service.QuestionService;
import life.ma.jiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/") // 映射根目录
    public  String index( Model model,
                         @RequestParam(name = "page",defaultValue = "1") Integer page
                         ) {
        PaginationDTO<QuestionDTO> paginationDTO = questionService.list(page);
        model.addAttribute("pagination",paginationDTO);
        return  "index";
    }
}
