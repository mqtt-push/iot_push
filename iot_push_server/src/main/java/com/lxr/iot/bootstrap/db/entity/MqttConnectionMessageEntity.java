package com.lxr.iot.bootstrap.db.entity;

import io.netty.handler.codec.mqtt.MqttConnectVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import lombok.Data;

/**
 * @author jason
 * @package com.lxr.iot.db.entity
 * @Description
 */
@Data
public class MqttConnectionMessageEntity extends MqttMessageEntity {

    private String name;

    private int version;

    private Boolean hasUserName;

    private Boolean hasPassword;

    private Boolean isWillRetain;

    private Integer willQos;

    private Boolean isWillFlag;

    private Boolean isCleanSession;

    private Integer keepAliveTimeSeconds;


    @Override
    public void convert(MqttMessage message) {
        super.convert(message);
        MqttConnectVariableHeader connectVariableHeader = (MqttConnectVariableHeader) message.variableHeader();
        this.name = connectVariableHeader.name();
        this.version = connectVariableHeader.version();
        this.hasUserName = connectVariableHeader.hasUserName();
        this.hasPassword = connectVariableHeader.hasPassword();
        this.isWillRetain = connectVariableHeader.isWillRetain();
        this.willQos = connectVariableHeader.willQos();
        this.isWillFlag = connectVariableHeader.isWillFlag();
        this.isCleanSession = connectVariableHeader.isCleanSession();
        this.keepAliveTimeSeconds = connectVariableHeader.keepAliveTimeSeconds();

    }
}
