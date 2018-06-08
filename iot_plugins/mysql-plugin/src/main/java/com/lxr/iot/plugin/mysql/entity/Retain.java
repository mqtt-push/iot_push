package com.lxr.iot.plugin.mysql.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author null123
 * @since 2018-06-07
 */
@TableName("mqtt_retain")
public class Retain extends BaseEntity<Retain> {

    private static final long serialVersionUID = 1L;

    private String topic;
    private String msgid;
    private String sender;
    private String node;
    private Integer qos;
    private String payload;
    private Date arrived;


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Date getArrived() {
        return arrived;
    }

    public void setArrived(Date arrived) {
        this.arrived = arrived;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Retain{" +
        "topic=" + topic +
        ", msgid=" + msgid +
        ", sender=" + sender +
        ", node=" + node +
        ", qos=" + qos +
        ", payload=" + payload +
        ", arrived=" + arrived +
        "}";
    }
}
