package com.wong.ffwb.scheduling_system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.model.entity.Store;

import java.io.Serializable;
import java.util.List;

/**
 * 门店信息(Store)表服务接口
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:20
 */
public interface StoreService extends IService<Store> {
    BaseResponse<List<Store>> selectByPage(PageRequest pageRequest, Store store);

    BaseResponse<Store> selectOne(Serializable id);

    BaseResponse<Boolean> insert(Store store);

    BaseResponse<Boolean> updateOne(Store store);

    BaseResponse<Boolean> delete(List<Long> idList);
}

