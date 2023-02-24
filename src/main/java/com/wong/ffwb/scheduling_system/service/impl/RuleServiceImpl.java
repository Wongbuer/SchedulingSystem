package com.wong.ffwb.scheduling_system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.PageRequest;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import com.wong.ffwb.scheduling_system.constant.CommonConstant;
import com.wong.ffwb.scheduling_system.dao.RuleDao;
import com.wong.ffwb.scheduling_system.model.entity.Rule;
import com.wong.ffwb.scheduling_system.service.RuleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * (Rule)表服务实现类
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:19
 */
@Service("ruleService")
public class RuleServiceImpl extends ServiceImpl<RuleDao, Rule> implements RuleService {
    @Override
    public BaseResponse<List<Rule>> selectByPage(PageRequest pageRequest, Rule rule) {
        Page<Rule> page = new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize());
        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>(rule);
        queryWrapper.orderBy(StringUtils.hasText(pageRequest.getSortField()), pageRequest.getSortOrder().equals(CommonConstant.SORT_ORDER_ASC), pageRequest.getSortField());
        page(page, queryWrapper);
        return ResultUtils.success(page.getRecords());
    }

    @Override
    public BaseResponse<Rule> selectOne(Serializable id) {
        Rule rule = getById(id);
        return ResultUtils.success(rule);
    }

    @Override
    public BaseResponse<Boolean> insert(Rule rule) {
        boolean isSave = save(rule);
        return ResultUtils.success(isSave);
    }

    @Override
    public BaseResponse<Boolean> updateOne(Rule rule) {
        boolean isUpdate = updateById(rule);
        return ResultUtils.success(isUpdate);
    }

    @Override
    public BaseResponse<Boolean> delete(List<Long> idList) {
        boolean isRemove = removeByIds(idList);
        return ResultUtils.success(isRemove);
    }
}

