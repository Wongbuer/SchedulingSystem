package com.wong.ffwb.scheduling_system.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * (WorkingScheduling)表实体类
 *
 * @author Wongbuer
 * @since 2023-02-24 21:25:51
 */
@Data
@Schema
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("serial")
public class WorkingScheduling extends Model<WorkingScheduling> {
    //排班主键    
    @TableId(value = "scheduling_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("id")
    private Long schedulingId;
    //起始时间    
    @TableField("scheduling_begin_time")
    @JsonProperty("start")
    private LocalDateTime schedulingBeginTime;
    //终止时间    
    @TableField("scheduling_end_time")
    @JsonProperty("end")
    private LocalDateTime schedulingEndTime;
    //员工id    
    @TableField("scheduling_employee_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long schedulingEmployeeId;

    @TableField(exist = false)
    private Employee employee;

}

