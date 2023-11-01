package com.example.maintenance;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import util.Test;

@SpringBootApplication
@ComponentScan({"util", "controller", "entity", "repository", "service"})
@EnableJpaRepositories("repository")
@EntityScan("entity")
public class MaintenanceApplication {
    @Autowired
    private Test test;

    public static void main(String[] args) {
        SpringApplication.run(MaintenanceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        test.test();
    }
}
