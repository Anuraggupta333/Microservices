package com.micro.user.service.configuration.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    @Autowired
    private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Authorize the client and retrieve the access token
        String token = oAuth2AuthorizedClientManager
                .authorize(
                        OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client")
                                .principal("internal")
                                .build()
                )
                .getAccessToken()
                .getTokenValue();

        // Add the Authorization header with the Bearer token
        requestTemplate.header("Authorization", "Bearer " + token);
    }
}
