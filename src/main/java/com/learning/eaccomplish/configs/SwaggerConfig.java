package com.learning.eaccomplish.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket petApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo()).groupName("v1");
        ApiSelectorBuilder builder = docket.select();
        builder.apis(RequestHandlerSelectors.basePackage("com.learning.eaccomplish"));
        builder.paths(PathSelectors.any());
        return builder.build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("eaccomplish REST API's", "", "v1", "",
                new Contact("", "", ""), "", "", new ArrayList<>());
    }
}
