package com.lxr.iot.plugin.mysql.service;

import com.baomidou.mybatisplus.service.IService;
import com.lxr.iot.plugin.mysql.entity.Sub;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author null123
 * @since 2018-06-07
 */
public interface ISubService extends IService<Sub> {


    Boolean delSub(String clientId, String[] topics);


    Boolean saveSub(List<Sub> subs);

    Collection<String> selectSubClients(String[] topics);
}
