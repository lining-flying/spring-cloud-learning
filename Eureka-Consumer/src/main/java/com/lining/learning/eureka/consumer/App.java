package com.lining.learning.eureka.consumer;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate myRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public IRule getRule(){
        //return new RoundRobinRule();
        return new RandomRule();
//        return new RetryRule();
    }
}
