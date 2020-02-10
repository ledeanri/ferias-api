package com.ferias.api.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.ferias.api")).paths(regex("/api.*")).build()
				.apiInfo(metaInfo());
	}

	 private ApiInfo metaInfo() {
		 
	        ApiInfo apiInfo = new ApiInfo(
	                "Férias API REST",
	                "API REST para marcação de férias.",
	                "1.0",
	                "Terms of Service",
	                new Contact("Leandro Ribeiro", "https://github.com/ledeanri",
	                        "ledeanri@gmail.com"),
	                "Apache License Version 2.0",
	                "https://www.apache.org/licesen.html");

	        return apiInfo;
	    }


}
