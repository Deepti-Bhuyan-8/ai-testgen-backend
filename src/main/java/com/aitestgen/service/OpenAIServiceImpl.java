package com.aitestgen.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAIServiceImpl implements AIService {

    private final WebClient webClient;

    public OpenAIServiceImpl(WebClient openAiWebClient) {
        this.webClient = openAiWebClient;
    }

    @Override
    public String generate(String prompt) throws Exception {
        // Minimal call to Chat Completions; adjust model & payload as needed.
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");
        body.put("messages", new Object[]{message});
        body.put("max_tokens", 1500);

        Mono<Map> resp = webClient.post()
                .uri("/v1/chat/completions")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class);

        Map result = resp.block();
        if (result == null) return "";
        try {
            Object choices = result.get("choices");
            if (choices instanceof java.util.List) {
                Map first = (Map) ((java.util.List) choices).get(0);
                Map messageObj = (Map) first.get("message");
                return (String) messageObj.getOrDefault("content", "");
            }
            return result.toString();
        } catch (Exception e) {
            return result.toString();
        }
    }
}
