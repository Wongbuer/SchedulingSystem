package com.wong.ffwb.scheduling_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import com.wong.ffwb.scheduling_system.constant.CommonConstant;
import com.wong.ffwb.scheduling_system.dao.TrafficDao;
import com.wong.ffwb.scheduling_system.model.entity.Traffic;
import com.wong.ffwb.scheduling_system.service.TrafficService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 人流量信息(Traffic)表服务实现类
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:20
 */
@Service("trafficService")
public class TrafficServiceImpl extends ServiceImpl<TrafficDao, Traffic> implements TrafficService {
    @Override
    public BaseResponse<List<Traffic>> selectByPage(PageRequest pageRequest, Traffic traffic) {
        Page<Traffic> page = new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize());
        QueryWrapper<Traffic> queryWrapper = new QueryWrapper<>(traffic);
        queryWrapper.orderBy(StringUtils.hasText(pageRequest.getSortField()), pageRequest.getSortOrder().equals(CommonConstant.SORT_ORDER_ASC), pageRequest.getSortField());
        page(page, queryWrapper);
        return ResultUtils.success(page.getRecords());
    }

    @Override
    public BaseResponse<Traffic> selectOne(Serializable id) {
        Traffic traffic = getById(id);
        return ResultUtils.success(traffic);
    }

    @Override
    public BaseResponse<Boolean> insert(Traffic traffic) {
        boolean isSave = save(traffic);
        return ResultUtils.success(isSave);
    }

    @Override
    public BaseResponse<Boolean> updateOne(Traffic traffic) {
        boolean isUpdate = updateById(traffic);
        return ResultUtils.success(isUpdate);
    }

    @Override
    public BaseResponse<Boolean> delete(List<Long> idList) {
        boolean isRemove = removeByIds(idList);
        return ResultUtils.success(isRemove);
    }
}

