package com.lxr.iot.plugin.mysql.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author null123
 * @since 2018-06-11
 */
@TableName("mqtt_receive_msg")
public class ReceiveMsg extends BaseEntity<ReceiveMsg> {

    private static final long serialVersionUID = 1L;

    private String deviceId;
    private Integer mid;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ReceiveMsg{" +
        "deviceId=" + deviceId +
        ", mid=" + mid +
        "}";
    }
}
