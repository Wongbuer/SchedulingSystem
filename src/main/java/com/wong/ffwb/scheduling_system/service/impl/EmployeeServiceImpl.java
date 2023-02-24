package com.wong.ffwb.scheduling_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import com.wong.ffwb.scheduling_system.constant.CommonConstant;
import com.wong.ffwb.scheduling_system.dao.EmployeeDao;
import com.wong.ffwb.scheduling_system.model.entity.Employee;
import com.wong.ffwb.scheduling_system.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 员工信息(Employee)表服务实现类
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:18
 */
@Service("employeeService")
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {
    @Override
    public BaseResponse<List<Employee>> selectByPage(PageRequest pageRequest, Employee employee) {
        Page<Employee> page = new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize());
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>(employee);
        queryWrapper.orderBy(StringUtils.hasText(pageRequest.getSortField()), pageRequest.getSortOrder().equals(CommonConstant.SORT_ORDER_ASC), pageRequest.getSortField());
        page(page, queryWrapper);
        return ResultUtils.success(page.getRecords());
    }

    @Override
    public BaseResponse<Employee> selectOne(Serializable id) {
        Employee employee = getById(id);
        return ResultUtils.success(employee);
    }

    @Override
    public BaseResponse<Boolean> insert(Employee employee) {
        boolean isSave = save(employee);
        return ResultUtils.success(isSave);
    }

    @Override
    public BaseResponse<Boolean> updateOne(Employee employee) {
        boolean isUpdate = updateById(employee);
        return ResultUtils.success(isUpdate);
    }

    @Override
    public BaseResponse<Boolean> delete(List<Long> idList) {
        boolean isRemove = removeByIds(idList);
        return ResultUtils.success(isRemove);
    }
}

