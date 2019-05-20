package com.example.mvc.lifecycle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class Clr implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(Clr.class);

    /**
     * Callback used to run the bean.
     *
     * @param args incomig main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        logger.info("commandLineRunner");
    }
}
