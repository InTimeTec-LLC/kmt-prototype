
package com.itt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration.
 * 
 * @author Rakesh Sawan
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Base package for the apis.
     */
    private static final String BASE_PACKAGE = "com.itt";

    /**
     * Swagger2 configuration.
     * 
     * @return Docket instance of Docket Bean
     */
    @Bean
    public Docket dbentityApi() {

        return new Docket(DocumentationType.SWAGGER_2).select().apis(
            RequestHandlerSelectors.basePackage(BASE_PACKAGE)).paths(PathSelectors.any()).build();

    }
}
