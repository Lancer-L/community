package life.ma.jiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    /**
     *  ctrl + p 可以进行提示
     *  ctrl + shift + f12 最大屏幕
     *  ctrl + shift + n 查找文件
     *  ctrl + alt + v 快速生成一个变量
     * **/
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }
}
