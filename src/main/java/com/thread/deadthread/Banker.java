package com.thread.deadthread;

import java.util.Scanner;

/**
 * 银行家算法
 * java实现  https://blog.csdn.net/qq_41384351/article/details/90046955
 * 原理      https://baike.baidu.com/item/%E9%93%B6%E8%A1%8C%E5%AE%B6%E7%AE%97%E6%B3%95/1679781?fr=aladdin
 */
public class Banker {
    /**
     * 可利用的资源
     **/
    int[] available = new int[]{3, 3, 2};
    /**
     * 每个进程最大资源数
     **/
    int[][] max = new int[][]{{7, 5, 3}, {3, 2, 2}, {9, 0, 2}, {2, 2, 2}, {4, 3, 3}};
    /**
     * 每个进程目前拥有的资源数
     **/
    int[][] allocation = new int[][]{{0, 1, 0}, {2, 0, 0}, {3, 0, 2}, {2, 1, 1}, {0, 0, 2}};
    /**
     * 每个进程需要的资源数
     **/
    int[][] need = new int[][]{{7, 4, 3}, {1, 2, 2}, {6, 0, 0}, {0, 1, 1}, {4, 3, 1}};

    void showData() {
        //展示数据输出每个进程的相关数
        System.out.println("进程号   Max     All     Need   ");
        System.out.println("     A  B  C  A  B  C  A  B  C");
        for (int i = 0; i < 5; i++) {
            System.out.print(i + "    ");
            for (int m = 0; m < 3; m++) {
                System.out.print(max[i][m] + "  ");
            }
            for (int m = 0; m < 3; m++) {
                System.out.print(allocation[i][m] + "  ");
            }
            for (int m = 0; m < 3; m++) {
                System.out.print(need[i][m] + "  ");
            }
            System.out.println();
        }
    }

    boolean change(int inRequestNum, int inRequest[])//分配数据
    {
        int requestNum = inRequestNum;
        int request[] = inRequest;
        // for(int i=0;i<3;i++)System.out.println("修改前available"+available[i]);
        if (!(request[0] <= need[requestNum][0] && request[1] <= need[requestNum][1] && request[2] <= need[requestNum][2])) {
            //request[0]<=need[requestNum][0]
            //request[1]<=need[requestNum][1]
            //request[2]<=need[requestNum][2]
            //每一类请求资源小于当前线程need的资源数
            System.out.println("请求的资源数超过了所需要的最大值，分配错误");
            return false;
        }
        if ((request[0] <= available[0] && request[1] <= available[1] && request[2] <= available[2]) == false) {
            //当前线程的每一类请求资源小于等于资源池对应资源的数量
            System.out.println("尚无足够资源分配，必须等待");
            return false;
        }

        //试分配数据给请求的线程
        for (int i = 0; i < 3; i++) {
            available[i] = available[i] - request[i];
            //资源池的每类资源减去每类请求资源数量
            allocation[requestNum][i] = allocation[requestNum][i] + request[i];
            //当前线程allocation中每类资源加上每类资源请求数量
            need[requestNum][i] = need[requestNum][i] - request[i];
            //当前线程need中每类资源数量减去每类资源的请求数量
        }
        // for(int i=0;i<3;i++)System.out.println("修改后available"+available[i]);
        //进行安全性检查并返回是否安全
        boolean flag = checkSafe(available[0], available[1], available[2]);
        // System.out.println("安全性检查后"+flag);
        if (flag == true) {
            System.out.println("能够安全分配");
            return true;
        } else//不能通过安全性检查 恢复到未分配前的数据
        {
            System.out.println("不能够安全分配");
            for (int i = 0; i < 3; i++) {
                available[i] = available[i] + request[i];
                allocation[requestNum][i] = allocation[requestNum][i] - request[i];
                need[requestNum][i] = need[requestNum][i] + request[i];
            }
            return false;
        }
    }

    boolean checkSafe(int a, int b, int c)//安全性检查
    {
        int work[] = new int[3];
        work[0] = a;
        work[1] = b;
        work[2] = c;
        int i = 0;
        boolean finish[] = new boolean[5];
        //寻找一个能够满足的认为完成后才去执行下一进程
        while (i < 5) {
            //找到满足的修改work值，然后i=0，重新从开始的为分配的中寻找
            if (finish[i] == false && need[i][0] <= work[0] && need[i][1] <= work[1] && need[i][2] <= work[2]) {
                System.out.println("分配成功的是" + i);
                for (int m = 0; m < 3; m++) {
                    work[m] = work[m] + allocation[i][m];
                }
                finish[i] = true;
                i = 0;
            } else//如果没有找到直接i++
            {
                i++;
            }
        }
        //通过finish数组判断是否都可以分配
        for (i = 0; i < 5; i++) {
            if (finish[i] == false) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Banker bank = new Banker();
        bank.showData();
        //请求线程资源存放的数组
        int request[] = new int[3];
        int requestNum;
        String source[] = new String[]{"A", "B", "C"};
        Scanner s = new Scanner(System.in);
        String choice = new String();
        //循环进行分配
        while (true) {
            System.out.println("请输入要请求的进程号（0--4）：");
            requestNum = s.nextInt();
            System.out.print("请输入请求的资源数目");
            for (int i = 0; i < 3; i++) {
                System.out.println(source[i] + "资源的数目：");
                request[i] = s.nextInt();
            }
            bank.change(requestNum, request);
            System.out.println("是否再请求分配(y/n)");
            choice = s.next();
            if ("n".equals(choice)) {
                break;
            }
        }
    }
}
