package com.lxr.iot;

import com.lxr.iot.plugin.mysql.MysqlDataBasePlugin;
import com.lxr.iot.server.plugins.MessageDataBasePlugin;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static org.springframework.boot.SpringApplication.run;

/**
 * 启动类
 *
 * @author lxr
 * @create 2017-11-18 13:54
 **/
@SpringBootApplication
@EnableAspectJAutoProxy //注解开启对aspectJ的支持
public class ServerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = run(ServerApplication.class, args);
    }


    @Bean
    @ConditionalOnMissingBean
    public MessageDataBasePlugin dataBasePlugin(){
        return new MysqlDataBasePlugin();
    }

}
