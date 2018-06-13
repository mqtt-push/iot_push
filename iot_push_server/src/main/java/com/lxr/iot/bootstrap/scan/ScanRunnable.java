package com.lxr.iot.bootstrap.scan;

import com.lxr.iot.bootstrap.SessionManager;
import com.lxr.iot.server.bean.SendMqttMessage;
import com.lxr.iot.server.plugins.MessageDataBasePlugin;
import com.lxr.iot.enums.ConfirmStatus;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 扫描未确认信息
 *
 * @author lxr
 * @create 2018-01-06 16:50
 **/

@Slf4j
@Setter
public abstract class ScanRunnable  implements Runnable {

    protected MessageDataBasePlugin dataBasePlugin;

    private ConcurrentLinkedQueue<SendMqttMessage> queue  = new ConcurrentLinkedQueue<>();


    public  boolean addQueue(SendMqttMessage t){
        return queue.add(t);
    }

    public  boolean addQueues(List<SendMqttMessage> ts){
        return queue.addAll(ts);
    }


    @Override
    public void run() {
        for(;;){
            Collection<String> devices =  SessionManager.getInstance().getDevices();
            for (String device : devices){
               Collection<SendMqttMessage> messages = dataBasePlugin.getClientAckMessages(device);
               if(null!=messages && messages.size()>0){
                    for (SendMqttMessage mqttMessage : messages){
                        if(mqttMessage.getConfirmStatus() != ConfirmStatus.COMPLETE){
                            mqttMessage.setChannel(SessionManager.getInstance().getChannel(device).getChannel());
                            doInfo(mqttMessage);
                        }else{
                            dataBasePlugin.removeClientAckMessage(mqttMessage.getDeviceId(),mqttMessage.getMessageId());
                        }

                    }
               }
            }
        }
    }
    public  abstract  void  doInfo( SendMqttMessage poll);


}
