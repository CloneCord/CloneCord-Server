package net.leloubil.clonecordserver.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI docket() {
        return new OpenAPI()
                .info(apiInfo())
                .components(new Components()
                        .addSecuritySchemes("user-auth", securityScheme())
                )
                .addSecurityItem(new SecurityRequirement().addList("user-auth"));
    }


    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    private Info apiInfo() {
        return new Info()
                .title("CloneCord API")
                .description("REST API to use CloneCord")
                .version("1.0.1")
                .license(new License().name("GPL"));
    }


}
