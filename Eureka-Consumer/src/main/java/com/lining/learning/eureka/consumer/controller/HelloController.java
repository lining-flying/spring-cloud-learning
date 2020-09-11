package com.lining.learning.eureka.consumer.controller;

import com.lining.learning.eureka.consumer.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    private ProviderService providerService;

    @Value("${myconfig.xxoo}")
    private String xxoo ;

    /*@Autowired
    private LoadBalancerClient loadBalancer;*/

    @GetMapping("/consumer/hello")
    public String helloConsumer(){
//        ServiceInstance instance = loadBalancer.choose("euraka-provider-hello");
//        String url = String.format("http://%s:%s/hello", instance.getHost(), instance.getPort());
        String url = "http://euraka-provider-hello/hello" ;
        ResponseEntity<String> resp = restTemplate.getForEntity(url,String.class);
        return resp.getBody();
    }

    @GetMapping("/consumer/alive")
    public String alive(){
        return providerService.alive();
    }


    @GetMapping("/map")
    public Map<Integer, String> map(Integer id) {
        System.out.println(id);
        return providerService.getMap(id);
    }

    @GetMapping("/map2")
    public Map<Integer, String> map2(Integer id,String name) {
        System.out.println(id);
        return providerService.getMap2(id,name);
    }


    @GetMapping("/map3")
    public Map<Integer, String> map3(@RequestParam Map<String, Object> map) {
//		System.out.println(id);
//		HashMap<String, Object> map = new HashMap<>(2);
//
//		map.put("id", id);
//		map.put("name", name);
//		syso
        System.out.println(map);
        return providerService.getMap3(map);
    }


    @GetMapping("/map4")
    public Map<Integer, String> map4(@RequestParam Map<String, Object> map) {
//		System.out.println(id);
//		HashMap<String, Object> map = new HashMap<>(2);
//
//		map.put("id", id);
//		map.put("name", name);
//		syso
        System.out.println(map);
        return providerService.postMap(map);
    }

    @GetMapping("/config")
    public String config(){
        return "myconfig.xxoo:"+xxoo ;
    }
}
