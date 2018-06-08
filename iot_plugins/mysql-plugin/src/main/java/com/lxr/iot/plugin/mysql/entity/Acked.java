package com.lxr.iot.plugin.mysql.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author null123
 * @since 2018-06-07
 */
@TableName("mqtt_acked")
public class Acked extends BaseEntity<Acked> {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private String clientid;
    /**
     * 订阅主题
     */
    private String topic;
    private String mid;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Acked{" +
        "clientid=" + clientid +
        ", topic=" + topic +
        ", mid=" + mid +
        "}";
    }
}
