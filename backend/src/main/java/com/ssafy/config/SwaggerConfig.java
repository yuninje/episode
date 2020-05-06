package com.ssafy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);
	
	@Bean
	public Docket api() {
		logger.trace("swagger ready..... %s", "준비중");
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("project")
				.apiInfo(info())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo info() {
		return new ApiInfoBuilder().title("Project Management API")
				.description("프로젝트를 위한 <b>CRUD</b>")
				.build();
	}
	
}
