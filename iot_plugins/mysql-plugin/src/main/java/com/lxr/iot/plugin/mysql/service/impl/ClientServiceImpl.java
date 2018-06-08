package com.lxr.iot.plugin.mysql.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lxr.iot.plugin.mysql.entity.Client;
import com.lxr.iot.plugin.mysql.mapper.ClientMapper;
import com.lxr.iot.plugin.mysql.service.IClientService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author null123
 * @since 2018-06-07
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements IClientService {

}
