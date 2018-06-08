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
@TableName("mqtt_msg")
public class Msg extends BaseEntity<Msg> {

    private static final long serialVersionUID = 1L;

    private String msgid;
    private String topic;
    private String sender;
    private String node;
    private Integer qos;
    private Integer retain;
    private String payload;


    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public Integer getRetain() {
        return retain;
    }

    public void setRetain(Integer retain) {
        this.retain = retain;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Msg{" +
        "msgid=" + msgid +
        ", topic=" + topic +
        ", sender=" + sender +
        ", node=" + node +
        ", qos=" + qos +
        ", retain=" + retain +
        ", payload=" + payload +
        "}";
    }
}
