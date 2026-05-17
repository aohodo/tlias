package com.itheima.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;

public interface StudentService extends IService<Student> {
    List<Student> findStudentList(StudentQueryParam student);
}
