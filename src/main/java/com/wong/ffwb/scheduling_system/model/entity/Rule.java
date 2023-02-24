package com.wong.ffwb.scheduling_system.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * (Rule)表实体类
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:19
 */
@Data
@Schema
@SuppressWarnings("serial")
public class Rule extends Model<Rule> {
    //规则id    
    @TableId(value = "rule_id", type = IdType.ASSIGN_ID)
    private Integer ruleId;
    //规则所指店铺ID    
    @TableField("rule_store_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long ruleStoreId;
    //开店前的准备时间(默认值0.5)    
    @TableField("rule_prepare_time")
    private Double rulePrepareTime;
    //开店前的员工参数(默认值50)    
    @TableField("rule_prepare_number")
    private Integer rulePrepareNumber;
    //开店前的指定职位    
    @TableField("rule_prepare_position")
    private Integer rulePreparePosition;
    //员工需求参数(默认值3.8)    
    @TableField("rule_demand_number")
    private Double ruleDemandNumber;
    //工作时的指定职位    
    @TableField("rule_duty_position")
    private Integer ruleDutyPosition;
    //无客流时值班人数(默认值1)    
    @TableField("rule_duty_number")
    private Integer ruleDutyNumber;
    //关店前打点时间(默认值2)    
    @TableField("rule_close_time")
    private Double ruleCloseTime;
    //关店打点人数参数一(默认值30)    
    @TableField("rule_close_number1")
    private Integer ruleCloseNumber1;
    //关店打点人数参数二(默认值2)    
    @TableField("rule_close_number2")
    private Integer ruleCloseNumber2;
    //关店打点的指定职位    
    @TableField("rule_close_position")
    private Integer ruleClosePosition;

}

