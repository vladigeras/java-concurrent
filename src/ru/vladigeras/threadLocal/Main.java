package ru.vladigeras.threadLocal;

public class Main {
	public static void main(String[] args) {
		try {
			new Thread(() -> SecurityContextHolder.setCurrentUser(new User(1L, "Ivan"))).start();
			Thread.sleep(2000L);
			new Thread(() -> SecurityContextHolder.setCurrentUser(new User(2L, "Sergey"))).start();
			Thread.sleep(2000L);
			new Thread(() -> SecurityContextHolder.setCurrentUser(new User(3L, "Fedor"))).start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
