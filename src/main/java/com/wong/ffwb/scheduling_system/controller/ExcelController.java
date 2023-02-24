package com.wong.ffwb.scheduling_system.controller;

import com.alibaba.excel.EasyExcel;
import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import com.wong.ffwb.scheduling_system.common.TrafficDataListener;
import com.wong.ffwb.scheduling_system.model.dto.TrafficRowModel;
import com.wong.ffwb.scheduling_system.model.entity.Traffic;
import com.wong.ffwb.scheduling_system.service.TrafficService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("excel")
public class ExcelController {
    @Resource
    private TrafficService trafficService;

    @GetMapping("/read}")
    public BaseResponse<?> readExcel() {
        TrafficDataListener trafficDataListener = new TrafficDataListener();
        EasyExcel.read("D:/download/image.xlsx", TrafficRowModel.class,  trafficDataListener).sheet().doRead();
        trafficDataListener.list.forEach(trafficRowModel -> {
            LocalDate date = LocalDate.parse(trafficRowModel.getLocalDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Traffic traffic = new Traffic();
            traffic.setTrafficDate(date);
            traffic.setTrafficCount(trafficRowModel.getCount());
            traffic.setTrafficStoreId(1627957171406266370L);
            traffic.setTrafficBeginTime(trafficRowModel.getBeginTime());
            traffic.setTrafficEndTime(trafficRowModel.getEndTime());
            trafficService.save(traffic);
        });
        return ResultUtils.success("ok");
    }
}
