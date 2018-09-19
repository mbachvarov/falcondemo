package com.falcon.springboot.falcondemo.config;

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

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }
    
    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "FALCON DEMO",
                "Simple data processing pipeline in the cloud.\n" + 
                "Application devolped in Java 8 using Spring Boot, Maven, MySQL database, Redis(Pub/Sub), Spring WebSockets implementation.",
                "0.0.1-SNAPSHOT",
                "",
                new Contact("Momchil Bachavrov","https://www.linkedin.com/in/momchil-bachvarov-814047b4/","momchilbuchvarov@abv.bg"),
                "",
                "",
                Collections.emptyList()
        );
    }
}


