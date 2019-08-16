package life.ma.jiang.community.controller;

import life.ma.jiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    private  UserService userService;
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam("name") String name,
                           @RequestParam("pwd") String pwd,
                           @RequestParam("bio") String bio,
                           Model model){
        userService.createUser(name,pwd,bio);
        model.addAttribute("info","注册成功请登录");
        return "login";
    }
}
