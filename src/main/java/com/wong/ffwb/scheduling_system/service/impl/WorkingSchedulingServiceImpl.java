package com.wong.ffwb.scheduling_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import com.wong.ffwb.scheduling_system.constant.CommonConstant;
import com.wong.ffwb.scheduling_system.dao.WorkingSchedulingDao;
import com.wong.ffwb.scheduling_system.model.entity.WorkingScheduling;
import com.wong.ffwb.scheduling_system.service.WorkingSchedulingService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * (WorkingScheduling)表服务实现类
 *
 * @author Wongbuer
 * @since 2023-02-24 17:39:40
 */
@Service("workingSchedulingService")
public class WorkingSchedulingServiceImpl extends ServiceImpl<WorkingSchedulingDao, WorkingScheduling> implements WorkingSchedulingService {
    @Override
    public BaseResponse<List<WorkingScheduling>> selectByPage(PageRequest pageRequest, WorkingScheduling workingScheduling) {
        Page<WorkingScheduling> page = new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize());
        QueryWrapper<WorkingScheduling> queryWrapper = new QueryWrapper<>(workingScheduling);
        queryWrapper.orderBy(StringUtils.hasText(pageRequest.getSortField()), pageRequest.getSortOrder().equals(CommonConstant.SORT_ORDER_ASC), pageRequest.getSortField());
        page(page, queryWrapper);
        return ResultUtils.success(page.getRecords());
    }

    @Override
    public BaseResponse<WorkingScheduling> selectOne(Serializable id) {
        WorkingScheduling workingScheduling = getById(id);
        return ResultUtils.success(workingScheduling);
    }

    @Override
    public BaseResponse<Boolean> insert(WorkingScheduling workingScheduling) {
        boolean isSave = save(workingScheduling);
        return ResultUtils.success(isSave);
    }

    @Override
    public BaseResponse<Boolean> updateOne(WorkingScheduling workingScheduling) {
        boolean isUpdate = updateById(workingScheduling);
        return ResultUtils.success(isUpdate);
    }

    @Override
    public BaseResponse<Boolean> delete(List<Long> idList) {
        boolean isRemove = removeByIds(idList);
        return ResultUtils.success(isRemove);
    }
}

