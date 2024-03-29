package life.ma.jiang.community.interceptor;

import life.ma.jiang.community.mapper.UserMapper;
import life.ma.jiang.community.model.User;
import life.ma.jiang.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class SessionIntceptor implements HandlerInterceptor {
    @Autowired
    private  UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String token = "";
        if(cookies != null){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    token =  cookie.getValue();
                    break;
                }
            }
            UserExample userExample = new UserExample();
            userExample.createCriteria().andTokenEqualTo(token);
            List<User> users = userMapper.selectByExample(userExample);
            if(users!=null && users.size() !=0) {
                request.getSession().setAttribute("user", users.get(0));
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
