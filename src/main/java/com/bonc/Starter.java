package com.bonc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZK on 2016/12/6.
 */
@RestController
@SpringBootApplication
public class Starter{
    public static void main(String[] args) throws Exception{
        SpringApplication.run(Starter.class, args);
    }

}
