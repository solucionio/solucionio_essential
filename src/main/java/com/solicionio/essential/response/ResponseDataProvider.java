package com.solicionio.essential.response;

import com.solicionio.essential.response.enums.ResponseDataType;
import com.solicionio.essential.response.handlers.IMessageHandler;
import com.solicionio.essential.response.modules.ResponseData;
import com.solicionio.essential.utils.UtilJSON;
import com.google.gson.JsonParser;
import org.json.JSONObject;

public class ResponseDataProvider {

    private static JSONObject convertToData(int statusCode, boolean error, ResponseDataType responseDataType, Object data) {
        if(data instanceof JSONObject) data = new JsonParser().parse(data.toString()).getAsJsonObject();
        return UtilJSON.convertFromModel(new ResponseData(statusCode, error, responseDataType.name(), data));
    }

    public static JSONObject successData(Object data) {
        return convertToData(1, false, ResponseDataType.DATA, data);
    }

    public static JSONObject errorData(Object data) {
        return convertToData(-1, true, ResponseDataType.DATA, data);
    }

    public static JSONObject constantMessage(IMessageHandler message) {
        return convertToData(message.getStatusCode(), message.isError(), ResponseDataType.MESSAGE, message.getMessage());
    }

}
