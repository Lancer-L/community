package life.ma.jiang.community.dto;

import life.ma.jiang.community.exception.CustomizeErrorCode;
import life.ma.jiang.community.exception.CustomizeException;
import lombok.Data;

import java.util.List;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;
    public static  ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO errorOf(CustomizeErrorCode customizeErrorCode){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(customizeErrorCode.getCode());
        resultDTO.setMessage(customizeErrorCode.getMessage());
        return  resultDTO;
    }
    public static  ResultDTO okOf(){
        return  errorOf(200,"请求成功");
    }
    public static <T>  ResultDTO okOf(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }
    public  static  ResultDTO errorOf(CustomizeException ex){
        return errorOf(ex.getCode(),ex.getMessage());
    }
}
