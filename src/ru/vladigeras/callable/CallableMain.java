package ru.vladigeras.callable;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CallableMain {
	public static void main(String[] args) throws InterruptedException {
		final Account a = new Account(1000);
		final Account b = new Account(2000);

		ScheduledExecutorService scheduleService = Executors.newSingleThreadScheduledExecutor();
		scheduleService.scheduleAtFixedRate(() -> {
			System.out.println(String.format("A: %d", a.getFailCount()));
			System.out.println(String.format("B: %d", b.getFailCount()));
		}, 1L, 1L, TimeUnit.SECONDS);

		ExecutorService executorService = Executors.newFixedThreadPool(3);
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			executorService.submit(new Transfer(a, b, random.nextInt(400)));
		}

		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.SECONDS);
	}
}
