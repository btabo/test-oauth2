package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * Created by bertrand on 17/04/2016.
 */
@Configuration
@EnableResourceServer
public class Oauth2Config implements ResourceServerConfigurer {

    private static final String SERVER_RESOURCE_ID = "oauth2-server";



    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //resources.resourceId(SERVER_RESOURCE_ID);
        MyRemoteTokenServices rts = new MyRemoteTokenServices();
        rts.setCheckTokenEndpointUrl("http://localhost:9999/uaa/oauth/check_token");

        resources.tokenServices(rts);

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().authorizeRequests()
                .antMatchers("/api/**").access("#oauth2.hasScope('confstatus_read')")
                //.antMatchers("/api/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
        ;
    }
}

