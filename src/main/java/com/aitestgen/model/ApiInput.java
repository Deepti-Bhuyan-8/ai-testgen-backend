package com.aitestgen.model;

public class ApiInput {
    private String endpoint;
    private String method;
    private String requestBody;
    private String responseBody;
    private String description;

    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getRequestBody() { return requestBody; }
    public void setRequestBody(String requestBody) { this.requestBody = requestBody; }

    public String getResponseBody() { return responseBody; }
    public void setResponseBody(String responseBody) { this.responseBody = responseBody; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
