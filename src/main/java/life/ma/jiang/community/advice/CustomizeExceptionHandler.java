package life.ma.jiang.community.advice;

import com.alibaba.fastjson.JSON;
import life.ma.jiang.community.dto.ResultDTO;
import life.ma.jiang.community.exception.CustomizeErrorCode;
import life.ma.jiang.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response){
        String contentType = request.getContentType();
        if(("application/json").equals(contentType)){
            //返回json
            ResultDTO resultDTO = null;
            if(ex instanceof CustomizeException){
                  resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            }else{
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            try {
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return null;
        }else{
            //错误页面跳转
            if(ex instanceof CustomizeException){
                model.addAttribute("message",ex.getMessage());
            }else{
                model.addAttribute("message","服务器冒烟了，要不然你稍后再试试！！！");
            }
            return new ModelAndView("error");
        }
    }
}
