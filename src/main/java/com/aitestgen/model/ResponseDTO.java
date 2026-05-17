package com.aitestgen.model;

public class ResponseDTO {
    private boolean success;
    private TestCaseOutput data;
    private String error;

    public ResponseDTO() {}

    public ResponseDTO(boolean success, TestCaseOutput data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public TestCaseOutput getData() { return data; }
    public void setData(TestCaseOutput data) { this.data = data; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
