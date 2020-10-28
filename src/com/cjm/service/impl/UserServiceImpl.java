package com.cjm.service.impl;

import com.cjm.base.BaseDao;
import com.cjm.base.BaseServiceImpl;
import com.cjm.mapper.UserMapper;
import com.cjm.po.User;
import com.cjm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public BaseDao<User> getBaseDao() {
        return userMapper;
    }
}
