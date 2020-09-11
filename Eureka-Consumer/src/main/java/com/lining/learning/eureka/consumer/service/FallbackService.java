package com.lining.learning.eureka.consumer.service;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

//@Component
public class FallbackService implements ProviderService {

    @Override
    public String alive() {
        return "alive fallback";
    }

    @Override
    public Map<Integer, String> getMap(Integer id) {
        return Collections.singletonMap(id,"fallback");
    }

    @Override
    public Map<Integer, String> getMap2(Integer id, String name) {
        return Collections.singletonMap(id,"fallback");
    }

    @Override
    public Map<Integer, String> getMap3(Map<String, Object> map) {
        return Collections.singletonMap(1,"fallback");
    }

    @Override
    public Map<Integer, String> postMap(Map<String, Object> map) {
        return Collections.singletonMap(1,"fallback");
    }
}
