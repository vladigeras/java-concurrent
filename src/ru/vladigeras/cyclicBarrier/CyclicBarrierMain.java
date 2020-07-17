package ru.vladigeras.cyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CyclicBarrierMain {
	public static void main(String[] args) {
		int parties = 4;
		CyclicBarrier barrier = new CyclicBarrier(parties);

		ExecutorService executorService = Executors.newFixedThreadPool(parties);
		List<Future<Integer>> tasks = new ArrayList<>();
		Random random = new Random();
		System.out.println(String.format("Before initializing pool of %d workers", parties));
		for (int i = 0; i < parties; i++) {
			int incCount = random.nextInt(7) + 1;
			tasks.add(executorService.submit(new Worker(incCount, barrier)));
			System.out.println(String.format("New task was pushed to pool [count = %d]", incCount));
		}
		System.out.println(String.format("After initializing pool of %d workers", parties));

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
