package com.example.accessing_data_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.accessing_data_jpa")
public class ConflictTrackerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConflictTrackerApp.class, args);
    }
}
