package com.wong.ffwb.scheduling_system.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wong.ffwb.scheduling_system.common.PreferenceTypeHandler;
import com.wong.ffwb.scheduling_system.model.dto.PreferenceContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 偏好信息(Preference)表实体类
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:18
 */
@Data
@Schema
@SuppressWarnings("serial")
public class Preference extends Model<Preference> {
    //偏好id    
    @TableId(value = "pref_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long prefId;
    //偏好内容(JSON)    
//    @TableField("pref_content")
//    @NotBlank(message = "prefContent不能为空")
//    @Size(max = 256, message = "prefContent长度不能超过256")
//    private String prefContent;

    @TableField(value = "pref_content", typeHandler = PreferenceTypeHandler.class)
    private PreferenceContent preferenceContent;
}

