package com.lxr.iot.plugin.mysql.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lxr.iot.plugin.mysql.entity.AckMsg;
import com.lxr.iot.plugin.mysql.mapper.AckMsgMapper;
import com.lxr.iot.plugin.mysql.service.IAckMsgService;
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
public class AckMsgServiceImpl extends ServiceImpl<AckMsgMapper, AckMsg> implements IAckMsgService {

}
