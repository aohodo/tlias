package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import com.itheima.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    
    @Override
    public PageResult<Student> getPageResult(StudentQueryParam student) {
        // TODO Auto-generated method stub
        Page<Student> pageResult = Page.of(student.getPage(), student.getPageSize());\

        return null;
    }

    
}
