package com.lxr.iot.plugin.mysql.common;

import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.CachePaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author jesus
 * @date 2017/7/10
 */

@Configuration
@MapperScan("com.lxr.iot.plugin.mysql.mapper")
public class MybatisPlusConfig {

    @Bean
    public CachePaginationInterceptor paginationInterceptor() {
        CachePaginationInterceptor paginationInterceptor = new CachePaginationInterceptor();
        paginationInterceptor.setDialectType(DBType.MYSQL.getDb());
        paginationInterceptor.setLocalPage(true);
        return paginationInterceptor;
    }




}