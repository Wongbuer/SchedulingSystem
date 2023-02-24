package com.wong.ffwb.scheduling_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import com.wong.ffwb.scheduling_system.constant.CommonConstant;
import com.wong.ffwb.scheduling_system.dao.StoreDao;
import com.wong.ffwb.scheduling_system.model.entity.Store;
import com.wong.ffwb.scheduling_system.service.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 门店信息(Store)表服务实现类
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:20
 */
@Service("storeService")
public class StoreServiceImpl extends ServiceImpl<StoreDao, Store> implements StoreService {
    @Override
    public BaseResponse<List<Store>> selectByPage(PageRequest pageRequest, Store store) {
        Page<Store> page = new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize());
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>(store);
        queryWrapper.orderBy(StringUtils.hasText(pageRequest.getSortField()), pageRequest.getSortOrder().equals(CommonConstant.SORT_ORDER_ASC), pageRequest.getSortField());
        page(page, queryWrapper);
        return ResultUtils.success(page.getRecords());
    }

    @Override
    public BaseResponse<Store> selectOne(Serializable id) {
        Store store = getById(id);
        return ResultUtils.success(store);
    }

    @Override
    public BaseResponse<Boolean> insert(Store store) {
        boolean isSave = save(store);
        return ResultUtils.success(isSave);
    }

    @Override
    public BaseResponse<Boolean> updateOne(Store store) {
        boolean isUpdate = updateById(store);
        return ResultUtils.success(isUpdate);
    }

    @Override
    public BaseResponse<Boolean> delete(List<Long> idList) {
        boolean isRemove = removeByIds(idList);
        return ResultUtils.success(isRemove);
    }
}

