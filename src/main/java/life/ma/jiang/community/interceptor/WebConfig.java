package life.ma.jiang.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/*@EnableWebMvc*/
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private  SessionIntceptor sessionIntceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // pathPatterns 表示请求的时候对哪些地址进行拦截 excludePathPatterns 除了这些地址不拦截以外
        registry.addInterceptor(sessionIntceptor).addPathPatterns("/**").excludePathPatterns("/login").excludePathPatterns("/register");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //        默认访问static文件
         registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

    }
}
