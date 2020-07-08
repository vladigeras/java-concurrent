package ru.vladigeras.callable;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author vladi_geras on 08.07.2020
 */
public class Transfer implements Callable<Boolean> {
	private static final int WAIT_SEC = 1;
	private final Account from;
	private final Account to;
	private final int amount;

	public Transfer(Account from, Account to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	@Override
	public Boolean call() throws Exception {
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
							Thread.sleep((new Random().nextInt(5) + 1) + 1000);
							System.out.println(String.format("Transfering %d from %s to %s end in %s", amount, from, to, Thread.currentThread().getName()));
						} finally {
							to.getLock().unlock();
							System.out.println(String.format("Release 2 lock %s", Thread.currentThread().getName()));
						}
					} else {
						System.out.println("Error waiting lock 2");
						to.incFailedTransferCount();
						return false;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					from.getLock().unlock();
					System.out.println(String.format("Release 1 lock %s", Thread.currentThread().getName()));
				}
			} else {
				System.out.println("Error waiting lock 1");
				from.incFailedTransferCount();
				return false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
}
