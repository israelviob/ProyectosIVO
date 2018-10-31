/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ol.exercise.exerciseol.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author israel
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ol.exercise.exerciseol"))
                .paths(PathSelectors.ant("/*"))
                .build()                
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("EjercicioAPIREST", "Ejercicio reclutamiento OpenLegacy.", 
                "API OL", "Ejercicio prueba", new Contact("Israel Villasenor", "www.ivo.com", 
                        "israelviob@ol.com"), "API license URL", "API license URL");

    }
}
