package com.rating.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class RestTemplateConfigTest {

    @Autowired
    private RestTemplateConfig restTemplateConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void ShouldHaeValidBeanCreatedForRestTemplate() {
        assertThat(restTemplateConfig.restTemplate()).isNotNull();
    }



}