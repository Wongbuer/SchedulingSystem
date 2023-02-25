package com.wong.ffwb.scheduling_system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.model.entity.Rule;
import com.wong.ffwb.scheduling_system.service.RuleService;
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
 * (Rule)表控制层
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:19
 */
@Validated
@RestController
@RequestMapping("rule")
public class RuleController {
    /**
     * 服务对象
     */
    @Resource
    private RuleService ruleService;

    /**
     * 分页查询所有数据
     *
     * @param pageRequest 分页请求对象
     * @param rule        查询实体
     * @return 所有数据
     */
    @Operation(summary = "分页查询数据")
    @Parameters({
            @Parameter(name = "pageRequest", description = "分页请求对象"),
            @Parameter(name = "rule", description = "查询实体")
    })
    @GetMapping
    public BaseResponse<List<Rule>> selectByPage(@NotNull PageRequest pageRequest, Rule rule) {
        return ruleService.selectByPage(pageRequest, rule);
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
    public BaseResponse<Rule> selectOne(@NotNull @PathVariable Serializable id) {
        return ruleService.selectOne(id);
    }

    /**
     * 新增数据
     *
     * @param rule 实体对象
     * @return 新增结果
     */
    @Operation(summary = "新增数据")
    @Parameters({
            @Parameter(name = "rule", description = "实体对象")
    })
    @PostMapping
    public BaseResponse<Boolean> insert(@Valid @RequestBody Rule rule) {
        return ruleService.insert(rule);
    }

    /**
     * 修改数据
     *
     * @param rule 实体对象
     * @return 修改结果
     */
    @Operation(summary = "修改数据")
    @Parameters({
            @Parameter(name = "rule", description = "实体对象")
    })
    @PutMapping
    public BaseResponse<Boolean> update(@RequestBody Rule rule) {
        return ruleService.updateOne(rule);
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
        return ruleService.delete(idList);
    }
}

