package net.leloubil.clonecordserver.swagger;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import net.leloubil.clonecordserver.security.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@SuppressWarnings("Guava")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
                .useDefaultResponseMessages(false)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .select()
                .paths(Predicates.not(PathSelectors.ant("/actuator/**"))) //disable spring metrics on swagger
                .apis(RequestHandlerSelectors.any())
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", SecurityConstants.HEADER_STRING,"header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(Predicates.not(PathSelectors.ant("/auth/*")))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] scopes = {authorizationScope};
        return Lists.newArrayList(new SecurityReference("JWT",scopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CloneCord API")
                .description("REST API to use CloneCord")
                .version("1.0.0")
                .license("GPL")
                .build();
    }


}
