package com.thread.deadthread;

/**
 * 得到对应的hash值来固定加锁的顺序
 */
public class InduceLockOrder {

    // 额外的锁、避免两个对象hash值相等的情况(即使很少)
    private static final Object tieLock = new Object();

    public void transferMoney(final Account fromAcct,
                              final Account toAcct,
                              final DollarAmount amount)
            throws Exception {
        class Helper {
            public void transfer() throws Exception {
                if (fromAcct.getBalance().compareTo(amount) < 0)
                    throw new Exception();
                else {
                    fromAcct.debit(amount);
                    toAcct.credit(amount);
                }
            }
        }
        // 得到锁的hash值
        int fromHash = System.identityHashCode(fromAcct);
        int toHash = System.identityHashCode(toAcct);

        // 根据hash值来上锁
        if (fromHash < toHash) {
            synchronized (fromAcct) {
                synchronized (toAcct) {
                    new Helper().transfer();
                }
            }

        } else if (fromHash > toHash) {// 根据hash值来上锁
            synchronized (toAcct) {
                synchronized (fromAcct) {
                    new Helper().transfer();
                }
            }
        } else {// 额外的锁、避免两个对象hash值相等的情况(即使很少)
            synchronized (tieLock) {
                synchronized (fromAcct) {
                    synchronized (toAcct) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }
}