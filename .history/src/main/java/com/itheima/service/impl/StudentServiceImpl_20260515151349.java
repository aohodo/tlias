package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.Student;
import com.itheima.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    
}
