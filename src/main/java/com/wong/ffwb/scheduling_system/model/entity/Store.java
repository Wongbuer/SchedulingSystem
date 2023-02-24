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
 * 门店信息(Store)表实体类
 *
 * @author Wongbuer
 * @since 2023-02-18 17:08:20
 */
@Data
@Schema
@SuppressWarnings("serial")
public class Store extends Model<Store> {
    //门店id    
    @TableId(value = "store_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long storeId;
    //门店名称    
    @TableField("store_name")
    @NotBlank(message = "storeName不能为空")
    @Size(max = 256, message = "storeName长度不能超过256")
    private String storeName;
    //门店地址    
    @TableField("store_address")
    @NotBlank(message = "storeAddress不能为空")
    @Size(max = 256, message = "storeAddress长度不能超过256")
    private String storeAddress;
    //门店人流量表id    
    @TableField("store_traffic_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long storeTrafficId;
    //门店面积    
    @TableField("store_size")
    private Double storeSize;

}

