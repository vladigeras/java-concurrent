package ru.vladigeras.readWriteLock;

public class ReadWriteLockMain {
	public static void main(String[] args) {
		final Account a = new Account(1000);
		final Account b = new Account(2000);

		new Thread(() -> transfer(a, b, 500)).start();
		new Thread(() -> transfer(b, a, 300)).start();
	}

	static void transfer(Account from, Account to, int amount) {
		try {

			if (from.getBalance() < amount) {
				throw new RuntimeException("Insufficient funds");
			}

			System.out.println(String.format("Start transfer in %s", Thread.currentThread().getName()));
			Thread.sleep(1000);
			from.withdraw(amount);
			Thread.sleep(1000);
			to.deposit(amount);
			System.out.println(String.format("End transfer in %s", Thread.currentThread().getName()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
