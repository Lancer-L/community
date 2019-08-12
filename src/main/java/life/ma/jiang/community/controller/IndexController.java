package life.ma.jiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/") // 映射根目录
    public  String index(){
        return  "index";
    }
}
