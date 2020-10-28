package com.cjm.service.impl;

import com.cjm.base.BaseDao;
import com.cjm.base.BaseServiceImpl;
import com.cjm.mapper.ScMapper;
import com.cjm.po.Sc;
import com.cjm.service.ScService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScServiceImpl extends BaseServiceImpl<Sc> implements ScService {
    @Autowired
    private ScMapper scMapper;
    @Override
    public BaseDao<Sc> getBaseDao() {
        return scMapper;
    }
}
