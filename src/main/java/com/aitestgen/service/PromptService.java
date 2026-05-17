package com.aitestgen.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;

@Service
public class PromptService {

    public String loadPrompt(String name) {
        try {
            ClassPathResource res = new ClassPathResource("prompts/" + name);
            return new String(Files.readAllBytes(res.getFile().toPath()));
        } catch (Exception e) {
            return "";
        }
    }
}
