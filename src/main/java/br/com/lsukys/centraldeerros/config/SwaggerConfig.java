package br.com.lsukys.centraldeerros.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Autowired
	BuildProperties buildProperties;

	@Bean
	public Docket apis() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Application V1")
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.lsukys.centraldeerros.controller"))
				.paths(PathSelectors.ant("/v1/**"))
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false);
				//.securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
		        //.securityContexts(Arrays.asList(securityContext()));
	}

	@Bean
	public Docket apisv2() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Application V2")
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.lsukys.centraldeerros.controller"))
				.paths(PathSelectors.ant("/v2/**"))
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false);
				//.securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
		        //.securityContexts(Arrays.asList(securityContext()));
	}
	
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(buildProperties.getName())
				.description("Projeto com uma API Rest para centralizar registros de erros de aplicações.")
				.version(buildProperties.getVersion()).contact(new Contact("Luis Sukys", null, null))
				.build();
	}

	
	private SecurityContext securityContext() {
	    return SecurityContext.builder()
	        .securityReferences(defaultAuth())
	        .forPaths(PathSelectors.ant("/v1/**"))
	        .build();
	}

	List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	        = new AuthorizationScope("ADMIN", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Arrays.asList(
	        new SecurityReference("Token Access", authorizationScopes));
	}

}
