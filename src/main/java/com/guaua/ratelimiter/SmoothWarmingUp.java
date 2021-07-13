package com.guaua.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @decription: 平滑预热限流
 * @author: 180449
 * @date 2021/7/12 10:59
 *
 * RateLimiter的SmoothWarmingUp是带有预热期的平滑限流，它启动后会有一段预热期，逐步将分发频率提升到配置的速率。
 */
public class SmoothWarmingUp {

    public static void main(String[] args) {
        testSmoothwarmingUp();
    }

    /**
     * 创建一个平均分发令牌速率为2，预热期为3分钟。
     * 由于设置了预热时间是3秒，令牌桶一开始并不会0.5秒发一个令牌，而是形成一个平滑线性下降的坡度，频率越来越高，
     * 在3秒钟之内达到原本设置的频率，以后就以固定的频率输出。
     * 这种功能适合系统刚启动需要一点时间来“热身”的场景。
     */
    public static void testSmoothwarmingUp() {
        RateLimiter r = RateLimiter.create(2, 3, TimeUnit.SECONDS);
        while (true)
        {
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("end");
            /*
             * output:
             * get 1 tokens: 0.0s
             * get 1 tokens: 1.329289s
             * get 1 tokens: 0.994375s
             * get 1 tokens: 0.662888s  上边三次获取的时间相加正好为3秒
             * end
             * get 1 tokens: 0.49764s  正常速率0.5秒一个令牌
             * get 1 tokens: 0.497828s
             * get 1 tokens: 0.49449s
             * get 1 tokens: 0.497522s
             */
        }
    }
}
