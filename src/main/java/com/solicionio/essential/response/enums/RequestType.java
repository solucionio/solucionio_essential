package com.solicionio.essential.response.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RequestType {

    LOGIN(PacketType.SYNC, "login",1),
    REGISTER(PacketType.ASYNC, "register",2),
    SESSION(PacketType.ASYNC, "session",3);

    private PacketType packetType;
    private int groupOrderID;
    private String requestName;

    RequestType(PacketType packetType, String requestName, int groupOrderID) {
        this.packetType = packetType;
        this.requestName = requestName;
        this.groupOrderID = groupOrderID;
    }

    public static com.solicionio.essential.response.enums.RequestType getRequestType(String requestName) {
        return Arrays.stream(com.solicionio.essential.response.enums.RequestType.values()).filter(type -> type.requestName.equalsIgnoreCase(requestName)).findFirst().orElse(null);
    }

    public static com.solicionio.essential.response.enums.RequestType getRequestType(int index) {
        return Arrays.stream(com.solicionio.essential.response.enums.RequestType.values()).filter(type -> type.ordinal() == index).findFirst().orElse(null);
    }

}
