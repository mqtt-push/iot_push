package com.lxr.iot.enums;/**
 * Created by wangcy on 2018/1/6.
 */

/**
 * 确认状态
 *
 * @author lxr
 * @create 2018-01-06 17:15
 **/
public enum ConfirmStatus {
    PUB,
    PUBREC,
    PUBREL,
    COMPLETE;


    public static ConfirmStatus valueOf(int ordinal){
        ConfirmStatus[] values = ConfirmStatus.values();
        for( ConfirmStatus status : values){
            if(status.ordinal() == ordinal){
                return status;
            }
        }
        return null;
    }
}
