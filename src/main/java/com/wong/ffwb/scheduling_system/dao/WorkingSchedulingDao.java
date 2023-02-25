package com.wong.ffwb.scheduling_system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wong.ffwb.scheduling_system.model.entity.WorkingScheduling;

import java.util.List;

/**
 * (WorkingScheduling)表数据库访问层
 *
 * @author Wongbuer
 * @since 2023-02-24 17:39:39
 */
public interface WorkingSchedulingDao extends BaseMapper<WorkingScheduling> {
    List<WorkingScheduling> selectWorkingSchedulingByDate(String dataStr);
}

