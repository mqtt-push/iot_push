package com.lxr.iot.redis;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis服务
 * @author Administrator
 */
@Component
public class RedisService {

    private RedisTemplate<String,Object> template;

    public RedisService(RedisTemplate redisTemplate) {
        this.template = redisTemplate;
    }

    /**
     * key-value存储
     * @param key
     * @param value
     */
    public void set(String key,Object value){
        template.opsForValue().set(key,value);
    }

    /**
     * 设置有效时间的key-value存储
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public void set(String key, Object value, long time, TimeUnit timeUnit){
        template.opsForValue().set(key,value,time,timeUnit);
    }


    /**
     * 根据key获取ksy-value
     * @param key
     * @return
     */
    public Object get(String key){
       return template.opsForValue().get(key);
    }

    /**
     *
     * 添加list元素
     * @param key
     * @param value
     */
    public void addList(String key,Object value){
        template.opsForList().leftPush(key,value);
    }

    /**
     * 删除key
     * @param key
     */
    public void remove(String key){
        template.delete(key);
    }

    /**
     * 获取总条数, 可用于分页
     * @param key
     * @return
     */
    public long getListSize(String key) {
        ListOperations<String, Object> listOps =  template.opsForList();
        return listOps.size(key);
    }

    /**
     * 获取list
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> getList(String key, long start, long end) {
        ListOperations<String, Object> listOps =  template.opsForList();
        return listOps.range(key, start, end);
    }





}
