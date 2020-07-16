package ru.vladigeras.cyclicBarrier;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

/**
 * @author vladi_geras on 14.07.2020
 */
public class Worker implements Callable<Integer> {
	private final int count;
	private final CyclicBarrier barrier;

	public Worker(int count, CyclicBarrier barrier) {
		this.count = count;
		this.barrier = barrier;
	}

	@Override
	public Integer call() throws Exception {
		System.out.println(String.format("%s start work", Thread.currentThread().getName()));
		int sum = 0;
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			Thread.sleep((random.nextInt(5) + 1) * 1000);
			sum += (i + 1);
		}
		System.out.println(String.format("%s end work and now waiting for the barrier", Thread.currentThread().getName()));
		barrier.await();
		System.out.println(String.format("%s successful waited for the barrier", Thread.currentThread().getName()));
		return sum;
	}
}
