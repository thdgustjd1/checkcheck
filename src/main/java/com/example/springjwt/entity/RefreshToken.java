package com.example.springjwt.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RequiredArgsConstructor
@RedisHash(value = "RefreshToken",timeToLive = 60*60*24*7)
public class RefreshToken {

    @Id
    private final String refreshToken;
    private final String username;
}
