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
@TableName("mqtt_sub")
public class Sub extends BaseEntity<Sub> {

    private static final long serialVersionUID = 1L;

    private String clientid;
    private String topic;
    private Integer qos;


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

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Sub{" +
                "clientid=" + clientid +
                ", topic=" + topic +
                ", qos=" + qos +
                "}";
    }
}
