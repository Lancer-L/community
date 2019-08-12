package life.ma.jiang.community.controller;

import life.ma.jiang.community.dto.AccessTokenDTO;
import life.ma.jiang.community.dto.GithubUserDTO;
import life.ma.jiang.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                           @RequestParam(name = "state") String state){
        AccessTokenDTO tokenDTO = new AccessTokenDTO();
        tokenDTO.setClient_id(clientId);
        tokenDTO.setClient_secret(clientSecret);
        tokenDTO.setCode(code);
        tokenDTO.setRedirect_uri(redirectUrl);
        tokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(tokenDTO);
        GithubUserDTO user = gitHubProvider.getUser(accessToken);
        System.out.println(user.toString());
        return "index";
    }
}
