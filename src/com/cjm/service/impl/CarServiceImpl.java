package com.cjm.service.impl;

import com.cjm.base.BaseDao;
import com.cjm.base.BaseServiceImpl;
import com.cjm.mapper.CarMapper;
import com.cjm.po.Car;
import com.cjm.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends BaseServiceImpl<Car> implements CarService {
    @Autowired
    private CarMapper carMapper;
    @Override
    public BaseDao<Car> getBaseDao() {
        return carMapper;
    }
}
