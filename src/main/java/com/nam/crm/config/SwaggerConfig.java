package com.nam.crm.config;

import static com.google.common.collect.Lists.newArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("API Reference").version("1.0.0").build();
	}

	@Bean
	public Docket customImplementation() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).securitySchemes(newArrayList(apiKey()))
				.select().paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.nam.crm.web.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("Authorization", "", "header"); 
	}

	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration("emailSecurity_client", "secret", "Spring", "emailSecurity", "",
				ApiKeyVehicle.HEADER, "", ",");
	}

}
