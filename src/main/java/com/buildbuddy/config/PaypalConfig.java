package com.buildbuddy.config;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {

    @Value("${spring.paypal.client-id}")
    private String clientId;
    @Value("${spring.paypal.client-secret}")
    private String clientSecret;
    @Value("${spring.paypal.mode}")
    private String mode;

    @Bean
    public APIContext apiContext(){
        return new APIContext(clientId, clientSecret, mode);
    }

}
