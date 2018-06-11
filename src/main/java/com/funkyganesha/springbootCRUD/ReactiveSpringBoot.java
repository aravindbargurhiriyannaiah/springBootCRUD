package com.funkyganesha.springbootCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactiveSpringBoot {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringBoot.class, args);
    }
}
