package com.solicionio.essential.adapters;

import com.solicionio.essential.response.enums.RequestType;
import com.solicionio.essential.response.modules.RequestModule;
import com.solicionio.essential.utils.UtilConsole;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RequestAdapter {

    public static ConcurrentHashMap<String, RequestModule> moduleAdapter = new ConcurrentHashMap<>();

    public static void registerModules(List<RequestModule> requestModuleList) {
        for(RequestModule module : requestModuleList) {
            moduleAdapter.put(module.getCallName(), module);
        }
    }

    public static RequestModule getModule(String moduleName) {
        return moduleAdapter.getOrDefault(moduleName, null);
    }

}
