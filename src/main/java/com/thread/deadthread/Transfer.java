package com.thread.deadthread;

/**
 * @decription: 转账
 * @author: 180449
 * @date 2021/7/16 11:00
 */
public class Transfer {

    /**
     * 上面的代码看起来是没有问题的：锁定两个账户来判断余额是否充足才进行转账！
     *
     * 但是，同样有可能会发生死锁：
     *
     * 如果两个线程同时调用transferMoney()
     * 线程A从X账户向Y账户转账
     * 线程B从账户Y向账户X转账
     * 那么就会发生死锁。
     *
     */
    // 转账
    public static void transferMoney(Account fromAccount,
                                     Account toAccount,
                                     DollarAmount amount)
            throws Exception {

        // 锁定汇账账户
        synchronized (fromAccount) {
            // 锁定来账账户
            synchronized (toAccount) {

                // 判余额是否大于0
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    throw new Exception();
                } else {

                    // 汇账账户减钱
                    fromAccount.debit(amount);

                    // 来账账户增钱
                    toAccount.credit(amount);
                }
            }
        }
    }
}
