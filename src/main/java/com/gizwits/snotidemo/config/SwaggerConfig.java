package com.gizwits.snotidemo.config;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * swagger 配置
 *
 * @author Jcxcc
 * @since 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket api() {
        return
                new Docket(DocumentationType.SWAGGER_2)
                        .select()
                        .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                        .paths(regex("^.*(?<!error)$"))
                        .build()
                        .securitySchemes(securityScheme())
                        .produces(ImmutableSet.of("application/json"))
                        .apiInfo(apiInfo());
    }

    private List<SecurityScheme> securityScheme() {
        SecurityScheme securityScheme = new ApiKey(swaggerProperties.getApiKey().getName(), swaggerProperties.getApiKey().getKeyName(), ApiKeyVehicle.HEADER.getValue());
        return
                Collections.singletonList(securityScheme);
    }

    private ApiInfo apiInfo() {
        return
                new ApiInfo(
                        swaggerProperties.getApiInfo().getTitle(),
                        swaggerProperties.getApiInfo().getDescription(),
                        swaggerProperties.getApiInfo().getVersion(),
                        swaggerProperties.getApiInfo().getTermsOfServiceUrl(),
                        new Contact(swaggerProperties.getApiInfo().getDeveloper(), swaggerProperties.getApiInfo().getDeveloperUrl(), swaggerProperties.getApiInfo().getDeveloperEmail()),
                        swaggerProperties.getApiInfo().getLicense(),
                        swaggerProperties.getApiInfo().getLicenseUrl(),
                        Collections.emptyList());
    }
}
