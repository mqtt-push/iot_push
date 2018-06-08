package com.lxr.iot.plugin.mysql.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * @author jason
 * @package com.lxr.iot.plugin.mysql.enums
 * @Description
 */
public enum ClientState implements IEnum {
    ONLINE(0),OFFLINE(1);

    ClientState(int value) {
        this.value = value;
    }

    private  int value;
    @Override
    public Serializable getValue() {
        return value;
    }
}
