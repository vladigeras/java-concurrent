package ru.vladigeras.deadlock;

public class Main {

    public static void main(String[] args) {
        final Account a = new Account(1000);
        final Account b = new Account(2000);

        new Thread(() -> {
            transfer(a, b, 500);
        }).start();

        new Thread(() -> {
            transfer(b, a, 300);
        }).start();
    }

    static void transfer(Account from, Account to, int amount) {
        System.out.println(String.format("Transfering %d from %s to %s start in %s", amount, from, to, Thread.currentThread().getName()));
        if (from.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }
        System.out.println(String.format("Before 1 lock %s", Thread.currentThread().getName()));
        synchronized (from) {
            System.out.println(String.format("After 1 lock %s", Thread.currentThread().getName()));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("Before 2 lock %s", Thread.currentThread().getName()));
            synchronized (to) {
                System.out.println(String.format("After 2 lock %s", Thread.currentThread().getName()));
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println(String.format("Transfering %d from %s to %s end in %s", amount, from, to, Thread.currentThread().getName()));
            }
            System.out.println(String.format("Release 2 lock %s", Thread.currentThread().getName()));
        }
        System.out.println(String.format("Release 1 lock %s", Thread.currentThread().getName()));
    }
}
