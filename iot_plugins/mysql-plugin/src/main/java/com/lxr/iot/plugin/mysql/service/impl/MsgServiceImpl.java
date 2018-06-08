package com.lxr.iot.plugin.mysql.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lxr.iot.plugin.mysql.entity.Msg;
import com.lxr.iot.plugin.mysql.mapper.MsgMapper;
import com.lxr.iot.plugin.mysql.service.IMsgService;
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
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements IMsgService {

}
