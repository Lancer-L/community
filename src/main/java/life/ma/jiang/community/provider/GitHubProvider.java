package life.ma.jiang.community.provider;

import com.alibaba.fastjson.JSON;
import life.ma.jiang.community.dto.AccessTokenDTO;
import life.ma.jiang.community.dto.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // 对象实例化 不需要new 就是ioc控制反转
public class GitHubProvider {
    public  String getAccessToken(AccessTokenDTO tokenDTO){
         MediaType json
                = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json,JSON.toJSONString(tokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")//调用的地址
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string =  response.body().string();
            String[] split = string.split("&");
            String token =   split[0].split("=")[1];
            return  token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public GithubUserDTO getUser(String access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+access_token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //快捷键 ctrl + alt + v
            GithubUserDTO githubUserDTO = JSON.parseObject(string, GithubUserDTO.class);// 将json转化为实体对象
            // fastjson 可以利用驼峰命名法 即将avatar_url 转化为 avatarUrl
            return  githubUserDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
