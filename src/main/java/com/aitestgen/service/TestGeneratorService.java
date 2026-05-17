package com.aitestgen.service;

import com.aitestgen.model.ApiInput;
import com.aitestgen.model.TestCaseOutput;
import org.springframework.stereotype.Service;

@Service
public class TestGeneratorService {

    private final AIService aiService;
    private final PromptService promptService;

    public TestGeneratorService(AIService aiService, PromptService promptService) {
        this.aiService = aiService;
        this.promptService = promptService;
    }

    public TestCaseOutput generateTests(ApiInput input) {
        try {
            String promptTemplate = promptService.loadPrompt("junit-prompt.txt");
            String prompt = promptTemplate
                    .replace("{{endpoint}}", input.getEndpoint() == null ? "" : input.getEndpoint())
                    .replace("{{method}}", input.getMethod() == null ? "GET" : input.getMethod())
                    .replace("{{requestBody}}", input.getRequestBody() == null ? "" : input.getRequestBody())
                    .replace("{{responseBody}}", input.getResponseBody() == null ? "" : input.getResponseBody())
                    .replace("{{description}}", input.getDescription() == null ? "" : input.getDescription());

            String result = aiService.generate(prompt);
            TestCaseOutput out = new TestCaseOutput();
            out.setTestCode(result);
            return out;
        } catch (Exception e) {
            TestCaseOutput out = new TestCaseOutput();
            out.setTestCode("// Error generating tests: " + e.getMessage());
            return out;
        }
    }
}
