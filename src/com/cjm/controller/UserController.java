package com.cjm.controller;

import com.cjm.base.BaseController;
import com.cjm.po.User;
import com.cjm.service.UserService;
import com.cjm.utils.Consts;
import com.cjm.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findBySql")
    public String findBySql(Model model,User user,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(Consts.MANAGE);
        if(attribute!=null) {
            String sql = "select * from user where 1=1 ";
            if (!isEmpty(user.getUserName())) {
                sql += " and userName like '%" + user.getUserName() + "%' ";
            }
            sql += " order by id";
            Pager<User> pagers = userService.findBySqlRerturnEntity(sql);
            model.addAttribute("pagers", pagers);
            model.addAttribute("obj", user);
            return "user/user";
        }else {
            model.addAttribute("msg","你还未登录，请先登录！");
            return "/login/mLogin";
        }
    }

    /**
     * 查看用户个人中心信息
     */
    @RequestMapping("/view")
    public String view(Model model, HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            model.addAttribute("msg","你还未登录，请先登录！");
            return "/login/uLogin";
        }else {
            Integer userId = Integer.valueOf(attribute.toString());
            User obj = userService.load(userId);
            model.addAttribute("obj", obj);
            return "user/view";
        }
    }

    /**
     * 修改用户信息验证
     */
    @RequestMapping("/exUpdate")
    public String exUpdate(User user,HttpServletRequest request,Model model){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            model.addAttribute("msg","你还未登录，请先登录！");
            return "/login/uLogin";
        }
        user.setId(Integer.valueOf(attribute.toString()));
        userService.updateById(user);
        return "redirect:/user/view";
    }
}
