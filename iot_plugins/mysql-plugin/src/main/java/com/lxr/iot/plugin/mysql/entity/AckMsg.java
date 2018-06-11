package com.lxr.iot.plugin.mysql.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author null123
 * @since 2018-06-11
 */
@TableName("mqtt_ack_msg")
public class AckMsg extends BaseEntity<AckMsg> {

    private static final long serialVersionUID = 1L;

    private Integer mid;
    private String deviceId;
    private Integer confirmStatus;
    private Date arrive;
    private String content;
    private Boolean retain;
    private Integer qos;
    private String topic;


    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Date getArrive() {
        return arrive;
    }

    public void setArrive(Date arrive) {
        this.arrive = arrive;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getRetain() {
        return retain;
    }

    public void setRetain(Boolean retain) {
        this.retain = retain;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AckMsg{" +
        "mid=" + mid +
        ", deviceId=" + deviceId +
        ", confirmStatus=" + confirmStatus +
        ", arrive=" + arrive +
        ", content=" + content +
        ", retain=" + retain +
        ", qos=" + qos +
        ", topic=" + topic +
        "}";
    }
}
