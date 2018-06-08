package com.lxr.iot.plugin.mysql.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lxr.iot.plugin.mysql.entity.Sub;
import com.lxr.iot.plugin.mysql.mapper.SubMapper;
import com.lxr.iot.plugin.mysql.service.ISubService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Executors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author null123
 * @since 2018-06-07
 */
@Service
public class SubServiceImpl extends ServiceImpl<SubMapper, Sub> implements ISubService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delSub(String clientId, String[] topics) {
        for (String topic : topics){
            Wrapper<Sub> wrapper = new EntityWrapper<>();
            wrapper.eq("clientid",clientId).and().eq("topic",topic);
            baseMapper.delete(wrapper);
        }
        return true;
    }
}
