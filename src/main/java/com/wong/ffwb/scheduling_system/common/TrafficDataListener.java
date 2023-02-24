package com.wong.ffwb.scheduling_system.common;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.wong.ffwb.scheduling_system.model.dto.TrafficRowModel;
import com.wong.ffwb.scheduling_system.model.entity.Traffic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TrafficDataListener implements ReadListener<TrafficRowModel> {
    public List<TrafficRowModel> list = new ArrayList<>(26);
    @Override
    public void invoke(TrafficRowModel trafficRowModel, AnalysisContext analysisContext) {
        list.add(trafficRowModel);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public static void main(String[] args) {
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
        });
    }

}
