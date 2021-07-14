package com.java8newfeature.datetimeapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SimpleDateFormat问题
 */
public class DateUtil {

    private static Map<String, SimpleDateFormat> ps = new HashMap<>();

    private static SimpleDateFormat getSdf1(String pattern) {
        SimpleDateFormat s = ps.get(pattern);
        if (s == null) {
            s = new SimpleDateFormat(pattern);
            ps.put(pattern, s);
        }
        return s;
    }

    public static String format(Date date, String pattern) {
        return getSdf1(pattern).format(date);
    }

    public static Date parse(String dateString, String pattern) throws ParseException {
        return getSdf1(pattern).parse(dateString);
    }

    public static void main(String[] args) throws ParseException {
        String dateStr1 = "2018-03-19 23:29:34";
        String dateStr2 = "2017-03-19";
        String pattern1 = "yyyy-MM-dd HH:mm:ss";
        String pattern2 = "yyyy-MM-dd";
        int fixedNum = 4;
        int threadNum = 9999;
        Runnable runnable = () -> {
            try {
                String resStr1 = DateUtil.format(DateUtil.parse(dateStr1, pattern1), pattern1);
                if (!dateStr1.equals(resStr1)) {
                    System.out.println("error：\t resStr1: " + resStr1 + " dateStr1: " + dateStr1);
                }
                String resStr2 = DateUtil.format(DateUtil.parse(dateStr2, pattern2), pattern2);
                if (!dateStr2.equals(resStr2)) {
                    System.out.println("error：\t resStr1: " + resStr2 + " dateStr2: " + dateStr2);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        };

        ExecutorService cachedPool = Executors.newCachedThreadPool();
        ExecutorService fixedPool = Executors.newFixedThreadPool(fixedNum);
        fixedPool.execute(() -> {
            for (int i = 0; i < threadNum; i++) {
                cachedPool.execute(runnable);
            }
        });
    }
}
