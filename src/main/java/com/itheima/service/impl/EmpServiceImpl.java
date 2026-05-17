package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.DeptService;
import com.itheima.service.EmpExprService;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements EmpService {

    @Autowired
    private DeptService deptService;

    @Autowired
    private EmpExprService exprService;

    /**
     * 分页查询
     *
     * @param param 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<Emp> getPageResult(EmpQueryParam param) {
        Page<Emp> page = Page.of(param.getPage(), param.getPageSize());
        page.addOrder(OrderItem.desc("update_time"));

        //侯建条件，可以不需要new一个Wrapper对象。可以直接用。
        page = lambdaQuery().like(param.getName() != null && !param.getName().isEmpty(), Emp::getName, param.getName())
                .eq(param.getGender() != null, Emp::getGender, param.getGender())
                .between(param.getBegin() != null && param.getEnd() != null, Emp::getEntryDate, param.getBegin(), param.getEnd())
                .page(page);


        // 根据最后修改时间倒序排序

//        // 调用page方法完成分页查询（上面已经有page了）
//        page = page(page);

        List<Emp> records = page.getRecords();

        //获得所有部门的id
        List<Integer> deptIds = records.stream().map(Emp::getDeptId).toList();
        if (!deptIds.isEmpty()) {
            List<Dept> depts = deptService.listByIds(deptIds);
            depts.forEach(dept -> records.forEach(emp -> {
                if (emp.getDeptId() != null && emp.getDeptId().equals(dept.getId())) {
                    emp.setDeptName(dept.getName());
                }
            }));
        }
        // 封装结果并返回
        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveEmp(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        save(emp);
        List<EmpExpr> exprList = emp.getExprList();
        if (!exprList.isEmpty()) {
            exprList.forEach(expr -> {
                expr.setEmpId(emp.getId());
            });
            exprService.saveBatch(exprList);
        }
    }

    @Override
    public Emp getEmpByID(Integer id) {
        Emp emp = this.getById(id);
        emp.setExprList(exprService.list(
                new LambdaQueryWrapper<EmpExpr>().eq(EmpExpr::getEmpId, id)
        ));
        return emp;
    }

    @Override
    public void updateEmp(Emp emp) {
//        - 根据id更新员工的基本信息
//        - 根据id批量删除员工的工作经历信息
//        - 批量保存员工的工作经历信息
        emp.setUpdateTime(LocalDateTime.now());
        this.updateById(emp); //MybatisPlus智能更新，值为null的阶段不参与更新，比之前手写要快很多
        exprService.remove(new LambdaQueryWrapper<EmpExpr>().eq(EmpExpr::getEmpId, emp.getId()));
        List<EmpExpr> exprList = emp.getExprList();
        if (!exprList.isEmpty()) {
            exprList.forEach(expr -> {
                expr.setEmpId(emp.getId());
            }); //设置员工的ID。
            exprService.saveBatch(exprList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEmpByids(List<Integer> ids) {
        this.removeBatchByIds(ids);
        exprService.remove(new LambdaQueryWrapper<EmpExpr>().in(EmpExpr::getEmpId, ids));
    }

    @Override
    public List<Emp> findEmpListAll() {
        return this.list();
    }

}

