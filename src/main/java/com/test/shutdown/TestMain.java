package com.test.shutdown;

public class TestMain {
    private ShutdownHook shutdownHook;

    public static void main(String[] args) {
        TestMain app = new TestMain();
        System.out.println("Hello World!");
        app.execute();
        System.out.println("End of main()");
    }

    public TestMain() {
        this.shutdownHook = new ShutdownHook(Thread.currentThread());
    }

    public void execute() {
        while (!shutdownHook.shouldShutDown()) {
            System.out.println("I am sleep");
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                System.out.println("execute() interrupted");
            }
            System.out.println("I am not sleep");
        }

        try {
            // 模拟实际业务代码
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end of execute()");
    }
}
