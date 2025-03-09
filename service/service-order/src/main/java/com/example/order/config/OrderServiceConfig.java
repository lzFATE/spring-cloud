package com.example.order.config;

import feign.RetryableException;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Configuration
public class OrderServiceConfig {

    @Bean
    Retryer retryer(){
        return new Retryer.Default();
    }

    @LoadBalanced // 开启 负载均衡
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
