package com.example.springjwt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    private final String redisHost;
    private final int redisPort;
    public RedisConfig(@Value("${spring.data.redis.host}") final String redisHost, @Value("${spring.data.redis.port}")final int redisPort){
        this.redisHost=redisHost;
        this.redisPort=redisPort;
    }
}
