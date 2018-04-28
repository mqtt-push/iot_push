package com.lxr.iot.db.entity;

import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;

/**
 * @author jason
 * @package com.lxr.iot.db.entity
 * @Description
 */
public class MqttMessageEntity {

    /**
     * 操作的服务器名称
     */
    private String serverId;

    /**
     * 消息创建时间
     */
    private String createTime;

    /**
     * 消息类型
     */
    private Integer messageType;
    /**
     * 是否保留消息
     */
    private Boolean isRetain;

    /**
     * 质量等级
     */
    private Integer qosLevel;

    /**
     * 消息发送的通道
     */
    private String topic;

    /**
     * 打开标志
     */
    private Boolean isDup;

    /**
     * 发送消息设备
     */
    private String from;

    /**
     * 消息ID
     */
    private String packetId;

    /**
     * 消息内容
     */
    private Object payload;

    /**
     * 消息ID
     */
    protected Integer messageId;


    public  void convert(MqttMessage message){
        this.isRetain = message.fixedHeader().isRetain();
        this.qosLevel = message.fixedHeader().qosLevel().value();
        this.isDup = message.fixedHeader().isDup();
        this.payload = message.payload();
        if(message.variableHeader() instanceof MqttMessageIdVariableHeader){
            MqttMessageIdVariableHeader idVariableHeader = (MqttMessageIdVariableHeader) message.variableHeader();
            this.messageId = idVariableHeader.messageId();
        }
    }
}
