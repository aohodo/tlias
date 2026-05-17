package com.itheima.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;

public interface StudentService extends IService<Student> {
     PageResult<Clazz> getPageResult(StudentQueryParam student);
}
