package com.itheima;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class QueryWrapperTest {
    @Autowired
    private EmpMapper empMapper;
    @Test
    void testQueryWrapper() {
        // 查询姓名中包含“李”且薪资大于等于5000的员工的 id, name, phone, salary字段
        QueryWrapper<Emp> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "李")
                .ge("salary", 5000)
                .select("id", "name", "phone", "salary");
        List<Emp> emps = empMapper.selectList(queryWrapper);
        System.out.println(emps);

    } //本质上用Java描述数据库
    @Test
    public void testUpdateByQueryWrapper() {
        // 更新名为"李忠"的员工的薪水为9000
        // Preparing: UPDATE emp SET salary=? WHERE (name = ?)
        // 把要更新的值封装在对象里面
        Emp emp = new Emp();
        emp.setSalary(9000);


        QueryWrapper<Emp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "李忠");
        empMapper.update(emp, queryWrapper);
    }

    @Test
    public void testUpdateWrapper() { //UpdateWrapper 主要是可以Set字段，用于 Update操作
        // 更新id为5, 6, 7的员工的薪水，加2000
        UpdateWrapper<Emp> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", 5, 6, 7)
                .setSql("salary = salary + 2000");
        empMapper.update(updateWrapper);

    }
    //上述已经弃用。最好不要写死

    @Test
    public void testLambdaQueryWrapper() {
        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Emp::getName, "李")
                .ge(Emp::getSalary, 5000)
                .select(Emp::getId, Emp::getName, Emp::getPhone, Emp::getSalary);
        List<Emp> empList = empMapper.selectList(queryWrapper);
        System.out.println(empList);
    }


    @Test
    public void testLambdaUpdateWrapper() {
        // 更新id为5, 6, 7的员工的薪水，加2000
        LambdaUpdateWrapper<Emp> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Emp::getId, 5, 6, 7)
                .setSql("salary = salary + 2000");
        empMapper.update(updateWrapper);
        //UPDATE emp SET salary = salary + 2000 WHERE (id IN (?,?,?))
    }

    @Test
    public void testLambdaQueryWrapper2() {
        // // 更新名为"李忠"的员工的薪水为9000
        Emp emp = new Emp();
        emp.setSalary(9000);
        LambdaUpdateWrapper<Emp> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Emp::getName, "李忠");
        empMapper.update(emp, updateWrapper);
    }
}
