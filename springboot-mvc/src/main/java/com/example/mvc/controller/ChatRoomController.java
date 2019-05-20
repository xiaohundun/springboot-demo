package com.example.mvc.controller;
/*
 * chou created at 2019-03-27
 * @Description:
 * */

import com.example.commons.utils.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

@RestController("chatRoom")
public class ChatRoomController {
    private final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("chatting")
    public Object chat() throws InterruptedException {
        StringRedisTemplate redisTemplate = ApplicationContextUtil.getBean(StringRedisTemplate.class);
        CountDownLatch countDownLatch = ApplicationContextUtil.getBean(CountDownLatch.class);
        LOGGER.info("Sending message...");
        redisTemplate.convertAndSend("chat", "Hello from redis");
        countDownLatch.await();
        return "";
    }
}
