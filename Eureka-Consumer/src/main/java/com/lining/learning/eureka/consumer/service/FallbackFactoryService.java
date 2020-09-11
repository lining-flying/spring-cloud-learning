package com.lining.learning.eureka.consumer.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class FallbackFactoryService implements FallbackFactory<ProviderService> {
    @Override
    public ProviderService create(Throwable throwable) {
        return new FallbackService();
    }
}
