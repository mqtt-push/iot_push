package com.lxr.iot.server.plugins;

import com.lxr.iot.enums.ConfirmStatus;
import com.lxr.iot.server.bean.RetainMessage;
import com.lxr.iot.server.bean.SendMqttMessage;
import com.lxr.iot.server.bean.SessionMessage;
import com.lxr.iot.server.bean.WillMeaasge;

import java.util.Collection;
import java.util.Set;

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
    void onlineDevice(String clientId, String serverNode);


    /**
     * 设备下线
     * @return
     */
    void offlineDevice(String client, String serverNode);

    /**
     * 保存保留消息
     * @param retainMessage
     * @return
     */
    Boolean saveRetainMessage(String topic, RetainMessage retainMessage, boolean isClean);

    /**
     * 根据主题获取保留消息
     * @param topic
     * @return
     */
    Collection<RetainMessage> getRetainMessage(String topic);

    /**
     * 保存消息记录
     * @param mqttMessageEntity
     * @return
     */
    Boolean saveMessage(SendMqttMessage mqttMessageEntity);


    /**
     * 保存确认消息
     * @param clientId
     * @param topic
     * @param packageId
     * @return
     */
    Boolean saveAckedMessage(String clientId, String topic, String packageId);


    /**
     * 保存订阅关系
     * @param clientId
     * @param topic
     * @param qos
     * @return
     */
    Boolean saveSub(String clientId, String[] topic, Integer qos);

    /**
     * 删除订阅主题
     * @param clientId
     * @param topics
     * @return
     */
    Boolean delSub(String clientId,String[] topics);

    /**
     * 清除当前设备的所有订阅
     * @param clientId
     */
    void clearSub(String clientId);


    /**
     * 通过订阅关系获取订阅的客户端ID
     * @param topics
     * @return
     */
    Collection<String> getSubClients(String[] topics);

    /**
     * 保存连接的session消息
     * @param deviceId
     * @param sessionMessage
     */
    void saveSessionMsg(String deviceId, SessionMessage sessionMessage);

    /***
     * 根据设备号获取session消息
     * @param deviceId
     * @return
     */
    Set<SessionMessage> getSessionMsg(String deviceId);


    /**
     * 获取设备的待确认消息
     * @param messageId
     */
    SendMqttMessage getClientAckMessage(String deviceId, int messageId);

    /**
     * 保存连接的待确认消息
     * @param deviceId
     * @param messageId
     * @param msg
     */
    void addClientAckMessage(String deviceId, int messageId, SendMqttMessage msg);

    /**
     * 删除客户的待确认消息
     * @param messageId
     */
    void removeClientAckMessage(String deviceId,Integer messageId);


    /**
     * 更新客户端的待确认消息
     * @param deviceId
     * @param messageId
     */
    void updateClientAckMessage(String deviceId,Integer messageId,ConfirmStatus status);

    /**
     * 添加客户端接收的消息
     * @param messageId
     * @param deviceId
     */
    void addClientReceiveMessage(String deviceId,int messageId);

    /**
     * 检查是否有这个消息
     * @param deviceId
     * @param messageId
     * @return
     */
    boolean checkClientReceiveMsg(String deviceId, int messageId);

    /**
     * 移除掉客户端收到的消息
     * @param deviceId
     * @param messageId
     * @return
     */
    boolean removeClientReceiveMsg(String deviceId, int messageId);

    /**
     * 保存客户端遗言消息
     * @param deviceId
     * @param meaasge
     */
    void saveClientWillMsg(String deviceId, WillMeaasge meaasge);

    /**
     * 获取客户端的遗言消息
     * @param deviceId
     * @return
     */
    WillMeaasge getClientWillMsg(String deviceId);

    /**
     * 删除客户端的遗言消息
     * @param deviceId
     */
    void removeClientWillMsg(String deviceId);

    /**
     * 根据设备编号查看待确认消息
     * @param device
     * @return
     */
    Collection<SendMqttMessage> getClientAckMessages(String device);
}
