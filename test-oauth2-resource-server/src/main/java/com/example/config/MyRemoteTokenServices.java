package com.example.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by btaboule010714 on 19/04/2016.
 */
public class MyRemoteTokenServices implements ResourceServerTokenServices {

        protected final Log logger = LogFactory.getLog(this.getClass());
        private RestOperations restTemplate = new RestTemplate();
        private String checkTokenEndpointUrl;
        private String clientId;
        private String clientSecret;
        private String tokenName = "token";
        private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

        public MyRemoteTokenServices() {
            ((RestTemplate)this.restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
                public void handleError(ClientHttpResponse response) throws IOException {
                    if(response.getRawStatusCode() != 400) {
                        super.handleError(response);
                    }

                }
            });
        }

        public void setRestTemplate(RestOperations restTemplate) {
            this.restTemplate = restTemplate;
        }

        public void setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
            this.checkTokenEndpointUrl = checkTokenEndpointUrl;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
            this.tokenConverter = accessTokenConverter;
        }

        public void setTokenName(String tokenName) {
            this.tokenName = tokenName;
        }

        public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
            LinkedMultiValueMap formData = new LinkedMultiValueMap();
            formData.add(this.tokenName, accessToken);
            HttpHeaders headers = new HttpHeaders();
            //headers.set("Authorization", this.getAuthorizationHeader(this.clientId, this.clientSecret));
            Map map = this.postForMap(this.checkTokenEndpointUrl, formData, headers);
            if(map.containsKey("error")) {
                this.logger.debug("check_token returned error: " + map.get("error"));
                throw new InvalidTokenException(accessToken);
            } else {
                Assert.state(map.containsKey("client_id"), "Client id must be present in response from auth server");
                return this.tokenConverter.extractAuthentication(map);
            }
        }

        public OAuth2AccessToken readAccessToken(String accessToken) {
            throw new UnsupportedOperationException("Not supported: read access token");
        }

        private String getAuthorizationHeader(String clientId, String clientSecret) {
            String creds = String.format("%s:%s", new Object[]{clientId, clientSecret});

            try {
                return "Basic " + new String(Base64.encode(creds.getBytes("UTF-8")));
            } catch (UnsupportedEncodingException var5) {
                throw new IllegalStateException("Could not convert String");
            }
        }

        private Map<String, Object> postForMap(String path, MultiValueMap<String, String> formData, HttpHeaders headers) {
            if(headers.getContentType() == null) {
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            }

            Map map = (Map)this.restTemplate.exchange(path, HttpMethod.POST, new HttpEntity(formData, headers), Map.class, new Object[0]).getBody();
            return map;
        }
    }