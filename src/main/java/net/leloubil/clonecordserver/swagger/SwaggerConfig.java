package net.leloubil.clonecordserver.swagger;

import net.leloubil.clonecordserver.security.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.List;
import java.util.function.Predicate;

@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
                .useDefaultResponseMessages(false)
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("net.leloubil"))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", SecurityConstants.HEADER_STRING,"header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(Predicate.not(PathSelectors.ant("/auth/*")))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] scopes = {authorizationScope};
        return List.of(new SecurityReference("JWT", scopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CloneCord API")
                .description("REST API to use CloneCord")
                .version("1.0.1")
                .license("GPL")
                .build();
    }


}
