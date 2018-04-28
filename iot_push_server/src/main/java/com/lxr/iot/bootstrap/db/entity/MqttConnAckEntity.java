package com.lxr.iot.bootstrap.db.entity;

import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import lombok.Data;

/**
 * @author jason
 * @package com.lxr.iot.db.entity
 * @Description
 */
@Data
public class MqttConnAckEntity extends MqttMessageEntity {

    private Byte connectReturnCode;

    private Boolean sessionPresent;

    @Override
    public void convert(MqttMessage message) {
        super.convert(message);
        MqttConnAckVariableHeader connAckVariableHeader = (MqttConnAckVariableHeader) message.variableHeader();
        this.connectReturnCode = connAckVariableHeader.connectReturnCode().byteValue();
        this.sessionPresent = connAckVariableHeader.isSessionPresent();
    }
}
