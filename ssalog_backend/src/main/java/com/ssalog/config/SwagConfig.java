package com.ssalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 예전에 ~~-context.xml파일을 대체하는 애노테이션. 즉 여기가 스프링의 객체 저장소중 하나가 된다는 이야기임.
@EnableSwagger2
public class SwagConfig {

	// xml로 객체 관리할때는 <bean> 태그 사용했었음. 자바 파일로 작성할때는 @Bean 으로 쓰면됨.
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("public-api")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ssalog.controller"))
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("ssalog.API")
				.description("SSALOG")
				.version("1.0")
				.build();
	}
	
}
