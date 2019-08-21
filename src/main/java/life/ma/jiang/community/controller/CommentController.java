package life.ma.jiang.community.controller;

import life.ma.jiang.community.dto.CommentDTO;
import life.ma.jiang.community.dto.ResultDTO;
import life.ma.jiang.community.enums.CommentTypeEnum;
import life.ma.jiang.community.exception.CustomizeErrorCode;
import life.ma.jiang.community.mapper.CommentMapper;
import life.ma.jiang.community.model.User;
import life.ma.jiang.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null)
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        if(commentDTO == null || StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        commentService.insert(commentDTO,user.getId());
        return ResultDTO.okOf();
    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.Comment);
        return  ResultDTO.okOf(commentDTOS);
    }
}
