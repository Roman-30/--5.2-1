package ru.vsu.cs.musiczoneserver.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Import({BeanValidatorPluginsConfiguration.class, SpringDataRestConfiguration.class})
public class SwaggerConfig {

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    @Bean
    public Docket api(HttpServletRequest httpReq) {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo())
                .securitySchemes(List.of(apiKey()));
    }

    private ApiInfo metaInfo() {

        return new ApiInfo(
                "Music Zone API Documentation",
                "Documentation of the functionality of the mobile music application: \"Catalog of musical works recorded in electronic form\".",
                "1.0.0",
                "https://github.com/Roman-30/TP-5.2-1/blob/main/Документация/Technical%20task.pdf",
                new Contact("Команда TP_5.2-1", "https://github.com/Roman-30/TP-5.2-1", ""),
                "",
                "",
                new ArrayList<>()
        );
    }


//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
////                .forPaths(regex(DEFAULT_INCLUDE_PATTERN))
//                .build();
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope
//                = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return List.of(
//                new SecurityReference("JWT", authorizationScopes));
//    }
}
