package com.wong.ffwb.scheduling_system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.model.entity.Employee;
import com.wong.ffwb.scheduling_system.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 员工信息(Employee)表控制层
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:17
 */
@Validated
@RestController
@RequestMapping("employee")
public class EmployeeController {
    /**
     * 服务对象
     */
    @Resource
    private EmployeeService employeeService;

    /**
     * 分页查询所有数据
     *
     * @param pageRequest 分页请求对象
     * @param employee    查询实体
     * @return 所有数据
     */
    @Operation(summary = "分页查询数据")
    @Parameters({
            @Parameter(name = "pageRequest", description = "分页请求对象"),
            @Parameter(name = "employee", description = "查询实体")
    })
    @GetMapping
    public BaseResponse<List<Employee>> selectByPage(@NotNull PageRequest pageRequest, Employee employee) {
        return employeeService.selectByPage(pageRequest, employee);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @Operation(summary = "通过主键查询单条数据")
    @Parameters({
            @Parameter(name = "id", description = "主键")
    })
    @GetMapping("{id}")
    public BaseResponse<Employee> selectOne(@NotNull @PathVariable Serializable id) {
        return employeeService.selectOne(id);
    }

    /**
     * 新增数据
     *
     * @param employee 实体对象
     * @return 新增结果
     */
    @Operation(summary = "新增数据")
    @Parameters({
            @Parameter(name = "employee", description = "实体对象")
    })
    @PostMapping
    public BaseResponse<Boolean> insert(@Valid @RequestBody Employee employee) {
        return employeeService.insert(employee);
    }

    /**
     * 修改数据
     *
     * @param employee 实体对象
     * @return 修改结果
     */
    @Operation(summary = "修改数据")
    @Parameters({
            @Parameter(name = "employee", description = "实体对象")
    })
    @PutMapping
    public BaseResponse<Boolean> update(@RequestBody Employee employee) {
        return employeeService.updateOne(employee);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @Operation(summary = "删除数据")
    @Parameters({
            @Parameter(name = "idList", description = "主键结合")
    })
    @DeleteMapping
    public BaseResponse<Boolean> delete(@NotEmpty @RequestParam("idList") List<Long> idList) {
        return employeeService.delete(idList);
    }
}

