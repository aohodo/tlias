package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import com.itheima.service.ClazzService;
import com.itheima.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private ClazzService clazzService;
    
    @Override
    public PageResult<Student> getPageResult(StudentQueryParam student) {
        // TODO Auto-generated method stub
        Page<Student> pageResult = Page.of(student.getPage(), student.getPageSize());
        pageResult = lambdaQuery()
                .like(student.getName() != null && !student.getName().isEmpty(), Student::getName, student.getName())
                .eq(student.getDegree() != null, Student::getDegree, student.getDegree())
                .eq(student.getClazzId() != null, Student::getClazzId, student.getClazzId())
                .page(pageResult);

        pageResult.getRecords().forEach(record -> record.setClazzName(clazzService.getClazzById(record.getClazzId()).getName()));
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    public void saveStudent(Student student) {
        student.setCreateTime(LocalDateTime.now());
        this.save(student);
    }

    @Override
    public void updateStudent(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        this.updateById(student);
    }

    @Override
    public void violation(Integer id, Integer score) {
        LambdaUpdateWrapper<Student> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Student::getId, id)
                .setSql("violation_count = violation_count + 1")
                .setSql("violation_score = violation_score + " + score);
        this.update(updateWrapper);
    }
}
