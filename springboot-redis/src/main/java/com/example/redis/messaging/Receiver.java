package com.example.redis.messaging;
/*
 * chou created at 2019-03-27
 * @Description:
 * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;


public class Receiver {

    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch countDownLatch;

    @Autowired
    public Receiver(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void receiveMessage(String message) {
        logger.info("Received <" + message + ">");
        countDownLatch.countDown();
    }
}
