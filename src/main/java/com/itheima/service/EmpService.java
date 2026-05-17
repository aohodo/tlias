package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface EmpService extends IService<Emp> {

    PageResult<Emp> getPageResult(EmpQueryParam param);
    void saveEmp(Emp emp);

    Emp getEmpByID(Integer id);

    void updateEmp(Emp emp);

    void deleteEmpByids(List<Integer> ids);

    List<Emp> findEmpListAll();
}
