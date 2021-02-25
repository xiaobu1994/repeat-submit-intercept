package com.xiaobu.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaobu
 * @version JDK1.8.0_171
 * @date on  2020/7/8 10:33
 * @description
 */

@Component
@Slf4j
public class RedissonUtils {

    private static void trylock() {
        // 1. 配置文件
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setPassword("xiaobu1994")
                .setDatabase(0);
        //2. 构造RedissonClient
        RedissonClient redissonClient = Redisson.create(config);

        //3. 设置锁定资源名称
        RLock lock = redissonClient.getLock("redlock");

        boolean isLock;
        try {
            isLock = lock.tryLock(500, 30000, TimeUnit.MILLISECONDS);
            if (isLock) {
                //TODO 成功获取到锁 执行业务逻辑
                log.info("获取到锁，执行相应的业务逻辑");
                TimeUnit.SECONDS.sleep(15);
            }
        } catch (InterruptedException e) {
            //TODO
        } finally {
            //解锁操作
            lock.unlock();
            log.info("释放锁");
        }
    }

    public static void main(String[] args) {
        trylock();
        System.exit(0);
    }

    public static void lock() {
        // 1. 配置文件
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setPassword("xiaobu1994")
                .setDatabase(0);
        //2. 构造RedissonClient
        RedissonClient redissonClient = Redisson.create(config);

        //3. 设置锁定资源名称
        RLock lock = redissonClient.getLock("redlock");
        lock.lock();
        try {
            log.info("获取锁成功，实现业务逻辑");
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            log.info("释放锁");
        }

    }
}