package com.lining.learning.eureka.consumer.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class MyBasicAuthRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Basic cm9vdDpyb290");
    }
}
