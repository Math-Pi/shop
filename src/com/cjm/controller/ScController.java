package com.cjm.controller;

import com.cjm.po.Item;
import com.cjm.po.Sc;
import com.cjm.service.ItemService;
import com.cjm.service.ScService;
import com.cjm.utils.Consts;
import com.cjm.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 收藏
 */
@Controller
@RequestMapping("/sc")
public class ScController {

    @Autowired
    private ScService scService;

    @Autowired
    private ItemService itemService;

    @RequestMapping("/toAdd")
    public String exAdd(Sc sc, HttpServletRequest request,Model model){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            model.addAttribute("msg","你还未登录，请先登录！");
            return "/login/uLogin";
        }
        //保存到收藏表
        Integer userId = Integer.valueOf(attribute.toString());
        sc.setUserId(userId);
        scService.insert(sc);
        //商品表收藏数+1
        Item item = itemService.load(sc.getItemId());
        item.setScNum(item.getScNum()+1);
        itemService.updateById(item);
        return "redirect:/sc/findBySql";
    }

    /**
     * 收藏列表
     */
    @RequestMapping("/findBySql")
    public String findBySql(Model model,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            model.addAttribute("msg","你还未登录，请先登录！");
            return "/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        String sql = "select * from sc where user_id="+userId+" order by id desc";
        Pager<Sc> pagers = scService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        return "/sc/collect";
    }

    /**
     * 取消收藏
     */
    @RequestMapping("/delete")
    public String delete(Integer id,Model model,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            model.addAttribute("msg","你还未登录，请先登录！");
            return "/login/uLogin";
        }
        scService.deleteById(id);
        return "redirect:/sc/findBySql";
    }
}
