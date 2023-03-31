package com.dhmusic.DHMusic.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket api() {

        ApiInfo apiInfo = new ApiInfo(
                "DHMusic",
                "An API that provides services ",
                "1.0.0",
                "https://en.wikipedia.org/wiki/MIT_License",
                new Contact("Team1", "https://develhope.co", "team1.doe@develhope.co"),
                "DEV",
                "https://en.wikipedia.org/wiki/MIT_License",
                Collections.emptyList()
        );

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo)
                .tags(
                        new Tag("default-controller", "The main initial controller for all the API features"),
                        new Tag("Artist-controller", "Controller dedicated just to API > Artist"),
                        new Tag("User-controller", "Controller dedicated just to API > User"),
                        new Tag("Album-controller", "Controller dedicated just to API > Album"),
                        new Tag("Song-controller", "Controller dedicated just to API > Song")
                );
    }
}
