package com.wong.ffwb.scheduling_system.common;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.wong.ffwb.scheduling_system.model.dto.PreferenceContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@MappedTypes({PreferenceContent.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class PreferenceTypeHandler extends AbstractJsonTypeHandler<PreferenceContent> {
    @Override
    protected PreferenceContent parse(String json) {
        return JSONUtil.toBean(json, PreferenceContent.class);
    }

    @Override
    protected String toJson(PreferenceContent obj) {
        return JSONUtil.toJsonStr(obj);
    }
}