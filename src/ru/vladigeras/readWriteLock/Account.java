package ru.vladigeras.readWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Account {
	private final Lock readLock;
	private final Lock writeLock;

	private int balance;

	public Account(int balance) {
		this.balance = balance;

		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		readLock = readWriteLock.readLock();
		writeLock = readWriteLock.writeLock();
	}

	public void withdraw(int amount) {
		System.out.println(String.format("Before WRITE lock (withdraw) in %s", Thread.currentThread().getName()));
		writeLock.lock();
		try {
			balance -= amount;
			System.out.println(String.format("Balance is %d in %s", balance, Thread.currentThread().getName()));
		} finally {
			writeLock.unlock();
			System.out.println(String.format("After WRITE unlock (withdraw) in %s", Thread.currentThread().getName()));
		}
	}

	public void deposit(int amount) {
		System.out.println(String.format("Before WRITE lock (deposit) in %s", Thread.currentThread().getName()));
		writeLock.lock();
		try {
			balance += amount;
			System.out.println(String.format("Balance is %d in %s", balance, Thread.currentThread().getName()));
		} finally {
			writeLock.unlock();
			System.out.println(String.format("After WRITE unlock (deposit) in %s", Thread.currentThread().getName()));
		}
	}

	public int getBalance() {
		System.out.println(String.format("Before READ lock in %s", Thread.currentThread().getName()));
		readLock.lock();
		try {
			System.out.println(String.format("Balance is %d in %s", balance, Thread.currentThread().getName()));
			return balance;
		} finally {
			readLock.unlock();
			System.out.println(String.format("After READ unlock in %s", Thread.currentThread().getName()));
		}
	}
}
