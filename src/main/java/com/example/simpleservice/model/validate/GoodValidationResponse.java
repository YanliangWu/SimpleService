package com.example.simpleservice.model.validate;

public class GoodValidationResponse extends ValidationResponse {
    private boolean status;

    public GoodValidationResponse(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
