package com.aitestgen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;

@Configuration
public class OpenAIConfig {

    private static final Logger log = LoggerFactory.getLogger(OpenAIConfig.class);

    @Value("${openai.base-url:https://api.openai.com}")
    private String baseUrl;

    @Value("${openai.api-key:}")
    private String apiKey;

    @PostConstruct
    public void validateApiKey() {
        if (!StringUtils.hasText(apiKey)) {
            apiKey = System.getenv("OPENAI_API_KEY");
        }
        if (StringUtils.hasText(apiKey) && !apiKey.equals("not-needed-for-mock")) {
            log.info("OpenAI/LocalAI API key is configured.");
        } else {
            log.warn("Running in mock mode or no API key configured. Using default/mock responses.");
        }
        log.info("AI Service Base URL: {}", baseUrl);
    }

    @Bean
    public WebClient openAiWebClient() {
        WebClient.Builder builder = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", "application/json");
        
        if (StringUtils.hasText(apiKey) && !apiKey.equals("not-needed-for-mock")) {
            builder.defaultHeader("Authorization", "Bearer " + apiKey);
            log.debug("WebClient configured with Authorization header");
        } else {
            log.debug("WebClient configured without Authorization (mock mode)");
        }
        
        return builder.build();
    }
}
