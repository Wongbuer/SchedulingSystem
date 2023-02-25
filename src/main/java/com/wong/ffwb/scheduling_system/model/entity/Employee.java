package com.wong.ffwb.scheduling_system.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wong.ffwb.scheduling_system.model.dto.PreferenceContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 员工信息(Employee)表实体类
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:17
 */
@Data
@Schema
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("serial")
public class Employee extends Model<Employee> {
    //员工ID    
    @TableId(value = "employee_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long employeeId;
    //员工姓名    
    @TableField("employee_name")
    @NotBlank(message = "employeeName不能为空")
    @Size(max = 256, message = "employeeName长度不能超过256")
    private String employeeName;
    //员工邮箱    
    @TableField("employee_email")
    @NotBlank(message = "employeeEmail不能为空")
    @Size(max = 256, message = "employeeEmail长度不能超过256")
    private String employeeEmail;
    //员工职位    
    @TableField("employee_position")
    private Integer employeePosition;
    //员工偏好id    
    @TableField("employee_preference_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long employeePreferenceId;
    //员工所属门店id    
    @TableField("employee_store_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long employeeStoreId;
    //用户性别    
    @TableField("employee_gender")
    @NotBlank(message = "employeeGender不能为空")
    @Size(max = 5, message = "employeeGender长度不能超过5")
    private String employeeGender;
    @TableField(exist = false)
    private PreferenceContent preferenceContent;

}

