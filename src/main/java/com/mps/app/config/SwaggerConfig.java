package com.mps.app.config;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.annotations.SwaggerDefinition;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Sandeep
 *
 */
@Configuration
@EnableSwagger2
@SwaggerDefinition
public class SwaggerConfig {
	public static final String AUTH_SERVER = "http://localhost:8081/MPSOAuth2Server/oauth";
	public static final String CLIENT_ID = "mpsadminuser";
	public static final String CLIENT_SECRET = "secret";

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build()
				.securitySchemes(Arrays.asList(securitySchema(), apiKey(), apiCookieKey()))
				.securityContexts(Arrays.asList(securityContext())).apiInfo(apiInfo());
	}

	@Bean
	public SecurityScheme apiKey() {
		return new ApiKey(HttpHeaders.AUTHORIZATION, "apiKey", "header");
	}

	@Bean
	public SecurityScheme apiCookieKey() {
		return new ApiKey(HttpHeaders.COOKIE, "apiKey", "cookie");
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder().clientId(CLIENT_ID).clientSecret(CLIENT_SECRET)
				.useBasicAuthenticationWithAccessCodeGrant(true).build();
	}

	private OAuth securitySchema() {

		List<AuthorizationScope> authorizationScopeList = newArrayList();
		authorizationScopeList.add(new AuthorizationScope("read", "read all"));
		authorizationScopeList.add(new AuthorizationScope("write", "access all"));
		authorizationScopeList.add(new AuthorizationScope("admin", "trust all"));

		List<GrantType> grantTypes = newArrayList();
		GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(AUTH_SERVER + "/token");
		grantTypes.add(passwordCredentialsGrant);

		return new OAuth("oauth2", authorizationScopeList, grantTypes);
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {

		final AuthorizationScope[] authorizationScopes = { new AuthorizationScope("read", "read all"),
				new AuthorizationScope("write", "write all"), new AuthorizationScope("admin", "trust all") };

		return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
	}

	/**
	 *
	 * @return ApiInf
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("MPS Authentication API")
				.description("Third Party Source to Authenticate MPS Resources")
				.termsOfServiceUrl("https://www.example.com/api")
				.contact(new Contact("Developers", "Yet To Build This URL", "")).license("Thirdparty Usgae Tool")
				.licenseUrl("\"https://www.apache.org/licenses/LICENSE-2.0").version("1.0.0").build();

	}

}
