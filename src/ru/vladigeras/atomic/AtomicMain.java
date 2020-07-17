package ru.vladigeras.atomic;

import java.util.concurrent.TimeUnit;

public class AtomicMain {
	private static final int WAIT_SEC = 1;

	public static void main(String[] args) {
		final Account a = new Account(1000);
		final Account b = new Account(2000);

		new Thread(() -> transfer(a, b, 500)).start();
		new Thread(() -> transfer(b, a, 300)).start();
	}

	static void transfer(Account from, Account to, int amount) {
		System.out.println(String.format("Transfering %d from %s to %s start in %s", amount, from, to, Thread.currentThread().getName()));

		System.out.println(String.format("Before 1 lock %s", Thread.currentThread().getName()));
		try {
			if (from.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
				if (from.getBalance() < amount) {
					throw new RuntimeException("Insufficient funds");
				}
				System.out.println(String.format("After 1 lock %s", Thread.currentThread().getName()));

				Thread.sleep(1000);
				try {
					System.out.println(String.format("Before 2 lock %s", Thread.currentThread().getName()));
					if (to.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
						try {
							System.out.println(String.format("After 2 lock %s", Thread.currentThread().getName()));
							from.withdraw(amount);
							to.deposit(amount);
							System.out.println(String.format("Transfering %d from %s to %s end in %s", amount, from, to, Thread.currentThread().getName()));
						} finally {
							to.getLock().unlock();
							System.out.println(String.format("Release 2 lock %s", Thread.currentThread().getName()));
						}
					}
				} catch (InterruptedException e) {
					to.incFailedTransferCount();
					System.out.println(String.format("Current failed transfer of account 2 is %s", to.getFailCount()));
				} finally {
					from.getLock().unlock();
					System.out.println(String.format("Release 1 lock %s", Thread.currentThread().getName()));
				}
			} else {
				from.incFailedTransferCount();
				System.out.println(String.format("Current failed transfer of account 1 is %s", from.getFailCount()));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
