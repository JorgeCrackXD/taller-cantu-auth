package com.taller.cantu.auth.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface WebClientService {

    public Mono<String> sendGetRequest(String endpoint);

    public Mono<String> sendGetRequest(String endpoint, HttpHeaders headers);

    public Mono<String> sendPostRequest(String endpoint, Object requestBody);

    public Mono<String> sendPostRequest(String endpoint, Object requestBody, HttpHeaders headers);

    public Mono<String> sendPutRequest(String endpoint, Object requestBody);

    public Mono<String> sendPutRequest(String endpoint, Object requestBody, HttpHeaders headers);

    public Mono<Void> sendDeleteRequest(String endpoint);

    public Mono<Void> sendDeleteRequest(String endpoint, HttpHeaders headers);
}
