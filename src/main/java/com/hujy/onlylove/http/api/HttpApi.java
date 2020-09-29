package com.hujy.onlylove.http.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-22 14:44
 */
@Component
public class HttpApi {

    @Resource
    private RestTemplate restTemplate;

    public  <T> T getForEntity(String url, Class<T> responseType) {
        ResponseEntity<T> responseEntity = this.restTemplate.getForEntity(url, responseType);
        return responseEntity.getBody();
    }
}
