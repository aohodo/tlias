package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.ClazzMapper;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageResult;
import com.itheima.service.ClazzService;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    @Autowired
    private EmpService empService;
    @Override
    public PageResult<Clazz> getPageResult(String name, LocalDate begin, LocalDate end, int page, int pageSize) {
        Page<Clazz> pageResult = Page.of(page,pageSize);
        pageResult = lambdaQuery().like(name != null && !name.isEmpty(), Clazz::getName, name)
                .between(begin != null && end != null, Clazz::getBeginDate, begin, end)
                .page(pageResult);
        List<Clazz> records =  pageResult.getRecords();
        List<Integer> masterIds = records.stream().map(Clazz::getMasterId).toList();
        if (!masterIds.isEmpty()) {
            //获得这里面的的MasterID
            List<Emp> masters = empService.list(); //单表查询增删改查就不需要字段了
            masters.forEach(emp -> {
                if (emp.getId() != null && masterIds.contains(emp.getId())) {
                    records.forEach(clazz -> {
                        if (clazz.getMasterId() != null && clazz.getMasterId().equals(emp.getId())) {
                            clazz.setMasterName(emp.getName());
                        }
                    });
                }
            });
        }
        //设置状态 班级状态 - 未开班 , 在读 , 已结课
//        当前时间在 开始 和 结束时间之间 那么在读
//                当前时间在 结束时间之后 那么已结课
//                当前时间在 开始时间之前 那么未开班
        LocalDate now = LocalDate.now();
        records.forEach(clazz -> {
            if (clazz.getBeginDate() != null && clazz.getEndDate() != null) {
                if (now.isBefore(clazz.getBeginDate())) {
                    clazz.setStatus("未开班"); // 未开班
                } else if (now.isAfter(clazz.getEndDate())) {
                    clazz.setStatus("已结课"); // 已结课
                } else {
                    clazz.setStatus("在读"); // 在读
                }
            }
        });
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    public Clazz getClazzById(Integer id) {
        Clazz clazz = getById(id);
        clazz.setUpdateTime(LocalDateTime.now());
        return clazz;
    }

}
