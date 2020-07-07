package ru.vladigeras.deadlock;

public class Account {
	private int balance;

	public Account(int balance) {
		this.balance = balance;
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
}
