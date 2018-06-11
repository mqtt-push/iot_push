package com.lxr.iot.plugin.mysql.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lxr.iot.plugin.mysql.entity.WillMsg;
import com.lxr.iot.plugin.mysql.mapper.WillMsgMapper;
import com.lxr.iot.plugin.mysql.service.IWillMsgService;
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
public class WillMsgServiceImpl extends ServiceImpl<WillMsgMapper, WillMsg> implements IWillMsgService {

}
