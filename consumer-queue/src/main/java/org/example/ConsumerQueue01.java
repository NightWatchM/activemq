package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ConsumerQueue01 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerQueue01.class);
    }

}
