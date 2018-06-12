package com.lxr.iot.plugin.mysql.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jason
 * @package com.lxr.iot.plugin.mysql.entity
 * @Description
 */
@Data
public class BaseEntity<T extends Model> extends Model<T> {

    public Long id;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    public Date updateTime;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    public Date createTime;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
