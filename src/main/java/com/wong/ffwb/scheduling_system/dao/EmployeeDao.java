package com.wong.ffwb.scheduling_system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wong.ffwb.scheduling_system.model.entity.Employee;

import java.util.List;

/**
 * 员工信息(Employee)表数据库访问层
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:17
 */
public interface EmployeeDao extends BaseMapper<Employee> {
    List<Employee> selectBatchWithPreferenceContent();
    Employee selectOneWithPreferenceContent(Long employeeId);
}

