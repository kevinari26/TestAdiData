package com.test.adidata.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CommonBean {
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		// setting untuk ignore field yang tidak ada di object tujuan
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// setting agar object mapper tidak mengambil nilai null
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// setting pakai local timezone
		objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));

		return objectMapper;
	}

	@Bean
    public RestTemplate restTemplate() {
	    RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }


    // config thread pool
    @Value("${threadPoolSize}")
    Integer threadPoolSize;
    @Bean
    public ExecutorService executorService() {
        System.out.println("threadPoolSize: " + threadPoolSize);
        return Executors.newFixedThreadPool(threadPoolSize);
    }
}
