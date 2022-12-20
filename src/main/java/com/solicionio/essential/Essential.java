package com.solicionio.essential;

import com.solicionio.essential.adapters.PacketAdapter;
import com.solicionio.essential.database.Redis;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.solicionio.essential.netty.Netty;
import com.solicionio.essential.utils.UtilConsole;

@SpringBootApplication
public class Essential {

    public static Netty netty;

    private static Redis redis;

    public static void enable(){
        try {
            PacketAdapter.enable();
            redis = new Redis();
            netty = new Netty();
        } catch (Exception e) {
            UtilConsole.log("Netty sunucusu aktifleştirilemedi. " + e.getMessage());
        }
    }

    public static Redis getRedis() {
        return redis;
    }

}