package com.example.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.CountDownLatch;

//@SpringBootApplication
public class RedisApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisApplication.class);

    public static void main(String[] args) throws InterruptedException {

        ConfigurableApplicationContext ctx = SpringApplication.run(RedisApplication.class, args);
        StringRedisTemplate redisTemplate = ctx.getBean(StringRedisTemplate.class);
        CountDownLatch countDownLatch = ctx.getBean(CountDownLatch.class);
        LOGGER.info("Sending message...");
        redisTemplate.convertAndSend("chat", "Hello from redis");
        countDownLatch.await();

    }

}
