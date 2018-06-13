package com.lxr.iot.server.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lxr.iot.enums.ConfirmStatus;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * mqtt 消息
 *
 * @author lxr
 * @create 2018-01-17 19:54
 **/
@Builder
@Data
public class SendMqttMessage implements Serializable {

    private int messageId;

    private String deviceId;

    private String node;

    @JsonIgnore
    private Channel channel;

    private volatile ConfirmStatus confirmStatus;

    private long time;

    private byte[]  byteBuf;

    private boolean isRetain;

    private MqttQoS qos;

    private String topic;

}
