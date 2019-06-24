package com.romanidze.chesshorse.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.romanidze.chesshorse.controllers",
                               "com.romanidze.chesshorse.services"})
@ServletComponentScan(basePackages = {"com.romanidze.chesshorse.servlets"})
public class ChessHorseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChessHorseApplication.class, args);
    }

}
