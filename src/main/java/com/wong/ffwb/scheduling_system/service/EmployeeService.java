package com.wong.ffwb.scheduling_system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.model.entity.Employee;

import java.io.Serializable;
import java.util.List;

/**
 * 员工信息(Employee)表服务接口
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:17
 */
public interface EmployeeService extends IService<Employee> {
    BaseResponse<List<Employee>> selectByPage(PageRequest pageRequest, Employee employee);

    BaseResponse<Employee> selectOne(Serializable id);

    BaseResponse<Boolean> insert(Employee employee);

    BaseResponse<Boolean> updateOne(Employee employee);

    BaseResponse<Boolean> delete(List<Long> idList);
}

