package life.ma.jiang.community.controller;

import life.ma.jiang.community.mapper.UserMapper;
import life.ma.jiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;
    @GetMapping("/") // 映射根目录
    public  String index(HttpServletRequest request) {
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
        return  "index";
    }
}
