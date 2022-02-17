package com.test.shutdown;

/**
 * java实现优雅停机： 可以让方法执行完
 * 中断信号：
 * <p>
 * 程序正常退出
 * 使用System.exit()
 * 终端使用Ctrl+C触发的中断
 * 系统关闭
 * 使用Kill pid命令干掉进程
 */
public class ShutdownHook extends Thread {
    private Thread mainThread;
    private boolean shutDownSignalReceived;

    @Override
    public void run() {
        System.out.println("Shut down signal received.");
        this.shutDownSignalReceived = true;
        mainThread.interrupt();
        try {
            mainThread.join(); //当收到停止信号时，等待mainThread的执行完成
        } catch (InterruptedException e) {
        }
        System.out.println("Shut down complete.");
    }

    public ShutdownHook(Thread mainThread) {
        super();
        this.mainThread = mainThread;
        this.shutDownSignalReceived = false;
        Runtime.getRuntime().addShutdownHook(this);
    }

    public boolean shouldShutDown() {
        return shutDownSignalReceived;
    }

}
