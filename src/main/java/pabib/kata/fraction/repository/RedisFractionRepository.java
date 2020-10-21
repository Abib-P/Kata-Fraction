package pabib.kata.fraction.repository;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import pabib.kata.fraction.core.Fraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

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
        RBucket<Fraction> bucket = redissonClient.getBucket(format("fraction_%d", fraction.hashCode()));
        bucket.set(fraction);
        Fraction fraction1 = bucket.get();
        return fraction1.hashCode();
    }

    @Override
    public boolean remove(int id) {
        // id+1 because the id is an id-1 (because of Fraction in memory)
        redissonClient.getKeys().deleteByPattern(format("fraction_%d", id));
        return true;
    }

    @Override
    public List<FractionEntity> findAll() {
        List<String> fractionId = redissonClient.getKeys().getKeysStreamByPattern("fraction_*").collect(Collectors.toList());
        List<FractionEntity> returnedFractionEntities = new ArrayList<>();

        for (String s : fractionId) {
            final var fraction = redissonClient.<Fraction>getBucket(s).get();
            returnedFractionEntities.add(new FractionEntity(fraction.hashCode(), fraction));
        }
        return returnedFractionEntities;
    }

    @Override
    public Optional<Fraction> find(int id) {
        RBucket<Fraction> bucket = redissonClient.getBucket(format("fraction_%d", id));

        return Optional.of(bucket)
                .filter(RBucket::isExists)
                .map(RBucket::get);
    }

    @Override
    public boolean isEmpty() {
        return redissonClient
                .getKeys()
                .getKeysStreamByPattern("fraction_*")
                .count() == 0;
    }
}
