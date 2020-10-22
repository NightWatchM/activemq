package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ConsumerTopic01 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerTopic01.class);
    }

}
