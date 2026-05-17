package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.pojo.EmpExpr;
import com.itheima.service.EmpExprService;
import org.springframework.stereotype.Service;

@Service
public class EmpExprServiceImpl extends ServiceImpl<EmpExprMapper, EmpExpr> implements EmpExprService {


}

//第一步，在Mapper里面继承BaseMapper
//第二步，在Service里面继承IService接口
//第三步，在ServiceImpl里面继承ServiceImpl 实现 Service。
