package com.onlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(
        scanBasePackages = {
                "com.onlineshop.controller",
                "com.onlineshop.config",
                "com.onlineshop.service"}
)
@PropertySources({
        @PropertySource(value = {
                "classpath:datasource.properties",
                "file:/C:/JavaProperties/onlineshop/datasource.properties"
        }, ignoreResourceNotFound = true),
        @PropertySource(value = {
                "classpath:services.properties",
                "file:/C:/JavaProperties/onlineshop/service.properties"
        }, ignoreResourceNotFound = true)
})
@EnableSwagger2
@EnableTransactionManagement
public class OnlineShopApplication {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.onlineshop"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("OnlineShop")
                .description("API приложения интернет магазин")
                .version("1.0")
                .termsOfServiceUrl("")
                .license("")
                .licenseUrl("")
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopApplication.class, args);
    }

}

