package com.wong.ffwb.scheduling_system.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 人流量信息(Traffic)表实体类
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:20
 */
@Data
@Schema
@SuppressWarnings("serial")
public class Traffic extends Model<Traffic> {
    //人流量id    
    @TableId(value = "traffic_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long trafficId;
    //门店id    
    @TableField("traffic_store_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long trafficStoreId;
    //日期信息    
    @TableField("traffic_date")
    private LocalDate trafficDate;
    //人流量阶段起始时间    
    @TableField("traffic_begin_time")
    private LocalTime trafficBeginTime;
    //人流量阶段结束时间    
    @TableField("traffic_end_time")
    private LocalTime trafficEndTime;
    //用户名    
    @TableField("traffic_count")
    private Double trafficCount;

}

