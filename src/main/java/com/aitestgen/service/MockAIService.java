package com.aitestgen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Mock AI Service for development/testing without external API dependencies.
 * Returns realistic JUnit 5 test code based on the API details provided.
 */
@Service
@Primary
@ConditionalOnProperty(name = "ai.service.mode", havingValue = "mock", matchIfMissing = true)
public class MockAIService implements AIService {

    @Override
    public String generate(String prompt) throws Exception {
        // Generate a realistic JUnit 5 test class based on the prompt
        String testCode = generateMockTestCode();
        return testCode;
    }

    private String generateMockTestCode() {
        return """
            import org.junit.jupiter.api.Test;
            import org.junit.jupiter.api.BeforeEach;
            import org.junit.jupiter.api.DisplayName;
            import org.mockito.Mock;
            import org.mockito.InjectMocks;
            import org.mockito.MockitoAnnotations;
            import static org.mockito.Mockito.*;
            import static org.junit.jupiter.api.Assertions.*;
            
            @DisplayName("API Test Cases")
            public class ApiServiceTest {
                
                @Mock
                private ApiClient apiClient;
                
                @InjectMocks
                private ApiService apiService;
                
                @BeforeEach
                public void setUp() {
                    MockitoAnnotations.openMocks(this);
                }
                
                @Test
                @DisplayName("Should successfully call API with valid request")
                public void testApiCallSuccess() {
                    // Arrange
                    String expectedResponse = "Success";
                    when(apiClient.call(anyString())).thenReturn(expectedResponse);
                    
                    // Act
                    String result = apiService.processRequest("valid-input");
                    
                    // Assert
                    assertNotNull(result);
                    assertEquals(expectedResponse, result);
                    verify(apiClient, times(1)).call(anyString());
                }
                
                @Test
                @DisplayName("Should handle API call failure gracefully")
                public void testApiCallFailure() {
                    // Arrange
                    when(apiClient.call(anyString())).thenThrow(new RuntimeException("API Error"));
                    
                    // Act & Assert
                    assertThrows(RuntimeException.class, () -> {
                        apiService.processRequest("invalid-input");
                    });
                    verify(apiClient, times(1)).call(anyString());
                }
                
                @Test
                @DisplayName("Should handle null response")
                public void testNullResponse() {
                    // Arrange
                    when(apiClient.call(anyString())).thenReturn(null);
                    
                    // Act
                    String result = apiService.processRequest("input");
                    
                    // Assert
                    assertNull(result);
                }
            }
            """;
    }
}


