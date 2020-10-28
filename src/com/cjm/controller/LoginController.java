package com.cjm.controller;


import com.alibaba.fastjson.JSONObject;
import com.cjm.base.BaseController;
import com.cjm.po.*;
import com.cjm.service.ItemCategoryService;
import com.cjm.service.ItemService;
import com.cjm.service.ManageService;
import com.cjm.service.UserService;
import com.cjm.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录相关的控制器
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
    Manage byEntity=null;
    @Autowired
    private ManageService manageService;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    /**
     * 管理员登录
     */
    @RequestMapping("login")
    public String login(){
        return "/login/mLogin";
    }

    /**
     * 登录验证
     */
    @RequestMapping("toLogin")
    public String toLogin(Model model,Manage manage, HttpServletRequest request){

        if(manage.getUserName()!=null && manage.getPassWord()!=null)
            byEntity = manageService.getByEntity(manage);
        if(byEntity==null){
            model.addAttribute("msg","用户名或密码错误!");
            return "/login/mLogin";
        }
        request.getSession().setAttribute(Consts.MANAGE,byEntity);
        return "/login/mIndex";
    }

    /**
     * 管理员退出
     */
    @RequestMapping("mtuichu")
    public String mtuichu(HttpServletRequest request){
        request.getSession().setAttribute(Consts.MANAGE,null);
        byEntity=null;
        return "/login/mLogin";
    }

    /**
     * 前端首页
     */
    @RequestMapping("/uIndex")
    public String uIndex(Model model, Item item,HttpServletRequest request){
        String sql1 = "select * from item_category where isDelete=0 and pid is null order by name";
        List<ItemCategory> fatherList = itemCategoryService.listBySqlReturnEntity(sql1);//一级目录
        List<CategoryDto> list = new ArrayList<>();
        if(!CollectionUtils.isEmpty(fatherList)){
            for(ItemCategory ic:fatherList){
                CategoryDto dto = new CategoryDto();
                dto.setFather(ic);
                //查询二级类目
                String sql2 = "select * from item_category where isDelete=0 and pid="+ic.getId();
                List<ItemCategory> childrens = itemCategoryService.listBySqlReturnEntity(sql2);//二级目录
                dto.setChildrens(childrens);
                list.add(dto);
                model.addAttribute("catalog",list);
            }
        }
        //折扣商品
        List<Item> zks = itemService.listBySqlReturnEntity("select * from item where isDelete=0 and zk is not null order by zk desc limit 0,10");
        model.addAttribute("zks",zks);

        //热销商品
        List<Item> rxs = itemService.listBySqlReturnEntity("select * from item where isDelete=0 order by gmNum desc limit 0,10");
        model.addAttribute("rxs",rxs);

        return "login/uIndex";
    }

    /**用户注册*/
    @RequestMapping("/res")
    public String res(){
        return "login/res";
    }

    /**提交注册表单*/
    @RequestMapping("/toRes")
    public String toRes(User user,Model model){
        if(user.getUserName()!=null&& user.getUserName()!="" &&user.getPassWord()!=null && user.getPassWord()!="") {
            userService.insert(user);
            return "login/uLogin";
        }else{
            model.addAttribute("msg","用户名或密码不能为空！");
            return "/login/res";
        }
    }

    /**用户登录入口*/
    @RequestMapping("/uLogin")
    public String uLogin(){
        return "login/uLogin";
    }

     /**
     * 提交登录表单
     */
    @RequestMapping("/utoLogin")
    public String utoLogin(User user,HttpServletRequest request,Model model){
        User byEntity = userService.getByEntity(user);
        if(byEntity==null){
            model.addAttribute("msg","用户名或密码错误！");
            return "/login/uLogin";
        }else {
            request.getSession().setAttribute("role",2);
            request.getSession().setAttribute(Consts.USERNAME,byEntity.getUserName());
            request.getSession().setAttribute(Consts.USERID,byEntity.getId());
            return "redirect:/login/uIndex";
        }
    }

    /**前端用户退出*/
    @RequestMapping("/uTui")
    public String uTui(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login/uIndex";
    }

    /**
     * 修改密码入口
     */
    @RequestMapping("/pass")
    public String pass(HttpServletRequest request,Model model){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            model.addAttribute("msg","你还未登录，请先登录！");
            return "/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        User load = userService.load(userId);
        request.setAttribute("obj",load);
        return "login/pass";
    }

    /**
     * 修改密码操作
     */
    @RequestMapping("/upass")
    @ResponseBody
    public String upass(String password,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        JSONObject js = new JSONObject();
        if(attribute==null){
            js.put(Consts.RES,0);
            return js.toString();
        }
        Integer userId = Integer.valueOf(attribute.toString());
        User user = userService.load(userId);
        user.setPassWord(password);
        userService.updateById(user);
        js.put(Consts.RES,1);
        return js.toString();
    }
}
