# AI TestGen Backend

**Generate JUnit 5 unit tests automatically using AI for your REST APIs.**

A Spring Boot backend application that uses AI to analyze API specifications and generate production-ready, fully structured JUnit 5 + Mockito test code. No manual test writing required.

## 🎯 Project Overview

AI TestGen Backend is a REST API service that:
- Accepts API endpoint details (endpoint, HTTP method, request/response bodies, description)
- Generates complete, ready-to-run JUnit 5 test classes
- Includes Mockito mocking setup
- Creates positive and negative test cases with assertions
- Works out-of-the-box with mock mode (no API keys needed)
- Supports multiple AI backends (Mock, OpenAI, LocalAI)

### Example

**Input:**
```json
{
  "endpoint": "POST /api/users",
  "method": "POST",
  "description": "Create a new user",
  "requestBody": "{\"name\":\"John\",\"email\":\"john@example.com\"}",
  "responseBody": "{\"id\":1,\"name\":\"John\",\"email\":\"john@example.com\"}"
}
```

**Output:**
```java
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
        when(apiClient.call(anyString())).thenReturn("Success");
        
        // Act
        String result = apiService.processRequest("valid-input");
        
        // Assert
        assertNotNull(result);
        assertEquals("Success", result);
        verify(apiClient, times(1)).call(anyString());
    }
    
    @Test
    @DisplayName("Should handle API call failure gracefully")
    public void testApiCallFailure() {
        // Test implementation...
    }
    
    @Test
    @DisplayName("Should handle null response")
    public void testNullResponse() {
        // Test implementation...
    }
}
```

## 🛠️ Technologies & Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Framework** | Spring Boot | 3.1.4 |
| **Java** | OpenJDK | 17+ |
| **Build Tool** | Maven | 3.6+ |
| **HTTP Client** | Spring WebFlux | Reactive |
| **JSON Processing** | Jackson | Bundled |
| **Testing** | JUnit 5 (generated) | - |
| **Mocking** | Mockito (generated) | - |
| **Server** | Tomcat | 10.1.13 |
| **Optional AI** | OpenAI API | gpt-4o-mini |
| **Optional AI** | LocalAI (Docker) | Latest |

## 📋 Prerequisites

