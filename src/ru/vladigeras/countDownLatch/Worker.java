package ru.vladigeras.countDownLatch;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author vladi_geras on 14.07.2020
 */
public class Worker implements Callable<Integer> {
	private final int count;
	private final CountDownLatch latch;

	public Worker(int count, CountDownLatch latch) {
		this.count = count;
		this.latch = latch;
	}

	@Override
	public Integer call() throws Exception {
		System.out.println(String.format("%s await work", Thread.currentThread().getName()));
		latch.await();
		System.out.println(String.format("%s start work", Thread.currentThread().getName()));
		int sum = 0;
		for (int i = 0; i < count; i++) {
			Thread.sleep(1000);
			sum += (i + 1);
		}
		System.out.println(String.format("%s end work", Thread.currentThread().getName()));
		return sum;
	}
}
