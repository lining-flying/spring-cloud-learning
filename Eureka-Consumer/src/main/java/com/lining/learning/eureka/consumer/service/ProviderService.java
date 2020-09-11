package com.lining.learning.eureka.consumer.service;

import com.lining.learning.eureka.consumer.configuration.FeignAuthConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 注意：使用openFeign时，name中传入的时服务实例ID（服务调用者的spring.application.name）
 */
//@FeignClient(name="euraka-provider-hello",configuration = FeignAuthConfiguration.class)
//@FeignClient(name="euraka-provider-hello",fallback = FallbackService.class)
@FeignClient(name="euraka-provider-hello",fallbackFactory = FallbackFactoryService.class)
public interface ProviderService {

    @GetMapping("/alive")
    public String alive();

    @GetMapping("/getMap")
    Map<Integer, String> getMap(@RequestParam("id") Integer id);
//    Map<Integer, String> getMap(Integer id);
    @GetMapping("/getMap2")
    Map<Integer, String> getMap2(@RequestParam("id") Integer id,@RequestParam("name") String name);

    @GetMapping("/getMap3")
    Map<Integer, String> getMap3(@RequestParam Map<String, Object> map);

    @PostMapping("/postMap")
    Map<Integer, String> postMap(Map<String, Object> map);
}
