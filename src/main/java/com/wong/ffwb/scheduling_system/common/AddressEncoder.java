package com.wong.ffwb.scheduling_system.common;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AddressEncoder implements InitializingBean {
    @Value("${amap.key}")
    private String key;
    private static String accessKey;
    private static final String url = "https://restapi.amap.com/v3/geocode/geo";

    public static String convert(String address) {
        Map<String, Object> params = new HashMap<>();
        params.put("address", address);
        params.put("key", accessKey);
        String data = HttpUtil.createGet(url).form(params).execute().body();
        JSONObject entries = JSONUtil.parseObj(data);
        String location = (String) ((JSONObject) ((JSONArray) entries.get("geocodes")).get(0)).get("location");
        return location;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        accessKey = this.key;
    }
}
