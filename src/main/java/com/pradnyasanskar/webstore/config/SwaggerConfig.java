package com.pradnyasanskar.webstore.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI webStoreOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()

                .info(new Info()

                        .title("Pradnyasanskar WebStore API")
                        .version("1.0.0")
                        .description("REST API Documentation for Pradnyasanskar Medical E-Commerce Backend")

                        .contact(new Contact()
                                .name("Backend Team")
                                .email("backend@pradnyasanskar.com"))

                        .license(new License()
                                .name("Pradnyasanskar Pvt. Ltd.")))

                .externalDocs(new ExternalDocumentation()
                        .description("Backend Documentation")
                        .url("https://example.com"))

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                )

                .schemaRequirement(
                        securitySchemeName,
                        new SecurityScheme()

                                .name(securitySchemeName)

                                .type(SecurityScheme.Type.HTTP)

                                .scheme("bearer")

                                .bearerFormat("JWT")
                );
    }
}