package com.solicionio.essential.response.constants;

import com.solicionio.essential.response.handlers.IMessageHandler;

public enum SuccessMessage implements IMessageHandler {

    LOGIN(101, "Login Success.");

    private int successCode;
    private String successMessage;

    SuccessMessage(int successCode, String successMessage) {
        this.successCode = successCode;
        this.successMessage = successMessage;
    }

    @Override
    public int getStatusCode() {
        return this.successCode;
    }

    @Override
    public String getMessage() {
        return this.successMessage;
    }

    @Override
    public boolean isError() {
        return true;
    }
}