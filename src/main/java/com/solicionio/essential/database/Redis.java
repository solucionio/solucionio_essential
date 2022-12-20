package com.solicionio.essential.database;

import com.solicionio.essential.Configuration;
import com.solicionio.essential.utils.UtilConsole;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class Redis {

    private Jedis jedis;

    public Redis(String host, int port, String password) {
        try {
            jedis = new Jedis(host, port);
            if(password != null) jedis.auth(password);

            UtilConsole.log("Redis bağlantısı kuruldu!");
        } catch (JedisException jedisException) {
            UtilConsole.log("Redis bağlantısı kurulamadı. " + jedisException.getMessage());
        }
    }

    public Jedis getConnection() {
        return this.jedis;
    }

}
