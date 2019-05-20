package com.example.mvc.controller;

import com.example.commons.utils.ApplicationContextUtil;
import com.example.commons.utils.ObjectMapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@Controller
@Api(value = "测试", tags = {"test"})
@RequestMapping("test")
public class HelloController {

    private final String template = "Hello, %s!";
    private final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @PostMapping("/s/{json}")
    @ResponseBody
    public Object testJason(@PathVariable String json) {
        try {
            ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
            return objectMapper.writeValueAsString(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @GetMapping("beanDefinitions")
    @ResponseBody
    public Object beanDefinitions() {
        String[] definitions = ApplicationContextUtil.getBeanDefinitions();
        ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
        try {
            return Objects.requireNonNull(objectMapper).writeValueAsString(definitions);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @GetMapping("/redis/put/{key}/{value}")
    @ResponseBody
    public Object redisPut(@PathVariable String key, @PathVariable String value) {
        return key + "-" + value;
    }

    @GetMapping("/redis/get/{key}")
    @ResponseBody
    public Object redisPut(@PathVariable String key) {
        StringRedisTemplate stringRedisTemplate = ApplicationContextUtil.getBean(StringRedisTemplate.class);
        RedisConnection redisConnection = stringRedisTemplate.getConnectionFactory().getConnection();
        return redisConnection.isPipelined();
    }
}