- **Java 17+** — [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** — [Download](https://maven.apache.org/download.cgi)
- **Windows/Linux/macOS** — Any OS with Java support

## 🚀 Quick Start

### 1. Clone or Extract Project
```bash
cd F:\ai-testgen\backend
```

### 2. Build the Project
```bash
mvn clean package -DskipTests
```

### 3. Run the Application
```bash
java -jar target/ai-testgen-backend-0.1.0.jar
```

The app starts on **http://localhost:8080**

### 4. Test with Browser UI
Open in your browser:
```
http://localhost:8080/test.html
```

Fill in the form:
- **Endpoint**: `/api/users` (or any endpoint)
- **Method**: `GET`, `POST`, `PUT`, `DELETE`
- **Description**: Brief description (optional)
- **Request Body (JSON)**: JSON request example (leave empty for GET)
- **Response Body (JSON)**: JSON response example

Click **"Generate Tests"** — generates JUnit 5 test code.

## 🔌 API Endpoint

### POST /api/generate-tests

**Request Body:**
```json
{
  "endpoint": "POST /api/users",
  "method": "POST",
  "description": "Create a new user",
  "requestBody": "{\"name\":\"John\"}",
  "responseBody": "{\"id\":1,\"name\":\"John\"}"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "testCode": "import org.junit.jupiter.api...full JUnit 5 test class here..."
  },
  "error": null
}
```

**Status Codes:**
- **200 OK** — Test code generated successfully
- **400 Bad Request** — Invalid request body
- **500 Internal Server Error** — Server error (check logs)

## 💻 Examples

### cURL
```bash
curl -X POST http://localhost:8080/api/generate-tests \
  -H "Content-Type: application/json" \
  -d '{
    "endpoint": "GET /api/users",
    "method": "GET",
    "description": "Fetch all users",
    "requestBody": "",
    "responseBody": "[{\"id\":1,\"name\":\"John\"}]"
  }'
```

### PowerShell
```powershell
$body = @{
    endpoint = "POST /api/users"
    method = "POST"
    description = "Create user"
    requestBody = '{"name":"John"}'
    responseBody = '{"id":1}'
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/generate-tests" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body $body
```

### JavaScript (Fetch)
```javascript
const payload = {
  endpoint: "POST /api/users",
  method: "POST",
  description: "Create user",
  requestBody: JSON.stringify({name: "John"}),
  responseBody: JSON.stringify({id: 1})
};

fetch('http://localhost:8080/api/generate-tests', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(payload)
})
  .then(res => res.json())
  .then(data => console.log(data.data.testCode))
  .catch(err => console.error(err));
```

### React/axios
```jsx
import axios from 'axios';

const generateTests = async () => {
  const response = await axios.post(
    'http://localhost:8080/api/generate-tests',
    {
      endpoint: "POST /api/users",
      method: "POST",
      description: "Create user",
      requestBody: JSON.stringify({name: "John"}),
      responseBody: JSON.stringify({id: 1})
    }
  );
  return response.data.data.testCode;
};
```

## ⚙️ Configuration

### Application Modes

Edit `src/main/resources/application.yml`:

**Mock Mode (Default — No API Key Needed)**
```yaml
server:
  port: 8080

ai:
  service:
    mode: mock

openai:
  base-url: http://localhost:8081
  api-key: not-needed-for-mock
```

**OpenAI Mode (Requires API Key)**
```yaml
ai:
  service:
    mode: openai

openai:
  base-url: https://api.openai.com
  api-key: sk-your-openai-api-key-here
```

To get an OpenAI API key:
1. Visit [https://platform.openai.com/api-keys](https://platform.openai.com/api-keys)
2. Create a new Secret Key
3. Add to `application.yml` or set environment variable:
```powershell
$env:OPENAI_API_KEY = "sk-your-key"
```

**LocalAI Mode (Docker-based Local LLM)**
```yaml
ai:
  service:
    mode: openai  # LocalAI uses OpenAI API format

openai:
  base-url: http://localhost:8081  # LocalAI Docker container
  api-key: not-needed-for-local-ai
```

Setup LocalAI:
1. Install Docker
2. Run container:
```bash
docker run --rm -p 8081:8080 \
  -v C:\models:/models \
  ghcr.io/go-skynet/localai:latest \
  localai serve --model /models/model.gguf --host 0.0.0.0 --port 8080
```
3. Rebuild and run the app

## 📁 Project Structure

```
ai-testgen-backend/
├── pom.xml                              # Maven dependencies & build config
├── src/
│   └── main/
│       ├── java/com/aitestgen/
│       │   ├── AiTestgenApplication.java         # Spring Boot entry point
│       │   ├── config/
│       │   │   └── OpenAIConfig.java             # WebClient & config beans
│       │   ├── controller/
│       │   │   └── TestCaseController.java       # REST endpoint
│       │   ├── model/
│       │   │   ├── ApiInput.java                 # Request DTO
│       │   │   ├── ResponseDTO.java              # Response DTO
│       │   │   └── TestCaseOutput.java           # Test output model
│       │   └── service/
│       │       ├── AIService.java                # AI interface
│       │       ├── MockAIService.java            # Mock implementation (default)
│       │       ├── OpenAIServiceImpl.java         # OpenAI implementation
│       │       ├── TestGeneratorService.java     # Main business logic
│       │       └── PromptService.java            # Load prompts from files
│       └── resources/
│           ├── application.yml                   # Configuration
│           ├── prompts/
│           │   └── junit-prompt.txt              # Template for test generation
│           └── static/
│               └── test.html                     # Browser UI for testing
└── target/
    └── ai-testgen-backend-0.1.0.jar             # Executable JAR
```

## 🔧 Build & Deploy

### Build JAR
```bash
cd F:\ai-testgen\backend
mvn clean package -DskipTests
```

### Run Locally
```bash
java -jar target/ai-testgen-backend-0.1.0.jar
```

### Deploy to Server
1. Copy `ai-testgen-backend-0.1.0.jar` to production server
2. Ensure Java 17+ installed on server
3. Set environment variables (if using real AI):
```bash
export OPENAI_API_KEY="sk-your-key"
```
4. Run:
```bash
java -jar ai-testgen-backend-0.1.0.jar
```

### Docker Deployment (Optional)
Create `Dockerfile`:
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/ai-testgen-backend-0.1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build & run:
```bash
docker build -t ai-testgen:latest .
docker run -p 8080:8080 ai-testgen:latest
```

## 🧪 Testing

### Using Browser UI
1. Open http://localhost:8080/test.html
2. Fill in API details
3. Click "Generate Tests"
4. Copy generated code

### Using cURL
```bash
curl -X POST http://localhost:8080/api/generate-tests \
  -H "Content-Type: application/json" \
  -d '{"endpoint":"/","method":"GET","description":"Root","requestBody":"","responseBody":"{}"}'
```

### Verify Server is Running
```powershell
netstat -ano | findstr :8080
```

### View Logs
```bash
# Check logs in terminal where you ran: java -jar ...
# Look for INFO and WARN messages starting with timestamps
```

## 🐛 Troubleshooting

### Port 8080 Already in Use
```powershell
# Find process
netstat -ano | findstr :8080

# Kill process (replace <PID> with actual PID)
taskkill /PID <PID> /F

# Or change port in application.yml: server.port: 8081
```

### Java Not Found
```bash
# Check Java version
java -version

# Install Java 17+
# Windows: https://www.oracle.com/java/technologies/downloads/
# macOS: brew install openjdk@17
# Linux: apt-get install openjdk-17-jdk
```

### Build Fails
```bash
# Clean and try again
mvn clean
mvn compile

# Show detailed errors
mvn -X compile
```

### API Returns Error
- Check terminal logs for stack traces
- Verify `application.yml` configuration
- Check all request fields are provided
- Ensure backend is running on correct port

### CORS Issues (If Using Different Frontend Origin)
- Backend allows CORS for `http://localhost:5173` by default
- To change: edit `TestCaseController.java` → `@CrossOrigin(origins = "http://your-origin")`
- For development only: `@CrossOrigin(origins = "*")`
- Rebuild: `mvn clean package -DskipTests`

## 📚 Architecture

```
HTTP Request (Browser/Frontend)
    ↓
TestCaseController (REST endpoint)
    ↓
TestGeneratorService (Business logic)
    ↓
AIService (Interface — pluggable)
    ├─→ MockAIService (Default, no API needed)
    ├─→ OpenAIServiceImpl (Real OpenAI API)
    └─→ LocalAI (Local LLM via Docker)
    ↓
PromptService (Load prompt template)
    ↓
Generated JUnit 5 Test Code
    ↓
HTTP Response (JSON with testCode field)
```

## 🔄 How It Works

1. **User submits API details** via browser form or HTTP request
2. **Controller validates** the request
3. **TestGeneratorService** loads the `junit-prompt.txt` template
4. **Prompt is customized** with user's API details
5. **AIService generates** test code based on prompt
6. **Response sent back** as JSON with generated test code
7. **User copies** the test code into their test file

## 🚀 Features

- ✅ **Works Out-of-the-Box** — Mock mode needs no configuration
- ✅ **JUnit 5 Compliant** — Latest testing standards
- ✅ **Mockito Integration** — Mocking setup included
- ✅ **Multiple Test Cases** — Success, failure, edge cases
- ✅ **Flexible AI Backend** — Switch between Mock/OpenAI/LocalAI
- ✅ **Browser UI** — Easy testing with built-in HTML page
- ✅ **REST API** — Integrates with any frontend
- ✅ **CORS Enabled** — Frontend-friendly
- ✅ **Production Ready** — Deployable as JAR or Docker
- ✅ **No Database** — Stateless, scales easily

## 📦 Dependencies

See `pom.xml` for full list. Key libraries:
- Spring Boot 3.1.4
- Spring WebFlux (reactive HTTP)
- Jackson (JSON processing)
- SLF4J (logging)
- Jakarta Annotations

## 🔐 Security Notes

- **API Key**: if using OpenAI mode, store securely in environment variables, not in version control
- **CORS**: Configured for `localhost:5173` by default; update for production origins
- **No Authentication**: Current version has no API key authentication; add if needed for production

## 📄 License

[Add your license here if applicable]

## 🤝 Contributing

To extend or customize:
1. Edit `src/main/resources/prompts/junit-prompt.txt` to customize test generation
2. Add new AIService implementations in `src/main/java/com/aitestgen/service/`
3. Update `application.yml` configuration as needed
4. Rebuild: `mvn clean package -DskipTests`

## 📞 Support

- Check logs when running `java -jar ...`
- Verify backend is running: `curl http://localhost:8080/test.html`
- Check ports: `netstat -ano | findstr :8080`
- Review configuration: `src/main/resources/application.yml`

## 🎉 Getting Started Right Now

```bash
# 1. Build
mvn clean package -DskipTests

# 2. Run
java -jar target/ai-testgen-backend-0.1.0.jar

# 3. Test
# Open browser: http://localhost:8080/test.html
# Or use: curl -X POST http://localhost:8080/api/generate-tests ... (see Examples)
```

That's it! Your AI-powered test generator is ready to use.

---

**Last Updated**: May 17, 2026  
**Status**: ✅ Production Ready  
**Current Mode**: Mock (No API key needed)

