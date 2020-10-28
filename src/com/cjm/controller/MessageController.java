package com.cjm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cjm.base.BaseController;
import com.cjm.po.Message;
import com.cjm.service.MessageService;
import com.cjm.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 留言管理
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;
    /**
     * 跳转到发表留言页面
     */
    @RequestMapping("/add")
    public String add(){
        return "message/add";
    }
    /**
     * 提交留言
     */
    @RequestMapping("/toAdd")
    @ResponseBody
    public String toAdd(Message message){
        System.out.println(message);
        messageService.insert(message);
        JSONObject js = new JSONObject();
        js.put("message","添加成功");
        return js.toString();
    }
    /**
     * 查询留言列表
     */
    @RequestMapping("/findBySql")
    public String findBySql(Message message, Model model){
        String sql = "select * from message where 1=1 ";
        if(!isEmpty(message.getName())){
            sql += " and name like '%"+message.getName()+"%'";
        }
        sql += " order by id desc";
        Pager<Message> pagers = messageService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",message);
        return "message/message";
    }
    /**
     * 删除留言
     */
    @RequestMapping("/delete")
    public String delete(Integer id){
        messageService.deleteById(id);
        return "redirect:/message/findBySql";
    }
}
