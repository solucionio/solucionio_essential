package com.solicionio.essential;

import com.solicionio.essential.adapters.PacketAdapter;
import com.solicionio.essential.database.Redis;
import com.solicionio.essential.netty.Netty;
import com.solicionio.essential.utils.UtilConsole;

public abstract class Essential {

    public static Netty netty;

    private static Redis redis;

    protected static void enableNetty(int port) {
        try {
            PacketAdapter.enable();
            netty = new Netty(port);
        } catch (Exception e) {
            UtilConsole.log("Netty sunucusu aktifleştirilemedi. " + e.getMessage());
        }
    }

    protected static void enableRedis(String host, int port, String password) {
        redis = new Redis(host, port, password);
    }

    public static Redis getRedis() {
        return redis;
    }

}