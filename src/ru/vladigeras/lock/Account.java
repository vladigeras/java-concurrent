package ru.vladigeras.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	private final Lock lock;
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

	public int getBalance() {
		return balance;
	}

	public Lock getLock() {
		return lock;
	}
}
