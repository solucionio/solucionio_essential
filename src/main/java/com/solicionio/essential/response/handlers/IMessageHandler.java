package com.solicionio.essential.response.handlers;

public interface IMessageHandler {

    int getStatusCode();
    String getMessage();
    boolean isError();

}
