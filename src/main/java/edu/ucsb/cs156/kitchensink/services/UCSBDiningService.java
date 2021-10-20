package edu.ucsb.cs156.kitchensink.services;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.beans.factory.annotation.Value;
import java.util.Arrays;


@Slf4j
@Service
public class UCSBDiningService {

    @Value("${app.ucsb.api.consumer_key}")
    private String apiKey;


    ObjectMapper mapper = new ObjectMapper();

    private final RestTemplate restTemplate;

    public UCSBDiningService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("ucsb-api-version", "1.0");
        headers.set("ucsb-api-key", this.apiKey);
        return headers;
    } 

    public String getDiningCommonsJSON(String date) throws HttpClientErrorException {

        final String ENDPOINT = "https://api.ucsb.edu/dining/menu/v1/{date}";

        HttpHeaders headers = getHeaders();

        log.info("date={}", date);

        HttpEntity<String> entity = new HttpEntity<>(headers);
      
        Map<String, String> uriVariables = Map.of("date", date);
        
        ResponseEntity<String> re = restTemplate.exchange(ENDPOINT, HttpMethod.GET, entity, String.class,
                uriVariables);
        return re.getBody();
    }

    public String getDiningCommonsMealsJSON(String date, String diningCommonsCode) throws HttpClientErrorException {

        final String ENDPOINT = "https://api.ucsb.edu/dining/menu/v1/{date}/{diningCommons}";

        HttpHeaders headers = getHeaders();

        log.info("date={}", date);
        log.info("diningCommonsCode={}", diningCommonsCode);

        HttpEntity<String> entity = new HttpEntity<>(headers);
      
        Map<String, String> uriVariables = Map.of("date", date, "diningCommons",diningCommonsCode);
        
        ResponseEntity<String> re = restTemplate.exchange(ENDPOINT, HttpMethod.GET, entity, String.class,
                uriVariables);
        return re.getBody();
    }


}