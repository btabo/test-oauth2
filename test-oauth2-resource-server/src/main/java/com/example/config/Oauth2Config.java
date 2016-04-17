package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * Created by bertrand on 17/04/2016.
 */
@Configuration
@EnableResourceServer
@EnableAuthorizationServer
public class Oauth2Config implements ResourceServerConfigurer {

    private static final String SERVER_RESOURCE_ID = "oauth2-server";



    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //resources.resourceId(SERVER_RESOURCE_ID);
//        RemoteTokenServices rts = new RemoteTokenServices();
//        rts.setCheckTokenEndpointUrl("localhost:9999/oauth/check_token");
//
//        resources.tokenServices(rts);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().requestMatchers()
                .antMatchers("/hello")
                .and().authorizeRequests()
                .antMatchers("/hello").access("#oauth2.hasScope('conf_status_read')")
        ;
    }

}
