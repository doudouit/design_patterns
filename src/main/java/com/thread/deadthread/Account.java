package com.thread.deadthread;

class Account implements Comparable {

    private Account balance = new Account();

    public Account getBalance() {
        return balance;
    }

    public void debit(DollarAmount amount) {
    }

    public void credit(DollarAmount amount) {
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

