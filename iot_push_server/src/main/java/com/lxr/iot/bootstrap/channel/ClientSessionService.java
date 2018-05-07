package com.lxr.iot.bootstrap.channel;

import com.lxr.iot.bootstrap.bean.SessionMessage;
import com.lxr.iot.bootstrap.db.MessageDataBasePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 会话保留处理
 *
 * @author lxr
 * @create 2017-11-23 11:21
 **/
@Service
public class ClientSessionService {

    @Autowired
    private MessageDataBasePlugin dataBasePlugin;


    public  void saveSessionMsg(String deviceId, SessionMessage sessionMessage) {
        dataBasePlugin.saveSessionMsg(deviceId,sessionMessage);
    }

    public Set<SessionMessage> getByteBuf(String deviceId){
        return dataBasePlugin.getSessionMsg(deviceId);
    }
}
