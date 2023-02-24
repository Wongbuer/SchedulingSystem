package com.wong.ffwb.scheduling_system.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.wong.ffwb.scheduling_system.common.TimeConverter;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TrafficRowModel {
    @ExcelProperty(index = 1)
    private String localDate;
    @ExcelProperty(index = 2, converter = TimeConverter.class)
    private LocalTime beginTime;
    @ExcelProperty(index = 3, converter = TimeConverter.class)
    private LocalTime endTime;
    @ExcelProperty(index = 4)
    private Double count;
}
