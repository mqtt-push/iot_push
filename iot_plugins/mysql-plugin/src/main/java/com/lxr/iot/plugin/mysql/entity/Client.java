package com.lxr.iot.plugin.mysql.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lxr.iot.plugin.mysql.enums.ClientState;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author null123
 * @since 2018-06-07
 */
@TableName("mqtt_client")
public class Client extends BaseEntity<Client> {

    private static final long serialVersionUID = 1L;

    private String clientid;
    private ClientState state;
    private String node;
    @TableField("online_at")
    private Date onlineAt;
    @TableField("offline_at")
    private Date offlineAt;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public ClientState getState() {
        return state;
    }

    public void setState(ClientState state) {
        this.state = state;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Date getOnlineAt() {
        return onlineAt;
    }

    public void setOnlineAt(Date onlineAt) {
        this.onlineAt = onlineAt;
    }

    public Date getOfflineAt() {
        return offlineAt;
    }

    public void setOfflineAt(Date offlineAt) {
        this.offlineAt = offlineAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Client{" +
        "clientid=" + clientid +
        ", state=" + state +
        ", node=" + node +
        ", onlineAt=" + onlineAt +
        ", offlineAt=" + offlineAt +
        "}";
    }
}
