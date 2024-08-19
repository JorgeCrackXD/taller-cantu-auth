package com.taller.cantu.auth.service.impl;

import com.taller.cantu.auth.service.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebServiceClientImpl implements WebClientService {

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<String> sendGetRequest(String endpoint) {
        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> sendGetRequest(String endpoint, HttpHeaders headers) {
        return webClient.get()
                .uri(endpoint)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> sendPostRequest(String endpoint, Object requestBody) {
        return webClient.post()
                .uri(endpoint)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> sendPostRequest(String endpoint, Object requestBody, HttpHeaders headers) {
        return webClient.post()
                .uri(endpoint)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> sendPutRequest(String endpoint, Object requestBody) {
        return webClient.put()
                .uri(endpoint)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> sendPutRequest(String endpoint, Object requestBody, HttpHeaders headers) {
        return webClient.put()
                .uri(endpoint)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<Void> sendDeleteRequest(String endpoint) {
        return webClient.delete()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> sendDeleteRequest(String endpoint, HttpHeaders headers) {
        return webClient.delete()
                .uri(endpoint)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(Void.class);
    }

}
