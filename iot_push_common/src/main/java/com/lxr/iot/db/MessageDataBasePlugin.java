package com.lxr.iot.db;

import com.lxr.iot.db.entity.MqttMessageEntity;

/**
 * @author jason
 * @package com.lxr.iot.db
 * @Description 进行消息持久化的接口，如果需要改变底层的存储实现集成此接口
 */
public interface MessageDataBasePlugin {


    /**
     * 设备上线
     * @return
     */
    void onlineDevice(String clientId,String serverNode);


    /**
     * 设备下线
     * @return
     */
    void offlineDevice(String client,String serverNode);

    /**
     * 保存保留消息
     * @param mqttMessageEntity
     * @return
     */
    Boolean saveRetainMessage(MqttMessageEntity mqttMessageEntity);

    /**
     * 保存消息记录
     * @param mqttMessageEntity
     * @return
     */
    Boolean saveMessage(MqttMessageEntity mqttMessageEntity);


    /**
     * 保存确认消息
     * @param clientId
     * @param topic
     * @param packageId
     * @return
     */
    Boolean saveAckedMessage(String clientId,String topic,String packageId);


    /**
     * 保存订阅关系
     * @param clientId
     * @param topic
     * @param qos
     * @return
     */
    Boolean saveSub(String clientId,String topic,Integer qos);
}
