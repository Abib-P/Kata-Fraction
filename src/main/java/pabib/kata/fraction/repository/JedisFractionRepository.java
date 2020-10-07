package pabib.kata.fraction.repository;

import redis.clients.jedis.Jedis;

public class JedisFractionRepository {

    public static Jedis CreateJedis(){
        Jedis jedis = new Jedis("localhost");
        System.out.println("Connection Successful");
        System.out.println("The server is running " + jedis.ping());
        jedis.set("company-name", "500Rockets.io");
        System.out.println("Stored string in redis: "+ jedis.get("company-name"));
        return jedis;
    }

}
