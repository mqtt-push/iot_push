package com.lxr.iot.db.entity;

import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import lombok.Data;

/**
 * @author jason
 * @package com.lxr.iot.db.entity
 * @Description
 */
@Data
public class MqttPublishEntity extends MqttMessageEntity {

    private Integer packetId;

    private String topicName;


    @Override
    public void convert(MqttMessage message) {
        super.convert(message);
        MqttPublishVariableHeader publishVariableHeader = (MqttPublishVariableHeader) message.variableHeader();
        this.topicName = publishVariableHeader.topicName();
        this.packetId = publishVariableHeader.packetId();
    }
}
