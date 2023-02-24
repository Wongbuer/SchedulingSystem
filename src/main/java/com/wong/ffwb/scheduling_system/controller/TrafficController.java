package com.wong.ffwb.scheduling_system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.model.entity.Traffic;
import com.wong.ffwb.scheduling_system.service.TrafficService;
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
 * 人流量信息(Traffic)表控制层
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:20
 */
@Validated
@RestController
@RequestMapping("traffic")
public class TrafficController {
    /**
     * 服务对象
     */
    @Resource
    private TrafficService trafficService;

    /**
     * 分页查询所有数据
     *
     * @param pageRequest 分页请求对象
     * @param traffic     查询实体
     * @return 所有数据
     */
    @Operation(summary = "分页查询数据")
    @Parameters({
            @Parameter(name = "pageRequest", description = "分页请求对象"),
            @Parameter(name = "traffic", description = "查询实体")
    })
    @GetMapping
    public BaseResponse<List<Traffic>> selectByPage(@NotNull PageRequest pageRequest, Traffic traffic) {
        return trafficService.selectByPage(pageRequest, traffic);
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
    public BaseResponse<Traffic> selectOne(@NotNull @PathVariable Serializable id) {
        return trafficService.selectOne(id);
    }

    /**
     * 新增数据
     *
     * @param traffic 实体对象
     * @return 新增结果
     */
    @Operation(summary = "新增数据")
    @Parameters({
            @Parameter(name = "traffic", description = "实体对象")
    })
    @PostMapping
    public BaseResponse<Boolean> insert(@Valid @RequestBody Traffic traffic) {
        return trafficService.insert(traffic);
    }

    /**
     * 修改数据
     *
     * @param traffic 实体对象
     * @return 修改结果
     */
    @Operation(summary = "修改数据")
    @Parameters({
            @Parameter(name = "traffic", description = "实体对象")
    })
    @PutMapping
    public BaseResponse<Boolean> update(@RequestBody Traffic traffic) {
        return trafficService.updateOne(traffic);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @Operation(summary = "删除数据”")
    @Parameters({
            @Parameter(name = "idList", description = "主键结合")
    })
    @DeleteMapping
    public BaseResponse<Boolean> delete(@NotEmpty @RequestParam("idList") List<Long> idList) {
        return trafficService.delete(idList);
    }
}

