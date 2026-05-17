package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.PageResult;

import java.time.LocalDate;

public interface ClazzService extends IService<Clazz> {

    PageResult<Clazz> getPageResult(String name, LocalDate begin, LocalDate end, int page, int pageSize);
    Clazz getClazzById(Integer id);

}
