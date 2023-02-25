package com.wong.ffwb.scheduling_system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.model.entity.WorkingScheduling;

import java.io.Serializable;
import java.util.List;

/**
 * (WorkingScheduling)表服务接口
 *
 * @author Wongbuer
 * @since 2023-02-24 17:39:39
 */
public interface WorkingSchedulingService extends IService<WorkingScheduling> {
    BaseResponse<List<WorkingScheduling>> selectByPage(PageRequest pageRequest, WorkingScheduling workingScheduling);

    BaseResponse<WorkingScheduling> selectOne(Serializable id);

    BaseResponse<Boolean> insert(WorkingScheduling workingScheduling);

    BaseResponse<Boolean> updateOne(WorkingScheduling workingScheduling);

    BaseResponse<Boolean> delete(List<Long> idList);
}

