package com.lxr.iot.plugin.mysql.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lxr.iot.plugin.mysql.entity.Sub;
import com.lxr.iot.plugin.mysql.mapper.SubMapper;
import com.lxr.iot.plugin.mysql.service.ISubService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSub(List<Sub> subs) {
        for (Sub sub : subs){
            if(baseMapper.selectOne(sub)==null){
                baseMapper.insert(sub);
            }
        }
        return true;
    }

    @Override
    public Collection<String> selectSubClients(String[] topics) {
        List<String> clients = new ArrayList<>();
        for (String topic : topics){
            Wrapper<Sub> wrapper = new EntityWrapper<>();
            wrapper.eq("topic",topic);
            Optional.ofNullable(baseMapper.selectList(wrapper))
                    .ifPresent(subs->subs.forEach(sub->clients.add(sub.getClientid())));
        }
        return clients;
    }
}
