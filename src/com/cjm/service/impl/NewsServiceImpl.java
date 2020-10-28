package com.cjm.service.impl;

import com.cjm.base.BaseDao;
import com.cjm.base.BaseServiceImpl;
import com.cjm.mapper.NewsMapper;
import com.cjm.po.News;
import com.cjm.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public BaseDao<News> getBaseDao() {
        return newsMapper;
    }
}
