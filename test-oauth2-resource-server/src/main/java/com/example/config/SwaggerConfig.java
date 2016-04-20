package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ImplicitGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by btaboule010714 on 19/04/2016.
 */
@Configuration
@EnableSwagger2
@EnableAutoConfiguration
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Value("#{'${auth.server.endpoints.authorize.url}'.trim()}")
    private  String authorizeEndpointUrl;

    @Value("#{'${auth.server.endpoints.authorize.client-id}'.trim()}")
    private  String clientId;

    @Value("#{'${auth.server.endpoints.authorize.client-secret}'.trim()}")
    private  String clientSecret;

    @Value("#{'${auth.server.endpoints.authorize.realme}'.trim()}")
    private  String realm;

    @Value("#{'${auth.server.endpoints.authorize.application-name}'.trim()}")
    private  String appName;

    @Value("#{'${auth.server.endpoints.authorize.api-key}'.trim()}")
    private  String apiKey;

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("confirmation")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .securitySchemes(newArrayList(oauth()))
                .securityContexts(newArrayList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring REST Sample with Swagger")
                .description("Spring REST Sample with Swagger")
                //.termsOfServiceUrl("API terms of services URL")
                //.contact("xx.yy@sgcib.com")
                //.license("API license type")
                //.licenseUrl("Licence URL")
                .version("1.0")
                .build();
    }

    @Bean
    SecurityContext securityContext() {
        SecurityReference securityReference = SecurityReference.builder()
                .reference("sample_swagger_auth")
                .scopes(scopes().toArray(new AuthorizationScope[scopes().size()]))
                .build();

        return SecurityContext.builder()
                .securityReferences(newArrayList(securityReference))
                .forPaths(PathSelectors.regex(swaggerProperties.getIncludePatterns()))
                .build();
    }

    @Bean
    SecurityScheme oauth() {
        return new OAuthBuilder()
                .name("sample_swagger_auth")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    List<AuthorizationScope> scopes() {
        return newArrayList(new AuthorizationScope("confstatus_read", "confstatus read"));
    }

    List<GrantType> grantTypes() {
        GrantType grantType = new ImplicitGrantBuilder().loginEndpoint(new LoginEndpoint(authorizeEndpointUrl)).build();
        //GrantType grantType2 = new ClientCredentialsGrant("http://localhost:9999/uaa/oauth/check_token");
        return newArrayList(grantType);
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(clientId, clientSecret, realm, appName, apiKey, ApiKeyVehicle.HEADER, "sample_swagger", " " /*scope separator*/);
    }

}