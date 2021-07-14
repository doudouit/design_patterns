package com.java8newfeature.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @decription: Stream测试
 * @author: 180449
 * @date 2021/7/14 9:28
 */
public class Java8StreamTester1 {

    public static void main(String[] args) {

        /*
        stream() − 为集合创建串行流。
        parallelStream() − 为集合创建并行流。
        */
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        filtered.forEach(System.out::println);

        /*
        map 方法用于映射每个元素到对应的结果，以下代码片段使用 map 输出了元素对应的平方数：
        distinct()可以去重
         */
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 获取对应的平方数
        List<Integer> integerList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        integerList.forEach(System.out::println);


        /*
        filter 方法用于通过设置的条件过滤出元素。以下代码片段使用 filter 方法过滤出空字符串
        * */
        // 获取空字符串的数量
        long count = strings.stream().filter(String::isEmpty).count();
        System.out.println("字符串为空的数量: \t" + count);


        /*
          limit 方法用于获取指定数量的流。 以下代码片段使用 limit 方法打印出 10 条数据：
        */
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);

        /*
            sorted 方法用于对流进行排序。以下代码片段使用 sorted 方法对输出的 10 个随机数进行排序：
        */
        random.ints().limit(10).sorted().forEach(System.out::println);

        /*
            parallelStream 是流并行处理程序的代替方法。以下实例我们使用 parallelStream 来输出空字符串的数量：
         */
        // 获取空字符串的数量
        long count2 = strings.parallelStream().filter(String::isEmpty).count();
        System.out.println(count2);


        /*
            Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串
         */
        List<String> filtered4 = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println("筛选列表: " + filtered4);
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);

        /*
            产生统计结果的收集器
         */
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
    }
}
