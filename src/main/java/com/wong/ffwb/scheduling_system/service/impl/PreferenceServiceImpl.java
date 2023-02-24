package com.wong.ffwb.scheduling_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import com.wong.ffwb.scheduling_system.constant.CommonConstant;
import com.wong.ffwb.scheduling_system.dao.PreferenceDao;
import com.wong.ffwb.scheduling_system.model.entity.Preference;
import com.wong.ffwb.scheduling_system.service.PreferenceService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 偏好信息(Preference)表服务实现类
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:18
 */
@Service("preferenceService")
public class PreferenceServiceImpl extends ServiceImpl<PreferenceDao, Preference> implements PreferenceService {
    @Override
    public BaseResponse<List<Preference>> selectByPage(PageRequest pageRequest, Preference preference) {
        Page<Preference> page = new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize());
        QueryWrapper<Preference> queryWrapper = new QueryWrapper<>(preference);
        queryWrapper.orderBy(StringUtils.hasText(pageRequest.getSortField()), pageRequest.getSortOrder().equals(CommonConstant.SORT_ORDER_ASC), pageRequest.getSortField());
        page(page, queryWrapper);
        return ResultUtils.success(page.getRecords());
    }

    @Override
    public BaseResponse<Preference> selectOne(Serializable id) {
        Preference preference = getById(id);
        return ResultUtils.success(preference);
    }

    @Override
    public BaseResponse<Boolean> insert(Preference preference) {
        boolean isSave = save(preference);
        return ResultUtils.success(isSave);
    }

    @Override
    public BaseResponse<Boolean> updateOne(Preference preference) {
        boolean isUpdate = updateById(preference);
        return ResultUtils.success(isUpdate);
    }

    @Override
    public BaseResponse<Boolean> delete(List<Long> idList) {
        boolean isRemove = removeByIds(idList);
        return ResultUtils.success(isRemove);
    }
}

