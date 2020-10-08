package pabib.kata.fraction.repository;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import pabib.kata.fraction.core.Fraction;

import java.util.List;
import java.util.Optional;

public class RedisFractionRepository implements FractionRepository{

    private final RedissonClient redissonClient;

    private RedisFractionRepository(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public static RedisFractionRepository createDefault(){
        return new RedisFractionRepository(createRedissonClient());
    }

    private static RedissonClient createRedissonClient() {
        final Config config = new Config();
        final SentinelServersConfig sentinelServersConfig = config.useSentinelServers();

        sentinelServersConfig
                .setCheckSentinelsList(false)
                .setMasterName("myLocalMaster")
                .addSentinelAddress("redis://127.0.0.1:26379")
                .setTimeout(1000)
                .setConnectTimeout(5000)
                .setPassword("dev");

        return Redisson.create(config);
    }

    @Override
    public int add(Fraction fraction) {
        RBucket<Fraction> bucket = redissonClient.getBucket(String.valueOf(fraction.hashCode()));
        bucket.set(fraction);
        Fraction fraction1 = bucket.get();
        System.out.println(fraction1.getNumerator()+" / "+ fraction1.getDenominator());
        return fraction1.hashCode();
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public List<Fraction> findAll() {
        return null;
    }

    @Override
    public Optional<Fraction> find(int id) {
        return Optional.empty();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
