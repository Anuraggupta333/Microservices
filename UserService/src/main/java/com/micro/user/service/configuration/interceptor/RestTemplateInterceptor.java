package com.micro.user.service.configuration.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    public RestTemplateInterceptor(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Retrieve the access token
        String token = oAuth2AuthorizedClientManager
                .authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client")
                        .principal("internal")
                        .build())
                .getAccessToken()
                .getTokenValue();

        // Add the Authorization header
        request.getHeaders().add("Authorization", "Bearer " + token);

        // Proceed with the execution of the request
        return execution.execute(request, body);
    }
}
