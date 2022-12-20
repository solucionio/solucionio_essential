package com.solicionio.essential.adapters;

import com.solicionio.essential.response.enums.RequestType;
import com.solicionio.essential.response.modules.RequestModule;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RequestAdapter {

    public static List<RequestModule> moduleList= null;

    public static ConcurrentHashMap<RequestType, RequestModule> moduleAdapter = new ConcurrentHashMap<>();

    public static void registerModules() {
        for(RequestModule module : moduleList) {
            moduleAdapter.put(module.getRequestType(), module);
        }
    }

    public static RequestModule getModule(RequestType requestType) {
        return moduleAdapter.getOrDefault(requestType, null);
    }

}
