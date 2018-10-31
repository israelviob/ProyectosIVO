package com.ol.exercise.exerciseol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ExerciseOlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExerciseOlApplication.class, args);

    }

}
