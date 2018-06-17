package com.mps.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.SwaggerDefinition;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Sandeep
 *
 */
@Configuration
@EnableSwagger2
@SwaggerDefinition
public class SwaggerConfig {

	@Bean
	public Docket api() {

		List<SecurityScheme> schemes = new ArrayList<>();
		// new ApiKeyAuthDefinition("apiKey", In.HEADER)
		// new ApiKey("Authorization", "Authorization", "header")
		schemes.add(new BasicAuth("base"));

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().description("A Spring Application For MPS Services")
						.license("This is Strictly Prohibited For Use by Third Parties.")
						.title("A Small Application for holding MPS Services").version("1.0")
						.contact(new Contact("Sandeep Reddy", "", "b.sandeepreddy209@gmail.com"))
						/* no format */
						.licenseUrl("") // TODO needs to be updated.
						.termsOfServiceUrl("") // TODO needs to be updated.
						.build())
				.securitySchemes(schemes).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
				.build();
	}

}
