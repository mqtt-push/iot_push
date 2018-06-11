package com.lxr.iot.plugin.mysql.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lxr.iot.plugin.mysql.entity.ReceiveMsg;
import com.lxr.iot.plugin.mysql.mapper.ReceiveMsgMapper;
import com.lxr.iot.plugin.mysql.service.IReceiveMsgService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author null123
 * @since 2018-06-11
 */
@Service
public class ReceiveMsgServiceImpl extends ServiceImpl<ReceiveMsgMapper, ReceiveMsg> implements IReceiveMsgService {

}
