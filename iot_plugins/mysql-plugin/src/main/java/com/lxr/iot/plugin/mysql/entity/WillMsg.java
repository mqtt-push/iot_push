package com.lxr.iot.plugin.mysql.entity;

import com.baomidou.mybatisplus.annotations.TableField;
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
@TableName("mqtt_will_msg")
public class WillMsg extends BaseEntity<WillMsg> {

    private static final long serialVersionUID = 1L;

    private String deviceId;
    private String willTopic;
    private String willMessage;
    private Integer qos;
    private boolean retain;

    @TableField("creeate_time")
    private Date creeateTime;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getWillTopic() {
        return willTopic;
    }

    public void setWillTopic(String willTopic) {
        this.willTopic = willTopic;
    }

    public String getWillMessage() {
        return willMessage;
    }

    public void setWillMessage(String willMessage) {
        this.willMessage = willMessage;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public boolean isRetain() {
        return retain;
    }

    public void setRetain(boolean retain) {
        this.retain = retain;
    }

    public Date getCreeateTime() {
        return creeateTime;
    }

    public void setCreeateTime(Date creeateTime) {
        this.creeateTime = creeateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WillMsg{" +
        "willTopic=" + willTopic +
        ", willMessage=" + willMessage +
        ", qos=" + qos +
        ", retain=" + retain +
        ", creeateTime=" + creeateTime +
        "}";
    }
}
