package life.ma.jiang.community.controller;

import life.ma.jiang.community.dto.AccessTokenDTO;
import life.ma.jiang.community.dto.GithubUserDTO;
import life.ma.jiang.community.mapper.UserMapper;
import life.ma.jiang.community.model.User;
import life.ma.jiang.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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

    @Autowired
    UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO tokenDTO = new AccessTokenDTO();
        tokenDTO.setClient_id(clientId);
        tokenDTO.setClient_secret(clientSecret);
        tokenDTO.setCode(code);
        tokenDTO.setRedirect_uri(redirectUrl);
        tokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(tokenDTO);
        GithubUserDTO githubuser = gitHubProvider.getUser(accessToken);
        if(githubuser != null){
            User user = userMapper.findUserByAccountId(String.valueOf(githubuser.getId()));
            String token ="";
            if(user==null){
                token = UUID.randomUUID().toString();
                user = new User();
                user.setAccountId(String.valueOf(githubuser.getId()));
                user.setName(githubuser.getName());
                user.setToken(token); // 以UUID的形式设置token
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                userMapper.insertUser(user);
            }else
                token = user.getToken();
            //登录成功 写cookie 和 session
            //cookie 在 response 里面
            response.addCookie(new Cookie("tokdden",token));
            return  "redirect:/";//重定向到 index页面
        }else{
            //登录失败 重新登录
            return  "redirect:/";
        }
    }
}
