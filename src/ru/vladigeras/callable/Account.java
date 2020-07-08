package ru.vladigeras.callable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	private final Lock lock;
	private final AtomicInteger failCount = new AtomicInteger();
	private int balance;

	public Account(int balance) {
		this.balance = balance;
		lock = new ReentrantLock();
	}

	public void withdraw(int amount) {
		balance -= amount;
	}

	public void deposit(int amount) {
		balance += amount;
	}

	public void incFailedTransferCount() {
		failCount.incrementAndGet();
	}

	public int getBalance() {
		return balance;
	}

	public Lock getLock() {
		return lock;
	}

	public int getFailCount() {
		return failCount.get();
	}
}
