package com.solicionio.essential.utils;

import com.solicionio.essential.Configuration;

public class UtilConsole {

    public static void log(String message){
        System.out.println(Configuration.PROJECT_NAME + " - " + message);
    }

}
