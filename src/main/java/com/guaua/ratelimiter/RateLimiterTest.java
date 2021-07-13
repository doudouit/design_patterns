package com.guaua.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @decription: Google ratelimiter测试
 * @author: 180449
 * @date 2021/7/12 10:34
 *
 * 主要目的是平滑流出速率
 * RateLimiter允许某次请求获取超出剩余令牌数的令牌，但是下一次请求将为此付出代价
 */
public class RateLimiterTest {
    // 每秒钟产生一个令牌
    private final static RateLimiter rateLimiter = RateLimiter.create(2);

    public static void main(String[] args) {
        /*ExecutorService executorService = Executors.newFixedThreadPool(5);
        IntStream.range(0, 5).forEach(i -> executorService.submit(RateLimiterTest::testLimiter));
        executorService.shutdown();*/

        testLimiter2();
    }

    private static void testLimiter() {
        System.out.println(Thread.currentThread() + " waiting " + rateLimiter.acquire());
    }

    /**
     * 设置等待令牌的超时时间，如果等待令牌的时间大于超时时间，将直接返回false，不再等待：
     */
    private static void testLimiter2() {
        System.out.println(rateLimiter.acquire(2));
        System.out.println(rateLimiter.tryAcquire(1, 2, TimeUnit.SECONDS));
    }
}
