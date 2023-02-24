package com.wong.ffwb.scheduling_system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.model.entity.Rule;

import java.io.Serializable;
import java.util.List;

/**
 * (Rule)表服务接口
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:19
 */
public interface RuleService extends IService<Rule> {
    BaseResponse<List<Rule>> selectByPage(PageRequest pageRequest, Rule rule);

    BaseResponse<Rule> selectOne(Serializable id);

    BaseResponse<Boolean> insert(Rule rule);

    BaseResponse<Boolean> updateOne(Rule rule);

    BaseResponse<Boolean> delete(List<Long> idList);
}

