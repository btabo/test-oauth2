package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * Created by btaboule010714 on 18/04/2016.
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthServerConfig extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {

    private static final String ENV_OAUTH = "security.oauth2.client.";
    private static final String PROP_CLIENTID = "client-id";
    private static final String PROP_SCOPE = "scope";
    private static final String PROP_SECRET = "client-secret";
    private static final String PROP_TOKEN_VALIDITY_SECONDS = "tokenValidityInSeconds";

    @Autowired
    private AuthenticationManager authenticationManager;

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient(propertyResolver.getProperty(PROP_CLIENTID))
            .secret(propertyResolver.getProperty(PROP_SECRET))
            .authorizedGrantTypes("client_credentials", "authorization_code", "refresh_token", "password")
            .scopes(propertyResolver.getProperty(PROP_SCOPE))
            .accessTokenValiditySeconds(propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 1800));
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
    }
}
