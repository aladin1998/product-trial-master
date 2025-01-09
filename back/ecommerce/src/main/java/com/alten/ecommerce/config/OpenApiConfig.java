package com.alten.ecommerce.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    GroupedOpenApi productsApi() {
        return GroupedOpenApi
                .builder()
                .addOpenApiCustomizer(apiCustomizer -> apiCustomizer.info(new Info().description("ALTEN Ecommerce API")))
                .group("ALTEN Ecommerce")
                .packagesToScan("com.alten.ecommerce")
                .build();
    }

}