package com.ssafy.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

import com.fasterxml.classmate.TypeResolver;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);
	@Autowired
	private TypeResolver typeResolver;
	
	@Bean
	public Docket api() {
		logger.trace("swagger ready..... %s", "준비중");
		
		return new Docket(DocumentationType.SWAGGER_2)
				.alternateTypeRules(
						AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), 
								typeResolver.resolve(Page.class)))
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
	
	@Getter
	@Setter
	@ApiModel
	static class Page {
	    @ApiModelProperty(value = "페이지 번호(1..N)")
	    private Integer page;

	    @ApiModelProperty(value = "페이지 크기", allowableValues="range[0, 100]")
	    private Integer size;
	}
}
