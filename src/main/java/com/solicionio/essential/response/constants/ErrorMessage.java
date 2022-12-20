package com.solicionio.essential.response.constants;

import com.solicionio.essential.response.handlers.IMessageHandler;

public enum ErrorMessage implements IMessageHandler {

    LOGIN_ERROR(201, "Username or password is not correct.");

    private int errorCode;
    private String errorMessage;

    ErrorMessage(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getStatusCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }

    @Override
    public boolean isError() {
        return true;
    }
}
