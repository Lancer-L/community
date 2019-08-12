package life.ma.jiang.community.controller;

import life.ma.jiang.community.dto.AccessTokenDTO;
import life.ma.jiang.community.dto.GithubUserDTO;
import life.ma.jiang.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {
    @Autowired // 自动根据类型进行实例化
    private GitHubProvider gitHubProvider;
    // ctrl + e 切换最近编辑文件
    @Value("${github.client.id}") // value注解 可以将配置文件 里面的值取出来
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO tokenDTO = new AccessTokenDTO();
        tokenDTO.setClient_id(clientId);
        tokenDTO.setClient_secret(clientSecret);
        tokenDTO.setCode(code);
        tokenDTO.setRedirect_uri(redirectUrl);
        tokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(tokenDTO);
        GithubUserDTO user = gitHubProvider.getUser(accessToken);
        if(user != null){
            //登录成功 写cookie 和 session
            request.getSession().setAttribute("user",user);
            return  "redirect:/";//重定向到 index页面
        }else{
            //登录失败 重新登录
            return  "redirect:/";
        }
    }
}
