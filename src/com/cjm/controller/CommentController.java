package com.cjm.controller;

import com.cjm.base.BaseController;
import com.cjm.po.Comment;
import com.cjm.service.CommentService;
import com.cjm.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 评论
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;
    /**
     * 添加评论
     */
    @RequestMapping("/toAdd")
    public String exAdd(Comment comment, HttpServletRequest request, Model model){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            model.addAttribute("msg","你还未登录，请先登录！");
            return "/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        comment.setAddTime(new Date());
        comment.setUserId(userId);
        if(comment.getItemId()!=null){
            commentService.insert(comment);
        }
        return "itemOrder/myOrder";
    }
}
