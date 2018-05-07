package com.lxr.iot.bootstrap.channel;

import com.lxr.iot.bootstrap.bean.WillMeaasge;
import com.lxr.iot.bootstrap.BaseApi;
import com.lxr.iot.bootstrap.ChannelService;
import com.lxr.iot.bootstrap.db.MessageDataBasePlugin;
import com.sun.scenario.effect.impl.prism.PrTexture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 遗嘱消息处理
 *
 * @author lxr
 * @create 2017-11-23 11:20
 **/
@Slf4j
@Component
@Setter
@Getter
@NoArgsConstructor
public class WillService  implements BaseApi {


    @Autowired
    private ChannelService channelService;

    @Autowired
    private MessageDataBasePlugin dataBasePlugin;

    /**
     *  deviceid -WillMeaasge
     */
    private static  ConcurrentHashMap<String,WillMeaasge> willMeaasges = new ConcurrentHashMap<>();



    /**
     * 保存遗嘱消息
     *  // 替换旧的
     */
    public void save(String deviceid, WillMeaasge build) {
//        willMeaasges.put(deviceid,build);
        dataBasePlugin.saveClientWillMsg(deviceid,build);
    }


    /**
     * 客户端断开连接后 开启遗嘱消息发送
     * @param deviceId
     */
    public void doSend( String deviceId) {
        WillMeaasge willMeaasge =dataBasePlugin.getClientWillMsg(deviceId);
        if(  StringUtils.isNotBlank(deviceId)&&(willMeaasge)!=null){
            // 发送遗嘱消息
            channelService.sendWillMsg(willMeaasge,deviceId);
            // 移除
            if(!willMeaasge.isRetain()){
                del(deviceId);
                log.info("deviceId will message["+willMeaasge.getWillMessage()+"] is removed");
            }
        }
    }

    /**
     * 删除遗嘱消息
     */
    public void del(String deviceid ) {
//        willMeaasges.remove(deviceid);
        dataBasePlugin.removeClientWillMsg(deviceid);
    }
}
