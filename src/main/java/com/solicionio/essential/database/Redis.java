package com.solicionio.essential.database;

import com.solicionio.essential.Configuration;
import com.solicionio.essential.utils.UtilConsole;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class Redis {

    private Jedis jedis;

    public Redis() {
        if(createConnection()) UtilConsole.log("Redis bağlantısı kuruldu!");
    }

    public synchronized Jedis getConnection() {
        return this.jedis;
    }

    private boolean createConnection() {
        try {
            jedis = new Jedis(Configuration.REDIS_HOST, Configuration.REDIS_PORT);
            return true;
        } catch (JedisException jedisException) {
            UtilConsole.log("Redis bağlantısı kurulamadı. " + jedisException.getMessage());
        }
        return false;
    }

}
