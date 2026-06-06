package com.example.collabapp;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CollabappApplication {

    @Value("${spring.data.mongodb.uri:NOT_FOUND}")
    private String mongoUri;

    @PostConstruct
    public void check() {
        System.out.println("==== MONGO URI LOADED: " + mongoUri);
    }

    public static void main(String[] args) {
        SpringApplication.run(CollabappApplication.class, args);
    }
}
