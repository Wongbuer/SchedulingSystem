package com.wong.ffwb.scheduling_system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.model.entity.Traffic;

import java.io.Serializable;
import java.util.List;

/**
 * 人流量信息(Traffic)表服务接口
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:20
 */
public interface TrafficService extends IService<Traffic> {
    BaseResponse<List<Traffic>> selectByPage(PageRequest pageRequest, Traffic traffic);

    BaseResponse<Traffic> selectOne(Serializable id);

    BaseResponse<Boolean> insert(Traffic traffic);

    BaseResponse<Boolean> updateOne(Traffic traffic);

    BaseResponse<Boolean> delete(List<Long> idList);
}

