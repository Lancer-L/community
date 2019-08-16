package life.ma.jiang.community.controller;

import life.ma.jiang.community.model.User;
import life.ma.jiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("name") String name,
                        @RequestParam("pwd") String pwd,
                        Model model,
                        HttpServletResponse response){
        User user = userService.checkLogin(name,pwd);
        if(user== null){
            model.addAttribute("info","账户密码错误");
            return "login";
        }
        user.setToken(UUID.randomUUID().toString());
        userService.createOrUpdateUser(user);
        response.addCookie(new Cookie("token",user.getToken()));
        return "redirect:/";
    }
    @GetMapping("/logout")
    public  String logout(HttpServletRequest request,
                          HttpServletResponse response){
        request.getSession().removeAttribute("user");
        //删除cookies 就是重新添加一个cookies
        Cookie cookie = new Cookie("token","");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }
}
