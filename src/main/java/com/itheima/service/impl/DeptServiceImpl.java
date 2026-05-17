package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.DeptMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {
    //继承默认实现 ServiceImpl
    //实现接口：DeptService->里面的自定义方法。除了增删改查
}

