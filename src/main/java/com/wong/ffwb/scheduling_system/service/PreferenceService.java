package com.wong.ffwb.scheduling_system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.model.entity.Preference;

import java.io.Serializable;
import java.util.List;

/**
 * 偏好信息(Preference)表服务接口
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:18
 */
public interface PreferenceService extends IService<Preference> {
    BaseResponse<List<Preference>> selectByPage(PageRequest pageRequest, Preference preference);

    BaseResponse<Preference> selectOne(Serializable id);

    BaseResponse<Boolean> insert(Preference preference);

    BaseResponse<Boolean> updateOne(Preference preference);

    BaseResponse<Boolean> delete(List<Long> idList);
}

