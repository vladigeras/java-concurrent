package ru.vladigeras.countDownLatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CountDownLatchMain {
	public static void main(String[] args) {
		int latchCount = 4;
		CountDownLatch latch = new CountDownLatch(latchCount);

		ExecutorService executorService = Executors.newFixedThreadPool(latchCount);
		List<Future<Integer>> tasks = new ArrayList<>();
		Random random = new Random();
		System.out.println(String.format("Before initializing pool of %d workers", latchCount));
		for (int i = 0; i < latchCount; i++) {
			int incCount = random.nextInt(7) + 1;
			tasks.add(executorService.submit(new Worker(incCount, latch)));
			System.out.println(String.format("New task was pushed to pool [latchCount = %d]", incCount));
			latch.countDown();
			System.out.println(String.format("Latch was invoked. Still left %d workers on latch", latch.getCount()));
		}
		System.out.println(String.format("After initializing pool of %d workers", latchCount));

		try {
			executorService.shutdown();
			for (Future<Integer> task : tasks) {
				System.out.println(String.format("Task finished with %d", task.get()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
