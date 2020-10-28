package com.cjm.service.impl;

import com.cjm.base.BaseDao;
import com.cjm.base.BaseServiceImpl;
import com.cjm.mapper.CommentMapper;
import com.cjm.po.Comment;
import com.cjm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public BaseDao<Comment> getBaseDao() {
        return commentMapper;
    }
}
