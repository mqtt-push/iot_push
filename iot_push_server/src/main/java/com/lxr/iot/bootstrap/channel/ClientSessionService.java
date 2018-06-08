package com.lxr.iot.bootstrap.channel;

import com.lxr.iot.server.bean.SessionMessage;
import com.lxr.iot.server.plugins.MessageDataBasePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    public Set<SessionMessage> getSessionMsg(String deviceId){
        return dataBasePlugin.getSessionMsg(deviceId);
    }
}
