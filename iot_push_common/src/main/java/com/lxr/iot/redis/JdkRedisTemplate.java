package com.lxr.iot.redis;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class JdkRedisTemplate extends RedisTemplate<String,Object> {

    public JdkRedisTemplate(RedisConnectionFactory connectionFactory) {
        setKeySerializer(new StringRedisSerializer());
        setValueSerializer(new RedisObjectSerializer());
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }


    @Override
    protected RedisConnection preProcessConnection(RedisConnection connection,
                                                   boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
